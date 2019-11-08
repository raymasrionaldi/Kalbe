package com.xsis.android.batch217.ui.keluarga

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.google.android.material.textfield.TextInputEditText
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.KeluargaFormAdapter
import com.xsis.android.batch217.adapters.fragments.KeluargaFragmentAdapter
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.KeluargaQueryHelper
import com.xsis.android.batch217.models.KeluargaDetail
import com.xsis.android.batch217.utils.ID_IDENTITAS
import com.xsis.android.batch217.utils.ID_JENIS
import kotlinx.android.synthetic.main.activity_keluarga_form.*

//WARNING : Masih untuk form ubah saja. Untuh tambah belum disetting untuk bisa menyimpan ke database (harus atur id).
class KeluargaFormActivity : AppCompatActivity() {
    val context = this
    val databaseHelper = DatabaseHelper(context)
    val databaseQueryHelper = KeluargaQueryHelper(databaseHelper)

    var id = 0
    var judul:TextView? = null
    var jenisKeluarga:TextInputEditText? = null
    var errorJenis:TextView? = null
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
        errorJenis = findViewById(R.id.errorJenisKeluarga)
        tambah = findViewById(R.id.tambahHubunganKeluarga)
        anggotaRecycler = findViewById(R.id.anggotaKeluarga)
        batal = findViewById(R.id.keluargaBatal)
        simpan = findViewById(R.id.keluargaSimpan)

        val bundle: Bundle? = intent.extras
        bundle?.let {
            judul!!.text = "Ubah Jenis Keluarga"

            id = bundle.getInt(ID_JENIS)
            listKeluargaDetail = databaseQueryHelper.readSemuaKeluargaDetail(id)
            val teks_jenisKeluarga = databaseQueryHelper.readJenisKeluarga(id)

            jenisKeluarga!!.setText(teks_jenisKeluarga)
            simpan!!.isEnabled = true
        }

        listKeluargaDetail.forEach {
            listAnggota.add(it.anggotaKeluarga) }
        println("LIST ANGGOTA $listAnggota")

        if (listAnggota.size == 0){
            listAnggota.add("")
        }

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        anggotaRecycler!!.layoutManager = layoutManager
        anggotaRecycler!!.adapter = KeluargaFormAdapter(id, context, listAnggota)

        tambahAnggota(listAnggota, anggotaRecycler!!)
        simpan(listAnggota, id)
        batal()
        cekIsiJenis()
        backKeluargaForm.setOnClickListener { finish() }
    }


    fun tambahAnggota(listAnggota:ArrayList<String>, anggotaRecycler:RecyclerView){
        tambah!!.setOnClickListener {
            if (!listAnggota[listAnggota.size-1].isEmpty()){
                listAnggota.add("")
                anggotaRecycler.adapter = KeluargaFormAdapter(id, context, listAnggota)
            }
        }
    }

    fun simpan(listAnggota:ArrayList<String>, id:Int){
        simpan!!.setOnClickListener {
            val Jenis = jenisKeluarga!!.text.toString().trim()

            for (i in 0 until listAnggota.size){
                if (listAnggota[i].trim().isEmpty()){
                    listAnggota.removeAt(i)
                }
            }

            if (Jenis.isEmpty()){
                Toast.makeText(context, "Jenis hubungan keluarga wajib diisi !", Toast.LENGTH_SHORT).show()
                errorJenis!!.isVisible = !Jenis.isEmpty()

                if (listAnggota.size == 0){ listAnggota.add("") }
            } else{

                if (databaseQueryHelper.cekKeluargaDataSudahAda(Jenis) && id == 0){
                    Toast.makeText(context, "Jenis Keluarga sudah ada ! Mohon ganti jenis keluarga.", Toast.LENGTH_SHORT).show()
                    if (listAnggota.size == 0){ listAnggota.add("") }
                } else{
                    if (listAnggota.size == 0){
                        Toast.makeText(context, "Input minimal 1 hubungan keluarga !", Toast.LENGTH_SHORT).show()
                        listAnggota.add("")
                    } else{
                        if (id != 0){
                            databaseQueryHelper.editKeluarga(listAnggota, id, Jenis)
                        } else{
                            databaseQueryHelper.tambahKeluargaData(Jenis, listAnggota)
                        }
                        finish()
                    }
                }
            }

        }
    }

    fun batal(){
        batal!!.setOnClickListener { finish() }
    }

    fun cekIsiJenis(){
        jenisKeluarga!!.addTextChangedListener(object :TextWatcher{

            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val Jenis = jenisKeluarga!!.text.toString().trim()
                errorJenis!!.isVisible = Jenis.isEmpty()
                simpan!!.isEnabled = true
            }

        })
    }
}
