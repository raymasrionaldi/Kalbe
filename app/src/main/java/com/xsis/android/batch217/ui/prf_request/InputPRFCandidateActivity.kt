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
import com.xsis.android.batch217.models.SRF
import com.xsis.android.batch217.utils.*
import kotlinx.android.synthetic.main.activity_input_prfcandidate.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class InputPRFCandidateActivity : AppCompatActivity() {
    val context = this
    var databaseHelper = DatabaseHelper(context)
    var databaseQueryHelper = PRFCandidateQueryHelper(databaseHelper)
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
    var id_from_request = 0
    lateinit var listPosition: List<EmployeePosition>
    lateinit var listSrf: List<SRF>

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
        } catch (e: NullPointerException) {
        }

        val bundle: Bundle? = intent.extras
        bundle?.let {
            id_from_request = bundle!!.getInt(ID_FROM_PRF)
        }
        println("ID = $id_from_request")

        name = inputNamaPRFCandidate
        batch = inputBatchBootcampPRFCandidate
        position = spinnerInputPositionPRFCandidate
        placementDate = inputPlacementDatePRFCandidate
        srfNumber = spinnerSRFNumberPRFCandidate
        customAllowence = inputCustomeAllowencePRFCandidate
        candidateStatus = spinnerInputCandidateStatusPRFCandidate
        signContractDate = inputSignContractDate
        notes = inputNotesPRFCandidate

        ubahButtonResetSpinner()
        requiredOff()

        buttonBackInputPRFCandidate.setOnClickListener {
            finish()
        }

        setDatePRFCandidatePicker()
        isiSpinnerPosition()
        isiSpinnerSRFNumber()
        isiSpinnerCandidateStatus()

        buttonSubmitPRFCandidate.setOnClickListener {
            validasiInput()
        }
        buttonResetPRFCandidate.setOnClickListener {
            resetForm()
        }

        name!!.addTextChangedListener(textWatcher)
        batch!!.addTextChangedListener(textWatcher)
        placementDate!!.addTextChangedListener(textWatcher)
        customAllowence!!.addTextChangedListener(textWatcher)
        signContractDate!!.addTextChangedListener(textWatcher)
        notes!!.addTextChangedListener(textWatcher)
    }

    fun ubahButtonResetSpinner() {
        buttonReset = buttonResetPRFCandidate
        position!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                buttonReset = buttonResetPRFCandidate
                if (position != 0) {
                    buttonResetPRFCandidate.isEnabled = true
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
                buttonReset = buttonResetPRFCandidate
                if (position != 0) {
                    buttonResetPRFCandidate.isEnabled = true
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
                buttonReset = buttonResetPRFCandidate
                if (position != 0) {
                    buttonResetPRFCandidate.isEnabled = true
                    ubahResetButton(context, true, buttonReset!!)
                } else {
                    ubahResetButton(context, false, buttonReset!!)
                }
            }

            override fun onNothingSelected(arg0: AdapterView<*>) {}
        }
    }

    fun validasiInput() {
        val name = name!!.text.toString().trim()
        val batch = batch!!.text.toString().trim()
        val position = spinnerInputPositionPRFCandidate.selectedItemPosition
        val positionItem = spinnerInputPositionPRFCandidate.selectedItem.toString()
        val placementDate = inputPlacementDatePRFCandidate.text.toString()
        val srfNumber = spinnerSRFNumberPRFCandidate.selectedItemPosition
        val srfNumberItem = spinnerSRFNumberPRFCandidate.selectedItem.toString()
        val customAllowence = customAllowence!!.text.toString()
        val candidateStatus = spinnerInputCandidateStatusPRFCandidate.selectedItemPosition
        val signContractDate = inputSignContractDate.text.toString().trim()
        val notes = notes!!.text.toString().trim()

        var isValid = true

        if (name.isEmpty()) {
            isValid = false
            inputNamaPRFCandidate.setHintTextColor(Color.RED)
            requiredNamePRFCandidate.isVisible = true
        } else {
            requiredNamePRFCandidate.isVisible = false
        }

        if (position == 0) {
            isValid = false
            requiredPositionPRFCandidate.isVisible = true
        }else {
            requiredPositionPRFCandidate.isVisible = false
        }

        if (placementDate.isEmpty()) {
            isValid = false
            requiredPlacementDatePRFCandidate.isVisible = true
        }else {
            requiredPlacementDatePRFCandidate.isVisible = false
        }

        if (srfNumber == 0) {
            isValid = false
            requiredSRFNumberPRFCandidate.isVisible = true
        } else {
            requiredSRFNumberPRFCandidate.isVisible = false
        }

        if (candidateStatus == 0) {
            isValid = false
            requiredCandidateStatusPRFCandidate.isVisible = true
        }else {
            requiredCandidateStatusPRFCandidate.isVisible = false
        }

        if (isValid == true){
            insertKeDatabase(
                name,
                batch,
                positionItem,
                placementDate,
                srfNumberItem,
                customAllowence,
                ARRAY_CANDIDATE_STATUS[candidateStatus],
                signContractDate,
                notes
            )
        }
    }

    fun insertKeDatabase(
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
        val model = PRFCandidate()
        model.id_prf_candidate = model.id_prf_candidate
        model.id_from_prf = id_from_request
        model.nama_prf_candidate = name
        model.batch = batch
        var cariPosition = databaseQueryHelper.cariEmployeePosition(position)
        model.position = cariPosition.toString()
        model.placement_date = placementDate
        model.srf_number = srfNumber
        model.allowence_candidate = customAllowence
        model.status_candidate = candidateStatus
        model.sign_contract_date = signContractDate
        model.notes = notes

        if (databaseQueryHelper!!.tambahPRFCandidate(model) == -1L) {
            Toast.makeText(context, SIMPAN_DATA_GAGAL, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, SIMPAN_DATA_BERHASIL, Toast.LENGTH_SHORT).show()
        }
        finish()

    }

    fun resetForm() {
        inputNamaPRFCandidate.setText("")
        inputBatchBootcampPRFCandidate.setText("")
        spinnerInputPositionPRFCandidate.setSelection(0)
        inputPlacementDatePRFCandidate.setText("")
        spinnerSRFNumberPRFCandidate.setSelection(0)
        inputCustomeAllowencePRFCandidate.setText("")
        spinnerInputCandidateStatusPRFCandidate.setSelection(0)
        inputSignContractDate.setText("")
        inputNotesPRFCandidate.setText("")
        requiredOff()
    }

    fun setDatePRFCandidatePicker() {
        val today = Calendar.getInstance()
        val yearNow = today.get(Calendar.YEAR)
        val monthNow = today.get(Calendar.MONTH)
        val dayNow = today.get(Calendar.DATE)

        inputPlacementDatePRFCandidate.setOnClickListener {
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
                    inputPlacementDatePRFCandidate.setText(tanggal)
                },
                yearNow,
                monthNow,
                dayNow
            )
            datePickerDialog.show()
        }
        inputSignContractDate.setOnClickListener {
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
                    inputSignContractDate.setText(tanggal)
                },
                yearNow,
                monthNow,
                dayNow
            )
            datePickerDialog.show()
        }

    }

    fun isiSpinnerPosition() {
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
        spinnerInputPositionPRFCandidate.adapter = adapterPosition
    }

    fun isiSpinnerSRFNumber() {
        listSrf = databaseQueryHelper.readSrfNumber()
        val isilistSrf = listSrf.map{
            it.id_srf
        }.toMutableList()
        isilistSrf.add(0, "SRF Number *")
        val adapterSRFNumber = ArrayAdapter<String>(
            context,
            android.R.layout.simple_spinner_item,
            isilistSrf
        )
        adapterSRFNumber.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerSRFNumberPRFCandidate.adapter = adapterSRFNumber
    }

    fun isiSpinnerCandidateStatus() {
        val adapterCandidateStatus = ArrayAdapter<String>(
            context,
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
            buttonReset = buttonResetPRFCandidate
            val namaTeks = name!!.text.toString().trim()
            val placementDateTeks = placementDate!!.text.toString().trim()
            val batchTeks = batch!!.text.toString().trim()
            val customAllowenceTeks = customAllowence!!.text.toString().trim()
            val signContractDateTeks = signContractDate!!.text.toString().trim()
            val notesTeks = notes!!.text.toString().trim()

            val kondisi = namaTeks.isNotEmpty() || placementDateTeks.isNotEmpty() || !batchTeks.isEmpty()
                    || !customAllowenceTeks.isEmpty() || !notesTeks.isEmpty()
                    || signContractDateTeks.isNotEmpty()
            buttonResetPRFCandidate.isEnabled = true
            ubahResetButton(context, kondisi, buttonReset!!)
        }

        override fun afterTextChanged(s: Editable) {

        }
    }

    fun requiredOff() {
        inputPlacementDatePRFCandidate.setHintTextColor(Color.GRAY)
        requiredPlacementDatePRFCandidate.isVisible =false
        inputNamaPRFCandidate.setHintTextColor(Color.GRAY)
        requiredNamePRFCandidate.isVisible = false
        requiredPositionPRFCandidate.isVisible = false
        requiredSRFNumberPRFCandidate.isVisible = false
        requiredCandidateStatusPRFCandidate.isVisible = false
    }
}
