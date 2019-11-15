package com.xsis.android.batch217.ui.timesheet.timesheet_collection

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.ListTimeSheetCollectAdapter
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.TimesheetQueryHelper
import com.xsis.android.batch217.models.Timesheet
import com.xsis.android.batch217.utils.*
import kotlinx.android.synthetic.main.activity_data_collected.*
import kotlinx.android.synthetic.main.activity_data_collected.buttonBackCollection
import kotlinx.android.synthetic.main.activity_data_submit.*
import kotlinx.android.synthetic.main.activity_timesheet_collection_detail.*
import kotlinx.android.synthetic.main.list_layout.*

class DataCollectedActivity : AppCompatActivity() {

    val context = this
    lateinit var listTimesheet : List<Timesheet>

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

        setContentView(R.layout.activity_data_collected)

        var year = ""
        var month = ""
        var client = ""

        val bundle = intent.extras
        bundle?.let {
            year = bundle.getString(YEAR_TIMESHEET, "")
            month = bundle.getString(MONTH_TIMESHEET, "")
            client = bundle.getString(CLIENT_DATABASE, "")
        }

        val databaseHelper = DatabaseHelper(context)
        val databaseQueryHelper = TimesheetQueryHelper(databaseHelper)

        listTimesheet = databaseQueryHelper.getTimesheetBerdasarkanWaktuProgressDanClient(month, year, client, SENT)

        if (listTimesheet.isEmpty() || year.isEmpty() || month.isEmpty() || client.isEmpty()) {
            dataNotFoundTimesheetCollection.visibility = View.VISIBLE
            listTimesheetCollectionRecycler.visibility = View.GONE
            buttonCheckCollection.visibility = View.INVISIBLE
                   }
        else {
            val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

            listTimesheetCollectionRecycler.layoutManager = layoutManager

            val dividerItemDecoration = DividerItemDecoration(context, layoutManager.orientation)
            listTimesheetCollectionRecycler.addItemDecoration(dividerItemDecoration)

            val adapterTimesheetCollection = ListTimeSheetCollectAdapter(context!!, listTimesheet)
            listTimesheetCollectionRecycler.adapter = adapterTimesheetCollection
            adapterTimesheetCollection.notifyDataSetChanged()

            buttonCheckCollection.setOnClickListener {
               val listSelected= adapterTimesheetCollection.getSelectedList()
                androidx.appcompat.app.AlertDialog.Builder(context, R.style.AlertDialogTheme)
                    .setCancelable(true)
                    .setTitle("DATA COLLLECTING")
                    .setMessage("Are you sure to process timesheet ?")
                    .setPositiveButton("COLLECT") { dialog, which ->
                        changeProgress(COLLECTED, listSelected)
                    }
                    .setNegativeButton("CANCEL") { dialog, which ->
                        dialog.cancel()
                    }
                    .create().show()
            }

        }

        buttonBackCollection.setOnClickListener{
            finish()
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            super.onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun changeProgress(progress: String, list: ArrayList<Int>) {
        val ids = ArrayList<Int>()
        list.forEach { index -> ids.add(listTimesheet[index].id_timesheet) }

        val databaseHelper = DatabaseHelper(context!!)
        val databaseQueryHelper = TimesheetQueryHelper(databaseHelper)

        val isSucceed = databaseQueryHelper!!.changeProgress(ids, progress)

        val message = if (isSucceed) {
            "DATA HAS BEEN ${progress.toUpperCase()}"
        } else {
            "AN ERROR OCCURRED"
        }

        androidx.appcompat.app.AlertDialog.Builder(context, R.style.AlertDialogTheme)
            .setCancelable(false)
            .setTitle(message)
            .setPositiveButton("CLOSE") { dialog, which ->
                (context as AppCompatActivity).finish()
            }
            .create().show()
    }

}

