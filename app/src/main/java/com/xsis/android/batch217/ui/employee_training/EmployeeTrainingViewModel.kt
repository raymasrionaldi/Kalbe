package com.xsis.android.batch217.ui.employee_training

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EmployeeTrainingViewModel: ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "Employe Training Fragment"
    }
    val text: LiveData<String> = _text
}