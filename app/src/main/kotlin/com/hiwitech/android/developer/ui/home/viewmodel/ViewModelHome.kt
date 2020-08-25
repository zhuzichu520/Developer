package com.hiwitech.android.developer.ui.home.viewmodel

import com.hiwitech.android.developer.base.ViewModelBase
import com.hiwitech.android.developer.repository.entity.BeanArticle
import com.hiwitech.android.mvvm.base.ArgDefault
import com.hiwitech.android.shared.ext.bindToSchedulers
import com.hiwitech.android.shared.ext.toast
import com.rxjava.rxlife.life
import rxhttp.RxHttp
import javax.inject.Inject

class ViewModelHome @Inject constructor() : ViewModelBase<ArgDefault>() {
    fun loadUser() {
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
