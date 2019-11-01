package com.xsis.android.batch217.ui.position_level

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PositionLevelViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Position Level Fragment"
    }
    val text: LiveData<String> = _text
}