package com.hiwitech.android.mvvm.base

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import androidx.fragment.app.FragmentContainerView
import com.alibaba.android.arouter.launcher.ARouter
import com.qmuiteam.qmui.QMUILog
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
            ARouter.getInstance().build(getRoute()).with(intent.extras)
                .navigation() as QMUIFragment, false
        )
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return androidInjector
    }

    @Suppress("SameParameterValue")
    private fun startFragment(fragment: QMUIFragment, isAnimation: Boolean): Int {
        val fragmentManager = supportFragmentManager
        if (fragmentManager.isStateSaved) {
            return -1
        }
        val transitionConfig = fragment.onFetchTransitionConfig()
        val tagName = fragment.javaClass.simpleName
        return fragmentManager.beginTransaction().apply {
            if (isAnimation) {
                setCustomAnimations(
                    transitionConfig.enter,
                    transitionConfig.exit,
                    transitionConfig.popenter,
                    transitionConfig.popout
                )
            }
        }.replace(contextViewId, fragment, tagName).addToBackStack(tagName).commit()
    }

}