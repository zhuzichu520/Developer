package com.chuzi.android.home.fragment

import com.alibaba.android.arouter.facade.annotation.Route
import com.chuzi.android.shared.base.FragmentBase
import com.chuzi.android.home.R
import com.chuzi.android.home.BR
import com.chuzi.android.home.databinding.FragmentHomeBinding
import com.chuzi.android.home.viewmodel.ViewModelHome
import com.chuzi.android.mvvm.base.ArgDefault
import com.chuzi.android.shared.ext.bindToSchedulers
import com.chuzi.android.shared.ext.toast
import com.chuzi.android.shared.repository.entity.BeanArticle
import com.chuzi.android.shared.route.RoutePath
import com.rxjava.rxlife.life
import rxhttp.RxHttp

@Route(path = RoutePath.Home.FRAGMENT_HOME_MAIN)
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
