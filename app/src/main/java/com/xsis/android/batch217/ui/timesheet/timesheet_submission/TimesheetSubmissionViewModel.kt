package com.xsis.android.batch217.ui.timesheet.timesheet_submission

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TimesheetSubmissionViewModel  : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Timesheet Submission Fragment"
    }
    val text: LiveData<String> = _text
}