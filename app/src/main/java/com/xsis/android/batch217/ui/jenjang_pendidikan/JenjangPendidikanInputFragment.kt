package com.xsis.android.batch217.ui.jenjang_pendidikan

import android.content.ContentValues
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import com.xsis.android.batch217.R
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.PendidikanQueryHelper
import com.xsis.android.batch217.utils.*
import kotlinx.android.synthetic.main.fragment_jenjang_pendidikan_input.*

class JenjangPendidikanInputFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_jenjang_pendidikan_input, container, false)
        val title = root.findViewById(R.id.titlePendidikan) as TextView
        title.text = INPUT_PENDIDIKAN


        cekIsi(root)
        hapus(root)
        simpan(root)
        batal(root)

        return root
    }

    fun simpan(view:View){
        val simpan = view.findViewById(R.id.btnSimpanPendidikan) as Button
        simpan.setOnClickListener {
            //Simpan ke database
            insertKeTabelPendidikan(view)

            //Ke activity list tipe identitas (list sudah terbarui)
            tutup(view)
            pindahFragment()
        }
    }

    fun insertKeTabelPendidikan(view:View){
        val nama_pendidikan = view.findViewById(R.id.teksPendidikan) as TextInputEditText
        val des_pendidikan = view.findViewById(R.id.teksDesPendidikan) as TextInputEditText

        val nama = nama_pendidikan.text.toString().trim()
        val des = des_pendidikan.text.toString().trim()
        //read
        val databaseHelper = DatabaseHelper(context!!)
        val databaseQueryHelper = PendidikanQueryHelper(databaseHelper)
        val listPendidikan = databaseQueryHelper.readNamaPendidikan(nama)
        println(listPendidikan.size)

        if (!listPendidikan.isEmpty()){
            //cek id_deleted
            if(listPendidikan[0].is_Deleted == "true"){
                //update tru jadi false
                databaseQueryHelper.updatePendidikan(nama,des)
                Toast.makeText(context, SIMPAN_DATA_BERHASIL, Toast.LENGTH_SHORT).show()
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
        }
    }

    fun batal(view:View){
        val batal = view.findViewById(R.id.btnBatalPendidikan) as Button
        //Ke fragment list tipe identitas
        batal.setOnClickListener { tutup(view) }
    }

    fun tutup(view:View){
        getActivity()?.getFragmentManager()?.popBackStack();
    }

    fun pindahFragment(){
        val fragment = JenjangPendidikanFragment()
        val fragmentManager = getActivity()!!.getSupportFragmentManager()
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(this.id, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    fun cekIsi(view:View){
        val nama = view.findViewById(R.id.teksPendidikan) as TextInputEditText
        val des = view.findViewById(R.id.teksDesPendidikan) as TextInputEditText
        val btnSimpan = view.findViewById(R.id.btnSimpanPendidikan) as Button
        val error = view.findViewById(R.id.teksErrorPendidikan) as TextView
        val clearPendidikan = view.findViewById(R.id.clearPendidikan) as Button
        val clearDes = view.findViewById(R.id.clearDeskripsi) as Button


        nama.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //Enable tombol simpan ketika user sudah mulai mengisi form
                btnSimpan.isEnabled = true

                //Tipe identitas tidak boleh kosong
                val pendidikan = nama.text.toString().trim()
                error.isVisible = pendidikan.isEmpty()

                clearPendidikan.isVisible = !pendidikan.isEmpty()
            }
        })
        des.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //Enable tombol simpan ketika user sudah mulai mengisi form
                btnSimpan.isEnabled = true

                val des = des.text.toString().trim()

                clearDes.isVisible = !des.isEmpty()
            }
        })
    }

    fun hapus(view:View){
        val nama = view.findViewById(R.id.teksPendidikan) as TextInputEditText
        val des = view.findViewById(R.id.teksDesPendidikan) as TextInputEditText
        val clearDes = view.findViewById(R.id.clearDeskripsi) as Button
        val clearPendidikan = view.findViewById(R.id.clearPendidikan) as Button
        clearDes.setOnClickListener {des.setText("") }
        clearPendidikan.setOnClickListener {nama.setText("") }
    }
}