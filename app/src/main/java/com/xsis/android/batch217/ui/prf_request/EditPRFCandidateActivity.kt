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
import com.xsis.android.batch217.models.PRFCandidate
import com.xsis.android.batch217.utils.*
import kotlinx.android.synthetic.main.activity_edit_prfcandidate.*
import java.text.SimpleDateFormat
import java.util.*

class EditPRFCandidateActivity : AppCompatActivity() {
    val context = this
    var databaseHelper = DatabaseHelper(this)
    var data = PRFCandidate()
    var buttonReset: Button? = null
    var name: Spinner? = null
    var batch: EditText? = null
    var position: Spinner? = null
    var srfNumber: Spinner? = null
    var customAllowence: EditText? = null
    var candidateStatus: Spinner? = null
    var signContractDate: EditText? = null
    var notes: EditText? = null
    var ID_prf_candidate = 0

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

        name = spinnerInputNamaPRFCandidateEdit
        batch = inputBatchBootcampPRFCandidateEdit
        position = spinnerInputPositionPRFCandidateEdit
        srfNumber = spinnerSRFNumberPRFCandidateEdit
        customAllowence = inputCustomeAllowencePRFCandidateEdit
        candidateStatus = spinnerInputCandidateStatusPRFCandidateEdit
        signContractDate = inputSignContractDatePRFCandidateEdit
        notes = inputNotesPRFCandidateEdit

        ubahButtonResetSpinner()

        val bundle: Bundle? = intent.extras
        bundle?.let {
            ID_prf_candidate = bundle!!.getInt(ID_PRF_CANDIDATE)
            loadDataPRFCandidate(ID_prf_candidate)
        }
    }

    fun ubahButtonResetSpinner() {
        buttonReset = buttonResetPRFCandidateEdit
        name!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
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
        position!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
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
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
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
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
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
        val name = spinnerInputNamaPRFCandidateEdit.selectedItemPosition
        val batch = inputBatchBootcampPRFCandidateEdit.text.toString().trim()
        val position = spinnerInputPositionPRFCandidateEdit.selectedItemPosition
        val placementDate = inputPlacementDatePRFCandidateEdit.text.toString()
        val srfNumber = spinnerSRFNumberPRFCandidateEdit.selectedItemPosition
        val customAllowence = inputCustomeAllowencePRFCandidateEdit.text.toString()
        val candidateStatus = spinnerInputCandidateStatusPRFCandidateEdit.selectedItemPosition
        val signContractDate = inputSignContractDatePRFCandidateEdit.text.toString().trim()
        val notes = inputNotesPRFCandidateEdit.text.toString().trim()

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
        else if (srfNumber == 0) {
            requiredSRFNumberPRFCandidateEdit.isVisible = true
        }
        else if (candidateStatus == 0){
            requiredCandidateStatusPRFCandidateEdit.isVisible = true
        }
        else {
            insertKeDatabase(id, ARRAY_NAME[name],
                batch,
                ARRAY_POSITION[position],
                placementDate,
                ARRAY_SRF_NUMBER[srfNumber],
                customAllowence,
                ARRAY_CANDIDATE_STATUS[candidateStatus],
                signContractDate,
                notes)

        }

    }

    fun insertKeDatabase(id: Int,
                         name: String,
                         batch: String,
                         position: String,
                         placementDate: String,
                         srfNumber: String,
                         customAllowence: String,
                         candidateStatus: String,
                         signContractDate: String,
                         notes: String) {

        val databaseHelper = DatabaseHelper(context)
        val db = databaseHelper.writableDatabase
        val databaseQueryHelper = PRFCandidateQueryHelper(databaseHelper)
        val listPRFCandidate = databaseQueryHelper.readUpdate(id,name)
        if(listPRFCandidate.isEmpty()){
            databaseQueryHelper.updateDelete(id, name, batch, position, placementDate, srfNumber, customAllowence, candidateStatus, signContractDate, notes)
            Toast.makeText(context, EDIT_DATA_BERHASIL, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, DATA_SUDAH_ADA, Toast.LENGTH_SHORT).show()
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
        inputNotesPRFCandidateEdit.setText("")
    }

    fun setReportDatePRFCandidatePicker(){
        val autoDate = inputPlacementDatePRFCandidateEdit.text.toString()
        val calendar = Calendar.getInstance()
        val formatter = SimpleDateFormat(DATE_PATTERN)
        val yearNow = calendar.get(Calendar.YEAR)
        val monthNow = calendar.get(Calendar.MONTH)
        val dayNow = calendar.get(Calendar.DATE)

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
            buttonReset = buttonResetPRFCandidateEdit
            val batchTeks = batch!!.text.toString().trim()
            val customAllowenceTeks = customAllowence!!.text.toString().trim()
            val signContractDate = signContractDate!!.text.toString().trim()
            val notesTeks = notes!!.text.toString().trim()

            val kondisi = !batchTeks.isEmpty() || !customAllowenceTeks.isEmpty()
                    || !signContractDate.isEmpty() || !notesTeks.isEmpty()
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
            ID_FROM_PRF, ID_PRF_CANDIDATE, NAMA_PRF_CANDIDATE, BATCH, POSITION, PLACEMENT_DATE, SRF_NUMBER, ALLOWENCE_CANDIDATE,
            STATUS_CANDIDATE, SIGN_CONTRACT_DATE, NOTES, IS_DELETED
        )
        val selection = ID_PRF_CANDIDATE + "=?"
        val selectionArgs = arrayOf(id.toString())
        val cursor =
            db.query(TABEL_PRF_CANDIDATE, projection, selection, selectionArgs, null, null, null)

        if (cursor.count == 1) {
            cursor.moveToFirst()
            data.id_prf_candidate = cursor.getInt(1)
            val dataName = cursor.getString(2)
            val indexName = ARRAY_NAME.indexOf(dataName)
            spinnerInputNamaPRFCandidateEdit.setSelection(indexName)

            data.batch = cursor.getString(3)
            inputBatchBootcampPRFCandidateEdit.setText(data.batch)

            val dataPosition = cursor.getString(4)
            val indexPosition = ARRAY_POSITION.indexOf(dataPosition)
            spinnerInputPositionPRFCandidateEdit.setSelection(indexPosition)

            data.placement_date = cursor.getString(5)
            inputPlacementDatePRFCandidateEdit.setText(data.placement_date)

            val dataSrfNumber = cursor.getString(6)
            val indexSrfNumber = ARRAY_NAME.indexOf(dataSrfNumber)
            spinnerSRFNumberPRFCandidateEdit.setSelection(indexSrfNumber)

            data.allowence_candidate = cursor.getString(7)
            inputCustomeAllowencePRFCandidateEdit.setText(data.allowence_candidate)

            val dataCandidateStatus = cursor.getString(8)
            val indexCandidateStatus = ARRAY_NOTEBOOK.indexOf(dataCandidateStatus)
            spinnerInputCandidateStatusPRFCandidateEdit.setSelection(indexCandidateStatus)

            data.sign_contract_date = cursor.getString(9)
            inputSignContractDatePRFCandidateEdit.setText(data.sign_contract_date)
            println("1 = ${cursor.getString(0)}")
            println("2 = ${cursor.getString(1)}")
            println("3 = ${cursor.getString(2)}")
            println("4 = ${cursor.getString(3)}")
            println("5 = ${cursor.getString(4)}")
            println("6 = ${cursor.getString(5)}")
            println("7 = ${cursor.getString(6)}")
            println("8 = ${cursor.getString(7)}")
            println("9 = ${cursor.getString(8)}")
            println("10 = ${cursor.getString(9)}")

            data.notes = cursor.getString(10)
            inputNotesPRFCandidateEdit.setText(data.notes)

            data.is_Deleted = cursor.getString(11)
        }
        setReportDatePRFCandidatePicker()
        isiSpinnerName()
        isiSpinnerPosition()
        isiSpinnerSRFNumber()
        isiSpinnerCandidateStatus()

        buttonSubmitPRFCandidateEdit.setOnClickListener {
            validasiInput(id)
        }
        buttonResetPRFCandidateEdit.setOnClickListener {
            resetForm()
        }
    }
}
