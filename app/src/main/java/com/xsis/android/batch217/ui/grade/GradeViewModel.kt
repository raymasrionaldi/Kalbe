package com.xsis.android.batch217.ui.grade

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GradeViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    private val _text = MutableLiveData<String>().apply {
        value = "Grade Fragment"
    }
    val text: LiveData<String> = _text
}
