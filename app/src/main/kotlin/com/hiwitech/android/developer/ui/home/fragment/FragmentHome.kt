package com.hiwitech.android.developer.ui.home.fragment

import com.hiwitech.android.developer.BR
import com.hiwitech.android.developer.R
import com.hiwitech.android.developer.databinding.FragmentHomeBinding
import com.hiwitech.android.developer.repository.entity.BeanArticle
import com.hiwitech.android.developer.ui.home.viewmodel.ViewModelHome
import com.hiwitech.android.mvvm.base.ArgDefault
import com.hiwitech.android.mvvm.base.BaseFragment
import com.hiwitech.android.shared.ext.bindToSchedulers
import com.hiwitech.android.shared.ext.toast
import com.uber.autodispose.autoDispose
import rxhttp.wrapper.param.RxHttp

class FragmentHome : BaseFragment<FragmentHomeBinding, ViewModelHome, ArgDefault>() {

    override fun bindVariableId(): Int = BR.viewModel

    override fun setLayoutId(): Int = R.layout.fragment_home

    override fun initView() {
        super.initView()
        RxHttp.get("/article/list/1/json")
            .asResponsePageList(BeanArticle::class.java)
            .bindToSchedulers()
            .autoDispose(viewModel)
            .subscribe(
                {
                    it.datas?.get(0)?.title?.toast()
                },
                {
                    it.message?.toast()
                }
            )
    }

}