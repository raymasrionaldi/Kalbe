package com.xsis.android.batch217.ui.jenis_catatan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class JenisCatatanViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Jenis Catatan Fragment"
    }
    val text: LiveData<String> = _text
}