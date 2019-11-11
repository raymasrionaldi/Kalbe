package com.xsis.android.batch217.ui.prf_request

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.ArrayAdapter
import com.xsis.android.batch217.R
import com.xsis.android.batch217.utils.*
import kotlinx.android.synthetic.main.activity_edit_prfrequest.*

class EditPRFRequestActivity : AppCompatActivity() {
    val context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar!!.hide()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_edit_prfrequest)
        try {
            this.supportActionBar!!.hide()
        } catch (e: NullPointerException){
        }

        isiSpinnerTanggal()
        isiSpinnerType()
        isiSpinnerPID()
        isiSpinnerNotebook()
        isiSpinnerBAST()
    }

    fun isiSpinnerTanggal(){
        val adapterTanggal = ArrayAdapter<String>(context,
            android.R.layout.simple_spinner_item,
            ARRAY_TANGGAL
        )
        adapterTanggal.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerInputTanggalPRFEdit.adapter = adapterTanggal
    }

    fun isiSpinnerType(){
        val adapterType = ArrayAdapter<String>(context,
            android.R.layout.simple_spinner_item,
            ARRAY_TYPE
        )
        adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerInputTypePRFEdit.adapter = adapterType
    }

    fun isiSpinnerPID(){
        val adapterPID = ArrayAdapter<String>(context,
            android.R.layout.simple_spinner_item,
            ARRAY_PID
        )
        adapterPID.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerInputPIDPRFEdit.adapter = adapterPID
    }

    fun isiSpinnerNotebook(){
        val adapterNotebook = ArrayAdapter<String>(context,
            android.R.layout.simple_spinner_item,
            ARRAY_NOTEBOOK
        )
        adapterNotebook.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerInputNotebookPRFEdit.adapter = adapterNotebook
    }

    fun isiSpinnerBAST(){
        val adapterBAST = ArrayAdapter<String>(context,
            android.R.layout.simple_spinner_item,
            ARRAY_BAST
        )
        adapterBAST.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerInputBastPRFEdit.adapter = adapterBAST
    }
}
