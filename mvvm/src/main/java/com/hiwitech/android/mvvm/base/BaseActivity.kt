package com.hiwitech.android.mvvm.base

import android.os.Bundle
import android.widget.FrameLayout
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.hiwitech.android.mvvm.Mvvm
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
        if (savedInstanceState == null) {
            val fragment = NavHostFragment.create(setNavGraph(), intent.extras)
            supportFragmentManager.beginTransaction()
                .replace(R.id.delegate_container, fragment)
                .setPrimaryNavigationFragment(fragment)
                .commit()
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
        overridePendingTransition(Mvvm.popEnterAnim, Mvvm.popExitAnim)
    }

}