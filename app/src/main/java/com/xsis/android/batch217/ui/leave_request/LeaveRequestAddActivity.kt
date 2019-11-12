package com.xsis.android.batch217.ui.leave_request

import android.app.Activity
import android.app.DatePickerDialog
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import com.chivorn.smartmaterialspinner.SmartMaterialSpinner
import com.xsis.android.batch217.R
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.LeaveRequestQueryHelper
import com.xsis.android.batch217.models.LeaveRequest
import com.xsis.android.batch217.utils.*
import kotlinx.android.synthetic.main.activity_leave_request_add.*
import java.text.SimpleDateFormat
import java.util.*

class LeaveRequestAddActivity : AppCompatActivity() {
    val context = this
    lateinit var listLeaveType: List<LeaveRequest>
    lateinit var listCutiKhusus: List<LeaveRequest>
    var databaseQueryHelper: LeaveRequestQueryHelper? = null

    var defaultColor = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leave_request_add)

        val databaseHelper = DatabaseHelper(context)
        databaseQueryHelper = LeaveRequestQueryHelper(databaseHelper)

        listLeaveType = databaseQueryHelper!!.getLeaveTypeModels()
        val listLeaveType = listLeaveType.map { leave -> leave.leaveType }.toList()
        spinnerLeaveType.item = listLeaveType
        setOnItemSelectedListener(spinnerLeaveType)

        listCutiKhusus = databaseQueryHelper!!.getCutiKhususModels()
        val listCutiKhusus = listCutiKhusus.map { leave -> leave.leaveName }.toList()
        spinnerLeaveName.item = listCutiKhusus
        setOnItemSelectedListener(spinnerLeaveType)

        initTimePickerStartLeave()
        initTimePickerEndLeave()

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
                    if(spinner.item[position]=="Cuti Khusus"){
                        spinnerLeaveName.visibility=View.VISIBLE
                    }else{
                        spinnerLeaveName.visibility=View.GONE
                    }
                    //spinner.errorText = ""
                    ubahResetButton(context, true, buttonResetLeave)
                }

                override fun onNothingSelected(adapterView: AdapterView<*>) {
                //spinner.errorText = "Required"
                }
            }
        }
    }

    private fun initTimePickerEndLeave() {
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

    private fun initTimePickerStartLeave() {
        inputStartLeave.setOnClickListener {
            val startLeave = inputStartLeave.text.toString()
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
                    inputStartLeave.setText(tanggal)
                }, yearStart, monthStart, dayStart
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

    fun submitLeaveRequest() {

        val indexLeaveType = spinnerLeaveType.selectedItemPosition
        val leaveType= spinnerLeaveType.item[indexLeaveType]
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

            if(leaveType=="Cuti Khusus"){
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
            inputStartLeave.setHintTextColor(Color.RED)
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
