package com.hiwitech.android.mvvm.di

import javax.inject.Scope

/**
 * desc FragmentScoped
 * author: 朱子楚
 * time: 2020/4/9 4:06 PM
 * since: v 1.0.0
 */
@Scope
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.TYPE, AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
annotation class FragmentScoped
