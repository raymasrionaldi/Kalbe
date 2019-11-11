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
import kotlinx.android.synthetic.main.activity_edit_prfrequest.*
import kotlinx.android.synthetic.main.activity_input_prfrequest.*
import java.text.SimpleDateFormat
import java.util.*

class InputPRFRequestActivity : AppCompatActivity() {
    val context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar!!.hide()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_input_prfrequest)
        try {
            this.supportActionBar!!.hide()
        } catch (e: NullPointerException){
        }

        buttonBackInputPRFRequest.setOnClickListener{
            finish()
        }

        setReportDatePRFRequestPicker()
        isiSpinnerType()
        isiSpinnerPID()
        isiSpinnerNotebook()
        isiSpinnerBAST()

        buttonResetPRFRequest.setOnClickListener{
            resetForm()
        }

        buttonSubmitPRFRequest.setOnClickListener {
            validasiInput()
        }

    }

    fun validasiInput() {
        val tanggal = inputTanggalPRF.text.toString().trim()
        val type = spinnerInputTypePRF.selectedItemPosition
        val placement = inputPlacementPRF.text.toString().trim()
        val PID = spinnerInputPIDPRF.selectedItemPosition
        val location = inputLocationPRF.text.toString().trim()
        val period = inputPeriodPRF.text.toString().trim()
        val userName = inputUserNamePRF.text.toString().trim()
        val telpMobilePhone = inputTelpPRF.text.toString().trim()
        val email = inputEmailPRF.text.toString().trim()
        val notebook = spinnerInputNotebookPRF.selectedItemPosition
        val overtime = inputOvertimePRF.text.toString().trim()
        val BAST = spinnerInputBastPRF.selectedItemPosition
        val billing = inputBillingPRF.text.toString().trim()

        if (type == 0) {
            inputTypePRF.setHintTextColor(Color.RED)
            requiredTypePRFRequest.isVisible = true
        }
        else if (placement.isEmpty()) {
            inputPlacementPRF.setHintTextColor(Color.RED)
            requiredPlacementPRFRequest.isVisible = true
        }
        else if (PID == 0) {
            inputPIDPRF.setHintTextColor(Color.RED)
            requiredPIDPRFRequest.isVisible = true
        }
        else if (period.isEmpty()) {
            inputPeriodPRF.setHintTextColor(Color.RED)
            requiredPeriodPRFRequest.isVisible = true
        }
        else if (userName.isEmpty()) {
            inputUserNamePRF.setHintTextColor(Color.RED)
            requiredUsernamePRFRequest.isVisible = true
        }
        else if (telpMobilePhone.isEmpty()) {
            inputTelpPRF.setHintTextColor(Color.RED)
            requiredTelpPRFRequest.isVisible = true
        }
        else if (email.isEmpty()) {
            inputTelpPRF.setHintTextColor(Color.RED)
            requiredTelpPRFRequest.isVisible = true
        }
        else if (notebook == 0){
            inputNotebookPRF.setHintTextColor(Color.RED)
            requiredNotebookPRFRequest.isVisible = true
        }
        else {

        }

    }

    fun resetForm(){
        inputTanggalPRF.setText("")
        spinnerInputTypePRF.setSelection(0)
        inputPlacementPRF.setText("")
        spinnerInputPIDPRF.setSelection(0)
        inputLocationPRF.setText("")
        inputPeriodPRF.setText("")
        inputUserNamePRF.setText("")
        inputTelpPRF.setText("")
        inputEmailPRF.setText("")
        spinnerInputNotebookPRF.setSelection(0)
        inputOvertimePRF.setText("")
        spinnerInputBastPRF.setSelection(0)
        inputBillingPRF.setText("")
    }

    fun insertKeDatabase() {

    }

    fun setReportDatePRFRequestPicker(){
        val today = Calendar.getInstance()
        val yearNow = today.get(Calendar.YEAR)
        val monthNow = today.get(Calendar.MONTH)
        val dayNow = today.get(Calendar.DATE)

        iconInputTanggalPRF.setOnClickListener {
            val datePickerDialog = DatePickerDialog(context, R.style.CustomDatePicker, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, month, dayOfMonth)

                //konversi ke string
                val formatDate = SimpleDateFormat("MMMM dd, yyyy")
                val tanggal = formatDate.format(selectedDate.time)

                //set tampilan
                inputTanggalPRF.setText(tanggal)
            }, yearNow,monthNow,dayNow )
            datePickerDialog.show()
            buttonResetPRFRequest.isEnabled = true
            buttonResetPRFRequest.setBackgroundResource(R.drawable.button_reset_on)
            buttonResetPRFRequest.setTextColor(Color.WHITE)
        }

    }

    fun isiSpinnerType(){
        val adapterType = ArrayAdapter<String>(context,
            android.R.layout.simple_spinner_item,
            ARRAY_TYPE
        )
        adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerInputTypePRF.adapter = adapterType
    }

    fun isiSpinnerPID(){
        val adapterPID = ArrayAdapter<String>(context,
            android.R.layout.simple_spinner_item,
            ARRAY_PID
        )
        adapterPID.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerInputPIDPRF.adapter = adapterPID
    }

    fun isiSpinnerNotebook(){
        val adapterNotebook = ArrayAdapter<String>(context,
            android.R.layout.simple_spinner_item,
            ARRAY_NOTEBOOK
        )
        adapterNotebook.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerInputNotebookPRF.adapter = adapterNotebook
    }

    fun isiSpinnerBAST(){
        val adapterBAST = ArrayAdapter<String>(context,
            android.R.layout.simple_spinner_item,
            ARRAY_BAST
        )
        adapterBAST.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerInputBastPRF.adapter = adapterBAST
    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            val buttonReset = buttonResetPRFRequest
            val tanggal = inputTanggalPRF.text.toString().trim()
            val placement = inputPlacementPRF.text.toString().trim()
            val location = inputLocationPRF.text.toString().trim()
            val period = inputPeriodPRF.text.toString().trim()
            val userName = inputUserNamePRF.text.toString().trim()
            val telpMobilePhone = inputTelpPRF.text.toString().trim()
            val email = inputEmailPRF.text.toString().trim()
            val overtime = inputOvertimePRF.text.toString().trim()
            val billing = inputBillingPRF.text.toString().trim()
            val type = spinnerInputTypePRF.selectedItemPosition
            val PID = spinnerInputPIDPRF.selectedItemPosition
            val notebook = spinnerInputNotebookPRF.selectedItemPosition
            val BAST = spinnerInputBastPRF.selectedItemPosition


            val kondisi = !tanggal.isEmpty() || !placement.isEmpty()
                                    || !location.isEmpty() || !period.isEmpty()
                                    || !userName.isEmpty() || !telpMobilePhone.isEmpty()
                                    || !email.isEmpty() || !overtime.isEmpty()
                                    || !billing.isEmpty() || type != 0 || PID != 0
                                    || notebook != 0 || BAST != 0

            ubahResetButton(context, kondisi, buttonReset)
        }

        override fun afterTextChanged(s: Editable) {

        }
    }


}
