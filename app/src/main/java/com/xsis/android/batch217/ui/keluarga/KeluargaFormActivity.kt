package com.xsis.android.batch217.ui.keluarga

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.KeluargaFormAdapter
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.KeluargaQueryHelper
import com.xsis.android.batch217.models.KeluargaDetail
import com.xsis.android.batch217.utils.ID_IDENTITAS
import com.xsis.android.batch217.utils.ID_JENIS

//WARNING : Masih untuk form ubah saja. Untuh tambah belum disetting untuk bisa menyimpan ke database (harus atur id).
class KeluargaFormActivity : AppCompatActivity() {
    val context = this
    val databaseHelper = DatabaseHelper(context)
    val databaseQueryHelper = KeluargaQueryHelper(databaseHelper)

    var id = 0
    var judul:TextView? = null
    var jenisKeluarga:TextInputEditText? = null
    var tambah:Button? = null
    var anggotaRecycler:RecyclerView? = null
    var batal:Button? = null
    var simpan:Button? = null
    var listKeluargaDetail:ArrayList<KeluargaDetail> = ArrayList<KeluargaDetail>()
    var listAnggota:ArrayList<String> = ArrayList<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_keluarga_form)

        judul = findViewById(R.id.judulFormKeluarga)
        jenisKeluarga = findViewById(R.id.jenisKeluarga)
        tambah = findViewById(R.id.tambahHubunganKeluarga)
        anggotaRecycler = findViewById(R.id.anggotaKeluarga)
        batal = findViewById(R.id.keluargaBatal)
        simpan = findViewById(R.id.keluargaSimpan)

        val bundle: Bundle? = intent.extras
        bundle?.let {
            judul!!.text = "Ubah Tipe Identitas"

            id = bundle.getInt(ID_JENIS)
            listKeluargaDetail = databaseQueryHelper.readSemuaKeluargaDetail(id)
            val teks_jenisKeluarga = databaseQueryHelper.readJenisKeluarga(id)

            jenisKeluarga!!.setText(teks_jenisKeluarga)
            simpan!!.isEnabled = true
        }

        listKeluargaDetail.forEach {
            listAnggota.add(it.anggotaKeluarga) }


        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        anggotaRecycler!!.layoutManager = layoutManager
        anggotaRecycler!!.adapter = KeluargaFormAdapter(listAnggota)

        tambahAnggota(listAnggota, anggotaRecycler!!)
        simpan(listAnggota, id)
        batal()
    }

    fun tambahAnggota(listAnggota:ArrayList<String>, anggotaRecycler:RecyclerView){
        tambah!!.setOnClickListener {
            listAnggota.add("")
            anggotaRecycler.adapter = KeluargaFormAdapter(listAnggota)
        }
    }

    fun simpan(listAnggota:ArrayList<String>, id:Int){
        simpan!!.setOnClickListener {

            val Jenis = jenisKeluarga!!.text.toString().trim()
            println(listAnggota)

            if (id != 0){
                databaseQueryHelper.editKeluarga(listAnggota, id, Jenis)
            } else{
                databaseQueryHelper.tambahKeluargaData(Jenis, listAnggota)
            }


            finish()
        }
    }

    fun batal(){
        batal!!.setOnClickListener { finish() }
    }
}
