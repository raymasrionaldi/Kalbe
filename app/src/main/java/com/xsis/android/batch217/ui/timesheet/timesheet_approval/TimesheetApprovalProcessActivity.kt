package com.xsis.android.batch217.ui.timesheet.timesheet_approval

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.ListTimesheetApprovalAdapter
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.TimesheetQueryHelper
import com.xsis.android.batch217.utils.MONTH_TIMESHEET
import com.xsis.android.batch217.utils.SUBMITTED
import com.xsis.android.batch217.utils.YEAR_TIMESHEET
import kotlinx.android.synthetic.main.activity_timesheet_approval_process.*

class TimesheetApprovalProcessActivity : AppCompatActivity() {
    val context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timesheet_approval_process)
        title = ""

        supportActionBar?.let {
            supportActionBar!!.setHomeButtonEnabled(true)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }

        var year = ""
        var month = ""

        val bundle = intent.extras
        bundle?.let {
            year = bundle.getString(YEAR_TIMESHEET, "")
            month = bundle.getString(MONTH_TIMESHEET, "")
        }

        val databaseHelper = DatabaseHelper(context)
        val databaseQueryHelper = TimesheetQueryHelper(databaseHelper)

        val listData =
            databaseQueryHelper.getTimesheetBerdasarkanWaktuDanProgress(month, year, SUBMITTED)

        if (listData.isEmpty() || year.isEmpty() || month.isEmpty()) {
            dataNotFoundTimesheetApproval.visibility = View.VISIBLE
            listTimesheetApprovalRecycler.visibility = View.GONE
        } else {
            val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

            listTimesheetApprovalRecycler.layoutManager = layoutManager

            val dividerItemDecoration = DividerItemDecoration(context, layoutManager.orientation)
            listTimesheetApprovalRecycler.addItemDecoration(dividerItemDecoration)


            val adapterTimesheetApproval = ListTimesheetApprovalAdapter(context!!, listData)
            listTimesheetApprovalRecycler.adapter = adapterTimesheetApproval
            adapterTimesheetApproval.notifyDataSetChanged()
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
