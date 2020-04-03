package com.hiwitech.android.shared.ext

import com.hiwitech.android.mvvm.base.BaseViewModel
import com.hiwitech.android.shared.http.exception.ExceptionManager.handleException
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers

fun <T> Flowable<T>.autoLoading(
    viewModel: BaseViewModel<*>,
    execute: (() -> Boolean)? = null
): Flowable<T> =
    if (execute?.invoke() != false)
        this.compose<T> {
            it.doOnSubscribe { viewModel.showLoading() }
                .doFinally { viewModel.hideLoading() }
        }
    else
        this.compose<T> {
            it
        }

fun <T> Observable<T>.autoLoading(
    viewModel: BaseViewModel<*>,
    execute: (() -> Boolean)? = null
): Observable<T> =
    if (execute?.invoke() != false)
        this.compose<T> {
            it.doOnSubscribe { viewModel.showLoading() }
                .doFinally { viewModel.hideLoading() }
        }
    else
        this.compose<T> {
            it
        }


private class ErrorFlowableResponseFunc<T> : Function<Throwable, Flowable<T>> {
    override fun apply(t: Throwable): Flowable<T> {
        return Flowable.error(handleException(t))
    }
}

private class ErrorObservableResponseFunc<T> : Function<Throwable, Observable<T>> {
    override fun apply(t: Throwable): Observable<T> {
        return Observable.error(handleException(t))
    }
}

fun <T> Flowable<T>.bindToException(): Flowable<T> =
    this.compose<T> {
        it.onErrorResumeNext(ErrorFlowableResponseFunc<T>())
    }

fun <T> Observable<T>.bindToException(): Observable<T> =
    this.compose<T> {
        it.onErrorResumeNext(ErrorObservableResponseFunc<T>())
    }


fun <T> Flowable<T>.bindToSchedulers(): Flowable<T> =
    this.compose<T> {
        it.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

fun <T> Observable<T>.bindToSchedulers(): Observable<T> =
    this.compose<T> {
        it.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

fun <T> createFlowable(
    mode: BackpressureStrategy = BackpressureStrategy.ERROR,
    closure: FlowableEmitter<T>.() -> Unit
): Flowable<T> =
    Flowable.create({
        closure.invoke(it)
    }, mode)

fun <T> createObservable(
    closure: ObservableEmitter<T>.() -> Unit
): Observable<T> =
    Observable.create {
        closure.invoke(it)
    }