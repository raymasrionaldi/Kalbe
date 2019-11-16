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
import com.xsis.android.batch217.models.PRFRequest
import com.xsis.android.batch217.models.ProjectCreate
import com.xsis.android.batch217.models.TypePRF
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
    var tanggal: EditText? = null
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
    lateinit var listTypePRF: List<TypePRF>
    lateinit var listPID: List<ProjectCreate>

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
        } catch (e: NullPointerException) {
        }

        tanggal = inputTanggalPRF
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
        requiredOff()

        buttonBackInputPRFRequest.setOnClickListener {
            finish()
        }

        setDatePRFRequestPicker()
        isiSpinnerType()
        isiSpinnerPID()
        isiSpinnerNotebook()
        isiSpinnerBAST()

        buttonResetPRFRequest.setOnClickListener {
            resetForm()
        }

        buttonSubmitPRFRequest.setOnClickListener {
            validasiInput()
        }

        tanggal!!.addTextChangedListener(textWatcher)
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
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
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
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
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
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
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
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
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

        if (type == 0 || placement.isEmpty() || PID == 0 || period.isEmpty() || userName.isEmpty()
            || telpMobilePhone.isEmpty() || email.isEmpty() || !email.matches(emailPattern.toRegex())
            || notebook == 0
        ) {
            requiredOn()
        } else {
            insertKeDatabase(
                tanggal,
                type,
                placement,
                PID,
                location,
                period,
                userName,
                telpMobilePhone,
                email,
                ARRAY_NOTEBOOK[notebook],
                overtime,
                ARRAY_BAST[BAST],
                billing
            )
        }

    }

    fun requiredOn() {
        inputTypePRF.setHintTextColor(Color.RED)
        requiredTypePRFRequest.isVisible = true
        inputPlacementPRF.setHintTextColor(Color.RED)
        requiredPlacementPRFRequest.isVisible = true
        requiredPIDPRFRequest.isVisible = true
        inputPeriodPRF.setHintTextColor(Color.RED)
        requiredPeriodPRFRequest.isVisible = true
        inputUserNamePRF.setHintTextColor(Color.RED)
        requiredUsernamePRFRequest.isVisible = true
        inputTelpPRF.setHintTextColor(Color.RED)
        requiredTelpPRFRequest.isVisible = true
        inputEmailPRF.setHintTextColor(Color.RED)
        requiredEmailPRFRequest.isVisible = true
        requiredNotebookPRFRequest.isVisible = true
    }

    fun resetForm() {
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

    fun insertKeDatabase(
        tanggal: String,
        type: Int,
        placement: String,
        pid: Int,
        location: String,
        period: String,
        userName: String,
        telpMobilePhone: String,
        email: String,
        notebook: String,
        overtime: String,
        bast: String,
        billing: String
    ) {
        val model = PRFRequest()
        model.id_prf_request = model.id_prf_request
        model.tanggal = tanggal
        model.type = type.toString()
        model.placement = placement
        model.pid = pid.toString()
        model.location = location
        model.period = period
        model.user_name = userName
        model.telp_number = telpMobilePhone
        model.email = email
        model.notebook = notebook
        model.overtime = overtime
        model.bast = bast
        model.billing = billing

        if (databaseQueryHelper!!.tambahPRFRequest(model) == -1L) {
            Toast.makeText(context, SIMPAN_DATA_GAGAL, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, SIMPAN_DATA_BERHASIL, Toast.LENGTH_SHORT)
                .show()
        }
        finish()
    }

    fun setDatePRFRequestPicker() {
        val today = Calendar.getInstance()
        val yearNow = today.get(Calendar.YEAR)
        val monthNow = today.get(Calendar.MONTH)
        val dayNow = today.get(Calendar.DATE)

        inputTanggalPRF.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                context,
                R.style.CustomDatePicker,
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    val selectedDate = Calendar.getInstance()
                    selectedDate.set(year, month, dayOfMonth)

                    //konversi ke string
                    val formatDate = SimpleDateFormat("MMMM dd, yyyy")
                    val tanggal = formatDate.format(selectedDate.time)

                    //set tampilan
                    inputTanggalPRF.setText(tanggal)
                },
                yearNow,
                monthNow,
                dayNow
            )
            datePickerDialog.show()
        }

    }

    fun isiSpinnerType() {
        listTypePRF = databaseQueryHelper.readTypePRFNew()
        val isilistType = listTypePRF.map {
            it.nama_type_prf
        }.toMutableList()
        isilistType.add(0, "Type *")
        val adapterType = ArrayAdapter<String>(
            context,
            android.R.layout.simple_spinner_item,
            isilistType
        )
        adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerInputTypePRF.adapter = adapterType
    }

    fun isiSpinnerPID() {
        listPID = databaseQueryHelper.readPIDPRFNew()
        val isilistPID = listPID.map {
            it.PID
        }.toMutableList()
        isilistPID.add(0, "PID *")
        val adapterPID = ArrayAdapter<String>(
            context,
            android.R.layout.simple_spinner_item,
            isilistPID
        )
        adapterPID.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerInputPIDPRF.adapter = adapterPID
    }

    fun isiSpinnerNotebook() {
        val adapterNotebook = ArrayAdapter<String>(
            context,
            android.R.layout.simple_spinner_item,
            ARRAY_NOTEBOOK
        )
        adapterNotebook.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerInputNotebookPRF.adapter = adapterNotebook
    }

    fun isiSpinnerBAST() {
        val adapterBAST = ArrayAdapter<String>(
            context,
            android.R.layout.simple_spinner_item,
            ARRAY_BAST
        )
        adapterBAST.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerInputBastPRF.adapter = adapterBAST
    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            buttonReset = buttonResetPRFRequest
            val tanggal = tanggal!!.text.toString().trim()
            val placementTeks = placement!!.text.toString().trim()
            val locationTeks = location!!.text.toString().trim()
            val periodTeks = period!!.text.toString().trim()
            val userNameTeks = userName!!.text.toString().trim()
            val telpMobilePhoneTeks = telpMobilePhone!!.text.toString().trim()
            val emailTeks = email!!.text.toString().trim()
            val overtimeTeks = overtime!!.text.toString().trim()
            val billingTeks = billing!!.text.toString().trim()

            val kondisi =
                tanggal.isNotEmpty() || !placementTeks.isEmpty() || !locationTeks.isEmpty() || !periodTeks.isEmpty()
                        || !userNameTeks.isEmpty() || !telpMobilePhoneTeks.isEmpty() || !emailTeks.isEmpty()
                        || !overtimeTeks.isEmpty() || !billingTeks.isEmpty()
            buttonResetPRFRequest.isEnabled = true
            ubahResetButton(context, kondisi, buttonReset!!)
        }

        override fun afterTextChanged(s: Editable) {}
    }

    fun requiredOff() {
        requiredTypePRFRequest.isVisible = false
        inputPlacementPRF.setHintTextColor(Color.GRAY)
        requiredPlacementPRFRequest.isVisible = false
        requiredPIDPRFRequest.isVisible = false
        requiredEmailPRFRequest.isVisible = false
        requiredPeriodPRFRequest.isVisible = false
        inputPeriodPRF.setHintTextColor(Color.GRAY)
        requiredUsernamePRFRequest.isVisible = false
        inputUserNamePRF.setHintTextColor(Color.GRAY)
        requiredTelpPRFRequest.isVisible = false
        inputEmailPRF.setHintTextColor(Color.GRAY)
        requiredNotebookPRFRequest.isVisible = false
    }
}
