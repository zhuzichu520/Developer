package com.chuzi.android.mvvm.ext

import androidx.core.os.bundleOf
import androidx.databinding.ViewDataBinding
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.launcher.ARouter
import com.chuzi.android.mvvm.Mvvm
import com.chuzi.android.mvvm.base.BaseArg
import com.chuzi.android.mvvm.base.BaseFragment
import com.chuzi.android.mvvm.base.BaseViewModel
import com.chuzi.android.mvvm.databinding.BindingCommand

fun createCommand(closure: () -> Unit): BindingCommand<Any> {
    return BindingCommand({
        closure.invoke()
    })
}

fun <T> createTypeCommand(closure: (T?) -> Unit): BindingCommand<T?> {
    return BindingCommand(consumer = {
        closure.invoke(this)
    })
}

fun Any.className(): String {
    return this.javaClass.simpleName
}


inline fun <reified T : Any> new(): T {
    val clz = T::class.java
    val mCreate = clz.getDeclaredConstructor()
    mCreate.isAccessible = true
    return mCreate.newInstance()
}

fun <TBinding : ViewDataBinding, TViewModel : BaseViewModel<TArg>, TArg : BaseArg> BaseFragment<TBinding, TViewModel, TArg>.setArg(
    arg: BaseArg
): BaseFragment<TBinding, TViewModel, TArg> {
    this.arguments = bundleOf(Mvvm.KEY_ARG to arg)
    return this
}

fun Postcard.withArg(arg: BaseArg): Postcard {
    this.with(bundleOf(Mvvm.KEY_ARG to arg))
    return this
}

fun String.toPostcard(): Postcard {
    return ARouter.getInstance().build(this)
}