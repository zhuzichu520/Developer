package com.hiwitech.android.mvvm.base

import android.os.Bundle
import android.widget.FrameLayout
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.hiwitech.android.mvvm.Mvvm
import com.hiwitech.android.mvvm.R
import dagger.android.support.DaggerAppCompatActivity

abstract class BaseActivity : DaggerAppCompatActivity() {

    abstract fun setNavGraph(): Int

    val navController by lazy { findNavController(R.id.delegate_container) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initContainer(savedInstanceState)
    }

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

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(Mvvm.popEnterAnim, Mvvm.popExitAnim)
    }

}