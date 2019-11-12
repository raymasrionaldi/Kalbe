package com.xsis.android.batch217.ui.timesheet.timesheet_entry

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.core.view.isVisible
import com.xsis.android.batch217.R
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.TimesheetQueryHelper
import com.xsis.android.batch217.models.Timesheet
import com.xsis.android.batch217.utils.ARRAY_OVERTIME_TIMESHEET
import com.xsis.android.batch217.utils.ARRAY_STATUS_TIMESHEET
import com.xsis.android.batch217.utils.HOUR_PATTERN
import com.xsis.android.batch217.utils.*
import kotlinx.android.synthetic.main.activity_entry_timesheet.*
import java.text.SimpleDateFormat
import java.util.*

class EntryTimesheetActivity : AppCompatActivity() {
    val context = this
    //    var required: TextView? = null
    var data = Timesheet()
    var buttonReset: Button? = null
    val databaseHelper = DatabaseHelper(this)
    val databaseQueryHelper = TimesheetQueryHelper(databaseHelper)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entry_timesheet)
        this.title = "add entry"
//        required = findViewById(R.id.requiredStatusTimesheet) as TextView
        buttonReset = findViewById(R.id.buttonResetEntryFormTimesheet)

        isiSpinnerStatusTimesheet()
        setReportDateTimesheetPicker()
        setStartReportDateTimesheetPicker()
        setEndReportDateTimesheetPicker()
        isiSpinnerClientTimesheet()
        isiSpinnerOvertimeTimesheet()
        setStartOvertimeTimesheetPicker()
        setEndOvertimeTimesheetPicker()
        inputReportDateEntryTimesheet.addTextChangedListener(textWatcher)
        inputStarDatetEntryTimesheet.addTextChangedListener(textWatcher)
        inputEndDateEntryTimesheet.addTextChangedListener(textWatcher)
        inputStartOvertimeEntryTimesheet.addTextChangedListener(textWatcher)
        inputEndtOvertimeEntryTimesheet.addTextChangedListener(textWatcher)
        inputNotesEntryTimesheet.addTextChangedListener(textWatcher)

        inputStatusTimesheet.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position != 0) {
                    ubahResetButton(context, true, buttonReset!!)
                } else {
                    ubahResetButton(context, false, buttonReset!!)
                }

                if (position > 1) {
                    // hide form dan set value ke default
                    inputStarDatetEntryTimesheet!!.visibility = View.GONE
                    inputEndDateEntryTimesheet!!.visibility = View.GONE
                    inputOvertimeTimesheet!!.visibility = View.GONE
                    inputStartOvertimeEntryTimesheet!!.visibility = View.GONE
                    inputEndtOvertimeEntryTimesheet!!.visibility = View.GONE
                    inputNotesEntryTimesheet!!.visibility = View.GONE
                    requiredStatusTimesheet!!.visibility = View.GONE
                    requiredClientTimesheet!!.visibility = View.GONE
                    requiredStartEntryTimesheet!!.visibility = View.GONE
                    requiredEndEntryTimesheet!!.visibility = View.GONE
                    requiredNotesEntryTimesheet!!.visibility = View.GONE

                    inputStarDatetEntryTimesheet!!.setText("")
                    inputEndDateEntryTimesheet!!.setText("")
                    inputOvertimeTimesheet.setSelection(0)
                    inputStartOvertimeEntryTimesheet!!.setText("")
                    inputEndtOvertimeEntryTimesheet!!.setText("")
                    inputNotesEntryTimesheet!!.setText("")

                } else {
                    //show form
                    inputStarDatetEntryTimesheet!!.visibility = View.VISIBLE
                    inputEndDateEntryTimesheet!!.visibility = View.VISIBLE
                    inputOvertimeTimesheet!!.visibility = View.VISIBLE
                    inputStartOvertimeEntryTimesheet!!.visibility = View.VISIBLE
                    inputEndtOvertimeEntryTimesheet!!.visibility = View.VISIBLE
                    inputNotesEntryTimesheet!!.visibility = View.VISIBLE
                }

            }

            override fun onNothingSelected(arg0: AdapterView<*>) {}

        }
        inputClientTimesheet.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position != 0) {
                    ubahResetButton(context, true, buttonReset!!)
                } else {
                    ubahResetButton(context, false, buttonReset!!)
                }
            }

            override fun onNothingSelected(arg0: AdapterView<*>) {}

        }
        inputOvertimeTimesheet.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (position != 0) {
                        ubahResetButton(context, true, buttonReset!!)
                    } else {
                        ubahResetButton(context, false, buttonReset!!)
                    }
                }

                override fun onNothingSelected(arg0: AdapterView<*>) {}

            }

        buttonSaveEntryFormTimesheet.setOnClickListener {
            validasiInputFormEntry()
        }
        buttonResetEntryFormTimesheet.setOnClickListener {
            resetTimesheet()
        }

    }

    fun validasiInputFormEntry() {
        val positionStatusTimesheet = inputStatusTimesheet.selectedItemPosition
        val positionClientTimesheet = inputClientTimesheet.selectedItemPosition
        val positionOvertimeTimesheet = inputOvertimeTimesheet.selectedItemPosition
        val statusTimesheet = inputStatusTimesheet.selectedItem.toString()
        val clientTimesheet = inputClientTimesheet.selectedItem.toString()
        val reportDateTimesheet = inputReportDateEntryTimesheet.text.toString()
        val startReportDateTimesheet = inputStarDatetEntryTimesheet.text.toString().trim()
        val endReportDateTimesheet = inputEndDateEntryTimesheet.text.toString().trim()
        var overtimeTimesheet = inputOvertimeTimesheet.selectedItem.toString()
        val startOvertimeTimesheet = inputStartOvertimeEntryTimesheet.text.toString().trim()
        val endOvertimeTimesheet = inputEndtOvertimeEntryTimesheet.text.toString().trim()
        val notesTimesheet = inputNotesEntryTimesheet.text.toString().trim()

        requiredStatusTimesheet.visibility = View.GONE
        requiredClientTimesheet.visibility = View.GONE
        requiredReportDateTimesheet.visibility = View.GONE
        requiredStartEntryTimesheet.visibility = View.GONE
        requiredEndEntryTimesheet.visibility = View.GONE
        requiredNotesEntryTimesheet.visibility = View.GONE

        var isValid = true
        if (positionStatusTimesheet <= 1) {

            if (positionStatusTimesheet == 0) {
                requiredStatusTimesheet.isVisible = true
                isValid = false
            }
            if (positionClientTimesheet == 0) {
                requiredClientTimesheet.isVisible = true
                isValid = false
            }
            if (reportDateTimesheet.equals("")) {
                requiredReportDateTimesheet.isVisible = true
                isValid = false
            }
            if (startReportDateTimesheet.equals("")) {
                requiredStartEntryTimesheet.isVisible = true
                isValid = false
            }
            if (endReportDateTimesheet.equals("")) {
                requiredEndEntryTimesheet.isVisible = true
                isValid = false
            } // tambah required utk overtime
            if (notesTimesheet.equals("")) {
                requiredNotesEntryTimesheet.isVisible = true
                isValid = false
            }
        } else {
            if (positionClientTimesheet == 0) {
                requiredClientTimesheet.isVisible = true
                isValid = false
            }
            if (reportDateTimesheet.equals("")) {
                requiredReportDateTimesheet.isVisible = true
                isValid = false
            }
        }
        if (isValid){
            val content = ContentValues()
            content.put(STATUS_TIMESHEET, statusTimesheet)
            content.put(CLIENT_DATABASE, clientTimesheet)
            content.put(REPORT_DATE_TIMESHEET, reportDateTimesheet)
            content.put(START_REPORT_DATE_TIMESHEET, startReportDateTimesheet)
            content.put(END_REPORT_DATE_TIMESHEET, endReportDateTimesheet)
            if (positionOvertimeTimesheet == 0){
                overtimeTimesheet = "NO"
            }
            content.put(OVERTIME_TIMESHEET, overtimeTimesheet)
            content.put(START_REPORT_OVERTIME, startOvertimeTimesheet)
            content.put(END_REPORT_OVERTIME, endOvertimeTimesheet)
            content.put(NOTES_TIMESHEET, notesTimesheet)
            content.put(PROGRESS_TIMESHEET, CREATED)
            content.put(IS_DELETED, "false")
            val db = DatabaseHelper(context!!).writableDatabase
            db.insert(TABEL_TIMESHEET, null, content)
            Toast.makeText(context, SIMPAN_DATA_BERHASIL, Toast.LENGTH_SHORT).show()
            finish()

        }

    }

    fun resetTimesheet() {
        inputStatusTimesheet.setSelection(0)
        inputClientTimesheet.setSelection(0)
        inputReportDateEntryTimesheet!!.setText("")
        inputStarDatetEntryTimesheet!!.setText("")
        inputEndDateEntryTimesheet!!.setText("")
        inputOvertimeTimesheet.setSelection(0)
        inputStartOvertimeEntryTimesheet!!.setText("")
        inputEndtOvertimeEntryTimesheet!!.setText("")
        inputNotesEntryTimesheet!!.setText("")
    }

    fun isiSpinnerStatusTimesheet() {
        val adapterStatusTimesheet = ArrayAdapter<String>(
            context, android.R.layout.simple_spinner_item,
            ARRAY_STATUS_TIMESHEET
        )
        adapterStatusTimesheet.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        inputStatusTimesheet.adapter = adapterStatusTimesheet


    }

    fun setReportDateTimesheetPicker() {
        val today = Calendar.getInstance()
        val yearNow = today.get(Calendar.YEAR)
        val monthNow = today.get(Calendar.MONTH)
        val dayNow = today.get(Calendar.DATE)
        inputReportDateEntryTimesheet.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                context,
                R.style.AppTheme,
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    val selectedDate = Calendar.getInstance()
                    selectedDate.set(year, month, dayOfMonth)

                    //konversi ke string
                    val formatDate = SimpleDateFormat("MMMM dd, yyyy")
                    val tanggal = formatDate.format(selectedDate.time)

                    //set tampilan
                    inputReportDateEntryTimesheet.text = tanggal
                },
                yearNow,
                monthNow,
                dayNow
            )
            datePickerDialog.show()
        }
    }

    fun setStartReportDateTimesheetPicker() {
//        val formatter = SimpleDateFormat("HH:mm")
//        val jam = formatter.parse()
        val formatter = SimpleDateFormat(HOUR_PATTERN)
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)
        inputStarDatetEntryTimesheet.setOnClickListener {
            val tpd = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { view, h, m ->

                Toast.makeText(this, "$h.$m", Toast.LENGTH_LONG).show()
                val selectedDate = Calendar.getInstance()
                selectedDate.set(Calendar.HOUR_OF_DAY, h)
                selectedDate.set(Calendar.MINUTE, m)

                val jam = formatter.format(selectedDate.time)
                Toast.makeText(this, "$jam", Toast.LENGTH_SHORT).show()
                //set tampilan
                inputStarDatetEntryTimesheet.setText(jam)

            }, hour, minute, true)

            tpd.show()
        }
    }

    fun setEndReportDateTimesheetPicker() {
        val formatter = SimpleDateFormat(HOUR_PATTERN)
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)
        inputEndDateEntryTimesheet.setOnClickListener {
            val tpd = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { view, h, m ->

                Toast.makeText(this, "$h.$m", Toast.LENGTH_LONG).show()
                val selectedDate = Calendar.getInstance()
                selectedDate.set(Calendar.HOUR_OF_DAY, h)
                selectedDate.set(Calendar.MINUTE, m)

                val jam = formatter.format(selectedDate.time)
                Toast.makeText(this, "$jam", Toast.LENGTH_SHORT).show()
                //set tampilan
                inputEndDateEntryTimesheet.setText(jam)

            }, hour, minute, true)

            tpd.show()
        }
    }

    fun setStartOvertimeTimesheetPicker() {
        val formatter = SimpleDateFormat(HOUR_PATTERN)
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)
        inputStartOvertimeEntryTimesheet.setOnClickListener {
            val tpd = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { view, h, m ->

                Toast.makeText(this, "$h.$m", Toast.LENGTH_LONG).show()
                val selectedDate = Calendar.getInstance()
                selectedDate.set(Calendar.HOUR_OF_DAY, h)
                selectedDate.set(Calendar.MINUTE, m)

                val jam = formatter.format(selectedDate.time)
                Toast.makeText(this, "$jam", Toast.LENGTH_SHORT).show()
                //set tampilan
                inputStartOvertimeEntryTimesheet.setText(jam)

            }, hour, minute, true)

            tpd.show()
        }
    }

    fun setEndOvertimeTimesheetPicker() {
        val formatter = SimpleDateFormat(HOUR_PATTERN)
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)
        inputEndtOvertimeEntryTimesheet.setOnClickListener {
            val tpd = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { view, h, m ->

                Toast.makeText(this, "$h.$m", Toast.LENGTH_LONG).show()
                val selectedDate = Calendar.getInstance()
                selectedDate.set(Calendar.HOUR_OF_DAY, h)
                selectedDate.set(Calendar.MINUTE, m)

                val jam = formatter.format(selectedDate.time)
                Toast.makeText(this, "$jam", Toast.LENGTH_SHORT).show()
                //set tampilan
                inputEndtOvertimeEntryTimesheet.setText(jam)

            }, hour, minute, true)

            tpd.show()
        }
    }

    fun isiSpinnerClientTimesheet() {
        val clientTimesheet = databaseQueryHelper.tampilkanClientTimesheet()

        val isiData = clientTimesheet.map {
            it.namaCompany
        }.toMutableList()
        isiData.add(0, "client*")
        val adapterClientTimesheet = ArrayAdapter<String>(
            context, android.R.layout.simple_spinner_item,
            isiData
        )
        adapterClientTimesheet.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        inputClientTimesheet.adapter = adapterClientTimesheet
    }

    fun isiSpinnerOvertimeTimesheet() {
        val adapterOvertimeTimesheet = ArrayAdapter<String>(
            context, android.R.layout.simple_spinner_item,
            ARRAY_OVERTIME_TIMESHEET
        )
        adapterOvertimeTimesheet.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        inputOvertimeTimesheet.adapter = adapterOvertimeTimesheet

    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            val reportDateTimesheet = inputReportDateEntryTimesheet!!.text.toString()
            val startReportDateTimesheet = inputStarDatetEntryTimesheet!!.text.toString().trim()
            val endReportDateTimesheet = inputEndDateEntryTimesheet!!.text.toString().trim()
            val startOvertimeTimesheet = inputStartOvertimeEntryTimesheet!!.text.toString().trim()
            val endOvertimeTimesheet = inputEndtOvertimeEntryTimesheet!!.text.toString().trim()
            val notesTimesheet = inputNotesEntryTimesheet!!.text.toString().trim()
            buttonReset = buttonResetEntryFormTimesheet

//            val kondisi = !namaTeks.isEmpty() || !notesTeks.isEmpty()
            val kondisi = !reportDateTimesheet.isEmpty() || !startReportDateTimesheet.isEmpty() ||
                    !endReportDateTimesheet.isEmpty()
                    || !startOvertimeTimesheet.isEmpty() || !endOvertimeTimesheet.isEmpty() || !notesTimesheet.isEmpty()

            ubahResetButton(context!!, kondisi, buttonReset!!)
        }

        override fun afterTextChanged(s: Editable) {

        }
    }
}
