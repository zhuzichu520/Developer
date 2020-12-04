@file:Suppress("DEPRECATION")

package com.chuzi.android.shared.base

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.alibaba.android.arouter.facade.Postcard
import com.chuzi.android.libs.tool.toCast
import com.chuzi.android.shared.ext.toStringByResId

class DefaultIntFragmentPagerAdapter(
    fm: FragmentManager,
    private val list: List<Postcard>,
    private val titles: List<Int>? = null
) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment = list[position].navigation().toCast()

    override fun getCount(): Int = list.size

    override fun getPageTitle(position: Int): CharSequence? {
        return if (titles == null)
            super.getPageTitle(position)
        else
            titles[position].toStringByResId()
    }

}

class DefaultStringFragmentPagerAdapter(
    fm: FragmentManager,
    private val list: List<Postcard>,
    private val titles: List<String>? = null
) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment = list[position].navigation().toCast()

    override fun getCount(): Int = list.size

    override fun getPageTitle(position: Int): CharSequence? {
        return if (titles == null)
            super.getPageTitle(position)
        else
            titles[position]
    }

}