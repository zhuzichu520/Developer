package com.hiwitech.android.mvvm.base

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.AnimBuilder
import androidx.navigation.Navigator
import androidx.navigation.findNavController
import com.hiwitech.android.libs.internal.MainHandler.postDelayed
import com.hiwitech.android.libs.tool.closeKeyboard
import com.hiwitech.android.libs.tool.decodeBase64
import com.hiwitech.android.libs.tool.json2Object
import com.hiwitech.android.libs.tool.toCast
import com.hiwitech.android.mvvm.Mvvm.KEY_ARG
import com.hiwitech.android.mvvm.Mvvm.KEY_ARG_JSON
import com.hiwitech.android.mvvm.Mvvm.enterAnim
import com.hiwitech.android.mvvm.Mvvm.exitAnim
import com.hiwitech.android.mvvm.Mvvm.getDefaultNavOptions
import com.hiwitech.android.mvvm.R
import com.hiwitech.android.widget.dialog.loading.LoadingMaker
import dagger.android.support.DaggerAppCompatDialogFragment
import java.lang.reflect.ParameterizedType
import javax.inject.Inject


/**
 * desc DilaogFragment基类
 * author: 朱子楚
 * time: 2020/4/9 4:06 PM
 * since: v 1.0.0
 */
abstract class BaseDialogFragment<TBinding : ViewDataBinding, TViewModel : BaseViewModel<TArg>, TArg : BaseArg> :
    DaggerAppCompatDialogFragment(), IBaseView<TArg>, IBaseCommon {

    /**
     * 页面ViewDataBinding对象
     */
    var binding: TBinding? = null

    /**
     * 页面参数
     */
    lateinit var arg: TArg

    /**
     * 页面的ViewModel
     */
    lateinit var viewModel: TViewModel

    /**
     * Fragment的Activity
     */
    lateinit var activityCtx: Activity

    /**
     * 页面导航器
     */
    val navController by lazy { activityCtx.findNavController(R.id.delegate_container) }

    /**
     * ViewModel工厂类
     */
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    /**
     * 布局id
     */
    abstract fun setLayoutId(): Int

    /**
     * 页面ViewModel的Id
     */
    abstract fun bindVariableId(): Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            setLayoutId(),
            container,
            false
        )
        binding?.lifecycleOwner = this
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewDataBinding()
        registUIChangeLiveDataCallback()
        initVariable()
        initView()
        initListener()
        initViewObservable()
        initData()
        if (!viewModel.isInitData) {
            initOneObservable()
            initOneData()
            viewModel.isInitData = true
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
        viewModel = ViewModelProvider(this, viewModelFactory).get(modelClass.toCast())
        viewModel.arg = arg
        initArgs(arg)
        binding?.setVariable(bindVariableId(), viewModel)
        lifecycle.addObserver(viewModel)
    }

    /**
     * 注册页面事件
     */
    private fun registUIChangeLiveDataCallback() {
        //页面跳转事件
        viewModel.uc.onStartEvent.observe(viewLifecycleOwner, Observer { payload ->
            navController.currentDestination?.getAction(payload.actionId)?.let {
                navController.navigate(
                    payload.actionId,
                    bundleOf(KEY_ARG to payload.arg),
                    getDefaultNavOptions(
                        payload.popUpTo,
                        payload.inclusive,
                        payload.singleTop,
                        payload.animBuilder,
                        payload.arg.useSystemAnimation
                    ),
                    payload.extras
                )
            }
        })

        //Activity页面跳转
        viewModel.uc.onStartActivityEvent.observe(viewLifecycleOwner, Observer { payload ->
            val intent = Intent(context, payload.clazz)
            intent.putExtras(bundleOf(KEY_ARG to payload.arg))
            payload.options?.let {
                requireActivity().startActivity(intent, it)
            } ?: let {
                requireActivity().startActivity(intent)
            }
            if (false == payload.arg.useSystemAnimation) {
                requireActivity().overridePendingTransition(
                    payload.animBuilder?.enter ?: enterAnim,
                    payload.animBuilder?.exit ?: exitAnim
                )
            }
        })

        //销毁Activity
        viewModel.uc.onFinishEvent.observe(viewLifecycleOwner, Observer {
            requireActivity().finish()
        })

        //页面返回事件
        viewModel.uc.onBackPressedEvent.observe(viewLifecycleOwner, Observer {
            activityCtx.onBackPressed()
        })

        //显示loading事件
        viewModel.uc.onShowLoadingEvent.observe(viewLifecycleOwner, Observer {
            closeKeyboard(activityCtx)
            postDelayed {
                LoadingMaker.showLoadingDialog(activityCtx)
            }
        })

        //隐藏loading事件
        viewModel.uc.onHideLoadingEvent.observe(viewLifecycleOwner, Observer {
            postDelayed {
                LoadingMaker.dismissLodingDialog()
            }
        })

    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        activityCtx = requireActivity()
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
        binding?.unbind()
        binding = null
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
     * 页面跳转
     * @param actionId action的Id
     * @param arg 页面参数
     * @param animBuilder 跳转动画
     * @param destinationId 页面Id
     * @param inclusive 是否销毁
     * @param singleTop
     */
    override fun start(
        actionId: Int,
        arg: BaseArg?,
        animBuilder: AnimBuilder?,
        destinationId: Int?,
        popUpTo: Int?,
        inclusive: Boolean?,
        singleTop: Boolean?,
        extras: Navigator.Extras?
    ) {
        viewModel.start(
            actionId,
            arg,
            animBuilder,
            destinationId,
            popUpTo,
            inclusive,
            singleTop,
            extras
        )
    }

    /**
     * Activity跳转
     */
    override fun startActivity(
        clazz: Class<out Activity>,
        arg: BaseArg?,
        animBuilder: AnimBuilder?,
        options: Bundle?,
        closure: (Intent.() -> Unit)?
    ) {
        viewModel.startActivity(clazz, arg, animBuilder, options, closure)
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
     * 第一次初始化数据 在onViewCreated回调
     */
    override fun initOneData() {
        viewModel.initOneData()
    }

    override fun initOneObservable() {
        viewModel.initOneObservable()
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

}