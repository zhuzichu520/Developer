package com.hiwitech.android.mvvm.domain

import androidx.lifecycle.MediatorLiveData

/**
 * desc MediatorUseCase
 * author: 朱子楚
 * time: 2020/4/9 4:06 PM
 * since: v 1.0.0
 */
abstract class MediatorUseCase<in P, R> {

    protected val result = MediatorLiveData<Result<R>>()

    open fun observe(): MediatorLiveData<Result<R>> {
        return result
    }

    abstract fun execute(parameters: P)
    
}