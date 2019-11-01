package com.xsis.android.batch217.ui.agama

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AgamaViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Agama Fragment"
    }
    val text: LiveData<String> = _text
}