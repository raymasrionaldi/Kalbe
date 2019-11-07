package com.xsis.android.batch217.ui.employee_status

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EmployeeStatusViewModel: ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "Employe Status Fragment"
    }
    val text: LiveData<String> = _text
}