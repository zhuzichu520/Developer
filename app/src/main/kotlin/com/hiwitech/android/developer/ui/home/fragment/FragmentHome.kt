package com.hiwitech.android.developer.ui.home.fragment

import com.alibaba.android.arouter.facade.annotation.Route
import com.hiwitech.android.developer.BR
import com.hiwitech.android.developer.R
import com.hiwitech.android.developer.base.FragmentBase
import com.hiwitech.android.developer.databinding.FragmentHomeBinding
import com.hiwitech.android.developer.repository.entity.BeanArticle
import com.hiwitech.android.developer.ui.home.viewmodel.ViewModelHome
import com.hiwitech.android.mvvm.base.ArgDefault
import com.hiwitech.android.shared.ext.bindToSchedulers
import com.hiwitech.android.shared.ext.toast
import com.hiwitech.android.shared.route.RoutePath
import com.rxjava.rxlife.life
import rxhttp.RxHttp

@Route(path = RoutePath.FRAGMENT_HOME)
class FragmentHome : FragmentBase<FragmentHomeBinding, ViewModelHome, ArgDefault>() {

    override fun bindVariableId(): Int = BR.viewModel

    override fun setLayoutId(): Int = R.layout.fragment_home

    override fun initView() {
        super.initView()
        RxHttp.get("/article/list/1/json")
            .asResponseResponsePageList(BeanArticle::class.java)
            .bindToSchedulers()
            .life(this)
            .subscribe(
                {
                    it?.datas?.get(0)?.title.toString().toast()
                },
                {
                    it.message?.toast()
                }
            )
    }
}
