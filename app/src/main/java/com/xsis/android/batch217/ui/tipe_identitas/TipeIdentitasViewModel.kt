package com.xsis.android.batch217.ui.tipe_identitas

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TipeIdentitasViewModel : ViewModel(){
    private val _text = MutableLiveData<String>().apply { value = "Tipe Identitas Fragment" }
    val text : LiveData<String> = _text
}