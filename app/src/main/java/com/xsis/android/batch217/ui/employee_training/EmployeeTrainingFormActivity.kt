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
import com.xsis.android.batch217.models.EmployeeTraining
import com.xsis.android.batch217.utils.*
import kotlinx.android.synthetic.main.activity_employee_training_form.*
import java.text.SimpleDateFormat
import java.util.*

class EmployeeTrainingFormActivity : AppCompatActivity() {
    val context = this
    var databaseHelper = DatabaseHelper(context)
    var databaseQueryHelper = EmployeeTrainingQueryHelper(databaseHelper)

    var buttonReset: Button? = null
    var employeeNameTraineeText: EditText? = null
    var employeeTrainingNameSpinner: Spinner? = null
    var employeeTrainingOrganizerSpinner: Spinner? = null
    var employeeTrainingTypeSpinner: Spinner? = null
    var employeeCertificationTypeSpinner: Spinner? = null
    var data = EmployeeTraining()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar!!.hide()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_employee_training_form)
        isiSpinnerNamaTraining()
        isiSpinnerTrainingOrganizer()
        isiSpinnerTrainingType()
        isiSpinnerCertificationType()


        databaseQueryHelper = EmployeeTrainingQueryHelper(databaseHelper)

        try {
            this.supportActionBar!!.hide()
        } catch (e: NullPointerException){
        }

        employeeNameTraineeText = inputNamaTrainee
        employeeTrainingNameSpinner = spinnerInputNamaEmployeeTraining
        employeeTrainingOrganizerSpinner = spinnerInputNamaEmployeeTrainingOrganizer
        employeeTrainingTypeSpinner = spinnerInputTypeEmployeeTraining
        employeeCertificationTypeSpinner = spinnerInputCertificationEmployeeTraining


        ubahButtonResetSpinner()

        buttonBackInputEmployeeTraining.setOnClickListener{
            finish()
        }

        setReportDateEmployeeTrainingPicker()

        buttonResetEmployeeTraining.setOnClickListener{
            resetForm()
        }

        buttonSubmitEmployeeTraining.setOnClickListener {
            validasiInput()
        }

        employeeNameTraineeText!!.addTextChangedListener(textWatcher)


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
        val employeeCertificationTypeSpinner = spinnerInputCertificationEmployeeTraining.selectedItem.toString()

        var isValid = true

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
            val model = EmployeeTraining()
            model.idEmployeeTraining = data.idEmployeeTraining
            model.namaTrainee = employeeNameTraineeText
            model.namaEmployeeTraining = employeeTrainingNameSpinner
            model.namaEmployeeTO = employeeTrainingOrganizerSpinner
            model.dateEmployeeTraining = employeeTrainingDate
            model.typeEmployeeTraining = employeeTrainingTypeSpinner
            model.typeEmployeeCertification = employeeCertificationTypeSpinner


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

        iconInputTanggalEmployeeTraining.setOnClickListener {
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
            buttonResetEmployeeTraining.isEnabled = true
            buttonResetEmployeeTraining.setBackgroundResource(R.drawable.button_reset_on)
            buttonResetEmployeeTraining.setTextColor(Color.WHITE)
        }

    }


    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) { }
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            buttonReset = buttonResetEmployeeTraining

            val employeeNameTraineeTeks = inputNamaTrainee!!.text.toString().trim()

            val kondisi = !employeeNameTraineeTeks.isEmpty()

            buttonResetEmployeeTraining.isEnabled = true
            ubahResetButton(context, kondisi, buttonReset!!)
        }
        override fun afterTextChanged(s: Editable) { }
    }

    fun isiSpinnerNamaTraining() {
        val namaTraining = databaseQueryHelper.tampilkanNamaTraining()
        val isiData = namaTraining.map {
            it.namaNyaTraining
        }.toList()
        val adapterNamaTraining = ArrayAdapter<String>(
            context, android.R.layout.simple_spinner_item,
            isiData
        )
        adapterNamaTraining.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerInputNamaEmployeeTraining.adapter = adapterNamaTraining
    }

    fun isiSpinnerTrainingOrganizer() {
        val trainingOrganizer = databaseQueryHelper.tampilkanNamaTrainingOrganizer()
        val isiData = trainingOrganizer.map {
            it.namaNyaTrainingOrganizer
        }.toList()
        val adapterNamaTrainingOrganizer = ArrayAdapter<String>(
            context, android.R.layout.simple_spinner_item,
            isiData
        )
        adapterNamaTrainingOrganizer.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerInputNamaEmployeeTrainingOrganizer.adapter = adapterNamaTrainingOrganizer
    }

    fun isiSpinnerTrainingType() {
        val trainingType = databaseQueryHelper.tampilkanTrainingType()
        val isiData = trainingType.map {
            it.namaTypeTraining
        }.toList()
        val adapterTypeTraining = ArrayAdapter<String>(
            context, android.R.layout.simple_spinner_item,
            isiData
        )
        adapterTypeTraining.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerInputTypeEmployeeTraining.adapter = adapterTypeTraining
    }

    fun isiSpinnerCertificationType() {
        val certificationType = databaseQueryHelper.tampilkanCertificationType()
        val isiData = certificationType.map {
            it.namaTypeCertification
        }.toList()
        val adapterCertificationType = ArrayAdapter<String>(
            context, android.R.layout.simple_spinner_item,
            isiData
        )
        adapterCertificationType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerInputCertificationEmployeeTraining.adapter = adapterCertificationType
    }

}
