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
import java.text.SimpleDateFormat
import java.util.*

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

        setReportDatePRFRequestPicker()
        isiSpinnerType()
        isiSpinnerPID()
        isiSpinnerNotebook()
        isiSpinnerBAST()
        buttonResetPRFRequestEdit.setOnClickListener{
            resetForm()
        }
        buttonSubmitPRFRequestEdit.setOnClickListener {
            validasiInput()
        }
    }

    fun validasiInput() {
        val tanggal = inputTanggalPRFEdit.text.toString().trim()
        val type = spinnerInputTypePRFEdit.selectedItemPosition
        val placement = inputPlacementPRFEdit.text.toString().trim()
        val PID = spinnerInputPIDPRFEdit.selectedItemPosition
        val location = inputLocationPRFEdit.text.toString().trim()
        val period = inputPeriodPRFEdit.text.toString().trim()
        val userName = inputUserNamePRFEdit.text.toString().trim()
        val telpMobilePhone = inputTelpPRFEdit.text.toString().trim()
        val email = inputEmailPRFEdit.text.toString().trim()
        val notebook = spinnerInputNotebookPRFEdit.selectedItemPosition
        val overtime = inputOvertimePRFEdit.text.toString().trim()
        val BAST = spinnerInputBastPRFEdit.selectedItemPosition
        val billing = inputBillingPRFEdit.text.toString().trim()

        if (type == 0) {
            inputTypePRFEdit.setHintTextColor(Color.RED)
            requiredTypePRFRequestEdit.isVisible = true
        }
        else if (placement.isEmpty()) {
            inputPlacementPRFEdit.setHintTextColor(Color.RED)
            requiredPlacementPRFRequestEdit.isVisible = true
        }
        else if (PID == 0) {
            inputPIDPRFEdit.setHintTextColor(Color.RED)
            requiredPIDPRFRequestEdit.isVisible = true
        }
        else if (period.isEmpty()) {
            inputPeriodPRFEdit.setHintTextColor(Color.RED)
            requiredPeriodPRFRequestEdit.isVisible = true
        }
        else if (userName.isEmpty()) {
            inputUserNamePRFEdit.setHintTextColor(Color.RED)
            requiredUsernamePRFRequestEdit.isVisible = true
        }
        else if (telpMobilePhone.isEmpty()) {
            inputTelpPRFEdit.setHintTextColor(Color.RED)
            requiredTelpPRFRequestEdit.isVisible = true
        }
        else if (email.isEmpty()) {
            inputTelpPRFEdit.setHintTextColor(Color.RED)
            requiredTelpPRFRequestEdit.isVisible = true
        }
        else if (notebook == 0){
            inputNotebookPRFEdit.setHintTextColor(Color.RED)
            requiredNotebookPRFRequestEdit.isVisible = true
        }
        else {

        }

    }

    fun resetForm(){
        inputTanggalPRFEdit.setText("")
        spinnerInputTypePRFEdit.setSelection(0)
        inputPlacementPRFEdit.setText("")
        spinnerInputPIDPRFEdit.setSelection(0)
        inputLocationPRFEdit.setText("")
        inputPeriodPRFEdit.setText("")
        inputUserNamePRFEdit.setText("")
        inputTelpPRFEdit.setText("")
        inputEmailPRFEdit.setText("")
        spinnerInputNotebookPRFEdit.setSelection(0)
        inputOvertimePRFEdit.setText("")
        spinnerInputBastPRFEdit.setSelection(0)
        inputBillingPRFEdit.setText("")
    }

    fun setReportDatePRFRequestPicker(){
        val today = Calendar.getInstance()
        val yearNow = today.get(Calendar.YEAR)
        val monthNow = today.get(Calendar.MONTH)
        val dayNow = today.get(Calendar.DATE)

        iconInputTanggalPRFEdit.setOnClickListener {
            val datePickerDialog = DatePickerDialog(context, R.style.CustomDatePicker, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, month, dayOfMonth)

                //konversi ke string
                val formatDate = SimpleDateFormat("MMMM dd, yyyy")
                val tanggal = formatDate.format(selectedDate.time)

                //set tampilan
                inputTanggalPRFEdit.setText(tanggal)
            }, yearNow,monthNow,dayNow )
            datePickerDialog.show()
            buttonResetPRFRequestEdit.isEnabled = true
            buttonResetPRFRequestEdit.setBackgroundResource(R.drawable.button_reset_on)
            buttonResetPRFRequestEdit.setTextColor(Color.WHITE)
        }
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

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            buttonResetPRFRequestEdit.isEnabled = true
            buttonResetPRFRequestEdit.setBackgroundResource(R.drawable.button_reset_on)
            buttonResetPRFRequestEdit.setTextColor(Color.WHITE)
        }

        override fun afterTextChanged(s: Editable) {

        }
    }
}
