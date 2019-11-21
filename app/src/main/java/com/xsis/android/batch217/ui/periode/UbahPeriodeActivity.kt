package com.xsis.android.batch217.ui.periode

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.Toast
import androidx.core.view.isVisible
import com.xsis.android.batch217.R
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.PeriodeQueryHelper
import com.xsis.android.batch217.models.Periode
import com.xsis.android.batch217.utils.*
import kotlinx.android.synthetic.main.activity_input_periode.*

class UbahPeriodeActivity : AppCompatActivity() {
    val context = this
    val databaseHelper = DatabaseHelper(context)
    val databaseQueryHelper = PeriodeQueryHelper(databaseHelper)
    var data = Periode()
    var idPeriode = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_periode)

        judulPeriode.text = getString(R.string.ubah_periode)

        val bundle: Bundle? = intent.extras
        bundle?.let {
            idPeriode = bundle!!.getInt(ID_PERIODE)
            loadDataPeriode(idPeriode)
        }

        btnNavDrawerPeriode.setOnClickListener {
            finish()
        }
    }

    fun loadDataPeriode(id: Int) {
        val db = databaseHelper.readableDatabase
        val projection = arrayOf<String>(ID_PERIODE, NAMA_PERIODE, DES_PERIODE, IS_DELETED)
        val selection = ID_PERIODE + "=?"
        val selectionArgs = arrayOf(id.toString())
        val cursor =
            db.query(TABEL_PERIODE, projection, selection, selectionArgs, null, null, null)
        if (cursor.count == 1) {
            cursor.moveToFirst()
            data.id_periode = cursor.getInt(0)
            data.nama_periode = cursor.getString(1)
            data.des_periode = cursor.getString(2)
            data.is_Deleted = cursor.getString(3)
            teksPeriode.setText(data.nama_periode)
            teksDesPeriode.setText(data.des_periode)
        }
        cekIsi()
        hapus()
        simpan(id)
        batal()
    }

    fun hapus(){
        clearDeskripsi.setOnClickListener {teksDesPeriode.setText("") }
        clearPeriode.setOnClickListener {teksPeriode.setText("") }
    }

    fun batal() {
        btnBatalPeriode.setOnClickListener { finish() }
    }

    fun cekIsi(){
        teksPeriode.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //Enable tombol simpan ketika user sudah mulai mengisi form
                btnSimpanPeriode.isEnabled = true
                btnSimpanPeriode.setBackgroundResource(R.drawable.button_simpan_on_2)
                btnSimpanPeriode.setTextColor(Color.WHITE)

                val namaPeriode = teksPeriode.text.toString().trim()
                if (namaPeriode.isEmpty()){
                    teksErrorPeriode.isVisible = true
                    clearPeriode.isVisible = false
                } else{
                    teksErrorPeriode.isVisible = false
                    clearPeriode.isVisible = true
                }
            }
        })
        teksDesPeriode.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //Enable tombol simpan ketika user sudah mulai mengisi form
                btnSimpanPeriode.isEnabled = true
                //Deskripsi Keahlian tidak boleh kosong
                val desPeriode = teksDesPeriode.text.toString().trim()
                clearPeriode.isVisible = !desPeriode.isEmpty()
            }
        })
    }

    fun simpan(id: Int) {
        //Simpan ke database
        val simpan = btnSimpanPeriode as Button
        simpan.setOnClickListener {
            if (teksPeriode.text.toString().trim().isEmpty()) {
                Toast.makeText(context, DATA_BELUM_LENGKAP, Toast.LENGTH_SHORT).show()
            } else {
                insertKeDatabase(id)
            }
        }
    }

    fun insertKeDatabase(id:Int){
        val nama = teksPeriode.text.toString().trim().toUpperCase()
        val des = teksDesPeriode.text.toString().trim()
        if(nama.isNullOrEmpty()){
            Toast.makeText(context, DATA_BELUM_LENGKAP, Toast.LENGTH_SHORT).show()
        } else {
            val listPeriode = databaseQueryHelper.readUpdate(idPeriode,nama)
            if(listPeriode.isEmpty()){
                databaseQueryHelper.updateDelete(idPeriode, nama,des)
                Toast.makeText(context, EDIT_DATA_BERHASIL, Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(context, DATA_SUDAH_ADA, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
