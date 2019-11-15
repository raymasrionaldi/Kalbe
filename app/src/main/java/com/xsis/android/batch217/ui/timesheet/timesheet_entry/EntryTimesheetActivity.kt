package com.xsis.android.batch217.ui.timesheet.timesheet_entry

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.core.view.isVisible
import com.xsis.android.batch217.R
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.TimesheetQueryHelper
import com.xsis.android.batch217.models.Timesheet
import com.xsis.android.batch217.ui.home.HomeFragment
import com.xsis.android.batch217.utils.ARRAY_OVERTIME_TIMESHEET
import com.xsis.android.batch217.utils.ARRAY_STATUS_TIMESHEET
import com.xsis.android.batch217.utils.HOUR_PATTERN
import com.xsis.android.batch217.utils.*
import kotlinx.android.synthetic.main.activity_entry_timesheet.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList






class EntryTimesheetActivity : AppCompatActivity() {
    val context = this
    //    var required: TextView? = null
    var data = Timesheet()
    var idTimesheet = 0
    var listCompany = ArrayList<String>()
    var buttonReset: Button? = null
    val databaseHelper = DatabaseHelper(this)
    val databaseQueryHelper = TimesheetQueryHelper(databaseHelper)
    var clientSpinner: Spinner? = null
    var statusSpinner: Spinner? = null
    var reportDate: TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entry_timesheet)
        context.title = "Timesheet"
        supportActionBar?.let {
            //menampilkan icon di toolbar
            supportActionBar!!.setHomeButtonEnabled(true)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
//            //ganti icon. Kalau mau default yang "<-", hapus line di bawah
//            supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_close_white)

        }
        isiSpinnerStatusTimesheet()
        isiSpinnerClientTimesheet()
        isiSpinnerOvertimeTimesheet()

        val bundle: Bundle? = intent.extras
        bundle?.let {
            idTimesheet = bundle!!.getInt(ID_TIMESHEET, 0)
            loadDataTimesheet(idTimesheet)
        }

        buttonReset = findViewById(R.id.buttonResetEntryFormTimesheet)
        clientSpinner = findViewById(R.id.inputClientTimesheet)
        statusSpinner = findViewById(R.id.inputStatusTimesheet)
        reportDate = findViewById(R.id.inputReportDateEntryTimesheet)


        setReportDateTimesheetPicker()
        setStartReportDateTimesheetPicker()
        setEndReportDateTimesheetPicker()
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

                if (position > 1 ) {
                    ubahResetButton(context, true, buttonReset!!)
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

//                    inputStarDatetEntryTimesheet.isEnabled = false
//                    inputEndDateEntryTimesheet.isEnabled = false
//                    inputOvertimeTimesheet.isEnabled = false
//                    inputStartOvertimeEntryTimesheet.isEnabled = false
//                    inputEndtOvertimeEntryTimesheet.isEnabled = false
//                    inputNotesEntryTimesheet.isEnabled = false

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

//                    inputStarDatetEntryTimesheet.isEnabled = true
//                    inputEndDateEntryTimesheet.isEnabled = true
//                    inputOvertimeTimesheet.isEnabled = true
//                    inputStartOvertimeEntryTimesheet.isEnabled = true
//                    inputEndtOvertimeEntryTimesheet.isEnabled = true
//                    inputNotesEntryTimesheet.isEnabled = true
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
                if (position != 0 ) {
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
                        if (position ==1){
//                            inputStartOvertimeEntryTimesheet!!.visibility = View.VISIBLE
//                            inputEndtOvertimeEntryTimesheet!!.visibility = View.VISIBLE
                            inputStartOvertimeEntryTimesheet.isClickable = true
                            inputEndtOvertimeEntryTimesheet.isClickable = true
                        }else if (position ==2){
//                            inputStartOvertimeEntryTimesheet!!.visibility = View.GONE
//                            inputEndtOvertimeEntryTimesheet!!.visibility = View.GONE
                            inputStartOvertimeEntryTimesheet.isClickable = false
                            inputEndtOvertimeEntryTimesheet.isClickable = false
                        }

                    } else {
                        ubahResetButton(context, false, buttonReset!!)
//                        inputStartOvertimeEntryTimesheet!!.visibility = View.VISIBLE
//                        inputEndtOvertimeEntryTimesheet!!.visibility = View.VISIBLE
                        inputStartOvertimeEntryTimesheet.isClickable = false
                        inputEndtOvertimeEntryTimesheet.isClickable = false
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
        var startReportDateTimesheet = inputStarDatetEntryTimesheet.text.toString().trim()
        var endReportDateTimesheet = inputEndDateEntryTimesheet.text.toString().trim()
        var overtimeTimesheet = inputOvertimeTimesheet.selectedItem.toString()
        var startOvertimeTimesheet = inputStartOvertimeEntryTimesheet.text.toString().trim()
        var endOvertimeTimesheet = inputEndtOvertimeEntryTimesheet.text.toString().trim()
        val notesTimesheet = inputNotesEntryTimesheet.text.toString().trim()

        requiredStatusTimesheet.visibility = View.GONE
        requiredClientTimesheet.visibility = View.GONE
        requiredReportDateTimesheet.visibility = View.GONE
        requiredStartEntryTimesheet.visibility = View.GONE
        requiredEndEntryTimesheet.visibility = View.GONE
        requiredNotesEntryTimesheet.visibility = View.GONE





        var isValid = true
        if (positionStatusTimesheet <= 1) {
            val sdf = SimpleDateFormat(HOUR_PATTERN)
            val sdf2 = SimpleDateFormat(HOUR_PATTERN)
            if (!startReportDateTimesheet.equals("") ||!endReportDateTimesheet.equals("") ){
                val startTime = sdf.parse(startReportDateTimesheet)
                val endTime = sdf.parse(endReportDateTimesheet)

                if(startTime >= endTime){
                    isValid = false
                    Toast.makeText(context, "Save Invalid, End time Report Date must be greater then Start time Report Date", Toast.LENGTH_SHORT).show()
                }
            }
            if (!startOvertimeTimesheet.equals("")||!endOvertimeTimesheet.equals("")){
                val startOvTime = sdf2.parse(startOvertimeTimesheet)
                val endOvTime = sdf2.parse(endOvertimeTimesheet)
                if (startOvTime >= endOvTime){
                    isValid = false
                    Toast.makeText(context, "Save Invalid, End time Overtime must be greater then Start time Overtime Date", Toast.LENGTH_SHORT).show()
                }
            }
            if (startOvertimeTimesheet <= endReportDateTimesheet && positionOvertimeTimesheet == 1){
                isValid = false
                Toast.makeText(context, "Save Invalid, Start time Overtime must be greater then End Time Report date", Toast.LENGTH_SHORT).show()
            }
            if (positionStatusTimesheet == 0) {
                requiredStatusTimesheet.isVisible = true
                isValid = false
            }
            if (positionClientTimesheet == 0) {
                requiredClientTimesheet.isVisible = true
                isValid = false
            }
            if (reportDateTimesheet.equals("")) {
                inputReportDateEntryTimesheet.setHintTextColor(Color.RED)
                requiredReportDateTimesheet.isVisible = true
                isValid = false
            } else {
                inputReportDateEntryTimesheet.setHintTextColor(Color.BLACK)
                requiredReportDateTimesheet.isVisible = false

            }
            if (startReportDateTimesheet.equals("")) {
                inputStarDatetEntryTimesheet.setHintTextColor(Color.RED)
                requiredStartEntryTimesheet.isVisible = true
                isValid = false
            } else {
                inputStarDatetEntryTimesheet.setHintTextColor(Color.BLACK)
                requiredStartEntryTimesheet.isVisible = false

            }
            if (endReportDateTimesheet.equals("")) {
                inputEndDateEntryTimesheet.setHintTextColor(Color.RED)
                requiredEndEntryTimesheet.isVisible = true
                isValid = false
            } else {
                inputEndDateEntryTimesheet.setHintTextColor(Color.BLACK)
                requiredEndEntryTimesheet.isVisible = false

            }// tambah required utk overtime
            if (positionOvertimeTimesheet == 0) {
                requiredOvertimeEntryTimesheet.isVisible = true
                isValid = false
            }
            if (notesTimesheet.equals("")) {
                inputNotesEntryTimesheet.setHintTextColor(Color.RED)
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
        if (isValid) {
            val model = Timesheet()
            model.id_timesheet = idTimesheet
            model.status_timesheet = statusTimesheet
            model.client_timesheet = clientTimesheet
            model.reportDate_timesheet = reportDateTimesheet
            model.startReportDate_timesheet = startReportDateTimesheet
            model.endReportDate_timesheet = endReportDateTimesheet
            if (positionOvertimeTimesheet == 0) {
                overtimeTimesheet = "NO"
            }
            model.overtime_timesheet = overtimeTimesheet
            model.starOvertime_timesheet = startOvertimeTimesheet
            model.endOvertime_timesheet = endOvertimeTimesheet
            model.notes_timesheet = notesTimesheet
            model.progress_timesheet = CREATED
            model.is_Deleted = "false"

            //add
            if (idTimesheet == 0) {
                if (databaseQueryHelper!!.tambahTimesheet(model) == -1L) {
                    Toast.makeText(context, SIMPAN_DATA_GAGAL, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, SIMPAN_DATA_BERHASIL, Toast.LENGTH_SHORT)
                        .show()
                }
                //update
            } else if (idTimesheet != 0) {
                if (databaseQueryHelper!!.editTimesheet(model) == 0) {
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
    fun isiSpinnerClientTimesheet(){
        val clientTimesheet = databaseQueryHelper.tampilkanClientTimesheet()

        listCompany.add("client*")
        clientTimesheet.forEach {
            listCompany.add(it.namaCompany!!)
        }

        val adapterClientTimesheet = ArrayAdapter<String>(
            context, android.R.layout.simple_spinner_item,
            listCompany
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //untuk kembali ke home activity
        if (item.itemId == android.R.id.home) {
            super.onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun loadDataTimesheet(ID_Timesheet: Int) {
        val db = databaseHelper.readableDatabase

        val projection = arrayOf<String>(
            ID_TIMESHEET, STATUS_TIMESHEET, CLIENT_DATABASE, REPORT_DATE_TIMESHEET,
            START_REPORT_DATE_TIMESHEET, END_REPORT_DATE_TIMESHEET, OVERTIME_TIMESHEET,
            START_REPORT_DATE_TIMESHEET, END_REPORT_DATE_TIMESHEET, NOTES_TIMESHEET,
            PROGRESS_TIMESHEET, IS_DELETED
        )
        val selection = ID_TIMESHEET + "=?"
        val selectionArgs = arrayOf(ID_Timesheet.toString())
        val cursor =
            db.query(TABEL_TIMESHEET, projection, selection, selectionArgs, null, null, null)

        if (cursor.count == 1) {
            cursor.moveToFirst()
            data.id_timesheet = cursor.getInt(0)

            val statusTimesheet = cursor.getString(1)
            val indexStatusTimesheet = ARRAY_STATUS_TIMESHEET.indexOf(statusTimesheet)
            inputStatusTimesheet.setSelection(indexStatusTimesheet)

            val clientTimesheet = cursor.getString(2)
            val indexClientTimesheet = listCompany.indexOf(clientTimesheet)
            inputClientTimesheet.setSelection(indexClientTimesheet)

            data.reportDate_timesheet = cursor.getString(3)
            inputReportDateEntryTimesheet.setText(data.reportDate_timesheet)

            data.startReportDate_timesheet = cursor.getString(4)
            inputStarDatetEntryTimesheet.setText(data.startReportDate_timesheet)

            data.endReportDate_timesheet = cursor.getString(5)
            inputEndDateEntryTimesheet.setText(data.endReportDate_timesheet)

            val overtimeTimesheet = cursor.getString(6)
            val indexOvertimeTimesheet = ARRAY_OVERTIME_TIMESHEET.indexOf(overtimeTimesheet)
            inputOvertimeTimesheet.setSelection(indexOvertimeTimesheet)

            data.starOvertime_timesheet = cursor.getString(7)
            inputStartOvertimeEntryTimesheet.setText(data.starOvertime_timesheet)

            data.endOvertime_timesheet = cursor.getString(8)
            inputEndtOvertimeEntryTimesheet.setText(data.endOvertime_timesheet)

            data.notes_timesheet = cursor.getString(9)
            inputNotesEntryTimesheet.setText(data.notes_timesheet)

            data.progress_timesheet = cursor.getString(10)

            data.is_Deleted = cursor.getString(11)

        }
    }

}
