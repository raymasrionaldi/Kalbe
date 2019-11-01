package com.xsis.android.batch217.ui.contact_status

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ContactStatusViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Contact Status Fragment"
    }
    val text: LiveData<String> = _text
}