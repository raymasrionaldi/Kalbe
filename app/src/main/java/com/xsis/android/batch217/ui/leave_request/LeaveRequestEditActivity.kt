package com.xsis.android.batch217.ui.leave_request


import android.app.Activity
import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.chivorn.smartmaterialspinner.SmartMaterialSpinner
import com.xsis.android.batch217.R
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.LeaveRequestQueryHelper
import com.xsis.android.batch217.models.LeaveRequest
import com.xsis.android.batch217.utils.*
import kotlinx.android.synthetic.main.activity_leave_request_add.*
import kotlinx.android.synthetic.main.activity_leave_request_edit.*
import java.text.SimpleDateFormat
import java.util.*

class LeaveRequestEditActivity : AppCompatActivity() {
    val context = this
    var ID_DETAIL: Int? = null
    lateinit var listLeaveType: List<LeaveRequest>
    lateinit var listCutiKhusus: List<LeaveRequest>
    var databaseQueryHelper: LeaveRequestQueryHelper? = null

    var defaultColor = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leave_request_edit)
        defaultColor = inputEditAddressLeave.currentHintTextColor

        context.title = getString(R.string.menu_leave_detail)

        supportActionBar?.let {
            //menampilkan icon di toolbar
            supportActionBar!!.setHomeButtonEnabled(true)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            //ganti icon. Kalau mau default yang "<-", hapus line di bawah
            supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_close_white)
        }

        var databaseHelper = DatabaseHelper(context)
        databaseQueryHelper = LeaveRequestQueryHelper(databaseHelper)

        var bundle: Bundle? = intent.extras
        //if bundle not null
        bundle?.let {
            ID_DETAIL = bundle!!.getInt(ID_LEAVE)
        }

        listLeaveType = databaseQueryHelper!!.getLeaveTypeModels()
        val listLeaveType = listLeaveType.map { leave -> leave.leaveType }.toList()
        spinnerEditLeaveType.item = listLeaveType

        listCutiKhusus = databaseQueryHelper!!.getCutiKhususModels()
        val listCutiKhusus = listCutiKhusus.map { leave -> leave.leaveName }.toList()
        spinnerEditLeaveName.item = listCutiKhusus

        setOnItemSelectedListener(spinnerEditLeaveType, spinnerEditLeaveName)

        initDatePickerStartLeave()
        initDatePickerEndLeave()

        buttonEditResetLeave.setOnClickListener {
            resetForm()
        }

        buttonEditSubmitLeave.setOnClickListener {
            submitLeaveRequest()
        }

        tampilkanDetail(ID_DETAIL!!)
    }

    private fun setOnItemSelectedListener(vararg spinners: SmartMaterialSpinner<*>) {
      /*  println("BULAN# SPINNERS- ${spinners[0]}")
        println("BULAN# SPINNERS- ${spinners[1]}")*/
        for (spinner in spinners) {
        //println("BULAN#SPINNER $spinner")
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    adapterView: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    if (spinners[0] == spinner) {
                        if (spinner.item[position] == "Cuti Khusus") {
                            spinnerEditLeaveName.visibility = View.VISIBLE
                        } else {
                            spinnerEditLeaveName.visibility = View.GONE
                            spinnerEditLeaveName.clearSelection()
                        }
                    }else if (spinners[1] == spinner) {
                        if (cekIsCutiKhusus()) {
                            val startLeave = inputEditStartLeave.text.toString()
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
                    //spinner.errorText = ""
                    ubahResetButton(context, true, buttonEditResetLeave)
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


    /*private fun setOnItemSelectedListener(vararg spinners: SmartMaterialSpinner<*>) {
        //        for (spinner in spinners) {
        var spinner = spinners[0]
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                if (spinner.item[position] == "Cuti Khusus") {
                    spinners[1].visibility = View.VISIBLE
                } else {
            //                        TODO check spinner is visible
                    spinners[1].visibility = View.GONE
                }
                //spinner.errorText = ""
                ubahResetButton(context, true, buttonEditResetLeave)
            }
            override fun onNothingSelected(adapterView: AdapterView<*>) {
                //spinner.errorText = "Required"
            }
        }

    }*/

    fun tampilkanDetail(id_detail: Int) {
        val data = databaseQueryHelper!!.getLeaveRequestDetailById(id_detail)

        //set spinner leaveType
        val indexLeaveType = listLeaveType.indexOfFirst { leaveRequest ->
            leaveRequest.idLeaveType == data.idLeaveType
        }
        if (indexLeaveType != -1) {
            spinnerEditLeaveType.setSelection(indexLeaveType)
        }
        if (data.leaveType == "Cuti Khusus") {

            //set spinner leaveName
            val indexCutiKhusus = listCutiKhusus.indexOfFirst { cutiKhusus ->
                cutiKhusus.idCutiKhusus == data.idCutiKhusus
            }
            if (indexCutiKhusus != -1) {
                spinnerEditLeaveName.visibility = View.VISIBLE
                spinnerEditLeaveName.setSelection(indexCutiKhusus)
            }
        } else {
            spinnerEditLeaveName.visibility = View.GONE

        }

        //set datepicker start
        inputEditStartLeave.setText(data.start)
        //set datepicker start
        inputEditEndLeave.setText(data.end)
        inputEditAddressLeave.setText(data.address)
        inputEditContactLeave.setText(data.contact)
        inputEditReasonLeave.setText(data.reason)
    }


    private fun initDatePickerEndLeave() {
        inputEditEndLeave.setOnClickListener {

            val endLeave = inputEditEndLeave.text.toString()
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
                    inputEditEndLeave.setText(tanggal)
                }, yearEnd, monthEnd, dayEnd
            )
            datePicker.show()
        }
    }

    private fun initDatePickerStartLeave() {
        inputEditStartLeave.setOnClickListener {
            val startLeave = inputEditStartLeave.text.toString()
            val calendar = Calendar.getInstance()
            val formatter = SimpleDateFormat(DATE_PATTERN)

            if (!startLeave.isBlank()) {
                calendar.time = formatter.parse(startLeave)
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
                    inputEditStartLeave.setText(tanggal)

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

    fun cekIsCutiKhusus(): Boolean {
        var isCutiKhusus = false
        if (spinnerEditLeaveType.item[spinnerEditLeaveType.selectedItemPosition] == "Cuti Khusus") {
            isCutiKhusus = true
        }
        return isCutiKhusus
    }

    fun hitungEndDateCutiKhusus(calendar: Calendar) {
        val quotaCutiKhusus = listCutiKhusus[spinnerEditLeaveName.selectedItemPosition].quotaCutiKhusus
        val formatter = SimpleDateFormat(DATE_PATTERN)

        val dateEnd = calendar
        dateEnd.add(Calendar.DATE, quotaCutiKhusus - 1)
        val tanggalEnd = formatter.format(dateEnd.time)
        inputEditEndLeave.setText(tanggalEnd)
    }

    private fun resetForm() {
        spinnerEditLeaveType.clearSelection()
        spinnerEditLeaveName.clearSelection()
        inputEditStartLeave.text = null
        inputEditEndLeave.text = null
        inputEditAddressLeave.text = null
        inputEditContactLeave.text = null
        inputEditReasonLeave.text = null
    }

    fun submitLeaveRequest() {

        val indexLeaveType = spinnerEditLeaveType.selectedItemPosition
        val indexCutiKhusus = spinnerEditLeaveName.selectedItemPosition

        //get string value spinner
        val leaveType= spinnerEditLeaveType.item[indexLeaveType]

        var inputIdLeaveType = 0
        var inputIdCutiKhusus = 0
        val inputStart = inputEditStartLeave.text.toString().trim()
        val inputEnd = inputEditEndLeave.text.toString().trim()
        val inputAddress = inputEditAddressLeave.text.toString().trim()
        val inputContactNumber = inputEditContactLeave.text.toString().trim()
        val inputReason = inputEditReasonLeave.text.toString().trim()

        var isValid = true

        if (indexLeaveType < 0) {
            isValid = false
            spinnerEditLeaveType.errorText = "Required"
        } else {
            inputIdLeaveType = listLeaveType[indexLeaveType].idLeaveType
            spinnerEditLeaveType.errorText = null

            if(leaveType=="Cuti Khusus"){
                if (indexCutiKhusus < 0) {
                    isValid = false
                    spinnerEditLeaveName.errorText = "Required"
                } else {
                    inputIdCutiKhusus = listCutiKhusus[indexCutiKhusus].idCutiKhusus
                    spinnerEditLeaveName.errorText = null
                }
            }
        }

        if (inputStart.isEmpty()) {
            isValid = false
            inputEditStartLeave.setHintTextColor(Color.RED)
            layoutEditStartLeave.error = "Required"
        } else {
            inputEditStartLeave.setHintTextColor(defaultColor)
            layoutEditStartLeave.error = null
        }

        if (inputEnd.isEmpty()) {
            isValid = false
            inputEditEndLeave.setHintTextColor(Color.RED)
            layoutEditEndLeave.error = "Required"
        } else {
            inputEditEndLeave.setHintTextColor(defaultColor)
            layoutEditEndLeave.error = null
        }

        if (inputAddress.isEmpty()) {
            isValid = false
            inputEditAddressLeave.setHintTextColor(Color.RED)
            layoutEditAddressLeave.error = "Required"
        } else {
            inputEditAddressLeave.setHintTextColor(defaultColor)
            layoutEditAddressLeave.error = null
        }

        if (inputContactNumber.isEmpty()) {
            isValid = false
            inputEditContactLeave.setHintTextColor(Color.RED)
            layoutEditContactLeave.error = "Required"
        } else {
            inputEditContactLeave.setHintTextColor(defaultColor)
            layoutEditContactLeave.error = null
        }

        if (inputReason.isEmpty()) {
            isValid = false
            inputEditReasonLeave.setHintTextColor(Color.RED)
            layoutEditReasonLeave.error = "Required"
        } else {
            inputEditReasonLeave.setHintTextColor(defaultColor)
            layoutEditReasonLeave.error = null
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
            val model = LeaveRequest()
            model.idLeaveRequest= ID_DETAIL!!
            model.idLeaveType = inputIdLeaveType
            model.idCutiKhusus = inputIdCutiKhusus
            model.start = inputStart
            model.end = inputEnd
            model.address = inputAddress
            model.contact = inputContactNumber
            model.reason = inputReason
            model.isDeleted = "false"

            //add
            if (databaseQueryHelper!!.updateLeaveRequest(model) == 0) {
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