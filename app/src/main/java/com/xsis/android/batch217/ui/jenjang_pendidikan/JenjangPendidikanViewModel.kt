package com.xsis.android.batch217.ui.jenjang_pendidikan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class JenjangPendidikanViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Jenjang Pendidikan Fragment"
    }
    val text: LiveData<String> = _text
}