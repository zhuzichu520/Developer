package com.chuzi.android.mvvm.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.core.os.bundleOf
import androidx.core.view.LayoutInflaterCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.launcher.ARouter
import com.chuzi.android.libs.tool.closeKeyboard
import com.chuzi.android.libs.tool.decodeBase64
import com.chuzi.android.libs.tool.json2Object
import com.chuzi.android.libs.tool.toCast
import com.chuzi.android.mvvm.Mvvm
import com.chuzi.android.mvvm.Mvvm.KEY_ARG
import com.chuzi.android.mvvm.Mvvm.KEY_ARG_JSON
import com.chuzi.android.widget.dialog.loading.LoadingMaker
import com.chuzi.android.widget.toast.toast
import com.qmuiteam.qmui.skin.QMUISkinLayoutInflaterFactory
import com.qmuiteam.qmui.skin.QMUISkinManager
import java.lang.reflect.ParameterizedType


/**
 * desc DilaogFragment基类
 * author: 朱子楚
 * time: 2020/4/9 4:06 PM
 * since: v 1.0.0
 */
abstract class BaseDialogFragment<TBinding : ViewDataBinding, TViewModel : BaseViewModel<TArg>, TArg : BaseArg> :
    AppCompatDialogFragment(), IBaseView<TArg>, IBaseCommon {

    /**
     * 皮肤Manager
     */
    private lateinit var skinManager: QMUISkinManager

    /**
     * 根View
     */
    lateinit var root: View

    /**
     * 页面ViewDataBinding对象
     */
    lateinit var binding: TBinding

    /**
     * 页面参数
     */
    lateinit var arg: TArg

    /**
     * 页面的ViewModel
     */
    lateinit var viewModel: TViewModel

    /**
     * 布局id
     */
    abstract fun setLayoutId(): Int

    /**
     * 页面ViewModel的Id
     */
    abstract fun bindVariableId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //解析页面参数
        parseArg()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        LayoutInflaterCompat.setFactory2(
            layoutInflater,
            QMUISkinLayoutInflaterFactory(requireActivity(), inflater)
        )
        binding = DataBindingUtil.inflate(
            inflater,
            setLayoutId(),
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        skinManager = QMUISkinManager.defaultInstance(requireContext())
        initViewDataBinding()
        registUIChangeLiveDataCallback()
        initVariable()
        initArgs(arg)
        initView()
        initListener()
        initViewObservable()
        initData()
    }

    /**
     * 解析页面参数
     */
    private fun parseArg() {
        val type = this::class.java.genericSuperclass
        val argClass = if (type is ParameterizedType) {
            type.actualTypeArguments[2]
        } else {
            ArgDefault::class.java
        }

        val argJson = arguments?.getString(KEY_ARG_JSON)
        arg = if (argJson.isNullOrEmpty()) {
            (arguments?.get(KEY_ARG) ?: ArgDefault()).toCast()
        } else {
            json2Object(decodeBase64(argJson), argClass) ?: ArgDefault().toCast()
        }
    }

    /**
     * 初始化ViewDataBinding
     */
    private fun initViewDataBinding() {
        val type = this::class.java.genericSuperclass
        val modelClass = if (type is ParameterizedType) {
            type.actualTypeArguments[1]
        } else {
            BaseViewModel::class.java
        }
        viewModel = ViewModelProvider(this).get(modelClass.toCast())
        viewModel.arg = arg
        viewLifecycleOwner.lifecycle.addObserver(viewModel)
        binding.setVariable(bindVariableId(), viewModel)
        binding.lifecycleOwner = viewLifecycleOwner
    }

    /**
     * 注册页面事件
     */
    private fun registUIChangeLiveDataCallback() {

        viewModel.onNavigateEvent.observe(viewLifecycleOwner) {
            ARouter.getInstance()
                .build(it.route)
                .withTransition(Mvvm.transitionConfig.enter, Mvvm.transitionConfig.exit)
                .with(bundleOf(KEY_ARG to it.arg))
                .navigation(requireContext())
        }

        //销毁Activity
        viewModel.onFinishEvent.observe(viewLifecycleOwner) {
            requireView().post {
                requireActivity().finish()
            }
        }

        //页面返回事件
        viewModel.onBackPressedEvent.observe(viewLifecycleOwner) {
            requireView().post {
                requireActivity().onBackPressed()
            }
        }

        //显示loading事件
        viewModel.onShowLoadingEvent.observe(viewLifecycleOwner) {
            requireView().post {
                closeKeyboard(requireActivity())
                LoadingMaker.showLoadingDialog(requireActivity(), Mvvm.loadingLayoutId)
            }
        }

        //隐藏loading事件
        viewModel.onHideLoadingEvent.observe(viewLifecycleOwner) {
            requireView().post {
                LoadingMaker.dismissLodingDialog()
            }
        }

        //吐司
        viewModel.onToastIntEvent.observe(viewLifecycleOwner) {
            toast(requireContext(), it)
        }

        //吐司
        viewModel.onToastStringEvent.observe(viewLifecycleOwner) {
            toast(requireContext(), it)
        }

    }

    override fun onStart() {
        super.onStart()
        skinManager.register(this)
    }

    override fun onResume() {
        super.onResume()
        if (!viewModel.isInitLazy) {
            initLazyData()
            viewModel.isInitLazy = true
        }
    }

    override fun onStop() {
        super.onStop()
        skinManager.unRegister(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.unbind()
    }

    /**
     * 返回
     */
    override fun back() {
        viewModel.back()
    }

    /**
     * 显示Loading
     */
    override fun showLoading() {
        viewModel.showLoading()
    }

    /**
     * 隐藏Loading
     */
    override fun hideLoading() {
        viewModel.hideLoading()
    }

    /**
     * 路由跳转
     */
    override fun navigate(route: String, arg: BaseArg?, isPop: Boolean?) {
        viewModel.navigate(route, arg, isPop)
    }

    /**
     * 销毁activity
     */
    override fun finish() {
        viewModel.finish()
    }

    /**
     * 初始化参数
     */
    override fun initArgs(arg: TArg) {
        viewModel.initArgs(arg)
    }

    /**
     * 初始化监听事件
     */
    override fun initViewObservable() {
        viewModel.initViewObservable()
    }

    /**
     * 初始化databinding参数
     */
    override fun initVariable() {
        viewModel.initVariable()
    }

    /**
     * 初始化View
     */
    override fun initView() {
        viewModel.initView()
    }

    /**
     * 初始化数据
     */
    override fun initData() {
        viewModel.initData()
    }

    /**
     * 懒加载初始化数据 在onResume 中回调， 比如ViewPage
     */
    override fun initLazyData() {
        viewModel.initLazyData()
    }

    /**
     * 初始化监听事件
     */
    override fun initListener() {
        viewModel.initListener()
    }

    /**
     * 吐司
     */
    override fun toast(textId: Int) {
        viewModel.toast(textId)
    }

    /**
     * 吐司
     */
    override fun toast(text: String) {
        viewModel.toast(text)
    }

}