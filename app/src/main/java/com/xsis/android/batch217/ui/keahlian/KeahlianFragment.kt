package com.xsis.android.batch217.ui.keahlian

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.ListKeahlianAdapter
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.KeahlianQueryHelper
import com.xsis.android.batch217.models.Keahlian
import kotlinx.android.synthetic.main.fragment_keahlian.view.*

class KeahlianFragment : Fragment() {
    //membawakan view model
    private lateinit var keahlianViewModel: KeahlianViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //ambil KeahlianViewModel untuk fragment_keahlian
        keahlianViewModel = ViewModelProviders.of(this).get(KeahlianViewModel::class.java)
        //buat inflater
        val root = inflater.inflate(R.layout.fragment_keahlian, container, false)
        //buat layout manager
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        //set variabel untuk recycler view
        val recyclerView= root.findViewById(R.id.listKeahlianRecycler) as RecyclerView
        //panggil database
        val databaseHelper = DatabaseHelper(activity!!)
        //panggil query helper
        val databaseQueryHelper = KeahlianQueryHelper(databaseHelper)

        //panggil isi tabel keahlian
        getSemuaKeahlian(recyclerView, databaseQueryHelper)
        //isi recycler view
        recyclerView.layoutManager=layoutManager

        //aksi ketika klik floating button
        root.fab_keahlian.setOnClickListener {
            //pindah ke activity input data
            val intent = Intent(context, InputDataKeahlianActivity::class.java)
            startActivity(intent)
        }

        return root
    }

    fun getSemuaKeahlian(recyclerView: RecyclerView, databaseQueryHelper:KeahlianQueryHelper){
        val listKeahlian= databaseQueryHelper.readSemuaKeahlianModels()
        tampilkanListKeahlian(listKeahlian,recyclerView)
    }

    fun tampilkanListKeahlian(listKeahlian:List<Keahlian>, recyclerView: RecyclerView){
        val adapterKeahlian = ListKeahlianAdapter(context,listKeahlian)
        recyclerView.adapter = adapterKeahlian
        adapterKeahlian.notifyDataSetChanged()
    }
}