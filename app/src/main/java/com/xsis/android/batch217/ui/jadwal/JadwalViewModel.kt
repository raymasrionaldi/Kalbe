package com.xsis.android.batch217.ui.jadwal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class JadwalViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Jadwal Fragment"
    }
    val text: LiveData<String> = _text
}