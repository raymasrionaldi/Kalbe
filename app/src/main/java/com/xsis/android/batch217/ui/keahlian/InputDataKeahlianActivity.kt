package com.xsis.android.batch217.ui.keahlian

import android.app.PendingIntent.getActivity
import android.content.ContentValues
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import androidx.core.view.isVisible
import com.xsis.android.batch217.R
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.KeahlianQueryHelper
import com.xsis.android.batch217.utils.*
import kotlinx.android.synthetic.main.activity_input_data_keahlian.*

class InputDataKeahlianActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar!!.hide()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_input_data_keahlian)
        try {
            this.supportActionBar!!.hide()
        } catch (e: NullPointerException){
        }

        cekIsi()
        hapus()
        simpan()
        batal()
        buttonBackInputKeahlian.setOnClickListener {
            finish()
        }
    }

    fun simpan(){
        //Simpan ke database
        val simpan = tipeKeahlianSimpan as Button
        simpan.setOnClickListener{
            if (tipeKeahlian.text.toString().trim().isEmpty()) {
                //tidak ada aksi
            }
            else{
                insertKeDatabase()
                finish()
            }

        }

    }

    fun insertKeDatabase() {
        val isiTipeKeahlian = tipeKeahlian.text.toString().trim()
        val isiDeskripsiKeahlian = deskripsiKeahlian.text.toString().trim()

        val databaseHelper = DatabaseHelper(this)
        val db = databaseHelper.writableDatabase

        val databaseQueryHelper = KeahlianQueryHelper(databaseHelper)
        val listKeahlian = databaseQueryHelper.readNamaKeahlian(isiTipeKeahlian)

        if (!listKeahlian.isEmpty()){
            //cek id_deleted
            if(listKeahlian[0].is_deleted == "true"){
                //update tru jadi false
                databaseQueryHelper.updateKeahlian(isiTipeKeahlian,isiDeskripsiKeahlian)
                Toast.makeText(this, SIMPAN_DATA_BERHASIL, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, DATA_SUDAH_ADA, Toast.LENGTH_SHORT).show()
            }
        } else {
            //jika tidak maka insert
            //cek nama
            val content = ContentValues()
            content.put(NAMA_KEAHLIAN, isiTipeKeahlian)
            content.put(DES_KEAHLIAN, isiDeskripsiKeahlian)
            content.put(IS_DELETED, "false")
            val db = DatabaseHelper(this).writableDatabase
            db.insert(TABEL_KEAHLIAN, null, content)
            Toast.makeText(this, SIMPAN_DATA_BERHASIL, Toast.LENGTH_SHORT).show()
        }
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
                tipeKeahlianSimpan.setBackgroundResource(R.drawable.button_simpan_on_2)
                tipeKeahlianSimpan.setTextColor(Color.WHITE)

                //Keahlian tidak boleh kosong
                val tipeKeahlian = tipeKeahlian.text.toString().trim()
                if (tipeKeahlian.isEmpty()){
                    errorKeahlian.isVisible = true
                    clearKeahlian.isVisible = false
                } else{
                    errorKeahlian.isVisible = false
                    clearKeahlian.isVisible = true
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
                //Deskripsi Keahlian tidak boleh kosong
                val deskripsiKeahlian = deskripsiKeahlian.text.toString().trim()
                if (deskripsiKeahlian.isEmpty()){
                    clearDeskripsiKeahlian.isVisible = false
                } else{
                    clearDeskripsiKeahlian.isVisible = true
                }
            }
        })
    }

    fun hapus(){
        clearDeskripsiKeahlian.setOnClickListener {deskripsiKeahlian.setText("") }
        clearKeahlian.setOnClickListener {tipeKeahlian.setText("") }
    }

}
