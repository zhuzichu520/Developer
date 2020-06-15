package com.hiwitech.android.mvvm.base

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.FrameLayout
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.hiwitech.android.libs.tool.decodeBase64
import com.hiwitech.android.libs.tool.json2Object
import com.hiwitech.android.libs.tool.toCast
import com.hiwitech.android.mvvm.Mvvm
import com.hiwitech.android.mvvm.Mvvm.KEY_ARG
import com.hiwitech.android.mvvm.Mvvm.KEY_ARG_JSON
import com.hiwitech.android.mvvm.Mvvm.enterAnim
import com.hiwitech.android.mvvm.Mvvm.exitAnim
import com.hiwitech.android.mvvm.R
import dagger.android.support.DaggerAppCompatActivity

/**
 * desc Activity的基类
 * author: 朱子楚
 * time: 2020/4/9 4:06 PM
 * since: v 1.0.0
 */
abstract class BaseActivity : DaggerAppCompatActivity() {

    abstract fun setNavGraph(): Int

    private lateinit var arg: BaseArg

    /**
     * 页面导航器
     */
    val navController by lazy { findNavController(R.id.delegate_container) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initContainer(savedInstanceState)
    }

    /**
     * 初始化Fragment容器
     */
    private fun initContainer(savedInstanceState: Bundle?) {
        val container = FrameLayout(this)
        container.id = R.id.delegate_container
        setContentView(container)
        initArg()
        if (savedInstanceState == null) {
            val fragment = NavHostFragment.create(setNavGraph(), intent.extras)
            supportFragmentManager.beginTransaction()
                .replace(R.id.delegate_container, fragment)
                .setPrimaryNavigationFragment(fragment)
                .commit()
        }
    }

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
     * 绑定toolbar的navigationup
     */
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
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

    fun startActivity(
        clazz: Class<out Activity>,
        arg: BaseArg? = null,
        options: Bundle? = null,
        isPop: Boolean? = null,
        context: Context? = null,
        closure: (Intent.() -> Unit)? = null
    ) {
        val payload = Payload.StartActivity(
            clazz,
            arg ?: ArgDefault(),
            options,
            isPop,
            context,
            closure
        )
        val ctx = payload.context ?: this
        val intent = Intent(ctx, payload.clazz)
        intent.putExtras(bundleOf(KEY_ARG to payload.arg))
        payload.options?.let {
            startActivity(intent, it)
        } ?: startActivity(intent)
        if (payload.arg.useSystemAnimation != true) {
            overridePendingTransition(
                payload.arg.enterAnim ?: enterAnim,
                payload.arg.exitAnim ?: exitAnim
            )
        }
        if (true == payload.isPop) {
            finish()
        }
    }

}