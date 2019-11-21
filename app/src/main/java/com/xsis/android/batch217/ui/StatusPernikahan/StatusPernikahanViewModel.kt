package com.xsis.android.batch217.ui.StatusPernikahan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StatusPernikahanViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Status Pernikahan Fragment"
    }
    val text: LiveData<String> = _text
}