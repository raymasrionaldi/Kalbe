package com.xsis.android.batch217.ui.tipe_tes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TipeTesViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Tipe Tes Fragment"
    }
    val text: LiveData<String> = _text
}