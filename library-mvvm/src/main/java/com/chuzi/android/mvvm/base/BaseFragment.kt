package com.chuzi.android.mvvm.base

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat.finishAfterTransition
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.enums.RouteType
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
import com.qmuiteam.qmui.arch.QMUIFragment
import java.lang.reflect.ParameterizedType

/**
 * desc Fragment基类
 * author: 朱子楚
 * time: 2020/4/9 4:06 PM
 * since: v 1.0.0
 */
abstract class BaseFragment<TBinding : ViewDataBinding, TViewModel : BaseViewModel<TArg>, TArg : BaseArg> :
    QMUIFragment(), IBaseView<TArg>, IBaseCommon {

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

    /**
     * 只执行一次
     */
    override fun onCreateView(): View? {
        parseArg()
        return binding.root.also {
            root = it
        }
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
     * 执行多次，返回之后也会执行
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewDataBinding()
        registUIChangeLiveDataCallback()
        initVariable()
        initViewObservable()
        if (!viewModel.isInitLazy) {
            initArgs(arg)
            initView()
            initListener()
            initData()
        }
    }

    /**
     * 初始化ViewDataBinding
     */
    private fun initViewDataBinding() {
        binding = DataBindingUtil.inflate(layoutInflater, setLayoutId(), null, false)
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
            val bundle = bundleOf(KEY_ARG to it.arg)
            val postcard = ARouter.getInstance().build(it.route)
                .with(bundle)
                .withTransition(Mvvm.transitionConfig.enter, Mvvm.transitionConfig.exit)
            postcard.navigation(requireContext())
            when (postcard.type) {
                RouteType.ACTIVITY -> {
                    if (it.isPop) {
                        finishAfterTransition(requireActivity())
                    }
                }
                RouteType.FRAGMENT -> {
                    val any = postcard.navigation()
                    (any as? QMUIFragment)?.let { fragment ->
                        if (it.isPop) {
                            startFragmentAndDestroyCurrent(fragment)
                        } else {
                            startFragment(fragment)
                        }
                    }
                    (any as? DialogFragment)?.let { dialogFragment ->
                        dialogFragment.arguments = bundle
                        dialogFragment.show(parentFragmentManager, any::class.simpleName)
                    }
                }
                else -> {

                }
            }
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

    override fun onResume() {
        super.onResume()
        if (!viewModel.isInitLazy) {
            initLazyData()
            viewModel.isInitLazy = true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
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

    /**
     * 初始化监听事件
     */
    override fun initListener() {
        viewModel.initListener()
    }

    open fun onNewIntent(intent: Intent?) {}

    override fun onFetchTransitionConfig(): TransitionConfig {
        return Mvvm.transitionConfig
    }

}