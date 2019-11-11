package com.xsis.android.batch217.ui.timesheet.timesheet_entry
import android.app.DatePickerDialog
import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import com.xsis.android.batch217.R
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.TimesheetQueryHelper
import com.xsis.android.batch217.models.Timesheet
import com.xsis.android.batch217.ui.timesheet.ARRAY_OVERTIME_TIMESHEET
import com.xsis.android.batch217.ui.timesheet.ARRAY_STATUS_TIMESHEET
import com.xsis.android.batch217.utils.*
import kotlinx.android.synthetic.main.activity_entry_timesheet.*
import java.text.SimpleDateFormat
import java.util.*

class EntryTimesheetActivity : AppCompatActivity() {
    val context  = this
//    var required: TextView? = null
    var data = Timesheet()

    val databaseHelper = DatabaseHelper(this)
    val databaseQueryHelper = TimesheetQueryHelper(databaseHelper)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entry_timesheet)
        this.title = "add entry"
//        required = findViewById(R.id.requiredStatusTimesheet) as TextView

        isiSpinnerStatusTimesheet()
        setReportDateTimesheetPicker()
        setStartReportDateTimesheetPicker()
//        setEndReportDateTimesheetPicker()
        isiSpinnerClientTimesheet()
        isiSpinnerOvertimeTimesheet()
//        inputStatusTimesheet.addTextChangedListener(textWatcher)
//        inputDepartmentProject.addTextChangedListener(textWatcher)
//        inputUserNameProject.addTextChangedListener(textWatcher)
//        inputProjectNameProject.addTextChangedListener(textWatcher)
//        inputStartProject.addTextChangedListener(textWatcher)
//        inputEndProject.addTextChangedListener(textWatcher)
//        inputRoleProject.addTextChangedListener(textWatcher)
//        inputProjectPhaseProject.addTextChangedListener(textWatcher)
//        inputProjectDesProject.addTextChangedListener(textWatcher)
//        inputProjectTechProject.addTextChangedListener(textWatcher)
//        inputMainTaskProject.addTextChangedListener(textWatcher)
        buttonSaveEntryFormTimesheet.setOnClickListener {
            validasiInputFormEntry()
        }
        buttonResetEntryFormTimesheet.setOnClickListener{
            resetTimesheet()
        }

    }
    fun validasiInputFormEntry(){
        val statusTimesheet = inputStatusTimesheet.selectedItemPosition
        val clientTimesheet = inputClientTimesheet.selectedItemPosition
        val reportDateTimesheet = inputReportDateEntryTimesheet.text.toString()
        val startReportDateTimesheet = inputStarDatetEntryTimesheet.text.toString().trim()
        val endReportDateTimesheet = inputEndDateEntryTimesheet.text.toString().trim()
        val overtimeTimesheet = inputOvertimeTimesheet.selectedItemPosition
        val startOvertimeTimesheet = inputStartOvertimeEntryTimesheet.text.toString().trim()
        val endOvertimeTimesheet = inputEndtOvertimeEntryTimesheet.text.toString().trim()
        val notesTimesheet = inputNotesEntryTimesheet.text.toString().trim()


        if (statusTimesheet == 0){
            requiredStatusTimesheet.isVisible = true
        } else if(clientTimesheet.equals("")){
            requiredClientTimesheet.isVisible = true
        } else if(reportDateTimesheet.equals("")){
            requiredReportDateTimesheet.isVisible = true
        } else if(startReportDateTimesheet.equals("")){
            requiredStartEntryTimesheet.isVisible = true
        } else if (endReportDateTimesheet.equals("")){
            requiredEndEntryTimesheet.isVisible = true
        } else if (notesTimesheet.equals("")){
            requiredNotesEntryTimesheet.isVisible = true
        } else {
            val content = ContentValues()
            content.put(STATUS_TIMESHEET, statusTimesheet)
            content.put(CLIENT_DATABASE, clientTimesheet)
            content.put(REPORT_DATE_TIMESHEET, reportDateTimesheet)
            content.put(START_REPORT_DATE_TIMESHEET, startReportDateTimesheet)
            content.put(END_REPORT_DATE_TIMESHEET, endReportDateTimesheet)
            content.put(OVERTIME_TIMESHEET, overtimeTimesheet)
            content.put(START_REPORT_OVERTIME, startOvertimeTimesheet)
            content.put(END_REPORT_OVERTIME, endOvertimeTimesheet)
            content.put(NOTES_TIMESHEET, notesTimesheet)
            val db = DatabaseHelper(context!!).writableDatabase
            db.insert(TABEL_TIMESHEET, null, content)
            Toast.makeText(context, SIMPAN_DATA_BERHASIL, Toast.LENGTH_SHORT).show()
            finish()
        }
    }
    fun resetTimesheet(){
        inputStatusTimesheet.setSelection(0)
        inputClientTimesheet.setSelection(0)
        inputReportDateEntryTimesheet!!.setText("")
        inputStarDatetEntryTimesheet!!.setText("")
        inputEndDateEntryTimesheet!!.setText("")
        inputOvertimeTimesheet.setSelection(0)
        inputStartOvertimeEntryTimesheet!!.setText("")
        inputEndtOvertimeEntryTimesheet!!.setText("")
        inputNotesEntryTimesheet!!.setText("")
//        val statusTimesheet = inputStatusTimesheet.selectedItemPosition
//        val clientTimesheet = inputClientTimesheet.selectedItemPosition
//        val reportDateTimesheet = inputReportDateEntryTimesheet.text.toString()
//        val startReportDateTimesheet = inputStarDatetEntryTimesheet.text.toString().trim()
//        val endReportDateTimesheet = inputEndDateEntryTimesheet.text.toString().trim()
//        val overtimeTimesheet = inputOvertimeTimesheet.selectedItemPosition
//        val startOvertimeTimesheet = inputStartOvertimeEntryTimesheet.text.toString().trim()
//        val endOvertimeTimesheet = inputEndtOvertimeEntryTimesheet.text.toString().trim()
//        val notesTimesheet = inputNotesEntryTimesheet.text.toString().trim()

//        if (statusTimesheet == 0 || clientTimesheet == 0|| reportDateTimesheet.equals("")||startReportDateTimesheet.equals("")||
//                endReportDateTimesheet.equals("")|| overtimeTimesheet==0
//                || startOvertimeTimesheet.equals("")||endOvertimeTimesheet.equals("")||notesTimesheet.equals("")){
//            buttonResetEntryFormTimesheet.isClickable =false
//        } else{
////            buttonResetEntryFormTimesheet.isClickable= true


//        }
    }
    fun isiSpinnerStatusTimesheet(){
        val adapterStatusTimesheet = ArrayAdapter<String>(context,android.R.layout.simple_spinner_item,
            ARRAY_STATUS_TIMESHEET)
        adapterStatusTimesheet.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        inputStatusTimesheet.adapter = adapterStatusTimesheet


    }
    fun setReportDateTimesheetPicker(){
        val today = Calendar.getInstance()
        val yearNow = today.get(Calendar.YEAR)
        val monthNow = today.get(Calendar.MONTH)
        val dayNow = today.get(Calendar.DATE)
        inputReportDateEntryTimesheet.setOnClickListener {
            val datePickerDialog = DatePickerDialog(context, R.style.AppTheme, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, month, dayOfMonth)

                //konversi ke string
                val formatDate = SimpleDateFormat("MMMM/dd/yyyy")
                val tanggal = formatDate.format(selectedDate.time)

                //set tampilan
                inputReportDateEntryTimesheet.text = tanggal
            }, yearNow,monthNow,dayNow )
            datePickerDialog.show()
        }
    }
    fun setStartReportDateTimesheetPicker(){
//        val formatter = SimpleDateFormat("HH:mm")
//        val jam = formatter.parse()

    }
    fun isiSpinnerClientTimesheet(){
        val clientTimesheet = databaseQueryHelper.tampilkanClientTimesheet()

        val isiData = clientTimesheet.map {
            it.namaCompany
        }.toMutableList()
        isiData.add(0, "client*")
        val adapterClientTimesheet = ArrayAdapter<String>(context,android.R.layout.simple_spinner_item,
            isiData)
        adapterClientTimesheet.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        inputClientTimesheet.adapter = adapterClientTimesheet
    }
    fun isiSpinnerOvertimeTimesheet(){
        val adapterOvertimeTimesheet = ArrayAdapter<String>(context,android.R.layout.simple_spinner_item,
            ARRAY_OVERTIME_TIMESHEET)
        adapterOvertimeTimesheet.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        inputOvertimeTimesheet.adapter = adapterOvertimeTimesheet

    }
    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
//            val namaTeks = nama!!.text.toString().trim()
//            val notesTeks = notes!!.text.toString().trim()
            val statusTimesheet = inputStatusTimesheet!!.selectedItemPosition
            val clientTimesheet = inputClientTimesheet!!.selectedItemPosition
            val reportDateTimesheet = inputReportDateEntryTimesheet!!.text.toString()
            val startReportDateTimesheet = inputStarDatetEntryTimesheet!!.text.toString().trim()
            val endReportDateTimesheet = inputEndDateEntryTimesheet!!.text.toString().trim()
            val overtimeTimesheet = inputOvertimeTimesheet!!.selectedItemPosition
            val startOvertimeTimesheet = inputStartOvertimeEntryTimesheet!!.text.toString().trim()
            val endOvertimeTimesheet = inputEndtOvertimeEntryTimesheet!!.text.toString().trim()
            val notesTimesheet = inputNotesEntryTimesheet!!.text.toString().trim()

//            val kondisi = !namaTeks.isEmpty() || !notesTeks.isEmpty()
            val kondisi = statusTimesheet != 0 || clientTimesheet != 0|| !reportDateTimesheet.isEmpty()||!startReportDateTimesheet.isEmpty()||
                    !endReportDateTimesheet.isEmpty()|| overtimeTimesheet!=0
                    || !startOvertimeTimesheet.isEmpty()||!endOvertimeTimesheet.isEmpty()||!notesTimesheet.isEmpty()

            ubahResetButton(context!!, kondisi, buttonResetEntryFormTimesheet!!)
        }

        override fun afterTextChanged(s: Editable) {

        }
    }
}
