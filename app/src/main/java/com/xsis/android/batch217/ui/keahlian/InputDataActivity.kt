package com.xsis.android.batch217.ui.keahlian

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.core.view.isVisible
import com.xsis.android.batch217.R
import kotlinx.android.synthetic.main.activity_input_data.*

class InputDataActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_data)

        cekIsi()
        hapus()
        simpan()
        batal()
    }

    fun simpan(){
        //Simpan ke database

        //Ke activity list tipe identitas (list sudah terbarui)
    }

    fun batal(){
        //Ke activity list tipe identitas
        tipeidentitasBatal.setOnClickListener { finish() }
    }

    fun cekIsi(){
        tipeTipeIdentitas.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //Enable tombol simpan ketika user sudah mulai mengisi form
                tipeidentitasSimpan.isEnabled = true

                //Tipe identitas tidak boleh kosong
                val tipeIdentitas = tipeTipeIdentitas.text.toString().trim()
                errorTipeIdentitas.isVisible = tipeIdentitas.isEmpty()
            }
        })
        deskripsiTipeIdentitas.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //Enable tombol simpan ketika user sudah mulai mengisi form
                tipeidentitasSimpan.isEnabled = true
            }
        })
    }

    fun hapus(){
        clearDeskripsi.setOnClickListener {deskripsiTipeIdentitas.setText("") }
        clearTipeIdentitas.setOnClickListener {tipeTipeIdentitas.setText("") }
    }


}
