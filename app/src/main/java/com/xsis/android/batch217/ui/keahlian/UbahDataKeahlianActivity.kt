package com.xsis.android.batch217.ui.keahlian

import android.content.ContentValues
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import androidx.core.view.isVisible
import com.xsis.android.batch217.R
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.models.Keahlian
import com.xsis.android.batch217.utils.*
import kotlinx.android.synthetic.main.activity_ubah_data_keahlian.*

class UbahDataKeahlianActivity : AppCompatActivity() {
    val context = this
    var databaseHelper = DatabaseHelper(context)
    var data = Keahlian()

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
            loadDataKeahlian(id)
        }
    }

    fun simpan(){
        //Simpan ke database
        val simpan = tipeKeahlianSimpanEdit as Button
        simpan.setOnClickListener{
            insertKeDatabase()
            if (tipeKeahlianEdit.text.toString().trim().isEmpty()) {
                //tidak ada aksi
            }
            else{
                finish()
            }

        }

        //Ke activity list
    }

    fun insertKeDatabase() {
        val isiTipeKeahlian = tipeKeahlianEdit.text.toString().trim()
        val isiDeskripsiKeahlian = deskripsiKeahlianEdit.text.toString().trim()

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
                tipeKeahlianSimpanEdit.setBackgroundResource(R.drawable.button_simpan_on)
                tipeKeahlianSimpanEdit.setTextColor(Color.WHITE)

                //Tipe identitas tidak boleh kosong
                val tipeKeahlian = tipeKeahlianEdit.text.toString().trim()
                if (tipeKeahlian.isEmpty()){
                    errorKeahlianEdit.isVisible = true
                } else{
                    errorKeahlianEdit.isVisible = false
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
            cursor.moveToFirst()
            data.id_keahlian = cursor.getInt(0)
            data.nama_keahlian = cursor.getString(1)
            data.des_keahlian = cursor.getString(2)
            data.is_deleted = cursor.getString(3)
            tipeKeahlianEdit.setText(data.nama_keahlian)
            deskripsiKeahlianEdit.setText(data.des_keahlian)
        }
    }
}
