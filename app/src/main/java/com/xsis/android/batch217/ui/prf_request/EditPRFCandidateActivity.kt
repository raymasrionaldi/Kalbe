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
import com.xsis.android.batch217.utils.ARRAY_CANDIDATE_STATUS
import com.xsis.android.batch217.utils.ARRAY_NAME
import com.xsis.android.batch217.utils.ARRAY_POSITION
import com.xsis.android.batch217.utils.ARRAY_SRF_NUMBER
import kotlinx.android.synthetic.main.activity_edit_prfcandidate.*
import java.text.SimpleDateFormat
import java.util.*

class EditPRFCandidateActivity : AppCompatActivity() {
    val context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar!!.hide()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_edit_prfcandidate)
        try {
            this.supportActionBar!!.hide()
        } catch (e: NullPointerException){
        }

        setReportDatePRFCandidatePicker()
        isiSpinnerName()
        isiSpinnerPosition()
        isiSpinnerSRFNumber()
        isiSpinnerCandidateStatus()

        buttonSubmitPRFCandidateEdit.setOnClickListener {
            validasiInput()
        }
        buttonResetPRFCandidateEdit.setOnClickListener {
            resetForm()
        }
    }

    fun validasiInput() {
        val name = spinnerInputNamaPRFCandidateEdit.selectedItemPosition
        val batch = inputBatchBootcampPRFCandidateEdit.text.toString().trim()
        val position = spinnerInputPositionPRFCandidateEdit.selectedItemPosition
        val placementDate = inputPlacementDatePRFCandidateEdit.text.toString()
        val SRFNumber = spinnerSRFNumberPRFCandidateEdit.selectedItemPosition
        val customAllowence = inputCustomeAllowencePRFCandidateEdit.text.toString()
        val candidateStatus = spinnerInputCandidateStatusPRFCandidateEdit.selectedItemPosition
        val signContractDate = inputSignContractDatePRFCandidateEdit.text.toString().trim()
        val notes = inputNotesPRFCandidate.text.toString().trim()

        if (name == 0) {
            requiredNamePRFCandidateEdit.isVisible = true
        }
        else if (position == 0) {
            requiredPositionPRFCandidateEdit.isVisible = true
        }
        else if (placementDate.isEmpty()) {
            inputPlacementDatePRFCandidateEdit.setHintTextColor(Color.RED)
            requiredPlacementDatePRFCandidateEdit.isVisible = true
        }
        else if (SRFNumber == 0) {
            requiredSRFNumberPRFCandidateEdit.isVisible = true
        }
        else if (candidateStatus == 0){
            requiredCandidateStatusPRFCandidateEdit.isVisible = true
        }
        else {

        }

    }

    fun resetForm() {
        spinnerInputNamaPRFCandidateEdit.setSelection(0)
        inputBatchBootcampPRFCandidateEdit.setText("")
        spinnerInputPositionPRFCandidateEdit.setSelection(0)
        inputPlacementDatePRFCandidateEdit.setText("")
        spinnerSRFNumberPRFCandidateEdit.setSelection(0)
        inputCustomeAllowencePRFCandidateEdit.setText("")
        spinnerInputCandidateStatusPRFCandidateEdit.setSelection(0)
        inputSignContractDatePRFCandidateEdit.setText("")
        inputNotesPRFCandidate.setText("")
    }

    fun setReportDatePRFCandidatePicker(){
        val today = Calendar.getInstance()
        val yearNow = today.get(Calendar.YEAR)
        val monthNow = today.get(Calendar.MONTH)
        val dayNow = today.get(Calendar.DATE)

        iconInputTanggalPRFCandidateEdit.setOnClickListener {
            val datePickerDialog = DatePickerDialog(context, R.style.CustomDatePicker, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, month, dayOfMonth)

                //konversi ke string
                val formatDate = SimpleDateFormat("MMMM dd, yyyy")
                val tanggal = formatDate.format(selectedDate.time)

                //set tampilan
                inputPlacementDatePRFCandidateEdit.setText(tanggal)
            }, yearNow,monthNow,dayNow )
            datePickerDialog.show()
            buttonResetPRFCandidateEdit.isEnabled = true
            buttonResetPRFCandidateEdit.setBackgroundResource(R.drawable.button_reset_on)
            buttonResetPRFCandidateEdit.setTextColor(Color.WHITE)
        }
    }

    fun isiSpinnerName(){
        val adapterName = ArrayAdapter<String>(context,
            android.R.layout.simple_spinner_item,
            ARRAY_NAME
        )
        adapterName.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerInputNamaPRFCandidateEdit.adapter = adapterName
    }

    fun isiSpinnerPosition(){
        val adapterPosition = ArrayAdapter<String>(context,
            android.R.layout.simple_spinner_item,
            ARRAY_POSITION
        )
        adapterPosition.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerInputPositionPRFCandidateEdit.adapter = adapterPosition
    }

    fun isiSpinnerSRFNumber(){
        val adapterSRFNumber = ArrayAdapter<String>(context,
            android.R.layout.simple_spinner_item,
            ARRAY_SRF_NUMBER
        )
        adapterSRFNumber.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerSRFNumberPRFCandidateEdit.adapter = adapterSRFNumber
    }

    fun isiSpinnerCandidateStatus(){
        val adapterCandidateStatus = ArrayAdapter<String>(context,
            android.R.layout.simple_spinner_item,
            ARRAY_CANDIDATE_STATUS
        )
        adapterCandidateStatus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerInputCandidateStatusPRFCandidateEdit.adapter = adapterCandidateStatus
    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            buttonResetPRFCandidateEdit.isEnabled = true
            buttonResetPRFCandidateEdit.setBackgroundResource(R.drawable.button_reset_on)
            buttonResetPRFCandidateEdit.setTextColor(Color.WHITE)
        }

        override fun afterTextChanged(s: Editable) {

        }
    }
}
