package com.xsis.android.batch217.ui.keluarga

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class KeluargaViewModel:ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "Keluarga Fragment"
    }
    val text: LiveData<String> = _text
}