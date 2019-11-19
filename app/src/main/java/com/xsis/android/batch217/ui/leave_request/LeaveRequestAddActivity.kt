package com.xsis.android.batch217.ui.leave_request

import android.app.Activity
import android.app.DatePickerDialog
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.chivorn.smartmaterialspinner.SmartMaterialSpinner
import com.xsis.android.batch217.R
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.LeaveRequestQueryHelper
import com.xsis.android.batch217.models.LeaveRequest
import com.xsis.android.batch217.utils.*
import kotlinx.android.synthetic.main.activity_leave_request_add.*
import kotlinx.android.synthetic.main.activity_leave_request_edit.*
import kotlinx.android.synthetic.main.form_anggota_keluarga.view.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList

class LeaveRequestAddActivity : AppCompatActivity() {
    val context = this
    lateinit var listLeaveType: List<LeaveRequest>
    lateinit var listCutiKhusus: List<LeaveRequest>
    var databaseQueryHelper: LeaveRequestQueryHelper? = null
    val currentYear = Calendar.getInstance().get(Calendar.YEAR)

    var defaultColor = 0
    var curLeaveNameItem = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leave_request_add)
        context.title = getString(R.string.menu_leave_request)

        supportActionBar?.let {
            //menampilkan icon di toolbar
            supportActionBar!!.setHomeButtonEnabled(true)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            //ganti icon. Kalau mau default yang "<-", hapus line di bawah
            supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_close_white)
        }

        val databaseHelper = DatabaseHelper(context)
        databaseQueryHelper = LeaveRequestQueryHelper(databaseHelper)

        listLeaveType = databaseQueryHelper!!.getLeaveTypeModels()
        val listLeaveType = listLeaveType.map { leave -> leave.leaveType }.toList()
        spinnerLeaveType.item = listLeaveType
        //setOnItemSelectedListener(spinnerLeaveType)

        listCutiKhusus = databaseQueryHelper!!.getCutiKhususModels()
        val listCutiKhusus = listCutiKhusus.map { leave -> leave.leaveName }.toList()
        spinnerLeaveName.item = listCutiKhusus
        //setOnItemSelectedListener(spinnerLeaveType)
        setOnItemSelectedListener(spinnerLeaveType, spinnerLeaveName)

        initDatePickerStartLeave()
        initDatePickerEndLeave()

        defaultColor = inputAddressLeave.currentHintTextColor

        buttonResetLeave.setOnClickListener {
            resetForm()
        }

        buttonSubmitLeave.setOnClickListener {
            submitLeaveRequest()
        }
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
                    /*if (spinner.item[position] == "Cuti Khusus") {
                        spinnerLeaveName.visibility = View.VISIBLE
                    } else {
                        spinnerLeaveName.visibility = View.GONE
                        // layoutEndLeave.setEnabled(true)
                    }*/

                    println("BULAN# onChange: $spinner")

                    if (spinners[0] == spinner) {
                        if (spinner.item[position] == "Cuti Khusus") {
                            spinnerLeaveName.visibility = View.VISIBLE
                            clearCalendar()
                        } else {
                            //TODO check spinner is visible
                            spinnerLeaveName.visibility = View.GONE
                            spinnerLeaveName.clearSelection()
                            clearCalendar()
                        }
                    } else if (spinners[1] == spinner) {
                        if (cekIsCutiKhusus()) {
                            val startLeave = inputStartLeave.text.toString()
                            println("BULAN# startLeave: $startLeave")

                            if (startLeave.isNotBlank() || startLeave.isNotEmpty()) {
                                val format = SimpleDateFormat(DATE_PATTERN)
                                val date = format.parse(startLeave)
                                val calendar = Calendar.getInstance()
                                calendar.time = date

                                hitungEndDateCutiKhusus(calendar)
                            }
                        }
                    }
                    ubahResetButton(context, true, buttonResetLeave)
                }

                override fun onNothingSelected(adapterView: AdapterView<*>) {
                    //spinner.errorText = "Required"
                }
            }
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

    private fun initDatePickerStartLeave() {
        inputStartLeave.setOnClickListener {
            // val startLeave = inputStartLeave.text.toString()
            val calendar = Calendar.getInstance()
            val formatter = SimpleDateFormat(DATE_PATTERN)

            /*if (!startLeave.isBlank()) {
                calendar.time = formatter.parse(startLeave)
            }*/

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
                    inputStartLeave.setText(tanggal)

                    //set end tanggal cuti khusus otomatis
                    if (cekIsCutiKhusus()) {
                        val format = SimpleDateFormat(DATE_PATTERN)
                        val date = format.parse(tanggal)
                        val calendar = Calendar.getInstance()
                        calendar.time = date

                        hitungEndDateCutiKhusus(calendar)
                    }
                }, yearStart, monthStart, dayStart
            )
            datePicker.show()
        }
    }

    fun clearCalendar(){
        inputStartLeave.text = null
        inputEndLeave.text = null
    }

    fun cekIsCutiKhusus(): Boolean {
        var isCutiKhusus = false
        if (spinnerLeaveType.item[spinnerLeaveType.selectedItemPosition] == "Cuti Khusus") {
            isCutiKhusus = true
        }
        return isCutiKhusus
    }

    fun hitungEndDateCutiKhusus(calendar: Calendar) {
        val quotaCutiKhusus = listCutiKhusus[spinnerLeaveName.selectedItemPosition].quotaCutiKhusus
        val formatter = SimpleDateFormat(DATE_PATTERN)

        val dateEnd = calendar
        dateEnd.add(Calendar.DATE, quotaCutiKhusus - 1)
        val tanggalEnd = formatter.format(dateEnd.time)
        inputEndLeave.setText(tanggalEnd)
    }

    /*fun hitungEndDateCutiKhusus(year: Int, month: Int, dayOfMonth: Int) {
        println("hitungEndDateCutiKhusus year: $year")
        println("hitungEndDateCutiKhusus month: $month")
        println("hitungEndDateCutiKhusus dayOfMonth: $dayOfMonth")

        val quotaCutiKhusus = listCutiKhusus[spinnerLeaveName.selectedItemPosition].quotaCutiKhusus

        val formatter = SimpleDateFormat(DATE_PATTERN)
        val defaultDateEnd = Calendar.getInstance()
        defaultDateEnd.set(year, month, dayOfMonth + (quotaCutiKhusus - 1))
        val tanggalEnd = formatter.format(defaultDateEnd.time)
        inputEndLeave.setText(tanggalEnd)
    }*/

    private fun initDatePickerEndLeave() {
        inputEndLeave.setOnClickListener {

            val endLeave = inputEndLeave.text.toString()
            val calendar = Calendar.getInstance()
            val formatter = SimpleDateFormat(DATE_PATTERN)

            if (!endLeave.isBlank()) {
                calendar.time = formatter.parse(endLeave)
                Toast.makeText(context, endLeave, Toast.LENGTH_SHORT).show()
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
                    inputEndLeave.setText(tanggal)
                }, yearEnd, monthEnd, dayEnd
            )
            datePicker.show()
        }
    }

    private fun resetForm() {
        spinnerLeaveType.clearSelection()
        spinnerLeaveName.clearSelection()
        inputStartLeave.text = null
        inputEndLeave.text = null
        inputAddressLeave.text = null
        inputContactLeave.text = null
        inputReasonLeave.text = null
    }

    fun isDateAlreadyRegistered(startDate:Calendar, endDate:Calendar):Boolean{
        var isExist=false

        //startDate.get(Calendar.MONTH) -> GET MONTH FROM CALENDAR
        val listRangeDate = databaseQueryHelper!!.getLeaveDateRangeByYear(startDate.get(Calendar.YEAR))
        val listSize= listRangeDate.size
        val formatter = SimpleDateFormat(DATE_PATTERN)
        val dbStart= Calendar.getInstance()
        val dbEnd= Calendar.getInstance()
        var dateRangeStart:Date? =null
        var dateRangeEnd:Date? =null
        var overlapStart=""
        var overlapEnd=""

        listRangeDate.forEach {dateRange->
            dateRangeStart= formatter.parse(dateRange.start)
            dateRangeEnd=formatter.parse(dateRange.end)

            dbStart.time = dateRangeStart
            dbEnd.time = dateRangeEnd

            if(dbStart<=endDate && dbEnd>=startDate){
                isExist=true
                println("BULAN# startDate: $startDate")
                overlapStart=  formatter.format(dbStart.time)
                overlapEnd=  formatter.format(dbEnd.time)
                showAlertOverlapDate(overlapStart,overlapEnd)
            }
        }

        return isExist
    }

    fun showAlertOverlapDate(overlapStart:String,overlapEnd:String){
        AlertDialog.Builder(context!!, R.style.AlertDialogTheme)
            .setMessage("Anda sedang cuti pada $overlapStart - $overlapEnd")
            .setCancelable(false)
            .setNegativeButton("OK") { dialog, which ->
            }
            .create()
            .show()

    }

    fun submitLeaveRequest() {

        val indexLeaveType = spinnerLeaveType.selectedItemPosition
        val indexCutiKhusus = spinnerLeaveName.selectedItemPosition

        var inputIdLeaveType = 0
        var inputIdCutiKhusus = 0
        val inputStart = inputStartLeave.text.toString().trim()
        val inputEnd = inputEndLeave.text.toString().trim()
        val inputAddress = inputAddressLeave.text.toString().trim()
        val inputContactNumber = inputContactLeave.text.toString().trim()
        val inputReason = inputReasonLeave.text.toString().trim()

        var isValid = true

        if (indexLeaveType < 0) {
            isValid = false
            spinnerLeaveType.errorText = "Required"
        } else {
            inputIdLeaveType = listLeaveType[indexLeaveType].idLeaveType
            spinnerLeaveType.errorText = null

            val leaveType = spinnerLeaveType.item[indexLeaveType]
            if (leaveType == "Cuti Khusus") {
                if (indexCutiKhusus < 0) {
                    isValid = false
                    spinnerLeaveName.errorText = "Required"
                } else {
                    inputIdCutiKhusus = listCutiKhusus[indexCutiKhusus].idCutiKhusus
                    spinnerLeaveName.errorText = null
                }
            }
        }

        if (inputStart.isEmpty()) {
            isValid = false
            layoutStartLeave.error = "Required"
        } else {
            inputStartLeave.setHintTextColor(defaultColor)
            layoutStartLeave.error = null
        }

        if (inputEnd.isEmpty()) {
            isValid = false
            inputEndLeave.setHintTextColor(Color.RED)
            layoutEndLeave.error = "Required"
        } else {
            inputEndLeave.setHintTextColor(defaultColor)
            layoutEndLeave.error = null
        }

        if (inputAddress.isEmpty()) {
            isValid = false
            inputAddressLeave.setHintTextColor(Color.RED)
            layoutAddressLeave.error = "Required"
        } else {
            inputAddressLeave.setHintTextColor(defaultColor)
            layoutAddressLeave.error = null
        }

        if (inputContactNumber.isEmpty()) {
            isValid = false
            inputContactLeave.setHintTextColor(Color.RED)
            layoutContactLeave.error = "Required"
        } else {
            inputContactLeave.setHintTextColor(defaultColor)
            layoutContactLeave.error = null
        }

        if (inputReason.isEmpty()) {
            isValid = false
            inputReasonLeave.setHintTextColor(Color.RED)
            layoutReasonLeave.error = "Required"
        } else {
            inputReasonLeave.setHintTextColor(defaultColor)
            layoutReasonLeave.error = null
        }

        // check valid duration
        val startDate = Calendar.getInstance()
        val endDate = Calendar.getInstance()
        if (inputStart.isNotEmpty() && inputEnd.isNotEmpty()) {
            val formatter = SimpleDateFormat(DATE_PATTERN)

            startDate.time = formatter.parse(inputStart)
            endDate.time = formatter.parse(inputEnd)

            if (!startDate.equals(endDate)) {
                if(!startDate.before(endDate)){
                    Toast.makeText(context, "Input Start dan End salah", Toast.LENGTH_SHORT).show()
                    isValid = false
                }
            }
        }

        // check date already exist
        if(isDateAlreadyRegistered(startDate,endDate)){
            Toast.makeText(context, "Anda sedang cuti", Toast.LENGTH_SHORT).show()
            isValid = false
        }

        if (isValid) {
            val model = LeaveRequest()
            model.idLeaveType = inputIdLeaveType
            model.idCutiKhusus = inputIdCutiKhusus
            model.start = inputStart
            model.end = inputEnd
            model.address = inputAddress
            model.contact = inputContactNumber
            model.reason = inputReason
            model.isDeleted = "false"

            //add
            if (databaseQueryHelper!!.tambahLeaveRequest(model) == -1L) {
                Toast.makeText(context, SIMPAN_DATA_GAGAL, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, SIMPAN_DATA_BERHASIL, Toast.LENGTH_SHORT)
                    .show()
            }
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

}
