package com.xsis.android.batch217.ui.prf_request

import android.app.DatePickerDialog
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Window
import android.view.WindowManager
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import com.xsis.android.batch217.R
import com.xsis.android.batch217.utils.*
import kotlinx.android.synthetic.main.activity_input_prfcandidate.*
import java.text.SimpleDateFormat
import java.util.*

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

        setReportDatePRFCandidatePicker()
        isiSpinnerName()
        isiSpinnerPosition()
        isiSpinnerSRFNumber()
        isiSpinnerCandidateStatus()

        buttonSubmitPRFCandidate.setOnClickListener {
            validasiInput()
        }
        buttonResetPRFCandidate.setOnClickListener {
            resetForm()
        }
    }

    fun validasiInput() {
        val name = spinnerInputNamaPRFCandidate.selectedItemPosition
        val batch = inputBatchBootcampPRFCandidate.text.toString().trim()
        val position = spinnerInputPositionPRFCandidate.selectedItemPosition
        val placementDate = inputPlacementDatePRFCandidate.text.toString()
        val SRFNumber = spinnerSRFNumberPRFCandidate.selectedItemPosition
        val customAllowence = inputCustomeAllowencePRFCandidate.text.toString()
        val candidateStatus = spinnerInputCandidateStatusPRFCandidate.selectedItemPosition
        val signContractDate = inputSignContractDatePRFCandidate.text.toString().trim()
        val notes = inputNotesPRFCandidate.text.toString().trim()

        if (name == 0) {
            requiredNamePRFCandidate.isVisible = true
        }
        else if (position == 0) {
            requiredPositionPRFCandidate.isVisible = true
        }
        else if (placementDate.isEmpty()) {
            inputPlacementDatePRFCandidate.setHintTextColor(Color.RED)
            requiredPlacementDatePRFCandidate.isVisible = true
        }
        else if (SRFNumber == 0) {
            requiredSRFNumberPRFCandidate.isVisible = true
        }
        else if (candidateStatus == 0){
            requiredCandidateStatusPRFCandidate.isVisible = true
        }
        else {

        }

    }

    fun resetForm() {
        spinnerInputNamaPRFCandidate.setSelection(0)
        inputBatchBootcampPRFCandidate.setText("")
        spinnerInputPositionPRFCandidate.setSelection(0)
        inputPlacementDatePRFCandidate.setText("")
        spinnerSRFNumberPRFCandidate.setSelection(0)
        inputCustomeAllowencePRFCandidate.setText("")
        spinnerInputCandidateStatusPRFCandidate.setSelection(0)
        inputSignContractDatePRFCandidate.setText("")
        inputNotesPRFCandidate.setText("")
    }

    fun setReportDatePRFCandidatePicker(){
        val today = Calendar.getInstance()
        val yearNow = today.get(Calendar.YEAR)
        val monthNow = today.get(Calendar.MONTH)
        val dayNow = today.get(Calendar.DATE)

        iconInputTanggalPRFCandidate.setOnClickListener {
            val datePickerDialog = DatePickerDialog(context, R.style.CustomDatePicker, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, month, dayOfMonth)

                //konversi ke string
                val formatDate = SimpleDateFormat("MMMM dd, yyyy")
                val tanggal = formatDate.format(selectedDate.time)

                //set tampilan
                inputPlacementDatePRFCandidate.setText(tanggal)
            }, yearNow,monthNow,dayNow )
            datePickerDialog.show()
            buttonResetPRFCandidate.isEnabled = true
            buttonResetPRFCandidate.setBackgroundResource(R.drawable.button_reset_on)
            buttonResetPRFCandidate.setTextColor(Color.WHITE)
        }

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

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            buttonResetPRFCandidate.isEnabled = true
            buttonResetPRFCandidate.setBackgroundResource(R.drawable.button_reset_on)
            buttonResetPRFCandidate.setTextColor(Color.WHITE)
        }

        override fun afterTextChanged(s: Editable) {

        }
    }
}
