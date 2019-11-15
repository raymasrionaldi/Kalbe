package com.xsis.android.batch217.ui.prf_request

import android.app.DatePickerDialog
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.isVisible
import com.xsis.android.batch217.R
import com.xsis.android.batch217.databases.CreateERFQueryHelper
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.models.ERF
import com.xsis.android.batch217.ui.srf.SRFFragmentForm.Companion.MODE_ADD
import com.xsis.android.batch217.utils.*
import kotlinx.android.synthetic.main.activity_create_erf.*
import java.text.SimpleDateFormat
import java.util.*

class CreateERFActivity : AppCompatActivity() {
    val context = this
    val databaseHelper = DatabaseHelper(context)
    val databaseQueryHelper = CreateERFQueryHelper(databaseHelper)
    var idPRFReg = 0
    var listKeahlian: List<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_erf)

        isiSpinnerKeahlian()

        val bundle: Bundle? = intent.extras
        bundle?.let {
            idPRFReg = bundle!!.getInt(ID_PRF_REQUEST)
            loadDataERF(idPRFReg)
        }

        buttonCancelERF.setOnClickListener {
            println("test1")
            resetForm()
            println("test2")
        }

        buttonSimpanERF.setOnClickListener {
            simpan(idPRFReg)
        }

        inputTglTerakhir.setOnClickListener {
            setDatePicker(inputTglTerakhir)
        }

        inputTglKembali.setOnClickListener {
            setDatePicker(inputTglKembali)
        }

        inputTglTerima.setOnClickListener {
            setDatePicker(inputTglTerima)
        }

    }

    fun resetForm(){
        inputNamaClient.setText("")
        inputNamaResource.setText("")
        spinnerKeahlian.setSelection(0)
        inputTglTerakhir.setText("")
        inputAlasan.setText("")
        inputNamaUserERF .setText("")
        inputTglKembali.setText("")
        inputNamaTerima.setText("")
        inputTglKembali.setText("")
        requiredNamaResource!!.visibility = View.INVISIBLE
        requiredTglTerakhir!!.visibility = View.INVISIBLE
        requiredNamaUserERF!!.visibility = View.INVISIBLE
        requiredTglKembali!!.visibility = View.INVISIBLE
        requiredNamaTerima!!.visibility = View.INVISIBLE
        requiredTglTerima!!.visibility = View.INVISIBLE
        requiredKeahlian!!.visibility = View.INVISIBLE
        finish()
    }

    fun simpan(idPRFReg : Int){
        val resource = inputNamaResource.text.toString().trim()
        val keahlian = spinnerKeahlian.selectedItem.toString()
        val tglTerakhir= inputTglTerakhir.text.toString()
        val alasan = inputAlasan.text.toString().trim()
        val userKembali = inputNamaUserERF.text.toString().trim()
        val tglKembali= inputTglKembali.text.toString()
        val namaTerima = inputNamaTerima.text.toString().trim()
        val tglTerima= inputTglTerima.text.toString()

        if (resource.isEmpty()) {
            requiredNamaResource!!.visibility = View.VISIBLE
        }
        if(keahlian.isEmpty()){
            requiredKeahlian!!.visibility = View.VISIBLE
        }
        if (tglTerakhir.isEmpty()) {
            requiredTglTerakhir!!.visibility = View.VISIBLE
        }
        if (userKembali.isEmpty()) {
            requiredNamaUserERF!!.visibility = View.VISIBLE
        }
        if (tglKembali.isEmpty()) {
            requiredTglKembali!!.visibility = View.VISIBLE
        }
        if (namaTerima.isEmpty()) {
            requiredNamaTerima!!.visibility = View.VISIBLE
        }
        if (tglTerima.isEmpty()) {
            requiredTglTerima!!.visibility = View.VISIBLE
        }
        if(resource.isNotEmpty() && keahlian.isNotEmpty() && tglTerakhir.isNotEmpty() && alasan.isNotEmpty()
            && userKembali.isNotEmpty() && tglKembali.isNotEmpty() && namaTerima.isNotEmpty() && tglTerima.isNotEmpty()){
            val model = ERF()
            model.id_prf_request =  idPRFReg
            model.nama_resource = resource
            var cariKeahlian = databaseQueryHelper.cariKeahlian(keahlian)
            model.id_keahlian = cariKeahlian
            model.tgl_terakhir = tglTerakhir
            model.alasan = alasan
            model.user_kembali = userKembali
            model.tgl_kembali = tglKembali
            model.nama_terima = namaTerima
            model.tgl_terima = tglTerima

            if (databaseQueryHelper!!.tambahERF(model) == -1L) {
                Toast.makeText(context, SIMPAN_DATA_GAGAL, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, SIMPAN_DATA_BERHASIL, Toast.LENGTH_SHORT).show()
                resetForm()
            }
        }
    }

    fun loadDataERF(id:Int){
        var client = databaseQueryHelper.cariClient(id)
        inputNamaClient.setText(client)
        spinnerKeahlian.setSelection(0)
    }

    fun isiSpinnerKeahlian(){
        listKeahlian = databaseQueryHelper.readListKeahlian()
        val adapterKeahlian= ArrayAdapter<String>(context,android.R.layout.simple_spinner_item, listKeahlian!!)
        adapterKeahlian.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerKeahlian.adapter = adapterKeahlian
    }

    fun setDatePicker(editText: EditText?){
        val calendar = Calendar.getInstance()
        val yearNow = calendar.get(Calendar.YEAR)
        val monthNow = calendar.get(Calendar.MONTH)
        val dayNow = calendar.get(Calendar.DATE)

        val datePickerDialog = DatePickerDialog(context!!, R.style.CustomDatePicker, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            val selectedDate = Calendar.getInstance()
            selectedDate.set(year, month, dayOfMonth)

            //konversi ke string
            val formatDate = SimpleDateFormat(DATE_PATTERN)
            val tanggal = formatDate.format(selectedDate.time)

            //set tampilan
            editText!!.setText(tanggal)
        }, yearNow,monthNow,dayNow )
        datePickerDialog.show()
    }
}