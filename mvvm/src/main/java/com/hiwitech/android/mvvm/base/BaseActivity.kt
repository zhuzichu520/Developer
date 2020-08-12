package com.hiwitech.android.mvvm.base

import android.os.Bundle
import com.alibaba.android.arouter.launcher.ARouter
import com.hiwitech.android.libs.tool.decodeBase64
import com.hiwitech.android.libs.tool.json2Object
import com.hiwitech.android.libs.tool.toCast
import com.hiwitech.android.mvvm.Mvvm
import com.hiwitech.android.mvvm.Mvvm.KEY_ARG
import com.hiwitech.android.mvvm.Mvvm.KEY_ARG_JSON
import com.hiwitech.android.mvvm.fragment.RootActivity
import com.hiwitech.android.mvvm.fragment.RootFragment

/**
 * desc Activity的基类
 * author: 朱子楚
 * time: 2020/4/9 4:06 PM
 * since: v 1.0.0
 */
abstract class BaseActivity : RootActivity() {

    private lateinit var arg: BaseArg

    /**
     * Fragment的路由
     */
    abstract fun getRoute(): String

    override fun getRootFragment(): RootFragment {
        initArg()
        return ARouter.getInstance().build(getRoute()).with(intent.extras)
            .navigation() as RootFragment
    }

    override fun onCreateNow(savedInstanceState: Bundle?) {
        super.onCreateNow(savedInstanceState)
        setAnim(
            arg.enterAnim ?: Mvvm.enterAnim,
            arg.exitAnim ?: Mvvm.exitAnim,
            arg.popEnterAnim ?: Mvvm.popEnterAnim,
            arg.popExitAnim ?: Mvvm.popExitAnim
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
}