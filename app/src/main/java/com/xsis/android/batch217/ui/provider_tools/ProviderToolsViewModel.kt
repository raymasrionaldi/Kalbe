package com.xsis.android.batch217.ui.agama

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProviderToolsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Provider Tools Fragment"
    }
    val text: LiveData<String> = _text
}