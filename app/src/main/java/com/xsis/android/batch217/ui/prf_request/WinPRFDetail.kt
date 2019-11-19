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
    var scroll:ScrollView? = null
    var buttonReset: Button? = null
    var buttonSubmit: Button? = null
    var tanggal:EditText? = null
    var type: EditText? = null
    var placement: EditText? = null
    var pid: EditText? = null
    var location: EditText? = null
    var period: EditText? = null
    var userName: EditText? = null
    var telpMobilePhone: EditText? = null
    var email: EditText? = null
    var notebook: EditText? = null
    var overtime: EditText? = null
    var bast: EditText? = null
    var billing: EditText? = null
    lateinit var listTypePRF: List<TypePRF>
    lateinit var listPID: List<ProjectCreate>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val customView = inflater.inflate(R.layout.fragment_form_win_check_prf,container,false)

        scroll = customView.findViewById(R.id.scrollviewPRFWinCheck)
        tanggal = customView.findViewById(R.id.inputTanggalPRFWinCheck)
        type = customView.findViewById(R.id.inputTypePRFWinCheck)
        placement = customView.findViewById(R.id.inputPlacementPRFWinCheck)
        pid = customView.findViewById(R.id.inputPIDPRFWinCheck)
        location = customView.findViewById(R.id.inputLocationPRFWinCheck)
        period = customView.findViewById(R.id.inputPeriodPRFWinCheck)
        userName = customView.findViewById(R.id.inputUserNamePRFWinCheck)
        telpMobilePhone = customView.findViewById(R.id.inputTelpPRFWinCheck)
        email = customView.findViewById(R.id.inputEmailPRFWinCheck)
        notebook = customView.findViewById(R.id.inputNotebookPRFWinCheck)
        overtime = customView.findViewById(R.id.inputOvertimePRFWinCheck)
        bast = customView.findViewById(R.id.inputBastPRFWinCheck)
        billing = customView.findViewById(R.id.inputBillingPRFWinCheck)
        buttonReset = customView.findViewById(R.id.buttonResetPRFRequestWinCheck)
        buttonSubmit = customView.findViewById(R.id.buttonSubmitPRFRequestWinCheck)

        buttonReset!!.setOnClickListener{ pindahKeFragmentData() }
        buttonSubmit!!.setOnClickListener { konfirmasiWin() }

        return customView
    }

    fun bawaID(id:Int){
        ID = id
        loadDataPRFRequest(ID)
        scroll!!.fullScroll(ScrollView.FOCUS_UP)
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
            tanggal!!.setText(data.tanggal)
            println("-------------------------------------${data.tanggal}")

            val dataType = cursor.getString(2)
            val indexType = isiSpinnerType().indexOf(dataType)
            type!!.setText(isiSpinnerType()[dataType.toInt()])
//            println("index type = $indexType")
//            println("data type = $dataType")

            data.placement = cursor.getString(3)
            placement!!.setText(data.placement)

            val dataPID = cursor.getString(4)
            val indexPID = isiSpinnerPID().indexOf(dataPID)
            pid!!.setText(isiSpinnerPID()[dataPID.toInt()])

            data.location = cursor.getString(5)
            location!!.setText(data.location)

            data.period = cursor.getString(6)
            period!!.setText(data.period)

            data.user_name = cursor.getString(7)
            userName!!.setText(data.user_name)

            data.telp_number = cursor.getString(8)
            telpMobilePhone!!.setText(data.telp_number)

            data.email = cursor.getString(9)
            email!!.setText(data.email)

            val dataNotebook = cursor.getString(10)
//            val indexnotebook = ARRAY_NOTEBOOK.indexOf(dataNotebook)
            notebook!!.setText(ARRAY_NOTEBOOK[dataNotebook.toInt()])

            data.overtime = cursor.getString(11)
            overtime!!.setText(data.overtime)

            val dataBast = cursor.getString(12)
            val indexBast = ARRAY_BAST.indexOf(dataBast)
            bast!!.setText(ARRAY_BAST[dataBast.toInt()])

            data.billing = cursor.getString(13)
            billing!!.setText(data.billing)

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
        return isilistType
    }
    fun isiSpinnerPID(): List<String> {
        listPID = databaseQueryHelper.readPIDPRFNew()
        val isilistPID = listPID.map {
            it.PID
        }.toMutableList()
        isilistPID.add(0, "PID *")
        return isilistPID
    }
}