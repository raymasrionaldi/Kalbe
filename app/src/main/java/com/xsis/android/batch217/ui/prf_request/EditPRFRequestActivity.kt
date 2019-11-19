package com.xsis.android.batch217.ui.prf_request

import android.app.DatePickerDialog
import android.content.ContentValues
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
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
import java.text.SimpleDateFormat
import java.util.*

class EditPRFRequestActivity : AppCompatActivity() {
    val context = this
    var databaseHelper = DatabaseHelper(context)
    var databaseQueryHelper = PRFRequestQueryHelper(databaseHelper)
    var data = PRFRequest()
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
    var ID_prf_request = 0
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
        setContentView(R.layout.activity_edit_prfrequest)
        try {
            this.supportActionBar!!.hide()
        } catch (e: NullPointerException) {
        }


        tanggal = inputTanggalPRFEdit
        type = spinnerInputTypePRFEdit
        placement = inputPlacementPRFEdit
        pid = spinnerInputPIDPRFEdit
        location = inputLocationPRFEdit
        period = inputPeriodPRFEdit
        userName = inputUserNamePRFEdit
        telpMobilePhone = inputTelpPRFEdit
        email = inputEmailPRFEdit
        notebook = spinnerInputNotebookPRFEdit
        overtime = inputOvertimePRFEdit
        bast = spinnerInputBastPRFEdit
        billing = inputBillingPRFEdit


        ubahButtonResetSpinner()
        isiSpinnerType()
        isiSpinnerPID()
        isiSpinnerNotebook()
        isiSpinnerBAST()

        val bundle: Bundle? = intent.extras
        bundle?.let {
            ID_prf_request = bundle!!.getInt(ID_PRF_REQUEST)
            loadDataPRFRequest(ID_prf_request)
        }

        buttonBackInputPRFRequestEdit.setOnClickListener {
            finish()
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
        buttonReset = buttonResetPRFRequestEdit
        type!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                buttonReset = buttonResetPRFRequestEdit
                if (position != 0) {
                    buttonResetPRFRequestEdit.isEnabled = true
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
                buttonReset = buttonResetPRFRequestEdit
                if (position != 0) {
                    buttonResetPRFRequestEdit.isEnabled = true
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
                buttonReset = buttonResetPRFRequestEdit
                if (position != 0) {
                    buttonResetPRFRequestEdit.isEnabled = true
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
                buttonReset = buttonResetPRFRequestEdit
                if (position >= 0) {
                    buttonResetPRFRequestEdit.isEnabled = true
                    ubahResetButton(context, true, buttonReset!!)
                }
//                else {
//                    ubahResetButton(context, false, buttonReset!!)
//                }
            }

            override fun onNothingSelected(arg0: AdapterView<*>) {}
        }
    }

    fun validasiInput(id: Int) {
        val tanggal = inputTanggalPRFEdit.text.toString().trim()
        val typePosition = spinnerInputTypePRFEdit.selectedItemPosition
        val typeItem = spinnerInputTypePRFEdit.selectedItem.toString()
        val placement = placement!!.text.toString().trim()
        val pidPosition = spinnerInputPIDPRFEdit.selectedItemPosition
        val pidItem = spinnerInputPIDPRFEdit.selectedItem.toString()
        val location = location!!.text.toString().trim()
        val period = period!!.text.toString().trim()
        val userName = userName!!.text.toString().trim()
        val telpMobilePhone = telpMobilePhone!!.text.toString().trim()
        val email = email!!.text.toString().trim()
        val notebook = spinnerInputNotebookPRFEdit.selectedItemPosition
        val overtime = overtime!!.text.toString().trim()
        val BAST = spinnerInputBastPRFEdit.selectedItemPosition
        val billing = billing!!.text.toString().trim()

        var isValid = true
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z.]+"

        if (typePosition == 0) {
            isValid = false
            requiredTypePRFRequestEdit.isVisible = true
        } else {
            requiredTypePRFRequestEdit.isVisible = false
        }
        if ( placement.isEmpty()) {
            isValid = false
            inputPlacementPRFEdit.setHintTextColor(Color.RED)
            requiredPlacementPRFRequestEdit.isVisible = true
        } else {
            requiredPlacementPRFRequestEdit.isVisible = false
        }
        if (pidPosition == 0) {
            isValid = false
            requiredPIDPRFRequestEdit.isVisible = true
        } else {
            requiredPIDPRFRequestEdit.isVisible = false
        }

        if (period.isEmpty()) {
            isValid = false
            inputPeriodPRFEdit.setHintTextColor(Color.RED)
            requiredPeriodPRFRequestEdit.isVisible = true
        } else {
            requiredPeriodPRFRequestEdit.isVisible = false
        }
        if (userName.isEmpty()) {
            isValid = false
            inputUserNamePRFEdit.setHintTextColor(Color.RED)
            requiredUsernamePRFRequestEdit.isVisible = true
        } else {
            requiredUsernamePRFRequestEdit.isVisible = false
        }
        if (telpMobilePhone.isEmpty()) {
            isValid = false
            inputTelpPRFEdit.setHintTextColor(Color.RED)
            requiredTelpPRFRequestEdit.isVisible = true
        } else {
            requiredTelpPRFRequestEdit.isVisible = false
        }

        if (email.isEmpty() || !email.matches(emailPattern.toRegex())) {
            isValid = false
            inputEmailPRFEdit.setHintTextColor(Color.RED)
            requiredEmailPRFRequestEdit.isVisible = true
        } else {
            requiredEmailPRFRequestEdit.isVisible = false
        }

        if (notebook == 0) {
            isValid = false
            requiredNotebookPRFRequestEdit.isVisible = true
        } else {
            requiredNotebookPRFRequestEdit.isVisible = false
        }


        if (isValid == true) {
            if (BAST == 0) {
                val isiBast = ""
                insertKeDatabase(
                    id,
                    tanggal,
                    typeItem,
                    placement,
                    pidItem,
                    location,
                    period,
                    userName,
                    telpMobilePhone,
                    email,
                    ARRAY_NOTEBOOK[notebook],
                    overtime,
                    isiBast,
                    billing
                )
            }
            else {
                insertKeDatabase(
                    id,
                    tanggal,
                    typeItem,
                    placement,
                    pidItem,
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

    }

    fun resetForm() {
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
        requiredOff()
    }

    fun insertKeDatabase(
        id: Int,
        tanggal: String,
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
        billing: String
    ) {
        var cariType = databaseQueryHelper.cariType(type)
        var cariPid = databaseQueryHelper.cariPid(pid)
        databaseQueryHelper.updateIsi(
            id,
            tanggal,
            cariType.toString(),
            placement,
            cariPid.toString(),
            location,
            period,
            userName,
            telpMobilePhone,
            email,
            notebook,
            overtime,
            bast,
            billing
        )
        Toast.makeText(context, EDIT_DATA_BERHASIL, Toast.LENGTH_SHORT).show()
        finish()
    }

    fun setDatePRFRequestPicker() {

        val autoDate = inputTanggalPRFEdit.text.toString()
        val calendar = Calendar.getInstance()
        val formatter = SimpleDateFormat(DATE_PATTERN)
        if (autoDate.isNotEmpty()) {
            calendar.time = formatter.parse(autoDate)
        }
        val yearNow = calendar.get(Calendar.YEAR)
        val monthNow = calendar.get(Calendar.MONTH)
        val dayNow = calendar.get(Calendar.DATE)

        inputTanggalPRFEdit.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                context,
                R.style.CustomDatePicker,
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    val selectedDate = Calendar.getInstance()
                    selectedDate.set(year, month, dayOfMonth)

                    //konversi ke string
                    val formatDate = SimpleDateFormat(DATE_PATTERN)
                    val tanggal = formatDate.format(selectedDate.time)

                    //set tampilan
                    inputTanggalPRFEdit.setText(tanggal)
                },
                yearNow,
                monthNow,
                dayNow
            )
            datePickerDialog.show()
        }
    }

    fun isiSpinnerType(): MutableList<String?> {
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
        spinnerInputTypePRFEdit.adapter = adapterType
        return isilistType
    }

    fun isiSpinnerPID(): List<String> {
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
        spinnerInputPIDPRFEdit.adapter = adapterPID
        return isilistPID
    }

    fun isiSpinnerNotebook() {
        val adapterNotebook = ArrayAdapter<String>(
            context,
            android.R.layout.simple_spinner_item,
            ARRAY_NOTEBOOK
        )
        adapterNotebook.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerInputNotebookPRFEdit.adapter = adapterNotebook
    }

    fun isiSpinnerBAST() {
        val adapterBAST = ArrayAdapter<String>(
            context,
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
            buttonReset = buttonResetPRFRequestEdit
            val tanggalTeks = tanggal!!.text.toString().trim()
            val placementTeks = placement!!.text.toString().trim()
            val locationTeks = location!!.text.toString().trim()
            val periodTeks = period!!.text.toString().trim()
            val userNameTeks = userName!!.text.toString().trim()
            val telpMobilePhoneTeks = telpMobilePhone!!.text.toString().trim()
            val emailTeks = email!!.text.toString().trim()
            val overtimeTeks = overtime!!.text.toString().trim()
            val billingTeks = billing!!.text.toString().trim()

            val kondisi =
                tanggalTeks!!.isNotEmpty() || !placementTeks.isEmpty() || !locationTeks.isEmpty() || !periodTeks.isEmpty()
                        || !userNameTeks.isEmpty() || !telpMobilePhoneTeks.isEmpty() || !emailTeks.isEmpty()
                        || !overtimeTeks.isEmpty() || !billingTeks.isEmpty()

            ubahResetButton(context, kondisi, buttonReset!!)
        }

        override fun afterTextChanged(s: Editable) {

        }
    }

    fun loadDataPRFRequest(id: Int) {
        println("------------------------ $id")
        val db = databaseHelper.readableDatabase

        val projection = arrayOf<String>(
            ID_PRF_REQUEST, TANGGAL, TYPE, PLACEMENT, PID, LOCATION, PERIOD, USER_NAME,
            TELP_NUMBER, EMAIL, NOTEBOOK, OVERTIME, BAST, BILLING, IS_DELETED
        )
        val selection = ID_PRF_REQUEST + "=?"
        val selectionArgs = arrayOf(id.toString())
        val cursor =
            db.query(TABEL_PRF_REQUEST, projection, selection, selectionArgs, null, null, null)
        if (cursor.count == 1) {
            cursor.moveToFirst()
            data.id_prf_request = cursor.getInt(0)
            data.tanggal = cursor.getString(1)
            inputTanggalPRFEdit.setText(data.tanggal)
            println("-------------------------------------${data.tanggal}")

            val dataType = cursor.getString(2)
            val indexType = isiSpinnerType().indexOf(dataType)
            println("index type = $indexType")
            spinnerInputTypePRFEdit.setSelection(dataType.toInt())
            println("data type = $dataType")

            data.placement = cursor.getString(3)
            inputPlacementPRFEdit.setText(data.placement)

            val dataPID = cursor.getString(4)
            val indexPID = isiSpinnerPID().indexOf(dataPID)
            spinnerInputPIDPRFEdit.setSelection(dataPID.toInt())

            data.location = cursor.getString(5)
            inputLocationPRFEdit.setText(data.location)

            data.period = cursor.getString(6)
            inputPeriodPRFEdit.setText(data.period)

            data.user_name = cursor.getString(7)
            inputUserNamePRFEdit.setText(data.user_name)

            data.telp_number = cursor.getString(8)
            inputTelpPRFEdit.setText(data.telp_number)

            data.email = cursor.getString(9)
            inputEmailPRFEdit.setText(data.email)

            val dataNotebook = cursor.getString(10)
            val indexnotebook = ARRAY_NOTEBOOK.indexOf(dataNotebook)
            spinnerInputNotebookPRFEdit.setSelection(indexnotebook)

            data.overtime = cursor.getString(11)
            inputOvertimePRFEdit.setText(data.overtime)

            val dataBast = cursor.getString(12)
            val indexBast = ARRAY_BAST.indexOf(dataBast)
            spinnerInputBastPRFEdit.setSelection(indexBast)

            data.billing = cursor.getString(13)
            inputBillingPRFEdit.setText(data.billing)

            data.is_Deleted = cursor.getString(14)
        }
        setDatePRFRequestPicker()

        buttonResetPRFRequestEdit.setOnClickListener {
            resetForm()
        }
        buttonSubmitPRFRequestEdit.setOnClickListener {
            validasiInput(id)
        }
    }

    fun requiredOff() {
        requiredTypePRFRequestEdit.isVisible = false
        inputPlacementPRFEdit.setHintTextColor(Color.GRAY)
        requiredPlacementPRFRequestEdit.isVisible = false
        requiredPIDPRFRequestEdit.isVisible = false
        requiredEmailPRFRequestEdit.isVisible = false
        requiredPeriodPRFRequestEdit.isVisible = false
        inputPeriodPRFEdit.setHintTextColor(Color.GRAY)
        requiredUsernamePRFRequestEdit.isVisible = false
        inputUserNamePRFEdit.setHintTextColor(Color.GRAY)
        requiredTelpPRFRequestEdit.isVisible = false
        inputEmailPRFEdit.setHintTextColor(Color.GRAY)
        requiredNotebookPRFRequestEdit.isVisible = false
    }
}
