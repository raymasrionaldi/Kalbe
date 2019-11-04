package com.xsis.android.batch217.ui.jenjang_pendidikan

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.core.view.isVisible
import com.google.android.material.navigation.NavigationView
import com.xsis.android.batch217.HomeActivity
import com.xsis.android.batch217.R
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.utils.*
import kotlinx.android.synthetic.main.activity_input_pendidikan.*

class InputPendidikanActivity : AppCompatActivity() {
    val context = this
    val databaseHelper = DatabaseHelper(context)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_pendidikan)

        cekIsi()
        clearTeks()
        simpan()
        batal()
//        val navView: NavigationView = findViewById(R.id.nav_view)
        btnNavDrawer.setOnClickListener {
            val intent = Intent(context, HomeActivity::class.java)
            startActivity(intent)
        }
    }

    fun cekIsi(){

    }

    fun clearTeks(){
        btnBatalPendidikan.setOnClickListener { finish() }
    }

    fun simpan(){
        teksPendidikan.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                btnSimpanPendidikan.isEnabled = true
                val namaPendidikan = teksPendidikan.text.toString().trim()
                teksErrorPendidikan.isVisible = namaPendidikan.isEmpty()
            }
        })
        teksDesPendidikan.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                btnSimpanPendidikan.isEnabled = true
            }

        })
    }

    fun batal(){
        clearDeskripsi.setOnClickListener {teksDesPendidikan.setText("") }
        clearPendidikan.setOnClickListener {teksPendidikan.setText("") }
    }
}
