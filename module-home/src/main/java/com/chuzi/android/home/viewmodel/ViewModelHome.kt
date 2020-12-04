package com.chuzi.android.home.viewmodel

import com.chuzi.android.shared.base.ViewModelBase
import com.chuzi.android.mvvm.base.ArgDefault
import com.chuzi.android.shared.ext.bindToSchedulers
import com.chuzi.android.shared.ext.toast
import com.chuzi.android.shared.repository.entity.BeanArticle
import com.rxjava.rxlife.life
import rxhttp.RxHttp

class ViewModelHome : ViewModelBase<ArgDefault>() {
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
