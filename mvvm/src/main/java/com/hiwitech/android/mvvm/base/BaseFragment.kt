package com.hiwitech.android.mvvm.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.launcher.ARouter
import com.hiwitech.android.libs.tool.closeKeyboard
import com.hiwitech.android.libs.tool.decodeBase64
import com.hiwitech.android.libs.tool.json2Object
import com.hiwitech.android.libs.tool.toCast
import com.hiwitech.android.mvvm.Mvvm
import com.hiwitech.android.mvvm.Mvvm.KEY_ARG
import com.hiwitech.android.mvvm.Mvvm.KEY_ARG_JSON
import com.hiwitech.android.widget.dialog.loading.LoadingMaker
import com.qmuiteam.qmui.arch.QMUIFragment
import com.uber.autodispose.AutoDispose
import com.uber.autodispose.FlowableSubscribeProxy
import com.uber.autodispose.ObservableSubscribeProxy
import com.uber.autodispose.SingleSubscribeProxy
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import java.lang.reflect.ParameterizedType
import javax.inject.Inject

/**
 * desc Fragment基类
 * author: 朱子楚
 * time: 2020/4/9 4:06 PM
 * since: v 1.0.0
 */


abstract class BaseFragment<TBinding : ViewDataBinding, TViewModel : BaseViewModel<TArg>, TArg : BaseArg> :
    QMUIFragment(), IBaseView<TArg>, IBaseCommon, HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

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


    override fun onCreateView(): View? {
        val view: View = LayoutInflater.from(context).inflate(setLayoutId(), null)
        binding = DataBindingUtil.bind(view)
        binding?.lifecycleOwner = this
        return binding?.root
    }

    override fun onViewCreated(rootView: View) {
        super.onViewCreated(rootView)
        initViewDataBinding()
        initView()
        initListener()
        initVariable()
        initData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registUIChangeLiveDataCallback()
        initViewObservable()
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
        viewModel.lifecycleOwner = viewLifecycleOwner
        initArgs(arg)
        binding?.setVariable(bindVariableId(), viewModel)
        lifecycle.addObserver(viewModel)
    }

    /**
     * 注册页面事件
     */
    private fun registUIChangeLiveDataCallback() {

        viewModel.onNavigateEvent.observe(viewLifecycleOwner, Observer {
            val bundle = bundleOf(KEY_ARG to it.arg)
            val any = ARouter.getInstance()
                .build(it.route)
                .with(bundle)
                .withTransition(
                    it.arg.enterAnim ?: Mvvm.enterAnim,
                    it.arg.exitAnim ?: Mvvm.exitAnim
                )
                .navigation(requireContext())
            (any as? DialogFragment)?.let { dialogFragment ->
                dialogFragment.arguments = bundle
                dialogFragment.show(childFragmentManager, any::class.simpleName)
            }
            (any as? QMUIFragment)?.let { fragment ->
                startFragment(fragment)
            }
        })


        //销毁Activity
        viewModel.onFinishEvent.observe(viewLifecycleOwner, Observer {
            requireView().post {
                requireActivity().finish()
            }
        })

        //页面返回事件
        viewModel.onBackPressedEvent.observe(viewLifecycleOwner, Observer {
            requireView().post {
                activityCtx.onBackPressed()
            }
        })

        //显示loading事件
        viewModel.onShowLoadingEvent.observe(viewLifecycleOwner, Observer {
            requireView().post {
                closeKeyboard(activityCtx)
                LoadingMaker.showLoadingDialog(activityCtx, Mvvm.loadingLayoutId)
            }
        })

        //隐藏loading事件
        viewModel.onHideLoadingEvent.observe(viewLifecycleOwner, Observer {
            requireView().post {
                LoadingMaker.dismissLodingDialog()
            }
        })

    }


    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
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
     * 路由跳转
     */
    override fun navigate(route: String, arg: BaseArg?) {
        viewModel.navigate(route, arg)
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

    fun <T> Single<T>.autoDispose(event: Lifecycle.Event = Lifecycle.Event.ON_DESTROY): SingleSubscribeProxy<T> =
        this.`as`(
            AutoDispose.autoDisposable(
                AndroidLifecycleScopeProvider.from(viewLifecycleOwner, event)
            )
        )

    fun <T> Flowable<T>.autoDispose(event: Lifecycle.Event = Lifecycle.Event.ON_DESTROY): FlowableSubscribeProxy<T> =
        this.`as`(
            AutoDispose.autoDisposable(
                AndroidLifecycleScopeProvider.from(viewLifecycleOwner, event)
            )
        )

    fun <T> Observable<T>.autoDispose(event: Lifecycle.Event = Lifecycle.Event.ON_DESTROY): ObservableSubscribeProxy<T> =
        this.`as`(
            AutoDispose.autoDisposable(
                AndroidLifecycleScopeProvider.from(viewLifecycleOwner, event)
            )
        )

    override fun androidInjector(): AndroidInjector<Any> {
        return androidInjector
    }

}