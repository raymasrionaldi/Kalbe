package com.xsis.android.batch217.ui.timesheet.timesheet_entry

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TimesheetEntryViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "Timesheet Entry Fragment"
    }
    val text: LiveData<String> = _text
}