package com.xsis.android.batch217.ui.timesheet.timesheet_submission

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.xsis.android.batch217.R
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.utils.ID_IDENTITAS
import com.xsis.android.batch217.utils.IS_DELETED
import com.xsis.android.batch217.utils.TABEL_TIPE_IDENTITAS
import kotlinx.android.synthetic.main.activity_data_submit.*

class DataSubmitActivity : AppCompatActivity() {

    val context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_submit)

        buttonNextSub.setOnClickListener{
            submitted()
        }
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
