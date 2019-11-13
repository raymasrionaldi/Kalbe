package com.xsis.android.batch217.ui.timesheet.timesheet_submission

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
import androidx.recyclerview.widget.RecyclerView
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.ListKeahlianAdapter
import com.xsis.android.batch217.adapters.ListTimSubAdapter
import com.xsis.android.batch217.adapters.ListTimesheetApprovalAdapter
import com.xsis.android.batch217.databases.CompanyQueryHelper
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.KeahlianQueryHelper
import com.xsis.android.batch217.databases.TimesheetQueryHelper
import com.xsis.android.batch217.models.Keahlian
import com.xsis.android.batch217.models.Timesheet
import com.xsis.android.batch217.ui.keahlian.KeahlianViewModel
import com.xsis.android.batch217.utils.*
import kotlinx.android.synthetic.main.activity_data_submit.*
import kotlinx.android.synthetic.main.activity_timesheet_approval_process.*

class DataSubmitActivity : AppCompatActivity() {

    val context = this
    lateinit var listTimesheet : List<Timesheet>
  //  var databaseQueryHelper: TimesheetQueryHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar!!.hide()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_data_submit)
        try {
            this.supportActionBar!!.hide()
        } catch (e: NullPointerException){
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

        listTimesheet = databaseQueryHelper.getTimesheetBerdasarkanWaktuDanProgress(month, year, CREATED)

        if (listTimesheet.isEmpty() || year.isEmpty() || month.isEmpty()) {
            dataNotFoundTimSub.visibility = View.VISIBLE
            listTimSubRecycler.visibility = View.GONE

            buttonNextSub.visibility = View.INVISIBLE

        } else {
            val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

            listTimSubRecycler.layoutManager = layoutManager

            val dividerItemDecoration = DividerItemDecoration(context, layoutManager.orientation)
            listTimSubRecycler.addItemDecoration(dividerItemDecoration)


            val adapterTimSub = ListTimSubAdapter(context!!, listTimesheet)
            listTimSubRecycler.adapter = adapterTimSub
            adapterTimSub.notifyDataSetChanged()

            buttonNextSub.setOnClickListener{
                submitted()
            }

        }

        buttonBackTimeSub.setOnClickListener{
            finish()
        }

    }



    fun submitted(){
        val konfirmasiSubmit = AlertDialog.Builder(context)
        konfirmasiSubmit.setMessage("DATA SUBMISSION\n\nAre you sure to submit the data ?\n\n\n")
            .setPositiveButton("SUBMIT", DialogInterface.OnClickListener{ dialog, which ->
                Toast.makeText(context,"SUKSES!!!", Toast.LENGTH_SHORT).show()
//
//                val databaseHelper = DatabaseHelper(context)
//                val db = databaseHelper.writableDatabase
//                val queryDelete = "UPDATE $TABEL_TIPE_IDENTITAS SET $IS_DELETED = 'true' WHERE $ID_IDENTITAS = $ID"
//                db.execSQL(queryDelete)
//                fragment.search2()
                val databaseHelper = DatabaseHelper(context!!)
                val databaseQueryHelper = TimesheetQueryHelper(databaseHelper)
                val db = databaseHelper.writableDatabase

                for(timesheet in listTimesheet) {
                    val queryProgress =
                        "UPDATE $TABEL_TIMESHEET SET $PROGRESS_TIMESHEET = 'Submitted' WHERE $ID_TIMESHEET = ${timesheet.id_timesheet}"
                    db.execSQL(queryProgress)
                }
                val berhasilSubmited = AlertDialog.Builder(context)
                berhasilSubmited.setMessage("DATA HAS BEEN SUBMISSION\n\n")
                    .setPositiveButton("CLOSE", DialogInterface.OnClickListener{ dialog, which ->
                        dialog.cancel()
                        finish()
                    })
                    .setCancelable(true)

                berhasilSubmited.create().show()

            })
            .setNegativeButton("CANCEL", DialogInterface.OnClickListener{ dialog, which ->
                dialog.cancel()
            })
            .setCancelable(true)

        konfirmasiSubmit.create().show()
    }
}
