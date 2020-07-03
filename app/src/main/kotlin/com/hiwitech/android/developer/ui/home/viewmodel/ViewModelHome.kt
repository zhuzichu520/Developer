package com.hiwitech.android.developer.ui.home.viewmodel

import com.hiwitech.android.developer.base.ViewModelBase
import com.hiwitech.android.developer.repository.entity.BeanArticle
import com.hiwitech.android.mvvm.base.ArgDefault
import com.hiwitech.android.mvvm.base.BaseViewModel
import com.hiwitech.android.shared.ext.bindToSchedulers
import com.hiwitech.android.shared.ext.toast
import com.uber.autodispose.android.autoDispose
import com.uber.autodispose.android.lifecycle.autoDispose
import rxhttp.wrapper.param.RxHttp
import javax.inject.Inject

class ViewModelHome @Inject constructor() : ViewModelBase<ArgDefault>(){
    fun loadUser(){
        RxHttp.get("/article/list/1/json")
            .asResponseResponsePageList(BeanArticle::class.java)
            .bindToSchedulers()
            .autoDispose()
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
