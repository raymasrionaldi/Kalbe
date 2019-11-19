package com.xsis.android.batch217.ui.project

import android.app.Activity
import android.app.DatePickerDialog
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import com.chivorn.smartmaterialspinner.SmartMaterialSpinner
import com.xsis.android.batch217.R
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.ProjectQueryHelper
import com.xsis.android.batch217.models.Company
import com.xsis.android.batch217.models.Project
import com.xsis.android.batch217.utils.*
import kotlinx.android.synthetic.main.activity_project_form.*
import java.text.SimpleDateFormat
import java.util.*

class ProjectFormActivity : AppCompatActivity() {
    val context = this
    var idProject = 0
    lateinit var listCompany: List<Company>
    var isSpinnerCompanySelected = false
    var databaseQueryHelper: ProjectQueryHelper? = null
    var defaultColor = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_form)
        context.title = getString(R.string.menu_project)

        supportActionBar?.let {
            //menampilkan icon di toolbar
            supportActionBar!!.setHomeButtonEnabled(true)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            //ganti icon. Kalau mau default yang "<-", hapus line di bawah
            supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_close_white)
        }
        val databaseHelper = DatabaseHelper(context)
        databaseQueryHelper = ProjectQueryHelper(databaseHelper)

        listCompany = databaseQueryHelper!!.getSemuaCompany()
        val listNamaCompany = listCompany.map { company -> company.namaCompany }.toList()
        spinnerClientNameProject.item = listNamaCompany

        val bundle: Bundle? = intent.extras
        bundle?.let {
            idProject = bundle.getInt(ID_PROJECT, 0)
            loadDataProject(idProject)
        }

        setOnItemSelectedListener(spinnerClientNameProject)

        editStartProject()
        editEndProject()

        defaultColor = inputProjectNameProject.currentHintTextColor

        inputLocationProject.addTextChangedListener(textWatcher)
        inputDepartmentProject.addTextChangedListener(textWatcher)
        inputUserNameProject.addTextChangedListener(textWatcher)
        inputProjectNameProject.addTextChangedListener(textWatcher)
        inputStartProject.addTextChangedListener(textWatcher)
        inputEndProject.addTextChangedListener(textWatcher)
        inputRoleProject.addTextChangedListener(textWatcher)
        inputProjectPhaseProject.addTextChangedListener(textWatcher)
        inputProjectDesProject.addTextChangedListener(textWatcher)
        inputProjectTechProject.addTextChangedListener(textWatcher)
        inputMainTaskProject.addTextChangedListener(textWatcher)

        buttonResetProject.setOnClickListener {
            resetForm()
        }

        buttonSubmitProject.setOnClickListener {
            submitProject()
        }
    }

    private fun resetForm() {
        spinnerClientNameProject.clearSelection()
        isSpinnerCompanySelected = false
        inputLocationProject.text = null
        inputDepartmentProject.text = null
        inputUserNameProject.text = null
        inputProjectNameProject.text = null
        inputStartProject.text = null
        inputEndProject.text = null
        inputRoleProject.text = null
        inputProjectPhaseProject.text = null
        inputProjectDesProject.text = null
        inputProjectTechProject.text = null
        inputMainTaskProject.text = null
    }

    private fun editEndProject() {
        inputEndProject.setOnClickListener {

            val endProject = inputEndProject.text.toString()
            val calendar = Calendar.getInstance()
            val formatter = SimpleDateFormat(DATE_PATTERN)

            if (!endProject.isBlank()) {
                calendar.time = formatter.parse(endProject)
                Toast.makeText(context, endProject, Toast.LENGTH_SHORT).show()
            }
            val yearEnd = calendar.get(Calendar.YEAR)
            val monthEnd = calendar.get(Calendar.MONTH)
            val dayEnd = calendar.get(Calendar.DATE)

            val datePicker = DatePickerDialog(
                context,
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    val selectedDate = Calendar.getInstance()
                    selectedDate.set(year, month, dayOfMonth)

                    val tanggal = formatter.format(selectedDate.time)

                    //set tampilan
                    inputEndProject.setText(tanggal)
                }, yearEnd, monthEnd, dayEnd
            )
            datePicker.show()
        }
    }

    private fun editStartProject() {
        inputStartProject.setOnClickListener {
            val startProject = inputStartProject.text.toString()
            val calendar = Calendar.getInstance()
            val formatter = SimpleDateFormat(DATE_PATTERN)

            if (!startProject.isBlank()) {
                calendar.time = formatter.parse(startProject)
            }

            val yearStart = calendar.get(Calendar.YEAR)
            val monthStart = calendar.get(Calendar.MONTH)
            val dayStart = calendar.get(Calendar.DATE)

            val datePicker = DatePickerDialog(
                context,
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    val selectedDate = Calendar.getInstance()
                    selectedDate.set(year, month, dayOfMonth)

                    val tanggal = formatter.format(selectedDate.time)

                    //set tampilan
                    inputStartProject.setText(tanggal)
                }, yearStart, monthStart, dayStart
            )
            datePicker.show()
        }
    }


    private fun loadDataProject(idProject: Int) {
        val data = databaseQueryHelper!!.cariProjectModelById(idProject)

        val index =
            listCompany.indexOfFirst { company -> company.idCompany == data.kodeCompProject }
        if (index != -1) {
            spinnerClientNameProject.setSelection(index)
        }

        inputLocationProject.setText(data.locationProject)
        inputDepartmentProject.setText(data.departmentProject)
        inputUserNameProject.setText(data.userProject)
        inputProjectNameProject.setText(data.nameProject)
        inputStartProject.setText(data.startProject)
        inputEndProject.setText(data.endProject)
        inputRoleProject.setText(data.roleProject)
        inputProjectPhaseProject.setText(data.phaseProject)
        inputProjectDesProject.setText(data.desProject)
        inputProjectTechProject.setText(data.techProject)
        inputMainTaskProject.setText(data.taskProject)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //untuk kembali ke home activity
        if (item.itemId == android.R.id.home) {
            super.onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setOnItemSelectedListener(vararg spinners: SmartMaterialSpinner<*>) {
        for (spinner in spinners) {
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    adapterView: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    isSpinnerCompanySelected = true
                    ubahResetButton(context, true, buttonResetProject)
                }

                override fun onNothingSelected(adapterView: AdapterView<*>) {
                }
            }
        }
    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            val inputLocation = inputLocationProject.text.toString().trim()
            val inputDepartement = inputDepartmentProject.text.toString().trim()
            val inputUserName = inputUserNameProject.text.toString().trim()
            val inputProjectName = inputProjectNameProject.text.toString().trim()
            val inputStart = inputStartProject.text.toString().trim()
            val inputEnd = inputEndProject.text.toString().trim()
            val inputRole = inputRoleProject.text.toString().trim()
            val inputPhase = inputProjectPhaseProject.text.toString().trim()
            val inputDes = inputProjectDesProject.text.toString().trim()
            val inputTech = inputProjectTechProject.text.toString().trim()
            val inputTask = inputMainTaskProject.text.toString().trim()

            val kondisi =
                inputLocation.isNotEmpty() || inputDepartement.isNotEmpty() || inputUserName.isNotEmpty()
                        || inputProjectName.isNotEmpty() || inputStart.isNotEmpty() || inputEnd.isNotEmpty()
                        || inputRole.isNotEmpty() || inputPhase.isNotEmpty() || inputDes.isNotEmpty() || inputTech.isNotEmpty() ||
                        inputTask.isNotEmpty() || isSpinnerCompanySelected

            ubahResetButton(context, kondisi, buttonResetProject)
        }

        override fun afterTextChanged(s: Editable) {

        }
    }

    fun submitProject() {
        val index = spinnerClientNameProject.selectedItemPosition

        var idCompany = 0
        val inputLocation = inputLocationProject.text.toString().trim()
        val inputDepartement = inputDepartmentProject.text.toString().trim()
        val inputUserName = inputUserNameProject.text.toString().trim()
        val inputProjectName = inputProjectNameProject.text.toString().trim()
        val inputStart = inputStartProject.text.toString().trim()
        val inputEnd = inputEndProject.text.toString().trim()
        val inputRole = inputRoleProject.text.toString().trim()
        val inputPhase = inputProjectPhaseProject.text.toString().trim()
        val inputDes = inputProjectDesProject.text.toString().trim()
        val inputTech = inputProjectTechProject.text.toString().trim()
        val inputTask = inputMainTaskProject.text.toString().trim()


        var isValid = true

        if (index < 0) {
            isValid = false
            spinnerClientNameProject.isEnableErrorLabel = true
        } else {
            idCompany = listCompany[index].idCompany
            spinnerClientNameProject.isEnableErrorLabel = false
        }

        if (inputProjectName.isEmpty()) {
            isValid = false
            inputProjectNameProject.setHintTextColor(Color.RED)
            layoutProjectNameProject.error = "Required"
        } else {
            inputProjectNameProject.setHintTextColor(defaultColor)
            layoutProjectNameProject.isErrorEnabled = false
        }

        if (inputStart.isEmpty()) {
            isValid = false
            inputStartProject.setHintTextColor(Color.RED)
            layoutStartProject.error = "Required"
        } else {
            inputStartProject.setHintTextColor(defaultColor)
            layoutStartProject.isErrorEnabled = false
        }

        if (inputEnd.isEmpty()) {
            isValid = false
            inputEndProject.setHintTextColor(Color.RED)
            layoutEndProject.error = "Required"
        } else {
            inputEndProject.setHintTextColor(defaultColor)
            layoutEndProject.isErrorEnabled = false
        }

        if (inputRole.isEmpty()) {
            isValid = false
            inputRoleProject.setHintTextColor(Color.RED)
            layoutRoleProject.error = "Required"
        } else {
            inputRoleProject.setHintTextColor(defaultColor)
            layoutRoleProject.isErrorEnabled = false
        }

        if (inputPhase.isEmpty()) {
            isValid = false
            inputProjectPhaseProject.setHintTextColor(Color.RED)
            layoutProjectPhaseProject.error = "Required"
        } else {
            inputProjectPhaseProject.setHintTextColor(defaultColor)
            layoutProjectPhaseProject.isErrorEnabled = false
        }

        if (inputDes.isEmpty()) {
            isValid = false
            inputProjectDesProject.setHintTextColor(Color.RED)
            layoutProjectDesProject.error = "Required"
        } else {
            inputProjectDesProject.setHintTextColor(defaultColor)
            layoutProjectDesProject.isErrorEnabled = false
        }

        if (inputTech.isEmpty()) {
            isValid = false
            inputProjectTechProject.setHintTextColor(Color.RED)
            layoutProjectTechProject.error = "Required"
        } else {
            inputProjectTechProject.setHintTextColor(defaultColor)
            layoutProjectTechProject.isErrorEnabled = false
        }

        if (inputTask.isEmpty()) {
            isValid = false
            inputMainTaskProject.setHintTextColor(Color.RED)
            layoutMainTaskProject.error = "Required"
        } else {
            inputMainTaskProject.setHintTextColor(defaultColor)
            layoutMainTaskProject.isErrorEnabled = false
        }

        // check valid duration
        if (inputStart.isNotEmpty() && inputEnd.isNotEmpty()) {

            val startDate = Calendar.getInstance()
            val endDate = Calendar.getInstance()
            val formatter = SimpleDateFormat(DATE_PATTERN)

            startDate.time = formatter.parse(inputStart)
            endDate.time = formatter.parse(inputEnd)
            if (!startDate.before(endDate)) {
                Toast.makeText(context, "Input Start dan End salah", Toast.LENGTH_SHORT).show()
                isValid = false
            }
        }

        if (isValid) {
            val model = Project()
            model.idProject = idProject
            model.kodeCompProject = idCompany
            model.locationProject = inputLocation
            model.departmentProject = inputDepartement
            model.userProject = inputUserName
            model.nameProject = inputProjectName
            model.startProject = inputStart
            model.endProject = inputEnd
            model.roleProject = inputRole
            model.phaseProject = inputPhase
            model.desProject = inputDes
            model.techProject = inputTech
            model.taskProject = inputTask

            //add
            if (idProject == 0) {
                if (databaseQueryHelper!!.tambahProject(model) == -1L) {
                    Toast.makeText(context, SIMPAN_DATA_GAGAL, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, SIMPAN_DATA_BERHASIL, Toast.LENGTH_SHORT)
                        .show()
                }
                //update
            } else if (idProject != 0) {
                if (databaseQueryHelper!!.editProject(model) == 0) {
                    Toast.makeText(context, EDIT_DATA_GAGAL, Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(context, EDIT_DATA_BERHASIL, Toast.LENGTH_SHORT)
                        .show()
                }
            }

            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}