package com.xsis.android.batch217.ui.timesheet.timesheet_entry

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.viewpager.widget.ViewPager
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.fragments.TimesheetHistoryAdapter
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.TimesheetQueryHelper
import com.xsis.android.batch217.models.Timesheet
import com.xsis.android.batch217.ui.timesheet.ARRAY_CLIENT_TIMESHEET
import com.xsis.android.batch217.ui.timesheet.ARRAY_OVERTIME_TIMESHEET
import com.xsis.android.batch217.ui.timesheet.ARRAY_STATUS_TIMESHEET
import com.xsis.android.batch217.ui.timesheet.timesheet_history.FragmentDataHistoryTimesheet
import kotlinx.android.synthetic.main.fragment_entry_timesheet.*
import java.text.SimpleDateFormat
import java.util.*

class EntryTimesheetActivity : AppCompatActivity() {
    val context  = this
    var data = Timesheet()
    val databaseHelper = DatabaseHelper(this)
    val databaseQueryHelper = TimesheetQueryHelper(databaseHelper)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entry_timesheet)
        this.title = "add entry"

        isiSpinnerStatusTimesheet()
        setReportDateTimesheetPicker()
        setStartReportDateTimesheetPicker()
//        setEndReportDateTimesheetPicker()
//        isiSpinnerClientTimesheet()
//        isiSpinnerOvertimeTimesheet()
        buttonSaveEntryFormTimesheet.setOnClickListener {
            validasiInputFormEntry()
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
        } else if(clientTimesheet == 0){
            requiredClientTimesheet.isVisible = true
        } else if(reportDateTimesheet == ""){
            requiredReportDateTimesheet.isVisible = true
        } else if(startReportDateTimesheet == ""){
            requiredStartEntryTimesheet.isVisible = true
        } else if (endReportDateTimesheet == ""){
            requiredEndEntryTimesheet.isVisible = true
        } else if (notesTimesheet == ""){
            requiredNotesEntryTimesheet.isVisible = true
        } else {
            val model = Timesheet()
            model.id_timesheet = data!!.id_timesheet
            model.status_timesheet = data!!.status_timesheet
            model.client_timesheet = data!!.client_timesheet
            model.reportDate_timesheet = reportDateTimesheet
            model.startReportDate_timesheet = startReportDateTimesheet
            model.endReportDate_timesheet = endReportDateTimesheet
            model.overtime_timesheet = data!!.overtime_timesheet
            model.starOvertime_timesheet = startOvertimeTimesheet
            model.endOvertime_timesheet = endOvertimeTimesheet
            model.notes_timesheet = notesTimesheet
        }
//        val viewPager = getView()!!.parent as ViewPager
//        val adapter = viewPager.adapter!! as TimesheetHistoryAdapter
//        val fragment = fm.fragments[0] as FragmentDataHistoryTimesheet
//        fragment.updateKontrak()
//        adapter.notifyDataSetChanged()
//        viewPager.setCurrentItem(0, true)
//            insertkeDatabaseTimesheet(
//                ARRAY_STATUS_TIMESHEET(statusTimesheet),
//                ARRAY_CLIENT_TIMESHEET(clientTimesheet),reportDateTimesheet,
//                startReportDateTimesheet,endReportDateTimesheet,
//                ARRAY_OVERTIME_TIMESHEET(overtimeTimesheet),startOvertimeTimesheet,
//                endOvertimeTimesheet,notesTimesheet)
//        }
//    }
//    fun insertkeDatabaseTimesheet(statusTimesheet: String,
//                                  clientTimesheet: String, reportDateTimesheet: String,
//                                  startReportDateTimesheet: String, endReportDateTimesheet: String,
//                                  overtimeTimesheet: String, startOvertimeTimesheet:String,
//                                  endOvertimeTimesheet: String, notesTimesheet: String){


    }
    fun isiSpinnerStatusTimesheet(){
        val adapterStatusTimesheet = ArrayAdapter<String>(context,android.R.layout.simple_spinner_item,
            ARRAY_STATUS_TIMESHEET)
        adapterStatusTimesheet.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        inputStatusTimesheet.adapter = adapterStatusTimesheet

//        val adapterClientTimesheet = ArrayAdapter<String>(context,android.R.layout.simple_spinner_item,
//            ARRAY_CLIENT_TIMESHEET)
//        adapterClientTimesheet.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        inputStatusTimesheet.adapter = adapterClientTimesheet
//
//        val adapterOvertimeTimesheet = ArrayAdapter<String>(context,android.R.layout.simple_spinner_item,
//            ARRAY_OVERTIME_TIMESHEET)
//        adapterOvertimeTimesheet.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        inputStatusTimesheet.adapter = adapterOvertimeTimesheet

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
//        val adapterClientTimesheet = ArrayAdapter<String>(context,android.R.layout.simple_spinner_item,
//            ARRAY_CLIENT_TIMESHEET)
//        adapterClientTimesheet.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        inputStatusTimesheet.adapter = adapterClientTimesheet
    }
    fun isiSpinnerOvertimeTimesheet(){
//        val adapterOvertimeTimesheet = ArrayAdapter<String>(context,android.R.layout.simple_spinner_item,
//            ARRAY_OVERTIME_TIMESHEET)
//        adapterOvertimeTimesheet.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        inputStatusTimesheet.adapter = adapterOvertimeTimesheet
    }
}
