package com.xsis.android.batch217.ui.jenjang_pendidikan

import android.content.ContentValues
import android.content.Intent
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
import com.google.android.material.navigation.NavigationView
import com.xsis.android.batch217.HomeActivity
import com.xsis.android.batch217.R
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.PendidikanQueryHelper
import com.xsis.android.batch217.utils.*
import kotlinx.android.synthetic.main.activity_input_pendidikan.*
import kotlinx.android.synthetic.main.activity_input_pendidikan.btnBatalPendidikan
import kotlinx.android.synthetic.main.activity_input_pendidikan.btnSimpanPendidikan
import kotlinx.android.synthetic.main.activity_input_pendidikan.clearDeskripsi
import kotlinx.android.synthetic.main.activity_input_pendidikan.clearPendidikan
import kotlinx.android.synthetic.main.activity_input_pendidikan.teksDesPendidikan
import kotlinx.android.synthetic.main.activity_input_pendidikan.teksErrorPendidikan
import kotlinx.android.synthetic.main.activity_input_pendidikan.teksPendidikan
import kotlinx.android.synthetic.main.fragment_jenjang_pendidikan_input.*

class InputPendidikanActivity : AppCompatActivity() {
    val context = this
    val databaseHelper = DatabaseHelper(context)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_pendidikan)

        cekIsi()
        hapus()
        simpan()
        batal()

        btnNavDrawer.setOnClickListener {
            finish()
        }
    }

    fun batal(){
        btnBatalPendidikan.setOnClickListener { finish() }
    }

    fun cekIsi(){
        teksPendidikan.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //Enable tombol simpan ketika user sudah mulai mengisi form
                btnSimpanPendidikan.isEnabled = true
                btnSimpanPendidikan.setBackgroundResource(R.drawable.button_simpan_on_2)
                btnSimpanPendidikan.setTextColor(Color.WHITE)

                val namaPendidikan = teksPendidikan.text.toString().trim()
                if (namaPendidikan.isEmpty()){
                    teksErrorPendidikan.isVisible = true
                    clearPendidikan.isVisible = false
                } else{
                    teksErrorPendidikan.isVisible = false
                    clearPendidikan.isVisible = true
                }
            }
        })
        teksDesPendidikan.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //Enable tombol simpan ketika user sudah mulai mengisi form
                btnSimpanPendidikan.isEnabled = true
                //Deskripsi Keahlian tidak boleh kosong
                val desPendidikan = teksDesPendidikan.text.toString().trim()
                clearDeskripsi.isVisible = !desPendidikan.isEmpty()
            }
        })
    }

    fun simpan() {
        val simpan = btnSimpanPendidikan as Button
        simpan.setOnClickListener {
            if (teksPendidikan.text.toString().trim().isEmpty()) {
                Toast.makeText(context, DATA_BELUM_LENGKAP, Toast.LENGTH_SHORT).show()
                teksErrorPendidikan.isVisible = true
            } else {
                insertKeDatabase()
            }
        }
    }

    fun insertKeDatabase(){
        val nama = teksPendidikan.text.toString().trim()
        val des = teksDesPendidikan.text.toString().trim()
        //read
        if(nama.isNullOrEmpty() || des.isNullOrEmpty()){
            Toast.makeText(context, DATA_BELUM_LENGKAP, Toast.LENGTH_SHORT).show()
        } else {
            val databaseHelper = DatabaseHelper(context!!)
            val databaseQueryHelper = PendidikanQueryHelper(databaseHelper)
            val listPendidikan = databaseQueryHelper.readNamaPendidikan(nama)
            if (!listPendidikan.isEmpty()){
                //cek id_deleted
                if(listPendidikan[0].is_Deleted == "true"){
                    //update tru jadi false
                    databaseQueryHelper.updatePendidikan(nama,des)
                    Toast.makeText(context, SIMPAN_DATA_BERHASIL, Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(context, DATA_SUDAH_ADA, Toast.LENGTH_SHORT).show()
                }
            } else {
                //jika tidak maka insert
                //cek nama
                val content = ContentValues()
                content.put(NAMA_PENDIDIKAN, nama)
                content.put(DES_PENDIDIKAN, des)
                content.put(IS_DELETED, "false")
                val db = DatabaseHelper(context!!).writableDatabase
                db.insert(TABEL_PENDIDIKAN, null, content)
                Toast.makeText(context, SIMPAN_DATA_BERHASIL, Toast.LENGTH_SHORT).show()
                finish()
            }
        }

    }

    fun hapus(){
        clearDeskripsi.setOnClickListener {teksDesPendidikan.setText("") }
        clearPendidikan.setOnClickListener {teksPendidikan.setText("") }
    }
}
