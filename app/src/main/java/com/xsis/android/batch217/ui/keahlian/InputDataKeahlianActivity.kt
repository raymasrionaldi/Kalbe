package com.xsis.android.batch217.ui.keahlian

import android.app.PendingIntent.getActivity
import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import androidx.core.view.isVisible
import com.xsis.android.batch217.R
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.utils.DES_KEAHLIAN
import com.xsis.android.batch217.utils.IS_DELETED
import com.xsis.android.batch217.utils.NAMA_KEAHLIAN
import com.xsis.android.batch217.utils.TABEL_KEAHLIAN
import kotlinx.android.synthetic.main.activity_input_data_keahlian.*

class InputDataKeahlianActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_data_keahlian)

        cekIsi()
        hapus()
        simpan()
        batal()
    }

    fun simpan(){
        //Simpan ke database
        val simpan = tipeKeahlianSimpan as Button
        simpan.setOnClickListener{
            insertKeDatabase()
            if (tipeKeahlian.text.toString().trim().isEmpty()) {
                //tidak ada aksi
            }
            else{
                finish()
            }

        }
        //Ke activity list

    }

    fun insertKeDatabase() {
        val isiTipeKeahlian = tipeKeahlian.text.toString().trim()
        val isiDeskripsiKeahlian = deskripsiKeahlian.text.toString().trim()

        val content = ContentValues()
        content.put(NAMA_KEAHLIAN, isiTipeKeahlian)
        content.put(DES_KEAHLIAN, isiDeskripsiKeahlian)
        content.put(IS_DELETED, "false")

        val databaseHelper = DatabaseHelper(this)
        val db = databaseHelper.writableDatabase

        db.insert(TABEL_KEAHLIAN, null, content)
    }

    fun batal(){
        //Ke activity list
        tipeKeahlianBatal.setOnClickListener { finish() }
    }

    fun cekIsi(){
        tipeKeahlian.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //Enable tombol simpan ketika user sudah mulai mengisi form
                tipeKeahlianSimpan.isEnabled = true

                //Tipe identitas tidak boleh kosong
                val tipeKeahlian = tipeKeahlian.text.toString().trim()
                if (tipeKeahlian.isEmpty()){
                    errorKeahlian.isVisible = true
                } else{
                    errorKeahlian.isVisible = false
                }
            }
        })
        deskripsiKeahlian.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //Enable tombol simpan ketika user sudah mulai mengisi form
                tipeKeahlianSimpan.isEnabled = true
            }
        })
    }

    fun hapus(){
        clearDeskripsiKeahlian.setOnClickListener {deskripsiKeahlian.setText("") }
        clearKeahlian.setOnClickListener {tipeKeahlian.setText("") }
    }

}
