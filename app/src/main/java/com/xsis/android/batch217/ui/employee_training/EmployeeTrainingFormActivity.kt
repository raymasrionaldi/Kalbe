package com.xsis.android.batch217.ui.employee_training

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import com.xsis.android.batch217.R
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.EmployeeTrainingQueryHelper
import com.xsis.android.batch217.models.EmployeeTraining
import com.xsis.android.batch217.utils.*
import kotlinx.android.synthetic.main.fragment_detail_employee_training.*

class EmployeeTrainingFormActivity : AppCompatActivity() {

    val context = this
    var databaseQueryHelper: EmployeeTrainingQueryHelper? = null
    var databaseHelper = DatabaseHelper(context)

    var employeeNameTraineeText: EditText? = null
    var employeeTrainingNameText: EditText? = null
    var employeeTrainingOrganizerText: EditText? = null
    var employeeTrainingDateText: EditText? = null
    var employeeTrainingTypeText: EditText? = null
    var employeeCertificationTypeText: EditText? = null
    var buttonReset: Button? = null
    var buttonSimpan: Button? = null
    var defaultColor = 0
    var idData = 0
    var data = EmployeeTraining()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee_training_form)

        val bundle: Bundle? = intent.extras
        bundle?.let {
            idData = bundle!!.getInt(ID_EMPLOYEE_TRAINING)
            loadDataEmployeeTraining(idData)
        }

        databaseQueryHelper = EmployeeTrainingQueryHelper(databaseHelper)

        employeeNameTraineeText = findViewById(R.id.inputNamaTrainee)
        employeeTrainingNameText = findViewById(R.id.inputNamaEmployeeTraining)
        employeeTrainingOrganizerText = findViewById(R.id.inputEmployeeTrainingOrganizer)
        employeeTrainingDateText = findViewById(R.id.inputEmployeeTrainingDate)
        employeeTrainingTypeText= findViewById(R.id.inputEmployeeTrainingType)
        employeeCertificationTypeText = findViewById(R.id.inputEmployeeCertificationType)

        buttonSimpan = findViewById(R.id.buttonSaveEmployeeTraining)
        buttonReset = findViewById(R.id.buttonResetEmployeeTraining)

        buttonSimpan!!.setOnClickListener {
            simpanEmployeeTraining()
        }

        buttonReset!!.setOnClickListener {
            resetForm()
        }


        employeeNameTraineeText!!.addTextChangedListener(textWatcher)
        employeeTrainingNameText!!.addTextChangedListener(textWatcher)
        employeeTrainingOrganizerText!!.addTextChangedListener(textWatcher)
        employeeTrainingDateText!!.addTextChangedListener(textWatcher)
        employeeTrainingTypeText!!.addTextChangedListener(textWatcher)
        employeeCertificationTypeText!!.addTextChangedListener(textWatcher)

    }

    fun resetForm() {
        employeeNameTraineeText!!.setText("")
        employeeTrainingNameText!!.setText("")
        employeeTrainingOrganizerText!!.setText("")
        employeeTrainingDateText!!.setText("")
        employeeTrainingTypeText!!.setText("")
        employeeCertificationTypeText!!.setText("")
    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            val namaEmployeeTraineeTeks = employeeNameTraineeText!!.text.toString().trim()
            val namaEmployeeTrainingTeks = employeeTrainingNameText!!.text.toString().trim()
            val namaEmployeeTrainingOrganizerTeks = employeeTrainingOrganizerText!!.text.toString().trim()
            val dateEmployeeTrainingTeks = employeeTrainingDateText!!.text.toString().trim()
            val typeEmployeeTrainingTeks = employeeTrainingTypeText!!.text.toString().trim()
            val certificationEmployeeTrainingTeks = employeeCertificationTypeText!!.text.toString().trim()

            val kondisi =
                namaEmployeeTraineeTeks.isNotEmpty() || namaEmployeeTrainingTeks.isNotEmpty() || namaEmployeeTrainingOrganizerTeks.isNotEmpty() || dateEmployeeTrainingTeks.isNotEmpty()
                        || typeEmployeeTrainingTeks.isNotEmpty() || certificationEmployeeTrainingTeks.isNotEmpty()

            ubahResetButton(context, kondisi, buttonReset!!)

        }

        override fun afterTextChanged(s: Editable) {

        }
    }

    fun simpanEmployeeTraining() {
        val required1 = findViewById(R.id.requiredNamaEmployeeTraining) as TextView
        val required2 = findViewById(R.id.requiredNamaEmployeeTrainingOrganizer) as TextView
        val required3 = findViewById(R.id.requiredEmployeeTrainingDate) as TextView

        val namaEmployeeTrainee = employeeNameTraineeText!!.text.toString().trim()
        val namaEmployeeTraining = employeeTrainingNameText!!.text.toString().trim()
        val namaEmployeeTrainingOrganizer = employeeTrainingOrganizerText!!.text.toString().trim()
        val employeeTrainingDate = employeeTrainingDateText!!.text.toString().trim()
        val typeEmployeeTraining = employeeTrainingTypeText!!.text.toString().trim()
        val certificationEmployeeTraining = employeeCertificationTypeText!!.text.toString().trim()

        employeeTrainingNameText!!.setHintTextColor(defaultColor)
        required1.visibility = View.INVISIBLE

        employeeTrainingOrganizerText!!.setHintTextColor(defaultColor)
        required2.visibility = View.INVISIBLE

        employeeTrainingDateText!!.setHintTextColor(defaultColor)
        required3.visibility = View.INVISIBLE

        if (namaEmployeeTraining.isEmpty()) {
            employeeTrainingNameText!!.setHintTextColor(Color.RED)
            required1.visibility = View.VISIBLE
        }

        if (namaEmployeeTrainingOrganizer.isEmpty()) {
            employeeTrainingOrganizerText!!.setHintTextColor(Color.RED)
            required2.visibility = View.VISIBLE
        }

        if (employeeTrainingDate.isEmpty()) {
            employeeTrainingDateText!!.setHintTextColor(Color.RED)
            required3.visibility = View.VISIBLE
        }

        if (namaEmployeeTraining.isNotEmpty() && namaEmployeeTrainingOrganizer.isNotEmpty() && employeeTrainingDate.isNotEmpty()) {
            val model = EmployeeTraining()
            model.idEmployeeTraining = data.idEmployeeTraining
            model.namaTrainee = namaEmployeeTrainee
            model.namaEmployeeTraining = namaEmployeeTraining
            model.namaEmployeeTO = namaEmployeeTrainingOrganizer
            model.dateEmployeeTraining = employeeTrainingDate
            model.typeEmployeeTraining = typeEmployeeTraining
            model.typeEmployeeCertification = certificationEmployeeTraining

            val cekEmployeeTraining =
                databaseQueryHelper!!.cekEmployeeTrainingSudahAda(model.namaTrainee!!)

            if (cekEmployeeTraining > 0) {
                Toast.makeText(context, DATA_SUDAH_ADA, Toast.LENGTH_SHORT).show()
                return
            }
            if (databaseQueryHelper!!.tambahEmployeeTraining(model) == -1L) {
                Toast.makeText(context, SIMPAN_DATA_GAGAL, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, SIMPAN_DATA_BERHASIL, Toast.LENGTH_SHORT)
                    .show()
            }

        }

        finish()
    }

    fun loadDataEmployeeTraining(id: Int) {
        val db = databaseHelper.readableDatabase

        val projection = arrayOf<String>(
            ID_EMPLOYEE_TRAINING, NAMA_TRAINEE, NAMA_EMPLOYEE_TRAINING, NAMA_EMPLOYEE_TRAINING_ORG, DATE_EMPLOYEE_TRAINING, TYPE_EMPLOYEE_TRAINING, TYPE_EMPLOYEE_CERTIFICATION, IS_DELETED
        )
        val selection = ID_EMPLOYEE_TRAINING + "=?"
        val selectionArgs = arrayOf(id.toString())
        val cursor =
            db.query(TABEL_EMPLOYEE_TRAINING, projection, selection, selectionArgs, null, null, null)

        if (cursor.count == 1) {
            cursor.moveToFirst()
            data.idEmployeeTraining = cursor.getInt(0)
            data.namaTrainee = cursor.getString(1)
            data.namaEmployeeTraining = cursor.getString(2)
            data.namaEmployeeTO = cursor.getString(3)
            data.dateEmployeeTraining = cursor.getString(4)
            data.typeEmployeeTraining = cursor.getString(5)
            data.typeEmployeeCertification = cursor.getString(6)
            data.isDeleted = cursor.getString(7)

            namaEmployeeTrainee.setText(data.namaTrainee)
            namaEmployeeTraining.setText(data.namaEmployeeTraining)
            EmployeeTrainingDate.setText(data.dateEmployeeTraining)
            namaEmployeeTrainingOrganizer.setText(data.namaEmployeeTO)
            EmployeeTrainingType.setText(data.typeEmployeeTraining)
            EmployeeCertificationType.setText(data.typeEmployeeCertification)
        }
    }

}
