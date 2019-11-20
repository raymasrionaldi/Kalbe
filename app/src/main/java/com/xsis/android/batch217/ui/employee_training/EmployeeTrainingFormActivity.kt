package com.xsis.android.batch217.ui.employee_training

import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.xsis.android.batch217.R
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.EmployeeTrainingQueryHelper
import com.xsis.android.batch217.models.*
import com.xsis.android.batch217.utils.*
import kotlinx.android.synthetic.main.activity_employee_training_form.*
import kotlinx.android.synthetic.main.activity_employee_training_form.buttonResetEmployeeTraining
import kotlinx.android.synthetic.main.activity_employee_training_form.buttonSubmitEmployeeTraining
import kotlinx.android.synthetic.main.activity_employee_training_form.requiredNamaEmployeeTraining
import kotlinx.android.synthetic.main.activity_employee_training_form.requiredNamaEmployeeTrainingOrganizer
import kotlinx.android.synthetic.main.activity_employee_training_form.requiredTanggalEmployeeTraining
import java.text.SimpleDateFormat
import java.util.*

class EmployeeTrainingFormActivity : AppCompatActivity() {
    val context = this
    var databaseHelper = DatabaseHelper(context)
    var databaseQueryHelper = EmployeeTrainingQueryHelper(databaseHelper)
    var buttonReset: Button? = null
    var employeeNameTraineeText: EditText? = null
    var employeeDateTrainingText: EditText? = null
    var employeeTrainingNameSpinner: Spinner? = null
    var employeeTrainingOrganizerSpinner: Spinner? = null
    var employeeTrainingTypeSpinner: Spinner? = null
    var employeeCertificationTypeSpinner: Spinner? = null
    var data = EmployeeTraining()
    lateinit var listNamaTraining: List<Training>
    lateinit var listNamaTrainingOrganizer: List<TrainingOrganizer>
    lateinit var listTypeTraining: List<TypeTraining>
    lateinit var listCertificationType: List<CertificationType>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar!!.hide()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setContentView(R.layout.activity_employee_training_form)

        databaseQueryHelper = EmployeeTrainingQueryHelper(databaseHelper)

        try {
            this.supportActionBar!!.hide()
        } catch (e: NullPointerException){
        }

        employeeNameTraineeText = inputNamaTrainee
        employeeDateTrainingText = inputTanggalEmployeeTraining
        employeeTrainingNameSpinner = spinnerInputNamaEmployeeTraining
        employeeTrainingOrganizerSpinner = spinnerInputNamaEmployeeTrainingOrganizer
        employeeTrainingTypeSpinner = spinnerInputTypeEmployeeTraining
        employeeCertificationTypeSpinner = spinnerInputCertificationEmployeeTraining

        ubahButtonResetSpinner()
        isiSpinnerNamaTraining()
        isiSpinnerTrainingOrganizer()
        isiSpinnerTrainingType()
        isiSpinnerCertificationType()

        setReportDateEmployeeTrainingPicker()

        buttonBackInputEmployeeTraining.setOnClickListener{
            finish()
        }

        buttonResetEmployeeTraining.setOnClickListener{
            resetForm()
        }

        buttonSubmitEmployeeTraining.setOnClickListener {
            validasiInput()
        }

        employeeNameTraineeText!!.addTextChangedListener(textWatcher)
        employeeDateTrainingText!!.addTextChangedListener(textWatcher)

    }

    fun ubahButtonResetSpinner() {
        buttonReset = buttonResetEmployeeTraining
        employeeTrainingNameSpinner!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                buttonReset = buttonResetEmployeeTraining
                if (position != 0) {
                    buttonResetEmployeeTraining.isEnabled = true
                    ubahResetButton(context, true, buttonReset!!)
                } else {
                    ubahResetButton(context, false, buttonReset!!)
                }
            }
            override fun onNothingSelected(arg0: AdapterView<*>) {}
        }
        employeeTrainingOrganizerSpinner!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                buttonReset = buttonResetEmployeeTraining
                if (position != 0) {
                    buttonResetEmployeeTraining.isEnabled = true
                    ubahResetButton(context, true, buttonReset!!)
                } else {
                    ubahResetButton(context, false, buttonReset!!)
                }
            }
            override fun onNothingSelected(arg0: AdapterView<*>) {}
        }
        employeeTrainingTypeSpinner!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                buttonReset = buttonResetEmployeeTraining
                if (position != 0) {
                    buttonResetEmployeeTraining.isEnabled = true
                    ubahResetButton(context, true, buttonReset!!)
                } else {
                    ubahResetButton(context, false, buttonReset!!)
                }
            }
            override fun onNothingSelected(arg0: AdapterView<*>) {}
        }
        employeeCertificationTypeSpinner!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                buttonReset = buttonResetEmployeeTraining
                if (position != 0) {
                    buttonResetEmployeeTraining.isEnabled = true
                    ubahResetButton(context, true, buttonReset!!)
                } else {
                    ubahResetButton(context, false, buttonReset!!)
                }
            }
            override fun onNothingSelected(arg0: AdapterView<*>) {}
        }
    }

    fun validasiInput() {
        val employeeTrainingDate = inputTanggalEmployeeTraining.text.toString().trim()
        val employeeNameTraineeText = inputNamaTrainee!!.text.toString().trim()
        val employeeTrainingNameSpinner = spinnerInputNamaEmployeeTraining.selectedItem.toString()
        val positionEmployeeTrainingSpinner = spinnerInputNamaEmployeeTraining.selectedItemPosition
        val employeeTrainingOrganizerSpinner = spinnerInputNamaEmployeeTrainingOrganizer.selectedItem.toString()
        val positionEmployeeTrainingOrganizerSpinner = spinnerInputNamaEmployeeTrainingOrganizer.selectedItemPosition
        val employeeTrainingTypeSpinner = spinnerInputTypeEmployeeTraining.selectedItem.toString()
        val positionEmployeeTrainingTypeSpinner = spinnerInputTypeEmployeeTraining.selectedItemPosition
        val employeeCertificationTypeSpinner = spinnerInputCertificationEmployeeTraining.selectedItem.toString()
        val positionEmployeeCertificationTypeSpinner = spinnerInputCertificationEmployeeTraining.selectedItemPosition

        var isValid = true

        if (employeeNameTraineeText.isEmpty()) {
            inputNamaTrainee.setHintTextColor(Color.RED)
            requiredNamaTrainee.visibility = View.VISIBLE
            isValid = false
        }

        if (positionEmployeeTrainingSpinner == 0) {
            inputNamaEmployeeTraining.setHintTextColor(Color.RED)
            requiredNamaEmployeeTraining.isVisible = true
            isValid = false
        }

        if (positionEmployeeTrainingOrganizerSpinner == 0) {
            inputNamaEmployeeTrainingOrganizer.setHintTextColor(Color.RED)
            requiredNamaEmployeeTrainingOrganizer.isVisible = true
            isValid = false
        }

        if (employeeTrainingDate.isEmpty()) {
            inputTanggalEmployeeTraining.setHintTextColor(Color.RED)
            requiredTanggalEmployeeTraining.isVisible = true
            isValid = false
        }

        if (isValid) {

            val currentDate = Calendar.getInstance()
            currentDate.set(Calendar.HOUR_OF_DAY, 0)
            currentDate.set(Calendar.MINUTE, 0)
            currentDate.set(Calendar.SECOND, 0)
            currentDate.set(Calendar.MILLISECOND, 0)

            var isValid2 = true

            val formatDate = SimpleDateFormat("MMMM dd, yyyy")
            val trainingTime = formatDate.parse(employeeTrainingDate)
            val trainingDate = Calendar.getInstance()
            trainingDate.time = trainingTime!!

            if (trainingDate.before(currentDate)) {
                inputTanggalEmployeeTraining.setHintTextColor(Color.RED)
                isValid2 = false
                Toast.makeText(context, "Tidak boleh memilih tanggal kemarin", Toast.LENGTH_SHORT)
                    .show()
                inputTanggalEmployeeTraining!!.setText("")
            }

            if(isValid2){
                val model = EmployeeTraining()
                model.idEmployeeTraining = data.idEmployeeTraining
                model.namaTrainee = employeeNameTraineeText
                model.namaEmployeeTraining = employeeTrainingNameSpinner
                model.namaEmployeeTO = employeeTrainingOrganizerSpinner
                model.dateEmployeeTraining = employeeTrainingDate
                if (positionEmployeeTrainingTypeSpinner == 0){
                    model.typeEmployeeTraining == ""
                }
                else{
                    model.typeEmployeeTraining = employeeTrainingTypeSpinner
                }

                if (positionEmployeeCertificationTypeSpinner == 0){
                    model.typeEmployeeCertification == ""
                }
                else{
                    model.typeEmployeeCertification = employeeCertificationTypeSpinner
                }

                val cekEmployeeTrainee =
                    databaseQueryHelper!!.cekEmployeeTrainingSudahTraining(model.namaTrainee!!, model.dateEmployeeTraining!!)

                if (cekEmployeeTrainee > 0) {
                    Toast.makeText(context, CEK_TRAINEE, Toast.LENGTH_SHORT).show()
                    return
                }
                if (databaseQueryHelper!!.tambahEmployeeTraining(model) == -1L) {
                    Toast.makeText(context, SIMPAN_DATA_GAGAL, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, SIMPAN_DATA_BERHASIL, Toast.LENGTH_SHORT)
                        .show()
                }

                finish()
            }
        }
    }


    fun resetForm(){
        inputTanggalEmployeeTraining.setText("")
        inputNamaTrainee.setText("")
        spinnerInputNamaEmployeeTraining.setSelection(0)
        spinnerInputNamaEmployeeTrainingOrganizer.setSelection(0)
        spinnerInputTypeEmployeeTraining.setSelection(0)
        spinnerInputCertificationEmployeeTraining.setSelection(0)
    }


    fun setReportDateEmployeeTrainingPicker(){
        val today = Calendar.getInstance()
        val yearNow = today.get(Calendar.YEAR)
        val monthNow = today.get(Calendar.MONTH)
        val dayNow = today.get(Calendar.DATE)

        inputTanggalEmployeeTraining.setOnClickListener {
            val datePickerDialog = DatePickerDialog(context, R.style.CustomDatePicker, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, month, dayOfMonth)

                //konversi ke string
                val formatDate = SimpleDateFormat("MMMM dd, yyyy")
                val tanggal = formatDate.format(selectedDate.time)

                //set tampilan
                inputTanggalEmployeeTraining.setText(tanggal)
            }, yearNow,monthNow,dayNow )
            datePickerDialog.show()
        }
    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) { }
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            buttonReset = buttonResetEmployeeTraining

            val employeeDateTrainingTeks = inputTanggalEmployeeTraining!!.text.toString().trim()
            val employeeNameTraineeTeks = inputNamaTrainee!!.text.toString().trim()

            val kondisi = !employeeNameTraineeTeks.isEmpty() || !employeeDateTrainingTeks.isEmpty()

            buttonResetEmployeeTraining.isEnabled = true
            ubahResetButton(context, kondisi, buttonReset!!)
        }
        override fun afterTextChanged(s: Editable) { }
    }

    fun isiSpinnerNamaTraining() {
        listNamaTraining = databaseQueryHelper.tampilkanNamaTraining()
        val isiDataNamaTraining = listNamaTraining.map {
            it.namaTraining
        }.toMutableList()
        isiDataNamaTraining.add(0, "Training *")
        val adapterNamaTraining = ArrayAdapter<String>(
            context, android.R.layout.simple_spinner_item,
            isiDataNamaTraining
        )
        adapterNamaTraining.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerInputNamaEmployeeTraining.adapter = adapterNamaTraining
    }

    fun isiSpinnerTrainingOrganizer() {
        listNamaTrainingOrganizer = databaseQueryHelper.tampilkanNamaTrainingOrganizer()
        val isiDataNamaTrainingOrganizer = listNamaTrainingOrganizer.map {
            it.namaTrainingOrganizer
        }.toMutableList()
        isiDataNamaTrainingOrganizer.add(0, "Organizer *")
        val adapterNamaTrainingOrganizer = ArrayAdapter<String>(
            context, android.R.layout.simple_spinner_item,
            isiDataNamaTrainingOrganizer
        )
        adapterNamaTrainingOrganizer.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerInputNamaEmployeeTrainingOrganizer.adapter = adapterNamaTrainingOrganizer
    }

    fun isiSpinnerTrainingType() {
        listTypeTraining = databaseQueryHelper.tampilkanTrainingType()
        val isiDataTrainingType = listTypeTraining.map {
            it.namaTypeTraining
        }.toMutableList()
        isiDataTrainingType.add(0, "Training Type")
        val adapterTypeTraining = ArrayAdapter<String>(
            context, android.R.layout.simple_spinner_item,
            isiDataTrainingType
        )
        adapterTypeTraining.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerInputTypeEmployeeTraining.adapter = adapterTypeTraining
    }

    fun isiSpinnerCertificationType() {
        listCertificationType = databaseQueryHelper.tampilkanCertificationType()
        val isiDataCertificationType = listCertificationType.map {
            it.namaTypeCertification
        }.toMutableList()
        isiDataCertificationType.add(0, "Certification Type")
        val adapterCertificationType = ArrayAdapter<String>(
            context, android.R.layout.simple_spinner_item,
            isiDataCertificationType
        )
        adapterCertificationType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerInputCertificationEmployeeTraining.adapter = adapterCertificationType
    }

}
