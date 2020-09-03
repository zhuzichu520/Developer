package com.hiwitech.android.mvvm.base

import android.os.Bundle
import com.alibaba.android.arouter.launcher.ARouter
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

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        (ARouter.getInstance()
            .build(getRoute())
            .with(intent.extras)
            .navigation() as? QMUIFragment)?.let {
            startFragment(
                it, false
            )
        }
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