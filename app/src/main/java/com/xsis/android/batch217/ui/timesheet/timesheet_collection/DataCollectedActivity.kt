package com.xsis.android.batch217.ui.timesheet.timesheet_collection

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

        setContentView(R.layout.activity_data_collected)

        try {
            this.supportActionBar!!.hide()
        } catch (e: NullPointerException){
        }

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

            buttonCheckCollection.setOnClickListener{
                collected()
            }
        }

        buttonBackCollection.setOnClickListener{
            finish()
        }
    }

    fun collected(){
        val konfirmasiCollected = AlertDialog.Builder(context)
        konfirmasiCollected.setMessage("DATA COLLECTING\n\nAre you sure to collect the data ?\n\n\n")
            .setPositiveButton("COLLECT", DialogInterface.OnClickListener{ dialog, which ->
                Toast.makeText(context," Collected Data", Toast.LENGTH_SHORT).show()

                val berhasilCollected = AlertDialog.Builder(context)
                berhasilCollected.setMessage("DATA HAS BEEN COLLECTED\n\n")
                    .setPositiveButton("CLOSE", DialogInterface.OnClickListener{ dialog, which ->
                        dialog.cancel()
                    })
                    .setCancelable(true)

                berhasilCollected.create().show()

            })
            .setNegativeButton("CANCEL", DialogInterface.OnClickListener{ dialog, which ->
                dialog.cancel()
            })
            .setCancelable(true)

        konfirmasiCollected.create().show()
    }
}

