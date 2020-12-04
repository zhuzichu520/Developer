package com.chuzi.android.mvvm.base

import android.content.Intent
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

    /**
     * 根Fragment
     */
    private var fragment: BaseFragment<*, *, *>? = null

    /**
     * Fragment的路由
     */
    abstract fun getRoute(): String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            fragment = ARouter.getInstance()
                .build(getRoute())
                .with(intent.extras)
                .navigation() as? BaseFragment<*, *, *>
            fragment?.let {
                loadRootFragment(it)
            }
        }
    }

    /**
     * 加载Fragment
     */
    private fun loadRootFragment(fragment: QMUIFragment): Int {
        val fragmentManager = supportFragmentManager
        if (fragmentManager.isStateSaved) {
            return -1
        }
        val tagName = fragment.javaClass.simpleName
        return fragmentManager.beginTransaction().replace(contextViewId, fragment, tagName)
            .addToBackStack(tagName).commit()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        fragment?.onNewIntent(intent)
    }

    override fun back() {
        fragment?.back()
    }

    override fun showLoading() {
        fragment?.showLoading()
    }

    override fun hideLoading() {
        fragment?.hideLoading()
    }

    override fun navigate(route: String, arg: BaseArg?, isPop: Boolean?) {
        fragment?.navigate(route, arg, isPop)
    }

    override fun toast(text: String) {
        fragment?.toast(text)
    }

    override fun toast(textId: Int) {
        fragment?.toast(textId)
    }

    override fun onDestroy() {
        super.onDestroy()
        fragment = null
    }

}