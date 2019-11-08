package com.xsis.android.batch217.ui.timesheet.timesheet_entry

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.xsis.android.batch217.R

class EntryTimesheetActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entry_timesheet)
        this.title = "add entry"
    }
}
