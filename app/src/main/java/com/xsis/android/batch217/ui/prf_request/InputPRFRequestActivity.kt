package com.xsis.android.batch217.ui.prf_request

import android.app.DatePickerDialog
import android.content.ContentValues
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.core.view.isVisible
import com.xsis.android.batch217.R
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.PRFRequestQueryHelper
import com.xsis.android.batch217.utils.*
import kotlinx.android.synthetic.main.activity_edit_prfrequest.*
import kotlinx.android.synthetic.main.activity_input_prfrequest.*
import java.text.SimpleDateFormat
import java.util.*

class InputPRFRequestActivity : AppCompatActivity() {
    val context = this
    var databaseHelper = DatabaseHelper(context)
    var databaseQueryHelper = PRFRequestQueryHelper(databaseHelper)
    var buttonReset: Button? = null
    var type: Spinner? = null
    var placement: EditText? = null
    var pid: Spinner? = null
    var location: EditText? = null
    var period: EditText? = null
    var userName: EditText? = null
    var telpMobilePhone: EditText? = null
    var email: EditText? = null
    var notebook: Spinner? = null
    var overtime: EditText? = null
    var bast: Spinner? = null
    var billing: EditText? = null
    var listTypePRF: List<String>? = null

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

        type = spinnerInputTypePRF
        placement = inputPlacementPRF
        pid = spinnerInputPIDPRF
        location = inputLocationPRF
        period = inputPeriodPRF
        userName = inputUserNamePRF
        telpMobilePhone = inputTelpPRF
        email = inputEmailPRF
        notebook = spinnerInputNotebookPRF
        overtime = inputOvertimePRF
        bast = spinnerInputBastPRF
        billing = inputBillingPRF

        ubahButtonResetSpinner()

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

        placement!!.addTextChangedListener(textWatcher)
        location!!.addTextChangedListener(textWatcher)
        period!!.addTextChangedListener(textWatcher)
        userName!!.addTextChangedListener(textWatcher)
        telpMobilePhone!!.addTextChangedListener(textWatcher)
        email!!.addTextChangedListener(textWatcher)
        overtime!!.addTextChangedListener(textWatcher)
        billing!!.addTextChangedListener(textWatcher)

    }

    fun ubahButtonResetSpinner() {
        buttonReset = buttonResetPRFRequest
        type!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                buttonReset = buttonResetPRFRequest
                if (position != 0) {
                    buttonResetPRFRequest.isEnabled = true
                    ubahResetButton(context, true, buttonReset!!)
                } else {
                    ubahResetButton(context, false, buttonReset!!)
                }
            }
            override fun onNothingSelected(arg0: AdapterView<*>) {}
        }
        pid!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                buttonReset = buttonResetPRFRequest
                if (position != 0) {
                    buttonResetPRFRequest.isEnabled = true
                    ubahResetButton(context, true, buttonReset!!)
                } else {
                    ubahResetButton(context, false, buttonReset!!)
                }
            }
            override fun onNothingSelected(arg0: AdapterView<*>) {}
        }
        notebook!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                buttonReset = buttonResetPRFRequest
                if (position != 0) {
                    buttonResetPRFRequest.isEnabled = true
                    ubahResetButton(context, true, buttonReset!!)
                } else {
                    ubahResetButton(context, false, buttonReset!!)
                }
            }
            override fun onNothingSelected(arg0: AdapterView<*>) {}
        }
        bast!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                buttonReset = buttonResetPRFRequest
                if (position != 0) {
                    buttonResetPRFRequest.isEnabled = true
                    ubahResetButton(context, true, buttonReset!!)
                } else {
                    ubahResetButton(context, false, buttonReset!!)
                }
            }
            override fun onNothingSelected(arg0: AdapterView<*>) {}
        }
    }

    fun validasiInput() {
        val tanggal = inputTanggalPRF.text.toString().trim()
        println("tanggal = $tanggal")
        val type = spinnerInputTypePRF.selectedItemPosition
        val placement = placement!!.text.toString().trim()
        val PID = spinnerInputPIDPRF.selectedItemPosition
        val location = location!!.text.toString().trim()
        val period = period!!.text.toString().trim()
        val userName = userName!!.text.toString().trim()
        val telpMobilePhone = telpMobilePhone!!.text.toString().trim()
        val email = email!!.text.toString().trim()
        val notebook = spinnerInputNotebookPRF.selectedItemPosition
        val overtime = overtime!!.text.toString().trim()
        val BAST = spinnerInputBastPRF.selectedItemPosition
        val billing = billing!!.text.toString().trim()

        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z.]+"

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
        else if (email.isEmpty() or !email.matches(emailPattern.toRegex())) {
            inputEmailPRF.setHintTextColor(Color.RED)
            requiredEmailPRFRequest.isVisible = true
        }
        else if (notebook == 0){
            inputNotebookPRF.setHintTextColor(Color.RED)
            requiredNotebookPRFRequest.isVisible = true
        }
        else {
            insertKeDatabase(tanggal,
                listTypePRF!![type],
                placement,
                ARRAY_PID[PID],
                location,
                period,
                userName,
                telpMobilePhone,
                email,
                ARRAY_NOTEBOOK[notebook],
                overtime,
                ARRAY_BAST[BAST],
                billing)
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
        requiredOff()
    }

    fun insertKeDatabase(tanggal: String,
                         type: String,
                         placement: String,
                         pid: String,
                         location: String,
                         period: String,
                         userName: String,
                         telpMobilePhone: String,
                         email: String,
                         notebook: String,
                         overtime: String,
                         bast: String,
                         billing: String) {
        val listPRFRequest = databaseQueryHelper.readPlacementPRF(placement)
        if (!listPRFRequest.isEmpty()){
            //cek id_deleted
            if(listPRFRequest[0].is_Deleted == "true"){
                //update tru jadi false
                databaseQueryHelper.updatePRFRequest(tanggal, type,placement,pid,location,period,userName,telpMobilePhone,email,notebook,overtime,bast,billing)
                Toast.makeText(this, SIMPAN_DATA_BERHASIL, Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, DATA_SUDAH_ADA, Toast.LENGTH_SHORT).show()
            }
        } else {
            //jika tidak maka insert
            //cek nama
            val content = ContentValues()
            content.put(TYPE, type)
            content.put(TANGGAL, tanggal)
            content.put(PLACEMENT, placement)
            content.put(PID, pid)
            content.put(LOCATION, location)
            content.put(PERIOD, period)
            content.put(USER_NAME, userName)
            content.put(TELP_NUMBER, telpMobilePhone)
            content.put(EMAIL, email)
            content.put(NOTEBOOK, notebook)
            content.put(OVERTIME, overtime)
            content.put(BAST, bast)
            content.put(BILLING, billing)
            content.put(IS_DELETED, "false")
            val db = DatabaseHelper(this).writableDatabase
            db.insert(TABEL_PRF_REQUEST, null, content)
            Toast.makeText(this, SIMPAN_DATA_BERHASIL, Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    fun setReportDatePRFRequestPicker(){
        val today = Calendar.getInstance()
        val yearNow = today.get(Calendar.YEAR)
        val monthNow = today.get(Calendar.MONTH)
        val dayNow = today.get(Calendar.DATE)

        inputTanggalPRF.setOnClickListener {
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
        listTypePRF = databaseQueryHelper.readTypePRF()
        val adapterType = ArrayAdapter<String>(context,
            android.R.layout.simple_spinner_item,
            listTypePRF!!
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
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) { }
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            buttonReset = buttonResetPRFRequest
            val placementTeks = placement!!.text.toString().trim()
            val locationTeks = location!!.text.toString().trim()
            val periodTeks = period!!.text.toString().trim()
            val userNameTeks = userName!!.text.toString().trim()
            val telpMobilePhoneTeks = telpMobilePhone!!.text.toString().trim()
            val emailTeks = email!!.text.toString().trim()
            val overtimeTeks = overtime!!.text.toString().trim()
            val billingTeks = billing!!.text.toString().trim()

            val kondisi = !placementTeks.isEmpty() || !locationTeks.isEmpty() || !periodTeks.isEmpty()
                            || !userNameTeks.isEmpty() || !telpMobilePhoneTeks.isEmpty() || !emailTeks.isEmpty()
                            || !overtimeTeks.isEmpty() || !billingTeks.isEmpty()
            buttonResetPRFRequest.isEnabled = true
            ubahResetButton(context, kondisi, buttonReset!!)
        }
        override fun afterTextChanged(s: Editable) { }
    }

    fun requiredOff() {
        requiredTypePRFRequest.isVisible = false
        requiredPlacementPRFRequest.isVisible = false
        requiredPIDPRFRequest.isVisible = false
        requiredEmailPRFRequest.isVisible = false
        requiredPeriodPRFRequest.isVisible = false
        requiredUsernamePRFRequest.isVisible = false
        requiredTelpPRFRequest.isVisible = false
        requiredNotebookPRFRequest.isVisible = false
    }
}
