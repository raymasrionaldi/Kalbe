package com.xsis.android.batch217.ui.employee

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EmployeeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Employee Fragment"
    }
    val text: LiveData<String> = _text
}