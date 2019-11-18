package com.xsis.android.batch217.ui.employee_training

import android.app.Activity
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
import androidx.core.view.get
import androidx.core.view.isVisible
import com.xsis.android.batch217.R
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.EmployeeTrainingQueryHelper
import com.xsis.android.batch217.models.*
import com.xsis.android.batch217.utils.*
import kotlinx.android.synthetic.main.activity_edit_prfrequest.*
import kotlinx.android.synthetic.main.activity_employee_training_edit.*
import kotlinx.android.synthetic.main.activity_employee_training_form.*
import kotlinx.android.synthetic.main.activity_employee_training_form.buttonResetEmployeeTraining
import kotlinx.android.synthetic.main.activity_employee_training_form.buttonSubmitEmployeeTraining
import kotlinx.android.synthetic.main.activity_employee_training_form.requiredNamaEmployeeTraining
import kotlinx.android.synthetic.main.activity_employee_training_form.requiredNamaEmployeeTrainingOrganizer
import kotlinx.android.synthetic.main.activity_employee_training_form.requiredTanggalEmployeeTraining
import kotlinx.android.synthetic.main.activity_project_form.*
import java.text.SimpleDateFormat
import java.util.*

class EmployeeTrainingEditActivity : AppCompatActivity() {
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
    var ID_EmployeeTraining = 0
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

        setContentView(R.layout.activity_employee_training_edit)

        isiSpinnerNamaTraining()
        isiSpinnerTrainingOrganizer()
        isiSpinnerTrainingType()
        isiSpinnerCertificationType()


        try {
            this.supportActionBar!!.hide()
        } catch (e: NullPointerException){
        }

        employeeNameTraineeText = findViewById(R.id.editNamaTrainee)
        employeeDateTrainingText = findViewById(R.id.editTanggalEmployeeTraining)
        employeeTrainingNameSpinner = findViewById(R.id.spinnerEditNamaEmployeeTraining)
        employeeTrainingOrganizerSpinner = findViewById(R.id.spinnerEditNamaEmployeeTrainingOrganizer)
        employeeTrainingTypeSpinner = findViewById(R.id.spinnerEditTypeEmployeeTraining)
        employeeCertificationTypeSpinner = findViewById(R.id.spinnerEditCertificationEmployeeTraining)

        ubahButtonResetSpinner()

        buttonBackEditEmployeeTraining.setOnClickListener{
            finish()
        }

        setReportDateEmployeeTrainingPicker()

        buttonResetEmployeeTraining.setOnClickListener{
            resetForm()
        }

        buttonSubmitEmployeeTraining.setOnClickListener {
            validasiEdit()
        }

        employeeNameTraineeText!!.addTextChangedListener(textWatcher)
        employeeDateTrainingText!!.addTextChangedListener(textWatcher)

        val bundle: Bundle? = intent.extras
        bundle?.let {
            ID_EmployeeTraining = bundle!!.getInt(ID_EMPLOYEE_TRAINING)
            loadDataEmployeeTraining(ID_EmployeeTraining)
        }

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

    fun validasiEdit() {
        val employeeTrainingDate = editTanggalEmployeeTraining.text.toString().trim()
        val employeeNameTraineeText = editNamaTrainee!!.text.toString().trim()
        val employeeTrainingNameSpinner = spinnerEditNamaEmployeeTraining.selectedItem.toString()
        val positionEmployeeTrainingSpinner = spinnerEditNamaEmployeeTraining.selectedItemPosition
        val employeeTrainingOrganizerSpinner = spinnerEditNamaEmployeeTrainingOrganizer.selectedItem.toString()
        val positionEmployeeTrainingOrganizerSpinner = spinnerEditNamaEmployeeTrainingOrganizer.selectedItemPosition
        val employeeTrainingTypeSpinner = spinnerEditTypeEmployeeTraining.selectedItem.toString()
        val employeeCertificationTypeSpinner = spinnerEditCertificationEmployeeTraining.selectedItem.toString()

        var isValid = true

        if (employeeNameTraineeText.isEmpty()) {
            editNamaTrainee.setHintTextColor(Color.RED)
            requiredNamaTraineeEdit.visibility = View.VISIBLE
            isValid = false
        }

        if (positionEmployeeTrainingSpinner == 0) {
            editNamaEmployeeTraining.setHintTextColor(Color.RED)
            requiredNamaEmployeeTraining.isVisible = true
            isValid = false
        }

        if (positionEmployeeTrainingOrganizerSpinner == 0) {
            editNamaEmployeeTrainingOrganizer.setHintTextColor(Color.RED)
            requiredNamaEmployeeTrainingOrganizer.isVisible = true
            isValid = false
        }

        if (employeeTrainingDate.isEmpty()) {
            editTanggalEmployeeTraining.setHintTextColor(Color.RED)
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

            if (databaseQueryHelper!!.editEmployeeTraining(model) == 0) {
                Toast.makeText(context, EDIT_DATA_GAGAL, Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(context, EDIT_DATA_BERHASIL, Toast.LENGTH_SHORT)
                    .show()
            }

            finish()
        }
    }


    fun resetForm(){
        editTanggalEmployeeTraining.setText("")
        editNamaTrainee.setText("")
        spinnerEditNamaEmployeeTraining.setSelection(0)
        spinnerEditNamaEmployeeTrainingOrganizer.setSelection(0)
        spinnerEditTypeEmployeeTraining.setSelection(0)
        spinnerEditCertificationEmployeeTraining.setSelection(0)
    }


    fun setReportDateEmployeeTrainingPicker(){
        val today = Calendar.getInstance()
        val yearNow = today.get(Calendar.YEAR)
        val monthNow = today.get(Calendar.MONTH)
        val dayNow = today.get(Calendar.DATE)

        iconEditTanggalEmployeeTraining.setOnClickListener {
            val datePickerDialog = DatePickerDialog(context, R.style.CustomDatePicker, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, month, dayOfMonth)

                //konversi ke string
                val formatDate = SimpleDateFormat("MMMM dd, yyyy")
                val tanggal = formatDate.format(selectedDate.time)

                //set tampilan
                editTanggalEmployeeTraining.setText(tanggal)
            }, yearNow,monthNow,dayNow )
            datePickerDialog.show()
//            buttonResetEmployeeTraining.isEnabled = true
//            buttonResetEmployeeTraining.setBackgroundResource(R.drawable.button_reset_on)
//            buttonResetEmployeeTraining.setTextColor(Color.WHITE)
        }

    }


    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) { }
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            buttonReset = buttonResetEmployeeTraining

            val employeeDateTrainingTeks = editTanggalEmployeeTraining!!.text.toString().trim()
            val employeeNameTraineeTeks = editNamaTrainee!!.text.toString().trim()

            val kondisi = !employeeNameTraineeTeks.isEmpty() || !employeeDateTrainingTeks.isEmpty()

            buttonResetEmployeeTraining.isEnabled = true
            ubahResetButton(context, kondisi, buttonReset!!)
        }
        override fun afterTextChanged(s: Editable) { }
    }

    fun isiSpinnerNamaTraining(): List<String?> {
        listNamaTraining = databaseQueryHelper.tampilkanNamaTraining()
        val isiDataTraining = listNamaTraining.map {
            it.namaTraining
        }.toMutableList()
        isiDataTraining.add(0, "Training *")
        val adapterNamaTraining = ArrayAdapter<String>(
            context, android.R.layout.simple_spinner_item,
            isiDataTraining
        )
        adapterNamaTraining.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerEditNamaEmployeeTraining.adapter = adapterNamaTraining
        return isiDataTraining
    }

    fun isiSpinnerTrainingOrganizer(): List<String?> {
        listNamaTrainingOrganizer = databaseQueryHelper.tampilkanNamaTrainingOrganizer()
        val isiDataTrainingOrganizer = listNamaTrainingOrganizer.map {
            it.namaTrainingOrganizer
        }.toMutableList()
        isiDataTrainingOrganizer.add(0, "Organizer *")
        val adapterNamaTrainingOrganizer = ArrayAdapter<String>(
            context, android.R.layout.simple_spinner_item,
            isiDataTrainingOrganizer
        )
        adapterNamaTrainingOrganizer.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerEditNamaEmployeeTrainingOrganizer.adapter = adapterNamaTrainingOrganizer
        return isiDataTrainingOrganizer
    }

    fun isiSpinnerTrainingType(): List<String?> {
        listTypeTraining = databaseQueryHelper.tampilkanTrainingType()
        val isiDataTypeTraining = listTypeTraining.map {
            it.namaTypeTraining
        }.toMutableList()
        isiDataTypeTraining.add(0, "Training Type")
        val adapterTypeTraining = ArrayAdapter<String>(
            context, android.R.layout.simple_spinner_item,
            isiDataTypeTraining
        )
        adapterTypeTraining.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerEditTypeEmployeeTraining.adapter = adapterTypeTraining
        return isiDataTypeTraining
    }

    fun isiSpinnerCertificationType(): List<String?> {
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
        spinnerEditCertificationEmployeeTraining.adapter = adapterCertificationType
        return isiDataCertificationType
    }

    fun loadDataEmployeeTraining(ID_EmployeeTraining: Int) {
        val db = databaseHelper.readableDatabase
        val namaTraining = databaseQueryHelper.tampilkanNamaTraining()

        val projection = arrayOf<String>(
            ID_EMPLOYEE_TRAINING, NAMA_TRAINEE, NAMA_EMPLOYEE_TRAINING, NAMA_EMPLOYEE_TRAINING_ORG, DATE_EMPLOYEE_TRAINING, TYPE_EMPLOYEE_TRAINING, TYPE_EMPLOYEE_CERTIFICATION, IS_DELETED
        )
        val selection = ID_EMPLOYEE_TRAINING + "=?"
        val selectionArgs = arrayOf(ID_EmployeeTraining.toString())
        val cursor =
            db.query(TABEL_EMPLOYEE_TRAINING, projection, selection, selectionArgs, null, null, null)

        if (cursor.count == 1) {
            cursor.moveToFirst()
            data.idEmployeeTraining = cursor.getInt(0)

            data.namaTrainee = cursor.getString(1)
            editNamaTrainee.setText(data.namaTrainee)

            val dataNamaEmployeeTraining = cursor.getString(2)
            val indexNamaEmployeeTraining = isiSpinnerNamaTraining().indexOf(dataNamaEmployeeTraining)
            spinnerEditNamaEmployeeTraining.setSelection(indexNamaEmployeeTraining)

            val dataNamaEmployeeTrainingOrganizer = cursor.getString(3)
            val indexNamaEmployeeTrainingOrganizer = isiSpinnerTrainingOrganizer().indexOf(dataNamaEmployeeTrainingOrganizer)
            spinnerEditNamaEmployeeTrainingOrganizer.setSelection(indexNamaEmployeeTrainingOrganizer)

            data.dateEmployeeTraining = cursor.getString(4)
            editTanggalEmployeeTraining.setText(data.dateEmployeeTraining)

            val dataTypeEmployeeTraining = cursor.getString(5)
            val indexTypeEmployeeTraining = isiSpinnerTrainingType().indexOf(dataTypeEmployeeTraining)
            spinnerEditTypeEmployeeTraining.setSelection(indexTypeEmployeeTraining)

            val dataCertificationType = cursor.getString(6)
            val indexCertificationType = isiSpinnerCertificationType().indexOf(dataCertificationType)
            spinnerEditCertificationEmployeeTraining.setSelection(indexCertificationType)

            data.isDeleted = cursor.getString(7)

        }
    }


}
