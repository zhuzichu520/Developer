package com.hiwitech.android.home.viewmodel

import com.hiwitech.android.shared.base.ViewModelBase
import com.hiwitech.android.mvvm.base.ArgDefault
import com.hiwitech.android.shared.ext.bindToSchedulers
import com.hiwitech.android.shared.ext.toast
import com.hiwitech.android.shared.repository.entity.BeanArticle
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
