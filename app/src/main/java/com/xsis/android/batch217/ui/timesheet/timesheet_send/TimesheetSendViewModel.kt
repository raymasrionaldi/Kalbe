package com.xsis.android.batch217.ui.timesheet.timesheet_send

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TimesheetSendViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Timesheet Send Fragment"
    }
    val text: LiveData<String> = _text
}