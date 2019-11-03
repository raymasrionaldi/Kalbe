package com.xsis.android.batch217.ui.keahlian

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

//KeahlianViewModel return ViewModel
class KeahlianViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Keahlian Fragment"
    }
    val text: LiveData<String> = _text
}