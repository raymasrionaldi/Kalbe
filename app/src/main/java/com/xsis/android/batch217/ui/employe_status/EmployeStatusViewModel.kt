package com.xsis.android.batch217.ui.employe_status

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EmployeStatusViewModel: ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "Employe Status Fragment"
    }
    val text: LiveData<String> = _text
}