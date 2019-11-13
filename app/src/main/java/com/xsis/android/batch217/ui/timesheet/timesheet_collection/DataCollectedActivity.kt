package com.xsis.android.batch217.ui.timesheet.timesheet_collection

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import com.xsis.android.batch217.R
import kotlinx.android.synthetic.main.activity_data_collected.*
import kotlinx.android.synthetic.main.activity_employee_training_form.*

class DataCollectedActivity : AppCompatActivity() {

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

        setContentView(R.layout.activity_data_collected)

        buttonBackCollection.setOnClickListener{
            finish()
        }

        buttonCheckCollection.setOnClickListener{
            collected()
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

