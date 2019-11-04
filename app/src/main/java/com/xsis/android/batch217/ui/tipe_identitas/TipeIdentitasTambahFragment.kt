package com.xsis.android.batch217.ui.tipe_identitas

import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.google.android.material.textfield.TextInputEditText
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.ListTipeIdentitasAdapter
import com.xsis.android.batch217.adapters.fragments.PositionLevelFragmentAdapter
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.TipeIdentitasQueryHelper
import com.xsis.android.batch217.ui.position_level.PositionLevelFragmentData
import com.xsis.android.batch217.utils.*
import kotlinx.android.synthetic.main.fragment_tipe_identitas.*
import kotlinx.android.synthetic.main.fragment_tipe_identitas_tambah.*


class TipeIdentitasTambahFragment:Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_tipe_identitas_tambah, container, false)

        cekIsi(root)
        hapus(root)
        simpan(root)
        batal(root)



        return root

    }

    fun simpan(view:View){
        val simpan = view.findViewById(R.id.tipeidentitasSimpan) as Button
        simpan.setOnClickListener {
            //Simpan ke database
            insertKeTabelTipeIdentitas(view)

            //Ke activity list tipe identitas (list sudah terbarui)
            tutup(view)
            pindahFragment()
        }
    }

    fun insertKeTabelTipeIdentitas(view:View){
        val tipeIdentitas_text = view.findViewById(R.id.tipeTipeIdentitas) as TextInputEditText
        val tipeIdentitas_deskripsi = view.findViewById(R.id.deskripsiTipeIdentitas) as TextInputEditText

        val nama = tipeIdentitas_text.text.toString().trim()
        val des = tipeIdentitas_deskripsi.text.toString().trim()

        //Dengan cara content values
        val content = ContentValues()
        content.put(NAMA_IDENTITAS, nama)
        content.put(DES_IDENTITAS, des)
        content.put(IS_DELETED, "false")

        //Insert
        val databaseHelper = DatabaseHelper(context!!)
        val db = databaseHelper.writableDatabase

        db.insert(TABEL_TIPE_IDENTITAS, null, content)

    }

    fun batal(view:View){
        val batal = view.findViewById(R.id.tipeidentitasBatal) as Button
        //Ke fragment list tipe identitas
        batal.setOnClickListener { tutup(view) }
    }

    fun tutup(view:View){
        getActivity()!!.getSupportFragmentManager().beginTransaction().remove(this).commit()
    }

    fun pindahFragment(){
        val fragment = TipeIdentitasFragment()
        val fragmentManager = getActivity()!!.getSupportFragmentManager()
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(this.id, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    fun cekIsi(view:View){
        val tipeIdentitas_text = view.findViewById(R.id.tipeTipeIdentitas) as TextInputEditText
        val tipeIdentitas_deskripsi = view.findViewById(R.id.deskripsiTipeIdentitas) as TextInputEditText
        val tipeIdentitas_simpan = view.findViewById(R.id.tipeidentitasSimpan) as Button
        val tipeIdentitas_error = view.findViewById(R.id.errorTipeIdentitas) as TextView

        tipeIdentitas_text.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //Enable tombol simpan ketika user sudah mulai mengisi form
                tipeIdentitas_simpan.isEnabled = true

                //Tipe identitas tidak boleh kosong
                val tipeIdentitas = tipeIdentitas_text.text.toString().trim()

                if (tipeIdentitas.isEmpty()){
                    tipeIdentitas_error.isVisible = true
                } else{
                    tipeIdentitas_error.isVisible = false
                }
            }
        })
        tipeIdentitas_deskripsi.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //Enable tombol simpan ketika user sudah mulai mengisi form
                tipeIdentitas_simpan.isEnabled = true
            }
        })
    }

    fun hapus(view:View){
        val tipeIdentitas_text = view.findViewById(R.id.tipeTipeIdentitas) as TextInputEditText
        val tipeIdentitas_deskripsi = view.findViewById(R.id.deskripsiTipeIdentitas) as TextInputEditText
        val clearDes = view.findViewById(R.id.clearDeskripsi) as Button
        val clearTipe = view.findViewById(R.id.clearTipeIdentitas) as Button
        clearDes.setOnClickListener {tipeIdentitas_deskripsi.setText("") }
        clearTipe.setOnClickListener {tipeIdentitas_text.setText("") }
    }
}