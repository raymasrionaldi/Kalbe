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
import com.xsis.android.batch217.databases.PRFCandidateQueryHelper
import com.xsis.android.batch217.models.EmployeePosition
import com.xsis.android.batch217.models.PRFCandidate
import com.xsis.android.batch217.utils.*
import kotlinx.android.synthetic.main.activity_edit_prfcandidate.*
import kotlinx.android.synthetic.main.activity_input_prfcandidate.*
import java.text.SimpleDateFormat
import java.util.*

class EditPRFCandidateActivity : AppCompatActivity() {
    val context = this
    var databaseHelper = DatabaseHelper(context)
    var databaseQueryHelper = PRFCandidateQueryHelper(databaseHelper)
    var data = PRFCandidate()
    var buttonReset: Button? = null
    var name: EditText? = null
    var batch: EditText? = null
    var position: Spinner? = null
    var placementDate: EditText? = null
    var srfNumber: Spinner? = null
    var customAllowence: EditText? = null
    var candidateStatus: Spinner? = null
    var signContractDate: EditText? = null
    var notes: EditText? = null
    var ID_prf_candidate = 0
    lateinit var listPosition: List<EmployeePosition>
    var listSrf: List<String>? = null

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
        } catch (e: NullPointerException) {
        }

        name = inputNamaPRFCandidateEdit
        batch = inputBatchBootcampPRFCandidateEdit
        position = spinnerInputPositionPRFCandidateEdit
        placementDate = inputPlacementDatePRFCandidateEdit
        srfNumber = spinnerSRFNumberPRFCandidateEdit
        customAllowence = inputCustomeAllowencePRFCandidateEdit
        candidateStatus = spinnerInputCandidateStatusPRFCandidateEdit
        signContractDate = inputSignContractDateEdit
        notes = inputNotesPRFCandidateEdit

        name!!.addTextChangedListener(textWatcher)
        batch!!.addTextChangedListener(textWatcher)
        placementDate!!.addTextChangedListener(textWatcher)
        customAllowence!!.addTextChangedListener(textWatcher)
        signContractDate!!.addTextChangedListener(textWatcher)
        notes!!.addTextChangedListener(textWatcher)

        ubahButtonResetSpinner()
        requiredOff()
        isiSpinnerPosition()
        isiSpinnerSRFNumber()
        isiSpinnerCandidateStatus()

        val bundle: Bundle? = intent.extras
        bundle?.let {
            ID_prf_candidate = bundle!!.getInt(ID_PRF_CANDIDATE)
            loadDataPRFCandidate(ID_prf_candidate)
        }
    }

    fun ubahButtonResetSpinner() {
        buttonReset = buttonResetPRFCandidateEdit
        position!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                buttonReset = buttonResetPRFCandidateEdit
                if (position != 0) {
                    buttonResetPRFCandidateEdit.isEnabled = true
                    ubahResetButton(context, true, buttonReset!!)
                } else {
                    ubahResetButton(context, false, buttonReset!!)
                }
            }

            override fun onNothingSelected(arg0: AdapterView<*>) {}
        }
        srfNumber!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                buttonReset = buttonResetPRFCandidateEdit
                if (position != 0) {
                    buttonResetPRFCandidateEdit.isEnabled = true
                    ubahResetButton(context, true, buttonReset!!)
                } else {
                    ubahResetButton(context, false, buttonReset!!)
                }
            }

            override fun onNothingSelected(arg0: AdapterView<*>) {}
        }
        candidateStatus!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                buttonReset = buttonResetPRFCandidateEdit
                if (position != 0) {
                    buttonResetPRFCandidateEdit.isEnabled = true
                    ubahResetButton(context, true, buttonReset!!)
                } else {
                    ubahResetButton(context, false, buttonReset!!)
                }
            }

            override fun onNothingSelected(arg0: AdapterView<*>) {}
        }
    }

    fun validasiInput(id: Int) {
        val name = inputNamaPRFCandidateEdit.text.toString().trim()
        val batch = inputBatchBootcampPRFCandidateEdit.text.toString().trim()
        val position = spinnerInputPositionPRFCandidateEdit.selectedItemPosition
        val positionItem = spinnerInputPositionPRFCandidate.selectedItem.toString()
        val placementDate = inputPlacementDatePRFCandidateEdit.text.toString()
        val srfNumber = spinnerSRFNumberPRFCandidateEdit.selectedItemPosition
        val customAllowence = inputCustomeAllowencePRFCandidateEdit.text.toString()
        val candidateStatus = spinnerInputCandidateStatusPRFCandidateEdit.selectedItemPosition
        val signContractDate = inputSignContractDateEdit.text.toString().trim()
        val notes = inputNotesPRFCandidateEdit.text.toString().trim()

        if (name.isEmpty()) {
            inputNamaPRFCandidateEdit.setHintTextColor(Color.RED)
            requiredNamePRFCandidate.isVisible = true
        }
        if (position == 0) {
            requiredPositionPRFCandidateEdit.isVisible = true
        }
        if (placementDate.isEmpty()) {
            requiredPlacementDatePRFCandidateEdit.isVisible = true
        }
        if (srfNumber == 0) {
            requiredSRFNumberPRFCandidateEdit.isVisible = true
        }
        if (candidateStatus == 0) {
            requiredCandidateStatusPRFCandidateEdit.isVisible = true
        } else {
            insertKeDatabase(
                id, name,
                batch,
                positionItem,
                placementDate,
                listSrf!![srfNumber],
                customAllowence,
                ARRAY_CANDIDATE_STATUS[candidateStatus],
                signContractDate,
                notes
            )

        }

    }

    fun insertKeDatabase(
        id: Int,
        name: String,
        batch: String,
        position: String,
        placementDate: String,
        srfNumber: String,
        customAllowence: String,
        candidateStatus: String,
        signContractDate: String,
        notes: String
    ) {
        var cariPosition = databaseQueryHelper.cariEmployeePosition(position)
        databaseQueryHelper.updateIsi(
            id,
            name,
            batch,
            cariPosition.toString(),
            placementDate,
            srfNumber,
            customAllowence,
            candidateStatus,
            signContractDate,
            notes
        )
        Toast.makeText(context, EDIT_DATA_BERHASIL, Toast.LENGTH_SHORT).show()
        finish()
    }

    fun resetForm() {
        inputNamaPRFCandidateEdit.setText("")
        inputBatchBootcampPRFCandidateEdit.setText("")
        spinnerInputPositionPRFCandidateEdit.setSelection(0)
        inputPlacementDatePRFCandidateEdit.setText("")
        spinnerSRFNumberPRFCandidateEdit.setSelection(0)
        inputCustomeAllowencePRFCandidateEdit.setText("")
        spinnerInputCandidateStatusPRFCandidateEdit.setSelection(0)
        inputSignContractDateEdit.setText("")
        inputNotesPRFCandidateEdit.setText("")
        requiredOff()
    }

    fun setDatePRFCandidatePicker() {
        val autoDate = inputPlacementDatePRFCandidateEdit.text.toString()
        val autoDate2 = inputSignContractDateEdit.text.toString()
        val calendar = Calendar.getInstance()
        val calendar2 = Calendar.getInstance()
        val formatter = SimpleDateFormat(DATE_PATTERN)
        calendar.time = formatter.parse(autoDate)

        if (autoDate2.isNotEmpty()) {
            calendar.time = formatter.parse(autoDate)
        }

        val yearNow = calendar.get(Calendar.YEAR)
        val monthNow = calendar.get(Calendar.MONTH)
        val dayNow = calendar.get(Calendar.DATE)
        val yearNow2 = calendar2.get(Calendar.YEAR)
        val monthNow2 = calendar2.get(Calendar.MONTH)
        val dayNow2 = calendar2.get(Calendar.DATE)

        inputPlacementDatePRFCandidateEdit.setOnClickListener {
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
                    inputPlacementDatePRFCandidateEdit.setText(tanggal)
                },
                yearNow,
                monthNow,
                dayNow
            )
            datePickerDialog.show()
            buttonResetPRFCandidateEdit.isEnabled = true
            buttonResetPRFCandidateEdit.setBackgroundResource(R.drawable.button_reset_on)
            buttonResetPRFCandidateEdit.setTextColor(Color.WHITE)
        }
        inputSignContractDateEdit.setOnClickListener {
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
                    inputSignContractDateEdit.setText(tanggal)
                },
                yearNow2,
                monthNow2,
                dayNow2
            )
            datePickerDialog.show()
            buttonResetPRFCandidateEdit.isEnabled = true
            buttonResetPRFCandidateEdit.setBackgroundResource(R.drawable.button_reset_on)
            buttonResetPRFCandidateEdit.setTextColor(Color.WHITE)
        }
    }

    fun isiSpinnerPosition(): MutableList<String?> {
        listPosition = databaseQueryHelper.readEmployeePosition()
        val isilistPosition = listPosition.map {
            it.nama_employee_position
        }.toMutableList()
        isilistPosition.add(0, "Position *")
        val adapterPosition = ArrayAdapter<String>(
            context,
            android.R.layout.simple_spinner_item,
            isilistPosition
        )
        adapterPosition.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerInputPositionPRFCandidateEdit.adapter = adapterPosition
        return isilistPosition
    }

    fun isiSpinnerSRFNumber() {
        listSrf = databaseQueryHelper.readSrfNumber()
        val adapterSRFNumber = ArrayAdapter<String>(
            context,
            android.R.layout.simple_spinner_item,
            listSrf!!
        )
        adapterSRFNumber.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerSRFNumberPRFCandidateEdit.adapter = adapterSRFNumber
    }

    fun isiSpinnerCandidateStatus() {
        val adapterCandidateStatus = ArrayAdapter<String>(
            context,
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
            buttonReset = buttonResetPRFCandidateEdit
            val placementDateTeks = placementDate!!.text.toString().trim()
            val batchTeks = batch!!.text.toString().trim()
            val customAllowenceTeks = customAllowence!!.text.toString().trim()
            val signContractDateTeks = signContractDate!!.text.toString().trim()
            val notesTeks = notes!!.text.toString().trim()

            val kondisi = placementDateTeks.isNotEmpty() || !batchTeks.isEmpty()
                    || !customAllowenceTeks.isEmpty() || !notesTeks.isEmpty()
                    || signContractDateTeks.isNotEmpty()
            buttonResetPRFCandidateEdit.isEnabled = true
            ubahResetButton(context, kondisi, buttonReset!!)
        }

        override fun afterTextChanged(s: Editable) {

        }
    }

    fun loadDataPRFCandidate(id: Int) {
        println("------------------------ $id")
        val db = databaseHelper.readableDatabase

        val projection = arrayOf<String>(
            ID_FROM_PRF,
            ID_PRF_CANDIDATE,
            NAMA_PRF_CANDIDATE,
            BATCH,
            POSITION,
            PLACEMENT_DATE,
            SRF_NUMBER,
            ALLOWENCE_CANDIDATE,
            STATUS_CANDIDATE,
            SIGN_CONTRACT_DATE,
            NOTES,
            IS_DELETED
        )
        val selection = ID_PRF_CANDIDATE + "=?"
        val selectionArgs = arrayOf(id.toString())
        val cursor =
            db.query(TABEL_PRF_CANDIDATE, projection, selection, selectionArgs, null, null, null)

        if (cursor.count == 1) {
            cursor.moveToFirst()
            data.id_prf_candidate = cursor.getInt(1)
            data.nama_prf_candidate = cursor.getString(2)
            inputNamaPRFCandidateEdit.setText(data.nama_prf_candidate)

            data.batch = cursor.getString(3)
            inputBatchBootcampPRFCandidateEdit.setText(data.batch)

            val dataPosition = cursor.getString(4)
            val indexPosition = isiSpinnerPosition().indexOf(dataPosition)
            spinnerInputPositionPRFCandidateEdit.setSelection(dataPosition.toInt())

            data.placement_date = cursor.getString(5)
            inputPlacementDatePRFCandidateEdit.setText(data.placement_date)

            val dataSrfNumber = cursor.getString(6)
            val indexSrfNumber = listSrf!!.indexOf(dataSrfNumber)
            spinnerSRFNumberPRFCandidateEdit.setSelection(indexSrfNumber)

            data.allowence_candidate = cursor.getString(7)
            inputCustomeAllowencePRFCandidateEdit.setText(data.allowence_candidate)

            val dataCandidateStatus = cursor.getString(8)
            val indexCandidateStatus = ARRAY_NOTEBOOK.indexOf(dataCandidateStatus)
            spinnerInputCandidateStatusPRFCandidateEdit.setSelection(indexCandidateStatus)

            data.sign_contract_date = cursor.getString(9)
            inputSignContractDateEdit.setText(data.sign_contract_date)

            data.notes = cursor.getString(10)
            inputNotesPRFCandidateEdit.setText(data.notes)

            data.is_Deleted = cursor.getString(11)
        }
        setDatePRFCandidatePicker()

        buttonSubmitPRFCandidateEdit.setOnClickListener {
            validasiInput(id)
        }
        buttonResetPRFCandidateEdit.setOnClickListener {
            resetForm()
        }
    }

    fun requiredOff() {
        inputPlacementDatePRFCandidateEdit.setHintTextColor(Color.GRAY)
        requiredPlacementDatePRFCandidateEdit.isVisible = false
        inputNamaPRFCandidateEdit.setHintTextColor(Color.GRAY)
        requiredNamePRFCandidateEdit.isVisible = false
        requiredPositionPRFCandidateEdit.isVisible = false
        requiredSRFNumberPRFCandidateEdit.isVisible = false
        requiredCandidateStatusPRFCandidateEdit.isVisible = false
    }
}
