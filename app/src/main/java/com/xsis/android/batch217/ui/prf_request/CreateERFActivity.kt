package com.xsis.android.batch217.ui.prf_request

import android.app.DatePickerDialog
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
    var tgl = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_erf)

        isiSpinnerKeahlian()

        val bundle: Bundle? = intent.extras
        bundle?.let {
            idPRFReg = bundle.getInt(ID_PRF_REQUEST)
            tgl = bundle.getString(TANGGAL)!!
            loadDataERF(idPRFReg)

        }

        cekDate(tgl)

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

    fun cekDate(tgl : String){
        val tglLimit = SimpleDateFormat(DATE_PATTERN).parse(tgl).time
        inputTglTerakhir.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val tglTerakhirCek  = inputTglTerakhir.text.toString()
                if (tglTerakhirCek.isNotEmpty()){
                    val tglTerakhir = SimpleDateFormat(DATE_PATTERN).parse(tglTerakhirCek).time
                    val selisih = tglTerakhir - tglLimit
                    if (selisih < 0){
                        inputTglTerakhir.setText("")
                        requiredTglTerakhir.isVisible = true
                        requiredTglTerakhir.text = "Tanggal terakhir tidak boleh kurang dari $tgl"
                    } else {
                        requiredTglTerakhir.isVisible = false
                    }
                } else {
                    requiredTglTerakhir.text = "Required"
                }
            }
        })
        inputTglTerima.addTextChangedListener(object :TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val tglTerimaCek  = inputTglTerima.text.toString()
                if (tglTerimaCek.isNotEmpty()){
                    val tglTerima = SimpleDateFormat(DATE_PATTERN).parse(tglTerimaCek).time
                    val selisih = tglTerima - tglLimit
                    if (selisih < 0){
                        inputTglTerima.setText("")
                        requiredTglTerima.isVisible = true
                        requiredTglTerima.text = "Tanggal terima tidak boleh kurang dari $tgl"
                    } else {
                        requiredTglTerima.isVisible = false
                    }
                } else {
                    requiredTglTerima.text = "Required"
                }
            }
        })
        inputTglKembali.addTextChangedListener(object:TextWatcher{
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val tglKembaliCek  = inputTglKembali.text.toString()
                if (tglKembaliCek.isNotEmpty()){
                    val tglKembali = SimpleDateFormat(DATE_PATTERN).parse(tglKembaliCek).time
                    val selisih = tglKembali - tglLimit
                    if (selisih < 0){
                        inputTglKembali.setText("")
                        requiredTglKembali.isVisible = true
                        requiredTglKembali.text = "Tanggal kembali tidak boleh kurang dari $tgl"
                    } else {
                        requiredTglKembali.isVisible = false
                    }
                } else {
                    requiredTglKembali.text = "Required"
                }
            }
        })
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
        if(resource.isNotEmpty() && keahlian.isNotEmpty() && tglTerakhir.isNotEmpty()
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