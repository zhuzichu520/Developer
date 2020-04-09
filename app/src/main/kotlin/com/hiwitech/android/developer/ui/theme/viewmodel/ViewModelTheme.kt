package com.hiwitech.android.developer.ui.theme.viewmodel

import androidx.lifecycle.MutableLiveData
import com.hiwitech.android.mvvm.base.ArgDefault
import com.hiwitech.android.mvvm.base.BaseViewModel
import com.hiwitech.android.shared.theme.ThemeManager
import javax.inject.Inject

class ViewModelTheme @Inject constructor() : BaseViewModel<ArgDefault>() {

    val checkedButton = MutableLiveData<Int>(ThemeManager.getCurrentThemeId())
}
