package com.xsis.android.batch217.ui.tipe_identitas

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.xsis.android.batch217.R
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.PendidikanQueryHelper
import com.xsis.android.batch217.databases.TipeIdentitasQueryHelper
import com.xsis.android.batch217.utils.*
import kotlinx.android.synthetic.main.activity_input_pendidikan.*
import kotlinx.android.synthetic.main.activity_input_pendidikan.clearDeskripsi
import kotlinx.android.synthetic.main.activity_keluarga_form.*
import kotlinx.android.synthetic.main.activity_tipe_identitas_tambah.*
import kotlinx.android.synthetic.main.fragment_tipe_identitas_tambah.*
class TipeIdentitasTambahActivity : AppCompatActivity() {
    val context = this
    var judul:TextView? = null
    var nama0:TextInputLayout? = null
    var des0:TextInputLayout? = null
    var nama:TextInputEditText? = null
    var des:TextInputEditText? = null
    var simpan:Button? = null
    var batal:Button? = null
    var clearNama:Button? = null
    var clearDes:Button? = null
    var error:TextView? = null
    var linearLayout:LinearLayout? = null
    var id:Int = 0

    val databaseHelper = DatabaseHelper(this)
    val databaseQueryHelper = TipeIdentitasQueryHelper(databaseHelper)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tipe_identitas_tambah)

        judul = findViewById(R.id.judulFormTipeIdentitas)
        nama0 = findViewById(R.id.teksTipeIdentitas0)
        des0 = findViewById(R.id.teksDesTipeIdentitas0)
        nama = findViewById(R.id.teksTipeIdentitas)
        des = findViewById(R.id.teksDesTipeIdentitas)
        simpan = findViewById(R.id.btnSimpanTipeIdentitas)
        batal = findViewById(R.id.btnBatalTipeIdentitas)
        clearNama = findViewById(R.id.clearTipeIdentitas)
        clearDes = findViewById(R.id.clearDeskripsi)
        error = findViewById(R.id.teksErrorTipeIdentitas)
        linearLayout = findViewById(R.id.linearLayoutTipeIdentitasForm)

        val bundle: Bundle? = intent.extras
        bundle?.let {
            judul!!.text = "Ubah Tipe Identitas"

            id = bundle.getInt(ID_IDENTITAS)
            val data = databaseQueryHelper.loadTipeIdentitas(id)

            nama!!.setText(data.nama_TipeIdentitas)
            des!!.setText(data.des_TipeIdentitas)

            enableUbah()
        }

        cekIsi()
        hapus()
        simpan(id)
        batal()

        backTipeIdentitasForm.setOnClickListener { finish() }
        linearLayout!!.setOnClickListener{ hideKeyboard()}

        teksTipeIdentitas.addTextChangedListener(textWatcher)
        teksDesTipeIdentitas.addTextChangedListener(textWatcher)
    }

    fun batal(){
        batal!!.setOnClickListener { finish() }
    }
    fun simpan(id:Int) {
        simpan!!.setOnClickListener {
            val Nama = nama!!.text.toString().trim().toUpperCase()
            val Des = des!!.text.toString().trim().toUpperCase()

            if (Nama.isEmpty()){
                Toast.makeText(this, DATA_BELUM_LENGKAP, Toast.LENGTH_SHORT).show()
            } else{
                if (id == 0){
                    insertKeDatabase(Nama, Des)
                } else{
                    update(id, Nama, Des)
                }
            }
        }
    }

    fun enableUbah(){
        simpan!!.isEnabled = true

        val Nama = nama!!.text.toString().trim()
        error!!.isVisible = Nama.isEmpty()
        clearNama!!.isVisible = !Nama.isEmpty()
        val Des = des!!.text.toString().trim()
        clearDes!!.isVisible = !Des.isEmpty()
    }

    fun cekIsi(){
        teksTipeIdentitas.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //Enable tombol simpan ketika user sudah mulai mengisi form

            }
        })
        teksDesTipeIdentitas.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //Enable tombol simpan ketika user sudah mulai mengisi form
                simpan!!.setBackgroundResource(R.drawable.button_simpan_on)
                simpan!!.setTextColor(Color.WHITE)
                simpan!!.isEnabled = true

                val Des = des!!.text.toString().trim()
                clearDes!!.isVisible = !Des.isEmpty()
            }
        })
    }


    fun insertKeDatabase(Nama:String, Des:String){
        val sudahAda = databaseQueryHelper.readNamaTipeIdentitas(Nama)
        println(sudahAda)

        if (sudahAda.size > 0){
            Toast.makeText(this, DATA_SUDAH_ADA, Toast.LENGTH_SHORT).show()
        } else {
            databaseQueryHelper.insertTipeIdentitas(Nama, Des)
            Toast.makeText(this, SIMPAN_DATA_BERHASIL, Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    fun update(id:Int, Nama:String, Des:String){
        val lain = databaseQueryHelper.readIdIdentitasLain(id, Nama)
        println(lain)

        if (lain.size > 0){
            Toast.makeText(this, DATA_SUDAH_ADA, Toast.LENGTH_SHORT).show()
        } else{
            databaseQueryHelper.updateTipeIdentitas(Nama, Des, id)
            Toast.makeText(this, SIMPAN_DATA_BERHASIL, Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    fun hapus(){
        clearDes!!.setOnClickListener {des!!.setText("") }
        clearNama!!.setOnClickListener {nama!!.setText("") }
    }

    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            simpan!!.setBackgroundResource(R.drawable.button_simpan_on)
            simpan!!.setTextColor(Color.WHITE)
            simpan!!.isEnabled = true

            val Nama = nama!!.text.toString().trim()
            val Des = des!!.text.toString().trim()

            clearNama!!.isVisible = !Nama.isEmpty()
            clearDes!!.isVisible = !Des.isEmpty()

            ubahTextInputEditTextColor(context, nama!!, nama0!!, Nama.isEmpty())

            if (Nama.isEmpty()){
                error!!.visibility = View.VISIBLE
            } else{
                error!!.visibility = View.INVISIBLE
            }

        }

    }

    fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }
    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
