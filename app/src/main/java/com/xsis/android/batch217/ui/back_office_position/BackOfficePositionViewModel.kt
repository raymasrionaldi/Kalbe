package com.xsis.android.batch217.ui.back_office_position

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BackOfficePositionViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Back Office Position Fragment"
    }
    val text: LiveData<String> = _text
}