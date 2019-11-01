package com.xsis.android.batch217.ui.company

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CompanyViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Company Fragment"
    }
    val text: LiveData<String> = _text
}