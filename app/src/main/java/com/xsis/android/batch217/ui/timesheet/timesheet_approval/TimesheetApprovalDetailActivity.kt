package com.xsis.android.batch217.ui.timesheet.timesheet_approval

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.xsis.android.batch217.R
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.TimesheetQueryHelper
import com.xsis.android.batch217.utils.ID_TIMESHEET
import kotlinx.android.synthetic.main.activity_timesheet_approval_detail.*

class TimesheetApprovalDetailActivity : AppCompatActivity() {
    val context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timesheet_approval_detail)
        title = ""

        supportActionBar?.let {
            supportActionBar!!.setHomeButtonEnabled(true)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }

        var idTimesheet = 0

        val bundle = intent.extras
        bundle?.let {
            idTimesheet = bundle.getInt(ID_TIMESHEET, 0)
        }

        val databaseHelper = DatabaseHelper(context)
        val databaseQueryHelper = TimesheetQueryHelper(databaseHelper)

        val timesheet = databaseQueryHelper.getTimesheetById(idTimesheet)

        if (timesheet.id_timesheet > 0) {
            reportDateDetailTimesheetApproval.text = timesheet.reportDate_timesheet
            statusDetailTimesheetApproval.text = timesheet.status_timesheet
            clientDetailTimesheetApproval.text = timesheet.client_timesheet
            tanggalDanJamReportDateApproval.text =
                "${timesheet.reportDate_timesheet} ${timesheet.startReportDate_timesheet} - ${timesheet.endReportDate_timesheet}"
            val overtime = timesheet.overtime_timesheet
            if (overtime.equals("YES")) {
                overtimeDetailTimesheetApproval.text =
                    "$overtime ${timesheet.starOvertime_timesheet} - ${timesheet.endOvertime_timesheet}"
            } else {
                overtimeDetailTimesheetApproval.text = "$overtime"
            }
            notesDetailTimesheetApproval.text = timesheet.notes_timesheet
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            super.onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
