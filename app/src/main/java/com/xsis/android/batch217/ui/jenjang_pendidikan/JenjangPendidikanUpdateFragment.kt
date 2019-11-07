package com.xsis.android.batch217.ui.jenjang_pendidikan

import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import com.xsis.android.batch217.R
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.PendidikanQueryHelper
import com.xsis.android.batch217.utils.*
import kotlinx.android.synthetic.main.fragment_jenjang_pendidikan_input.*

class JenjangPendidikanUpdateFragment:Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_jenjang_pendidikan_input, container, false)
        val title = root.findViewById(R.id.titlePendidikan) as TextView
        val nama = root.findViewById(R.id.teksPendidikan) as TextView
        val des = root.findViewById(R.id.teksDesPendidikan) as TextView
        var idPendidikan = 0

        if(arguments!!.getString("nama") != null){
            idPendidikan = arguments!!.getInt("id")
            nama.setText(arguments!!.getString("nama"))
            des.setText(arguments!!.getString("des"))
        }

        cekIsi(root)
        hapus(root)
        simpan(root, idPendidikan)
        batal(root)


        return root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback = object : OnBackPressedCallback(
            true // default to enabled
        ) {
            override fun handleOnBackPressed() {
                tutup()
//                pindahFragment()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            this, // LifecycleOwner
            callback
        )
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

    fun simpan(view:View, idPendidikan: Int){
        val simpan = view.findViewById(R.id.btnSimpanPendidikan) as Button
        simpan.setOnClickListener {
            //Simpan ke database
            insertKeTabelPendidikan(view, idPendidikan)

            //Ke activity list tipe identitas (list sudah terbarui)
            tutup()
//            pindahFragment()
        }
    }

    fun insertKeTabelPendidikan(view:View, idPendidikan: Int){
        val nama_pendidikan = view.findViewById(R.id.teksPendidikan) as TextInputEditText
        val des_pendidikan = view.findViewById(R.id.teksDesPendidikan) as TextInputEditText

        val nama = nama_pendidikan.text.toString().trim()
        val des = des_pendidikan.text.toString().trim()
        //read
        val databaseHelper = DatabaseHelper(context!!)
        val databaseQueryHelper = PendidikanQueryHelper(databaseHelper)
        val listPendidikan = databaseQueryHelper.readUpdate(idPendidikan,nama)
        if(listPendidikan.isEmpty()){
            databaseQueryHelper.updateDelete(idPendidikan, nama,des)
            Toast.makeText(context, EDIT_DATA_BERHASIL, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, DATA_SUDAH_ADA, Toast.LENGTH_SHORT).show()
        }
    }

    fun tutup(){
        getActivity()?.getFragmentManager()?.popBackStack();
        getActivity()!!.getSupportFragmentManager().beginTransaction().remove(this).commit()

    }

//    fun pindahFragment(){
//        val fragment = JenjangPendidikanFragment()
//        //fragment.arguments = null
//        val fragmentManager = getActivity()!!.getSupportFragmentManager()
//        val fragmentTransaction = fragmentManager.beginTransaction()
//        fragmentTransaction.replace(this.id, fragment)
//        fragmentTransaction.addToBackStack(null)
//        fragmentTransaction.commit()
//    }

    fun batal(view:View){
        val batal = view.findViewById(R.id.btnBatalPendidikan) as Button
        //Ke fragment list tipe identitas
        batal.setOnClickListener {
            tutup()
//            pindahFragment()
        }
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