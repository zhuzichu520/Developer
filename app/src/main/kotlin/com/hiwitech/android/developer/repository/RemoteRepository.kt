package com.hiwitech.android.developer.repository

import com.hiwitech.android.developer.repository.entity.*
import com.hiwitech.android.shared.http.entity.Response
import com.hiwitech.android.shared.http.entity.ResponsePageList
import io.reactivex.Observer

interface RemoteRepository {
    fun login(username: String, password: String): Observer<Response<BeanLogin>>

    fun getCoins(page: Int): Observer<Response<ResponsePageList<BeanCoin>>>

    fun getArticles(page: Int, cid: Int?): Observer<Response<ResponsePageList<BeanArticle>>>

    fun getBanner(): Observer<Response<List<BeanBanner>>>

    fun getTopArticles(): Observer<Response<List<BeanArticle>>>

    fun collect(id: Int): Observer<Response<Any>>

    fun unCollect(id: Int): Observer<Response<Any>>

    fun getProjects(page: Int): Observer<Response<ResponsePageList<BeanArticle>>>

    fun getUserInfo(): Observer<Response<BeanUserInfo>>

    fun getCollections(page: Int): Observer<Response<ResponsePageList<BeanArticle>>>

    fun search(page: Int, keyWord: String): Observer<Response<ResponsePageList<BeanArticle>>>

    fun getHotKey(): Observer<Response<List<BeanKeyword>>>

    fun getTree(): Observer<Response<List<BeanNode>>>
}

// class RemoteRepositoryImpl: RemoteRepository {
//    override fun login(username: String, password: String): Observer<Response<BeanLogin>> {
//        return RxHttp.postForm("/user/login")
//            .add("username",username)
//            .add("password",password)
//
//    }
//
//    override fun getCoins(page: Int): Observer<Response<ResponsePageList<BeanCoin>>> {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun getArticles(page: Int, cid: Int?): Observer<Response<ResponsePageList<BeanArticle>>> {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun getBanner(): Observer<Response<List<BeanBanner>>> {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun getTopArticles(): Observer<Response<List<BeanArticle>>> {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun collect(id: Int): Observer<Response<Any>> {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun unCollect(id: Int): Observer<Response<Any>> {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun getProjects(page: Int): Observer<Response<ResponsePageList<BeanArticle>>> {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun getUserInfo(): Observer<Response<BeanUserInfo>> {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun getCollections(page: Int): Observer<Response<ResponsePageList<BeanArticle>>> {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun search(page: Int, keyWord: String): Observer<Response<ResponsePageList<BeanArticle>>> {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun getHotKey(): Observer<Response<List<BeanKeyword>>> {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun getTree(): Observer<Response<List<BeanNode>>> {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
// }
