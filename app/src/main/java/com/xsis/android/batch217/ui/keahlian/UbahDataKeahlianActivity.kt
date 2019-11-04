package com.xsis.android.batch217.ui.keahlian

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.core.view.isVisible
import com.xsis.android.batch217.R
import kotlinx.android.synthetic.main.activity_ubah_data_keahlian.*

class UbahDataKeahlianActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ubah_data_keahlian)

        cekIsi()
        hapus()
        simpan()
        batal()
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
                val tipeIdentitas = tipeKeahlianEdit.text.toString().trim()
                errorKeahlianEdit.isVisible = tipeIdentitas.isEmpty()
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
}
