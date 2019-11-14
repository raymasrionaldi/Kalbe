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
    var listTypePRF: List<String>? = null

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


        isiSpinnerType()
        isiSpinnerPID()
        isiSpinnerNotebook()
        isiSpinnerBAST()
        setDisable()

        return customView
    }

    fun bawaID(id:Int){
        ID = id
        loadDataPRFRequest(ID)
    }

    fun setDisable(){
        tanggal!!.isEnabled = false
        type!!.isEnabled = false
        placement!!.isEnabled = false
        pid!!.isEnabled = false
        location!!.isEnabled = false
        period!!.isEnabled = false
        userName!!.isEnabled = false
        telpMobilePhone!!.isEnabled = false
        email!!.isEnabled = false
        notebook!!.isEnabled = false
        overtime!!.isEnabled = false
        bast!!.isEnabled = false
        billing!!.isEnabled = false

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
            val indexType = listTypePRF!!.indexOf(dataType)
            println("index type = $indexType")
            spinnerInputTypePRFEdit.setSelection(indexType)
            println("data type = $dataType")

            data.placement = cursor.getString(3)
            inputPlacementPRFEdit.setText(data.placement)

            val dataPID = cursor.getString(4)
            val indexPID = ARRAY_PID.indexOf(dataPID)
            spinnerInputPIDPRFEdit.setSelection(indexPID)

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
            val indexnotebook = ARRAY_NOTEBOOK.indexOf(dataNotebook)
            spinnerInputNotebookPRFEdit.setSelection(indexnotebook)

            data.overtime = cursor.getString(11)
            inputOvertimePRFEdit.setText(data.overtime)

            val dataBast = cursor.getString(12)
            val indexBast  = ARRAY_BAST.indexOf(dataBast)
            spinnerInputBastPRFEdit.setSelection(indexBast)

            data.billing = cursor.getString(13)
            inputBillingPRFEdit.setText(data.billing)

            data.is_Deleted = cursor.getString(14)
        }

        buttonResetPRFRequestEdit.setOnClickListener{
            pindahKeFragmentData()
        }
        buttonSubmitPRFRequestEdit.setOnClickListener {
            konfirmasiWin()
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

    fun isiSpinnerType(){
        listTypePRF = databaseQueryHelper.readTypePRF()
        val adapterType =context?.let {
            ArrayAdapter<String>(it, android.R.layout.simple_spinner_item, listTypePRF!!)
        }
        adapterType!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        type!!.adapter = adapterType
    }
    fun isiSpinnerPID(){
        val adapterPID = context?.let { ArrayAdapter<String>(it,
            android.R.layout.simple_spinner_item,
            ARRAY_PID
        ) }
        adapterPID!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        pid!!.adapter = adapterPID
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