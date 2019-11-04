package com.xsis.android.batch217.ui.keahlian

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.core.view.isVisible
import com.xsis.android.batch217.R
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.utils.*
import kotlinx.android.synthetic.main.activity_ubah_data_keahlian.*

class UbahDataKeahlianActivity : AppCompatActivity() {
    val context = this
    var databaseHelper = DatabaseHelper(context)
    var ID = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ubah_data_keahlian)

        cekIsi()
        hapus()
        simpan()
        batal()
        val bundle: Bundle? = intent.extras
        bundle?.let {
            val id = bundle!!.getInt(ID_KEAHLIAN)
            ID = id
            loadDataKeahlian(ID)
        }
    }

    fun simpan(){
        //Simpan ke database

        //Ke activity list
    }

    fun batal(){
        //Ke activity list
        tipeKeahlianBatalEdit.setOnClickListener { finish() }
    }

    fun cekIsi(){
        tipeKeahlianEdit.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //Enable tombol simpan ketika user sudah mulai mengisi form
                tipeKeahlianSimpanEdit.isEnabled = true

                //Tipe identitas tidak boleh kosong
                var tipeKeahlian = tipeKeahlianEdit.text.toString().trim()
                errorKeahlianEdit.isVisible = tipeKeahlian.isEmpty()
            }
        })
        deskripsiKeahlianEdit.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //Enable tombol simpan ketika user sudah mulai mengisi form
                tipeKeahlianSimpanEdit.isEnabled = true
            }
        })
    }

    fun hapus(){
        clearDeskripsiKeahlianEdit.setOnClickListener {deskripsiKeahlianEdit.setText("") }
        clearKeahlianEdit.setOnClickListener {tipeKeahlianEdit.setText("") }
    }

    fun loadDataKeahlian(id: Int){
        val db = databaseHelper.readableDatabase

        val projection = arrayOf<String>(
            ID_KEAHLIAN, NAMA_KEAHLIAN, DES_KEAHLIAN, IS_DELETED
        )
        val selection = ID_KEAHLIAN + "=?"
        val selectionArgs = arrayOf(id.toString())
        val cursor = db.query(TABEL_KEAHLIAN, projection, selection, selectionArgs, null, null, null)

        if (cursor.count == 1){
            tipeKeahlianEdit.setText(cursor.getString(1))
            deskripsiKeahlianEdit.setText(cursor.getString(2))
        }
    }
}
