package com.xsis.android.batch217.ui.timesheet.timesheet_submission

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.ListKeahlianAdapter
import com.xsis.android.batch217.adapters.ListTimSubAdapter
import com.xsis.android.batch217.databases.CompanyQueryHelper
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.TimesheetQueryHelper
import com.xsis.android.batch217.models.Keahlian
import com.xsis.android.batch217.models.Timesheet
import com.xsis.android.batch217.ui.keahlian.KeahlianViewModel
import com.xsis.android.batch217.utils.CREATED
import com.xsis.android.batch217.utils.ID_IDENTITAS
import com.xsis.android.batch217.utils.IS_DELETED
import com.xsis.android.batch217.utils.TABEL_TIPE_IDENTITAS
import kotlinx.android.synthetic.main.activity_data_submit.*

class DataSubmitActivity : AppCompatActivity() {

    val context = this

    var databaseQueryHelper: TimesheetQueryHelper? = null

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


        buttonNextSub.setOnClickListener{
            submitted()
        }

        buttonBackTimeSub.setOnClickListener{
            finish()
        }
        val layoutManager=LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        listTimSubRecycler.layoutManager=layoutManager

        val databaseHelper = DatabaseHelper(context!!)
        databaseQueryHelper = TimesheetQueryHelper(databaseHelper)

        getSemuaTimSubCreated(listTimSubRecycler, databaseQueryHelper!!)
    }

    fun getSemuaTimSubCreated(
        recyclerView: RecyclerView,
        databaseQueryHelper: TimesheetQueryHelper
    ) {
        val listTimesheet = databaseQueryHelper.getTimesheetBerdasarkanWaktuDanProgress("January", "2019", "Created")
        tampilkanListTimSub(listTimesheet, recyclerView)
    }

    fun tampilkanListTimSub(listTimesheet:List<Timesheet>, recyclerView: RecyclerView){
        val adapterTimSub = ListTimSubAdapter(context,  listTimesheet)
        recyclerView.adapter = adapterTimSub
        adapterTimSub.notifyDataSetChanged()
    }

    fun submitted(){
        val konfirmasiSubmit = AlertDialog.Builder(context)
        konfirmasiSubmit.setMessage("DATA SUBMISSION\n\nAre you sure to submit the data ?\n\n\n")
            .setPositiveButton("SUBMIT", DialogInterface.OnClickListener{ dialog, which ->
                Toast.makeText(context,"Submit Data", Toast.LENGTH_SHORT).show()
//
//                val databaseHelper = DatabaseHelper(context)
//                val db = databaseHelper.writableDatabase
//                val queryDelete = "UPDATE $TABEL_TIPE_IDENTITAS SET $IS_DELETED = 'true' WHERE $ID_IDENTITAS = $ID"
//                db.execSQL(queryDelete)
//                fragment.search2()

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
