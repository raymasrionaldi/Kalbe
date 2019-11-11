package com.xsis.android.batch217.ui.prf_request

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.ArrayAdapter
import com.xsis.android.batch217.R
import com.xsis.android.batch217.utils.*
import kotlinx.android.synthetic.main.activity_input_prfcandidate.*

class InputPRFCandidateActivity : AppCompatActivity() {
    val context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar!!.hide()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_input_prfcandidate)
        try {
            this.supportActionBar!!.hide()
        } catch (e: NullPointerException){
        }

        buttonBackInputPRFCandidate.setOnClickListener {
            finish()
        }

        isiSpinnerName()
        isiSpinnerPosition()
        isiSpinnerSRFNumber()
        isiSpinnerCandidateStatus()
    }

    fun isiSpinnerName(){
        val adapterName = ArrayAdapter<String>(context,
            android.R.layout.simple_spinner_item,
            ARRAY_NAME
        )
        adapterName.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerInputNamaPRFCandidate.adapter = adapterName
    }

    fun isiSpinnerPosition(){
        val adapterPosition = ArrayAdapter<String>(context,
            android.R.layout.simple_spinner_item,
            ARRAY_POSITION
        )
        adapterPosition.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerInputPositionPRFCandidate.adapter = adapterPosition
    }

    fun isiSpinnerSRFNumber(){
        val adapterSRFNumber = ArrayAdapter<String>(context,
            android.R.layout.simple_spinner_item,
            ARRAY_SRF_NUMBER
        )
        adapterSRFNumber.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerSRFNumberPRFCandidate.adapter = adapterSRFNumber
    }

    fun isiSpinnerCandidateStatus(){
        val adapterCandidateStatus = ArrayAdapter<String>(context,
            android.R.layout.simple_spinner_item,
            ARRAY_CANDIDATE_STATUS
        )
        adapterCandidateStatus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerInputCandidateStatusPRFCandidate.adapter = adapterCandidateStatus
    }
}
