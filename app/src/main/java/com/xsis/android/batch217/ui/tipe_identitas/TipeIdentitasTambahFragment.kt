package com.xsis.android.batch217.ui.tipe_identitas

import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.ListTipeIdentitasAdapter
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.PendidikanQueryHelper
import com.xsis.android.batch217.databases.TipeIdentitasQueryHelper
import com.xsis.android.batch217.models.TipeIdentitas
import com.xsis.android.batch217.utils.*

class TipeIdentitasTambahFragment:Fragment() {
    var judul:TextView? = null
    var nama:TextInputEditText? = null
    var des:TextInputEditText? = null
    var simpan:Button? = null
    var batal:Button? = null
    var clearNama:Button? = null
    var clearDes:Button? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_tipe_identitas_tambah, container, false)

        judul = root.findViewById(R.id.judulFormTipeIdentitas) as TextView
        nama = root.findViewById(R.id.tipeTipeIdentitas) as TextInputEditText
        des = root.findViewById(R.id.deskripsiTipeIdentitas) as TextInputEditText
        simpan = root.findViewById(R.id.tipeidentitasSimpan) as Button
        batal = root.findViewById(R.id.tipeidentitasBatal) as Button
        clearNama = root.findViewById(R.id.clearTipeIdentitas) as Button
        clearDes = root.findViewById(R.id.clearDeskripsi) as Button

        if (arguments != null){
            if (arguments!!.getString("judul") != null){
                judul!!.text = arguments!!.getString("judul")
                nama!!.setText(arguments!!.getString("nama"))
                des!!.setText(arguments!!.getString("des"))
                clearNama!!.isVisible = true
                clearDes!!.isVisible = true
                simpan!!.isEnabled = true
            }
        }
        hasOptionsMenu()

        cekIsi(root)
        hapus(root)
        simpan(root)
        batal(root)

        return root

    }

    override fun onStart() {
        super.onStart()
        // where mText is the title you want on your toolbar/actionBar
        activity!!.setTitle("Tipe Identitas")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback = object : OnBackPressedCallback(
            true // default to enabled
        ) {
            override fun handleOnBackPressed() {
                tutup()
//                pindahFragment()
//                tutup()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            this, // LifecycleOwner
            callback
        )
    }

    fun tutup(){
        getActivity()!!.getSupportFragmentManager().beginTransaction().remove(this).commit()
//        count == 0
    }

    fun simpan(view:View){
        val simpan = view.findViewById(R.id.tipeidentitasSimpan) as Button
        val tipeIdentitas_text = view.findViewById(R.id.tipeTipeIdentitas) as TextInputEditText
        val tipeIdentitas_des = view.findViewById(R.id.deskripsiTipeIdentitas) as TextInputEditText

        //read
        val databaseHelper = DatabaseHelper(context!!)
        val databaseQueryHelper = TipeIdentitasQueryHelper(databaseHelper)

        simpan.setOnClickListener {
            val nama = tipeIdentitas_text.text.toString().trim()
            val des = tipeIdentitas_des.text.toString().trim()
            var pindah = false

            if (nama.isEmpty()){
                Toast.makeText(context,"Tipe identitas tidak boleh kosong !", Toast.LENGTH_SHORT).show()
            } else{
                if (arguments!!.getString("judul") == null){
                    //Simpan ke database
                    insertKeTabelTipeIdentitas(view)
                    pindah = true
                }else{
                    //Update
                    val ada = databaseQueryHelper.updateTipeIdentitas(nama, des, arguments!!.getInt("id"))
                    if (ada){
                        Toast.makeText(context, DATA_SUDAH_ADA, Toast.LENGTH_SHORT).show()
                    } else{
                        pindah = true
                    }
                }


                if (pindah){
                    //Ke activity list tipe identitas (list sudah terbarui)
                    tutup()
//                    pindahFragment()
                }
            }
        }
    }

    fun insertKeTabelTipeIdentitas(view:View){
        val tipeIdentitas_text = view.findViewById(R.id.tipeTipeIdentitas) as TextInputEditText
        val tipeIdentitas_deskripsi = view.findViewById(R.id.deskripsiTipeIdentitas) as TextInputEditText

        val nama = tipeIdentitas_text.text.toString().trim()
        val des = tipeIdentitas_deskripsi.text.toString().trim()

        //read
        val databaseHelper = DatabaseHelper(context!!)
        val databaseQueryHelper = TipeIdentitasQueryHelper(databaseHelper)
        val listTipeIdentitas = databaseQueryHelper.readNamaTipeIdentitas(nama)

        if (listTipeIdentitas.size == 0){
            //Dengan cara content values
            val content = ContentValues()
            content.put(NAMA_IDENTITAS, nama)
            content.put(DES_IDENTITAS, des)
            content.put(IS_DELETED, "false")

            //Insert
            val db = databaseHelper.writableDatabase
            db.insert(TABEL_TIPE_IDENTITAS, null, content)

        } else{
            if (listTipeIdentitas[0].is_deleted == "true"){
                val ada = databaseQueryHelper.updateTipeIdentitas(nama, des, listTipeIdentitas[0].id_TipeIdentitas)
                if (ada){
                    Toast.makeText(context, DATA_SUDAH_ADA, Toast.LENGTH_SHORT).show()
                }
            } else{
                Toast.makeText(context, DATA_SUDAH_ADA, Toast.LENGTH_SHORT).show()
            }
        }

    }

    fun batal(view:View){
        val batal = view.findViewById(R.id.tipeidentitasBatal) as Button
        //Ke fragment list tipe identitas
        batal.setOnClickListener {
            tutup()
//            pindahFragment()
////            tutup()
        }
    }

    fun pindahFragment(){//Untuk simpan
        val bundle = Bundle()
        bundle.putString("dariTambah", "iya")
        val fragment = TipeIdentitasFragment()
        fragment.arguments = bundle
        val fragmentManager = getActivity()!!.getSupportFragmentManager()
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.remove(fragment).commit()
//        fragmentTransaction.add(fragment, null).commit()
//        fragmentTransaction.replace(this.id, fragment)
//        fragmentTransaction
    }

    fun pindahFragment_batal(){
        val fragment = TipeIdentitasFragment()
        fragment.arguments = null
        val fragmentManager = getActivity()!!.getSupportFragmentManager()
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.detach(fragment).attach(fragment)
        fragmentTransaction.commit()
    }

    fun cekIsi(view:View){
        val tipeIdentitas_text = view.findViewById(R.id.tipeTipeIdentitas) as TextInputEditText
        val tipeIdentitas_deskripsi = view.findViewById(R.id.deskripsiTipeIdentitas) as TextInputEditText
        val tipeIdentitas_simpan = view.findViewById(R.id.tipeidentitasSimpan) as Button
        val tipeIdentitas_error = view.findViewById(R.id.errorTipeIdentitas) as TextView
        val clearDes = view.findViewById(R.id.clearDeskripsi) as Button
        val clearTipe = view.findViewById(R.id.clearTipeIdentitas) as Button

        tipeIdentitas_text.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //Enable tombol simpan ketika user sudah mulai mengisi form
                tipeIdentitas_simpan.isEnabled = true

                //Tipe identitas tidak boleh kosong
                val tipeIdentitas = tipeIdentitas_text.text.toString().trim()
                tipeIdentitas_error.isVisible = tipeIdentitas.isEmpty()

                //Tombol clear
                clearTipe.isVisible = !tipeIdentitas.isEmpty()
            }
        })
        tipeIdentitas_deskripsi.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //Enable tombol simpan ketika user sudah mulai mengisi form
                tipeIdentitas_simpan.isEnabled = true

                //Tombol clear
                val des = tipeIdentitas_deskripsi.text.toString().trim()
                clearDes.isVisible = !des.isEmpty()
            }
        })
    }

    fun hapus(view:View) {
        val tipeIdentitas_text = view.findViewById(R.id.tipeTipeIdentitas) as TextInputEditText
        val tipeIdentitas_deskripsi =
            view.findViewById(R.id.deskripsiTipeIdentitas) as TextInputEditText
        val clearDes = view.findViewById(R.id.clearDeskripsi) as Button
        val clearTipe = view.findViewById(R.id.clearTipeIdentitas) as Button
        clearDes.setOnClickListener { tipeIdentitas_deskripsi.setText("") }
        clearTipe.setOnClickListener { tipeIdentitas_text.setText("") }
    }
}