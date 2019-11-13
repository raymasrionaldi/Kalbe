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
    var srfNumber: Spinner? = null
    var customAllowence: EditText? = null
    var candidateStatus: Spinner? = null
    var notes: EditText? = null
    var id_from_request = 0
    var listPosition: List<String>? = null

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

        val bundle: Bundle? = intent.extras
        bundle?.let {
            id_from_request = bundle!!.getInt(ID_FROM_PRF)
        }
        println("ID = $id_from_request")

        name = inputNamaPRFCandidate
        batch = inputBatchBootcampPRFCandidate
        position = spinnerInputPositionPRFCandidate
        srfNumber = spinnerSRFNumberPRFCandidate
        customAllowence = inputCustomeAllowencePRFCandidate
        candidateStatus = spinnerInputCandidateStatusPRFCandidate
        notes = inputNotesPRFCandidate

        ubahButtonResetSpinner()

        buttonBackInputPRFCandidate.setOnClickListener {
            finish()
        }

        setReportDatePRFCandidatePicker()
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
        customAllowence!!.addTextChangedListener(textWatcher)
        notes!!.addTextChangedListener(textWatcher)
    }

    fun ubahButtonResetSpinner() {
        buttonReset = buttonResetPRFCandidate
        position!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
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
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
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
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
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
        val placementDate = inputPlacementDatePRFCandidate.text.toString()
        val srfNumber = spinnerSRFNumberPRFCandidate.selectedItemPosition
        val customAllowence = customAllowence!!.text.toString()
        val candidateStatus = spinnerInputCandidateStatusPRFCandidate.selectedItemPosition
        val signContractDate = inputSignContractDate.text.toString().trim()
        val notes = notes!!.text.toString().trim()

        if (name.isEmpty()) {
            inputNamaPRFCandidate.setHintTextColor(Color.RED)
            requiredNamePRFCandidate.isVisible = true
        }
        else if (position == 0) {
            requiredPositionPRFCandidate.isVisible = true
        }
        else if (placementDate.isEmpty()) {
            inputPlacementDatePRFCandidate.setHintTextColor(Color.RED)
            requiredPlacementDatePRFCandidate.isVisible = true
        }
        else if (srfNumber == 0) {
            requiredSRFNumberPRFCandidate.isVisible = true
        }
        else if (candidateStatus == 0){
            requiredCandidateStatusPRFCandidate.isVisible = true
        }
        else {
            insertKeDatabase(name,
                                batch,
                                listPosition!![position],
                                placementDate,
                                ARRAY_SRF_NUMBER[srfNumber],
                                customAllowence,
                                ARRAY_CANDIDATE_STATUS[candidateStatus],
                                signContractDate,
                                notes)
        }
    }

    fun insertKeDatabase(name: String,
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
        val listPRFCandidate = databaseQueryHelper.readNamaPRFCandidate(name)
//        var listNamaYangAda = ArrayList<String>()
//        listPRFCandidate.forEach{
//            if(it.nama_prf_candidate!!.isNotEmpty()){
//                listNamaYangAda.add(it.nama_prf_candidate!!)
//            }
//        }
//
//        println(listPRFCandidate.size)
//        println(ArrayList<String>().size)
//        println(listNamaYangAda.size)
//        println(listNamaYangAda)
        if (!listPRFCandidate.isEmpty()){
            //cek id_deleted
            if(listPRFCandidate[0].is_Deleted == "true"){
                //update tru jadi false
                databaseQueryHelper.updatePRFCandidate(name, batch, position, placementDate, srfNumber, customAllowence, candidateStatus, signContractDate, notes)
                Toast.makeText(this, SIMPAN_DATA_BERHASIL, Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, DATA_SUDAH_ADA, Toast.LENGTH_SHORT).show()
            }
        } else {
            //jika tidak maka insert
            //cek nama
            println("ID = $id_from_request")
            val content = ContentValues()
            content.put(ID_FROM_PRF, id_from_request)
            content.put(NAMA_PRF_CANDIDATE, name)
            content.put(BATCH, batch)
            content.put(POSITION, position)
            content.put(PLACEMENT_DATE, placementDate)
            content.put(SRF_NUMBER, srfNumber)
            content.put(ALLOWENCE_CANDIDATE, customAllowence)
            content.put(STATUS_CANDIDATE, candidateStatus)
            content.put(SIGN_CONTRACT_DATE, signContractDate)
            content.put(NOTES, notes)
            content.put(IS_DELETED, "false")
            val db = DatabaseHelper(this).writableDatabase
            db.insert(TABEL_PRF_CANDIDATE, null, content)
            Toast.makeText(this, SIMPAN_DATA_BERHASIL, Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    fun resetForm() {
        inputNotesPRFCandidate.setSelection(0)
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

    fun setReportDatePRFCandidatePicker(){
        val today = Calendar.getInstance()
        val yearNow = today.get(Calendar.YEAR)
        val monthNow = today.get(Calendar.MONTH)
        val dayNow = today.get(Calendar.DATE)

        inputPlacementDatePRFCandidate.setOnClickListener {
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
        inputSignContractDate.setOnClickListener {
            val datePickerDialog = DatePickerDialog(context, R.style.CustomDatePicker, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, month, dayOfMonth)

                //konversi ke string
                val formatDate = SimpleDateFormat("MMMM dd, yyyy")
                val tanggal = formatDate.format(selectedDate.time)

                //set tampilan
                inputSignContractDate.setText(tanggal)
            }, yearNow,monthNow,dayNow )
            datePickerDialog.show()
            buttonResetPRFCandidate.isEnabled = true
            buttonResetPRFCandidate.setBackgroundResource(R.drawable.button_reset_on)
            buttonResetPRFCandidate.setTextColor(Color.WHITE)
        }

    }

    fun isiSpinnerPosition(){
        listPosition = databaseQueryHelper.readEmployeePosition()
        val adapterPosition = ArrayAdapter<String>(context,
            android.R.layout.simple_spinner_item,
            listPosition!!
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
            buttonReset = buttonResetPRFCandidate
            val batchTeks = batch!!.text.toString().trim()
            val customAllowenceTeks = customAllowence!!.text.toString().trim()
            val notesTeks = notes!!.text.toString().trim()

            val kondisi = !batchTeks.isEmpty() || !customAllowenceTeks.isEmpty() || !notesTeks.isEmpty()
            buttonResetPRFCandidate.isEnabled = true
            ubahResetButton(context, kondisi, buttonReset!!)
        }

        override fun afterTextChanged(s: Editable) {

        }
    }

    fun requiredOff() {
        requiredNamePRFCandidate.isVisible = false
        requiredPositionPRFCandidate.isVisible = false
        requiredSRFNumberPRFCandidate.isVisible = false
        requiredCandidateStatusPRFCandidate.isVisible = false
    }
}
