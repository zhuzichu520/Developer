package com.hiwitech.android.mvvm.base

import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import androidx.fragment.app.FragmentContainerView
import com.alibaba.android.arouter.launcher.ARouter
import com.hiwitech.android.libs.tool.decodeBase64
import com.hiwitech.android.libs.tool.json2Object
import com.hiwitech.android.libs.tool.toCast
import com.hiwitech.android.mvvm.Mvvm
import com.hiwitech.android.mvvm.Mvvm.KEY_ARG
import com.hiwitech.android.mvvm.Mvvm.KEY_ARG_JSON
import com.qmuiteam.qmui.arch.QMUIFragment
import com.qmuiteam.qmui.arch.QMUIFragmentActivity
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject


/**
 * desc Activity的基类
 * author: 朱子楚
 * time: 2020/4/9 4:06 PM
 * since: v 1.0.0
 */
abstract class BaseActivity : QMUIFragmentActivity(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    private lateinit var arg: BaseArg

    /**
     * Fragment的路由
     */
    abstract fun getRoute(): String

    /**
     * 初始化RootView
     */
    override fun onCreateRootView(fragmentContainerId: Int): RootView {
        return MvvmRootView(this, fragmentContainerId)
    }

    /**
     * 自定义RootView
     */
    private class MvvmRootView(context: Context, fragmentContainerId: Int) :
        RootView(context, fragmentContainerId) {

        private var fragmentContainer: FragmentContainerView = FragmentContainerView(context)

        init {
            fragmentContainer.id = fragmentContainerId
            addView(
                fragmentContainer,
                LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            )
        }

        override fun getFragmentContainerView(): FragmentContainerView = fragmentContainer

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        startFragment(
            ARouter.getInstance().build(getRoute()).with(intent.extras).navigation() as QMUIFragment
        )
    }

    /**
     * 初始化页面参数
     */
    private fun initArg() {
        val arguments = intent.extras
        val argJson = arguments?.getString(KEY_ARG_JSON)
        arg = if (argJson.isNullOrEmpty()) {
            (arguments?.get(KEY_ARG) ?: ArgDefault()).toCast()
        } else {
            json2Object(decodeBase64(argJson), BaseArg::class.java) ?: ArgDefault().toCast()
        }
    }

    /**
     * 添加销毁动画
     */
    override fun finish() {
        super.finish()
        if (arg.useSystemAnimation != true) {
            overridePendingTransition(
                arg.popEnterAnim ?: Mvvm.popEnterAnim,
                arg.popExitAnim ?: Mvvm.popExitAnim
            )
        }
    }


    override fun androidInjector(): AndroidInjector<Any> {
        return androidInjector
    }

}