package com.xsis.android.batch217.ui.jenjang_pendidikan

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
import com.xsis.android.batch217.databases.PendidikanQueryHelper
import com.xsis.android.batch217.models.Pendidikan
import com.xsis.android.batch217.utils.*
import kotlinx.android.synthetic.main.activity_input_pendidikan.*
import kotlinx.android.synthetic.main.activity_ubah_pendidikan.*
import kotlinx.android.synthetic.main.activity_ubah_pendidikan.clearDeskripsi
import kotlinx.android.synthetic.main.activity_ubah_pendidikan.clearPendidikan
import kotlinx.android.synthetic.main.activity_ubah_pendidikan.teksDesPendidikan
import kotlinx.android.synthetic.main.activity_ubah_pendidikan.teksPendidikan

class UbahPendidikanActivity : AppCompatActivity() {
    val context = this
    var databaseHelper = DatabaseHelper(context)
    var data = Pendidikan()
    var idPendidikan = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ubah_pendidikan)

        val bundle: Bundle? = intent.extras
        bundle?.let {
            idPendidikan = bundle!!.getInt(ID_PENDIDIKAN)
            loadDataKeahlian(idPendidikan)
        }

        btnNavDrawerUpdate.setOnClickListener { finish() }
    }

    fun loadDataKeahlian(id: Int) {
        val db = databaseHelper.readableDatabase

        val projection = arrayOf<String>(
            ID_PENDIDIKAN, NAMA_PENDIDIKAN, DES_PENDIDIKAN, IS_DELETED
        )
        val selection = ID_PENDIDIKAN + "=?"
        val selectionArgs = arrayOf(id.toString())
        val cursor =
            db.query(TABEL_PENDIDIKAN, projection, selection, selectionArgs, null, null, null)

        if (cursor.count == 1) {
            cursor.moveToFirst()
            data.id_pendidikan = cursor.getInt(0)
            data.nama_pendidikan = cursor.getString(1)
            data.des_pendidikan = cursor.getString(2)
            data.is_Deleted = cursor.getString(3)
            teksPendidikan.setText(data.nama_pendidikan)
            teksDesPendidikan.setText(data.des_pendidikan)
        }
        cekIsi()
        hapus()
        simpan(id)
        batal()
    }

    fun hapus(){
        clearDeskripsi.setOnClickListener {teksDesPendidikan.setText("") }
        clearPendidikan.setOnClickListener {teksPendidikan.setText("") }
    }

    fun batal() {
        //Ke activity list
        btnBatalPendidikanUpdate.setOnClickListener { finish() }
    }

    fun cekIsi(){
        teksPendidikan.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //Enable tombol simpan ketika user sudah mulai mengisi form
                btnSimpanPendidikanUpdate.isEnabled = true
                btnSimpanPendidikanUpdate.setBackgroundResource(R.drawable.button_simpan_on_2)
                btnSimpanPendidikanUpdate.setTextColor(Color.WHITE)

                val namaPendidikan = teksPendidikan.text.toString().trim()
                if (namaPendidikan.isEmpty()){
                    teksErrorPendidikanUpdate.isVisible = true
                    clearPendidikan.isVisible = false
                } else{
                    teksErrorPendidikanUpdate.isVisible = false
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
                btnSimpanPendidikanUpdate.isEnabled = true
                //Deskripsi Keahlian tidak boleh kosong
                val desPendidikan = teksDesPendidikan.text.toString().trim()
                clearDeskripsi.isVisible = !desPendidikan.isEmpty()
            }
        })
    }

    fun simpan(id: Int) {
        //Simpan ke database
        val simpan = btnSimpanPendidikanUpdate as Button
        simpan.setOnClickListener {
            if (teksPendidikan.text.toString().trim().isEmpty()) {
                //tidak ada aksi
            } else {
                insertKeDatabase(id)
            }
        }
    }

    fun insertKeDatabase(id:Int){
        val nama = teksPendidikan.text.toString().trim()
        val des = teksDesPendidikan.text.toString().trim()
        if(nama.isNullOrEmpty() || des.isNullOrEmpty()){
            Toast.makeText(context, DATA_BELUM_LENGKAP, Toast.LENGTH_SHORT).show()
        } else {
            val databaseQueryHelper = PendidikanQueryHelper(databaseHelper)
            val listPendidikan = databaseQueryHelper.readUpdate(idPendidikan,nama)
            if(listPendidikan.isEmpty()){
                databaseQueryHelper.updateDelete(idPendidikan, nama,des)
                Toast.makeText(context, EDIT_DATA_BERHASIL, Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(context, DATA_SUDAH_ADA, Toast.LENGTH_SHORT).show()
            }
        }

    }

}
