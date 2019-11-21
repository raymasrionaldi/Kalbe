package com.xsis.android.batch217.ui.periode

import android.content.ContentValues
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
import com.xsis.android.batch217.utils.*
import kotlinx.android.synthetic.main.activity_input_periode.*

class InputPeriodeActivity : AppCompatActivity() {
    val context = this
    val databaseHelper = DatabaseHelper(context)
    val databaseQueryHelper = PeriodeQueryHelper(databaseHelper)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_periode)

        cekIsi()
        hapus()
        simpan()
        batal()

        btnNavDrawerPeriode.setOnClickListener {
            finish()
        }
    }

    fun batal(){
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
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //Enable tombol simpan ketika user sudah mulai mengisi form
                btnSimpanPeriode.isEnabled = true
                //Deskripsi Keahlian tidak boleh kosong
                val desPeriode = teksDesPeriode.text.toString().trim()
                clearDeskripsi.isVisible = !desPeriode.isEmpty()
            }
        })
    }

    fun simpan() {
        val simpan = btnSimpanPeriode as Button
        simpan.setOnClickListener {
            if (teksPeriode.text.toString().trim().isEmpty()) {
                Toast.makeText(context, DATA_BELUM_LENGKAP, Toast.LENGTH_SHORT).show()
                teksErrorPeriode.isVisible = true
            } else {
                insertKeDatabase()
            }
        }
    }

    fun insertKeDatabase(){
        val nama = teksPeriode.text.toString().trim().toUpperCase()
        val des = teksDesPeriode.text.toString().trim()
        //read
        if(nama.isNullOrEmpty()){
            Toast.makeText(context, DATA_BELUM_LENGKAP, Toast.LENGTH_SHORT).show()
        } else {
            val listPeriode = databaseQueryHelper.readNamaPeriode(nama)
            if (!listPeriode.isEmpty()){
                Toast.makeText(context, DATA_SUDAH_ADA, Toast.LENGTH_SHORT).show()
            } else {
                val content = ContentValues()
                content.put(NAMA_PERIODE, nama)
                content.put(DES_PERIODE, des)
                content.put(IS_DELETED, "false")
                val db = DatabaseHelper(context!!).writableDatabase
                db.insert(TABEL_PERIODE, null, content)
                Toast.makeText(context, SIMPAN_DATA_BERHASIL, Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    fun hapus(){
        clearDeskripsi.setOnClickListener {teksDesPeriode.setText("") }
        clearPeriode.setOnClickListener {teksPeriode.setText("") }
    }
}
