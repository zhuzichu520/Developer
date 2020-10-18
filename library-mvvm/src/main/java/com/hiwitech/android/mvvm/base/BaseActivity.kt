package com.hiwitech.android.mvvm.base

import android.os.Bundle
import com.alibaba.android.arouter.launcher.ARouter
import com.qmuiteam.qmui.arch.QMUIFragment
import com.qmuiteam.qmui.arch.QMUIFragmentActivity

/**
 * desc Activity的基类
 * author: 朱子楚
 * time: 2020/4/9 4:06 PM
 * since: v 1.0.0
 */
abstract class BaseActivity : QMUIFragmentActivity(), IBaseCommon {

    private lateinit var fragment: BaseFragment<*, *, *>

    /**
     * Fragment的路由
     */
    abstract fun getRoute(): String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            (ARouter.getInstance()
                .build(getRoute())
                .with(intent.extras)
                .navigation() as? BaseFragment<*, *, *>)?.let {
                fragment = it
                startFragment(
                    fragment, false
                )
            }
        }
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

    override fun back() {
        fragment.back()
    }

    override fun showLoading() {
        fragment.showLoading()
    }

    override fun hideLoading() {
        fragment.hideLoading()
    }

    override fun navigate(route: String, arg: BaseArg?, isPop: Boolean?) {
        fragment.navigate(route, arg, isPop)
    }

    override fun toast(text: String) {
        fragment.toast(text)
    }

    override fun toast(textId: Int) {
        fragment.toast(textId)
    }
}