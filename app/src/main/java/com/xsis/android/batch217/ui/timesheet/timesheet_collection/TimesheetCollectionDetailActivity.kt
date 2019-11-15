package com.xsis.android.batch217.ui.timesheet.timesheet_collection

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import com.xsis.android.batch217.R
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.TimesheetQueryHelper
import com.xsis.android.batch217.utils.ID_TIMESHEET
import kotlinx.android.synthetic.main.activity_data_collected.*
import kotlinx.android.synthetic.main.activity_timesheet_collection_detail.*
import kotlinx.android.synthetic.main.activity_timesheet_collection_detail.buttonBackCollection
import kotlinx.android.synthetic.main.list_layout.*

class TimesheetCollectionDetailActivity : AppCompatActivity() {

    val context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar!!.hide()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        try {
            this.supportActionBar!!.hide()
        } catch (e: NullPointerException){
        }

        setContentView(R.layout.activity_timesheet_collection_detail)

        var idTimesheet = 0

        buttonBackCollection.setOnClickListener{
            finish()
        }

        val bundle = intent.extras
        bundle?.let {
            idTimesheet = bundle.getInt(ID_TIMESHEET, 0)
        }

        val databaseHelper = DatabaseHelper(context)
        val databaseQueryHelper = TimesheetQueryHelper(databaseHelper)

        val timesheet = databaseQueryHelper.getTimesheetById(idTimesheet)

        if (timesheet.id_timesheet > 0) {
            reportDateDetailTimesheetCollection.text = timesheet.reportDate_timesheet
            statusDetailTimesheetCollection.text = timesheet.status_timesheet
            clientDetailTimesheetCollection.text = timesheet.client_timesheet
            tanggalDanJamReportDateCollection.text =
                "${timesheet.reportDate_timesheet} ${timesheet.startReportDate_timesheet} - ${timesheet.endReportDate_timesheet}"
            val overtime = timesheet.overtime_timesheet
            if (overtime.equals("YES")) {
                overtimeDetailTimesheetCollection.text =
                    "$overtime ${timesheet.starOvertime_timesheet} - ${timesheet.endOvertime_timesheet}"
            } else {
                overtimeDetailTimesheetCollection.text = "$overtime"
            }
            notesDetailTimesheetCollection.text = timesheet.notes_timesheet
        }

    }
}
