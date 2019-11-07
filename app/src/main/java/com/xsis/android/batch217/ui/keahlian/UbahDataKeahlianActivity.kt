package com.xsis.android.batch217.ui.keahlian

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import androidx.core.view.isVisible
import com.xsis.android.batch217.R
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.KeahlianQueryHelper
import com.xsis.android.batch217.models.Keahlian
import com.xsis.android.batch217.utils.*
import kotlinx.android.synthetic.main.activity_ubah_data_keahlian.*

class UbahDataKeahlianActivity : AppCompatActivity() {
    val context = this
    var databaseHelper = DatabaseHelper(context)
    var data = Keahlian()
    var ID_Keahlian = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar!!.hide()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_ubah_data_keahlian)
        try {
            this.supportActionBar!!.hide()
        } catch (e: NullPointerException){
        }
        val bundle: Bundle? = intent.extras
        bundle?.let {
            ID_Keahlian = bundle!!.getInt(ID_KEAHLIAN)
            loadDataKeahlian(ID_Keahlian)
        }
        buttonBackUbahKeahlian.setOnClickListener {
            finish()
        }
    }

    fun simpan(id: Int) {
        //Simpan ke database
        val simpan = tipeKeahlianSimpanEdit as Button
        simpan.setOnClickListener {
            if (tipeKeahlianEdit.text.toString().trim().isEmpty()) {
                //tidak ada aksi
            } else {
                insertKeDatabase(id)
                finish()
            }

        }

        //Ke activity list
    }

    fun insertKeDatabase(id: Int) {
        val isiTipeKeahlian = tipeKeahlianEdit.text.toString().trim()
        val isiDeskripsiKeahlian = deskripsiKeahlianEdit.text.toString().trim()

        val databaseHelper = DatabaseHelper(this)
        val db = databaseHelper.writableDatabase

        val databaseQueryHelper = KeahlianQueryHelper(databaseHelper)
        val listKeahlian = databaseQueryHelper.readUpdate(id, isiTipeKeahlian)

        if(listKeahlian.isEmpty()){
            databaseQueryHelper.updateDelete(id, isiTipeKeahlian,isiDeskripsiKeahlian)
            Toast.makeText(context, EDIT_DATA_BERHASIL, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, DATA_SUDAH_ADA, Toast.LENGTH_SHORT).show()
        }
    }

    fun batal() {
        //Ke activity list
        tipeKeahlianBatalEdit.setOnClickListener { finish() }
    }

    fun cekIsi() {
        tipeKeahlianEdit.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //Enable tombol simpan ketika user sudah mulai mengisi form
                tipeKeahlianSimpanEdit.isEnabled = true
                tipeKeahlianSimpanEdit.setBackgroundResource(R.drawable.button_simpan_on_2)
                tipeKeahlianSimpanEdit.setTextColor(Color.WHITE)

                //Tipe identitas tidak boleh kosong
                val tipeKeahlian = tipeKeahlianEdit.text.toString().trim()
                if (tipeKeahlian.isEmpty()) {
                    errorKeahlianEdit.isVisible = true
                    clearKeahlianEdit.isVisible = false
                } else {
                    errorKeahlianEdit.isVisible = false
                    clearKeahlianEdit.isVisible = true
                }
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
                //Deskripsi tidak boleh kosong
                val deskripsiKeahlian = deskripsiKeahlianEdit.text.toString().trim()
                if (deskripsiKeahlian.isEmpty()) {
                    clearDeskripsiKeahlianEdit.isVisible = false
                } else {
                    clearDeskripsiKeahlianEdit.isVisible = true
                }
            }
        })
    }

    fun hapus() {
        clearDeskripsiKeahlianEdit.setOnClickListener { deskripsiKeahlianEdit.setText("") }
        clearKeahlianEdit.setOnClickListener { tipeKeahlianEdit.setText("") }
    }

    fun loadDataKeahlian(id: Int) {
        val db = databaseHelper.readableDatabase

        val projection = arrayOf<String>(
            ID_KEAHLIAN, NAMA_KEAHLIAN, DES_KEAHLIAN, IS_DELETED
        )
        val selection = ID_KEAHLIAN + "=?"
        val selectionArgs = arrayOf(id.toString())
        val cursor =
            db.query(TABEL_KEAHLIAN, projection, selection, selectionArgs, null, null, null)

        if (cursor.count == 1) {
            cursor.moveToFirst()
            data.id_keahlian = cursor.getInt(0)
            data.nama_keahlian = cursor.getString(1)
            data.des_keahlian = cursor.getString(2)
            data.is_deleted = cursor.getString(3)
            tipeKeahlianEdit.setText(data.nama_keahlian)
            deskripsiKeahlianEdit.setText(data.des_keahlian)
        }
        cekIsi()
        hapus()
        simpan(id)
        batal()
    }
}
