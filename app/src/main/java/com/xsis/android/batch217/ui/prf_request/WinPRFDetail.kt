package com.xsis.android.batch217.ui.prf_request

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.fragments.PRFWinFragmentAdapter
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.PRFRequestQueryHelper
import com.xsis.android.batch217.models.PRFRequest
import com.xsis.android.batch217.models.ProjectCreate
import com.xsis.android.batch217.models.TypePRF
import com.xsis.android.batch217.utils.*
import kotlinx.android.synthetic.main.activity_edit_prfrequest.*
import java.text.SimpleDateFormat
import java.util.*

class WinPRFDetail(context: Context, val fm:FragmentManager):Fragment() {
    var ID = 0
//    val context = this
    var databaseHelper = DatabaseHelper(context)
    var databaseQueryHelper = PRFRequestQueryHelper(databaseHelper)
    var data = PRFRequest()
    var toolbar:LinearLayout? = null
    var judulForm:TextView? = null
    var buttonReset: Button? = null
    var buttonSubmit: Button? = null
    var tanggal:EditText? = null
    var type: Spinner? = null
    var placement: EditText? = null
    var pid: Spinner? = null
    var location: EditText? = null
    var period: EditText? = null
    var userName: EditText? = null
    var telpMobilePhone: EditText? = null
    var email: EditText? = null
    var notebook: Spinner? = null
    var overtime: EditText? = null
    var bast: Spinner? = null
    var billing: EditText? = null
    var ID_prf_request = 0
    lateinit var listTypePRF: List<TypePRF>
    lateinit var listPID: List<ProjectCreate>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val customView = inflater.inflate(R.layout.activity_edit_prfrequest,container,false)

        toolbar = customView.findViewById(R.id.toolbarPRF)
        judulForm = customView.findViewById(R.id.judulFormUbahPRFRequest)
        tanggal = customView.findViewById(R.id.inputTanggalPRFEdit)
        type = customView.findViewById(R.id.spinnerInputTypePRFEdit)
        placement = customView.findViewById(R.id.inputPlacementPRFEdit)
        pid = customView.findViewById(R.id.spinnerInputPIDPRFEdit)
        location = customView.findViewById(R.id.inputLocationPRFEdit)
        period = customView.findViewById(R.id.inputPeriodPRFEdit)
        userName = customView.findViewById(R.id.inputUserNamePRFEdit)
        telpMobilePhone = customView.findViewById(R.id.inputTelpPRFEdit)
        email = customView.findViewById(R.id.inputEmailPRFEdit)
        notebook = customView.findViewById(R.id.spinnerInputNotebookPRFEdit)
        overtime = customView.findViewById(R.id.inputOvertimePRFEdit)
        bast = customView.findViewById(R.id.spinnerInputBastPRFEdit)
        billing = customView.findViewById(R.id.inputBillingPRFEdit)
        buttonReset = customView.findViewById(R.id.buttonResetPRFRequestEdit)
        buttonSubmit = customView.findViewById(R.id.buttonSubmitPRFRequestEdit)

        toolbar!!.isVisible = false
        judulForm!!.isVisible = false

        buttonReset!!.setOnClickListener{ pindahKeFragmentData() }
        buttonSubmit!!.setOnClickListener { konfirmasiWin() }

        setDisable()
        isiSpinnerNotebook()
        isiSpinnerBAST()

        return customView
    }

    fun bawaID(id:Int){
        ID = id
        loadDataPRFRequest(ID)
    }

    fun setDisable(){
        tanggal!!.isFocusable = false
        placement!!.isFocusable = false
        location!!.isFocusable = false
        period!!.isFocusable = false
        userName!!.isFocusable = false
        telpMobilePhone!!.isFocusable = false
        email!!.isFocusable = false
        overtime!!.isFocusable = false
        billing!!.isFocusable = false

        type!!.isFocusable = false
        type!!.isClickable = false
        pid!!.isFocusableInTouchMode = false

        bast!!.isEnabled = false
        notebook!!.isEnabled = false

        buttonReset!!.text = "Back"
        buttonReset!!.isEnabled = true
        buttonReset!!.setBackgroundResource(R.drawable.button_reset_on)
        buttonReset!!.setTextColor(Color.WHITE)

        buttonSubmit!!.text = "WIN"

    }

    fun loadDataPRFRequest(id: Int) {
        println("------------------------ $id")
        val db = databaseHelper.readableDatabase

        val projection = arrayOf<String>(
            ID_PRF_REQUEST, TANGGAL, TYPE, PLACEMENT, PID, LOCATION, PERIOD, USER_NAME,
            TELP_NUMBER, EMAIL, NOTEBOOK, OVERTIME, BAST, BILLING, IS_DELETED
        )
        val selection = ID_PRF_REQUEST + "=?"
        val selectionArgs = arrayOf(id.toString())
        val cursor =
            db.query(TABEL_PRF_REQUEST, projection, selection, selectionArgs, null, null, null)
        if (cursor.count == 1) {
            cursor.moveToFirst()
            data.id_prf_request = cursor.getInt(0)
            data.tanggal = cursor.getString(1)
            inputTanggalPRFEdit.setText(data.tanggal)
            println("-------------------------------------${data.tanggal}")

            val dataType = cursor.getString(2)
            val indexType = isiSpinnerType().indexOf(dataType)
            println("index type = $indexType")
            spinnerInputTypePRFEdit.setSelection(dataType.toInt())
            println("data type = $dataType")

            data.placement = cursor.getString(3)
            inputPlacementPRFEdit.setText(data.placement)

            val dataPID = cursor.getString(4)
            val indexPID = isiSpinnerPID().indexOf(dataPID)
            spinnerInputPIDPRFEdit.setSelection(dataPID.toInt())

            data.location = cursor.getString(5)
            inputLocationPRFEdit.setText(data.location)

            data.period = cursor.getString(6)
            inputPeriodPRFEdit.setText(data.period)

            data.user_name = cursor.getString(7)
            inputUserNamePRFEdit.setText(data.user_name)

            data.telp_number = cursor.getString(8)
            inputTelpPRFEdit.setText(data.telp_number)

            data.email = cursor.getString(9)
            inputEmailPRFEdit.setText(data.email)

            val dataNotebook = cursor.getString(10)
//            val indexnotebook = ARRAY_NOTEBOOK.indexOf(dataNotebook)
            spinnerInputNotebookPRFEdit.setSelection(dataNotebook.toInt())

            data.overtime = cursor.getString(11)
            inputOvertimePRFEdit.setText(data.overtime)

            val dataBast = cursor.getString(12)
            val indexBast = ARRAY_BAST.indexOf(dataBast)
            spinnerInputBastPRFEdit.setSelection(dataBast.toInt())

            data.billing = cursor.getString(13)
            inputBillingPRFEdit.setText(data.billing)

            data.is_Deleted = cursor.getString(14)
        }

    }

    fun konfirmasiWin(){
        val konfirmasiWin = AlertDialog.Builder(context)
        konfirmasiWin.setMessage("Yakin mau win data ini ?")
            .setPositiveButton("Ya", DialogInterface.OnClickListener{ dialog, which ->
                Toast.makeText(context,"Win berhasil", Toast.LENGTH_SHORT).show()
                databaseQueryHelper.setWinPRF(ID)
                pindahKeFragmentData()

            })
            .setNegativeButton("Tidak", DialogInterface.OnClickListener{ dialog, which ->
                dialog.cancel()
            })
            .setCancelable(true)

        konfirmasiWin.create().show()
    }

    fun pindahKeFragmentData(){
        val fragment = fm.fragments[0] as WinPRFData
        val viewPager = fragment.view!!.parent as ViewPager
        val adapter = viewPager.adapter!! as PRFWinFragmentAdapter

        fragment.search2()
        adapter.notifyDataSetChanged()
        viewPager.setCurrentItem(0, true)
    }

    fun isiSpinnerType(): MutableList<String?> {
        listTypePRF = databaseQueryHelper.readTypePRFNew()
        val isilistType = listTypePRF.map {
            it.nama_type_prf
        }.toMutableList()
        isilistType.add(0, "Type *")
        val adapterType = ArrayAdapter<String>(
            context!!,
            android.R.layout.simple_spinner_item,
            isilistType
        )
        adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerInputTypePRFEdit.adapter = adapterType
        return isilistType
    }
    fun isiSpinnerPID(): List<String> {
        listPID = databaseQueryHelper.readPIDPRFNew()
        val isilistPID = listPID.map {
            it.PID
        }.toMutableList()
        isilistPID.add(0, "PID *")
        val adapterPID = ArrayAdapter<String>(
            context!!,
            android.R.layout.simple_spinner_item,
            isilistPID
        )
        adapterPID.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerInputPIDPRFEdit.adapter = adapterPID
        return isilistPID
    }
    fun isiSpinnerNotebook(){
        val adapterNotebook = context?.let{ArrayAdapter<String>(it,
            android.R.layout.simple_spinner_item,
            ARRAY_NOTEBOOK
        )}
        adapterNotebook!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        notebook!!.adapter = adapterNotebook
    }
    fun isiSpinnerBAST(){
        val adapterBAST = context?.let { ArrayAdapter<String>(it,
            android.R.layout.simple_spinner_item,
            ARRAY_BAST
        ) }
        adapterBAST!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        bast!!.adapter = adapterBAST
    }
}