package com.xsis.android.batch217.ui.keahlian

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.ListKeahlianAdapter
import com.xsis.android.batch217.databases.CadanganDbHelper
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.KeahlianQueryHelper
import com.xsis.android.batch217.models.Keahlian
import kotlinx.android.synthetic.main.fragment_keahlian.view.*

class KeahlianFragment : Fragment() {

    private lateinit var keahlianViewModel: KeahlianViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        keahlianViewModel = ViewModelProviders.of(this).get(KeahlianViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_keahlian, container, false)
        /*val textView: TextView = root.findViewById(R.id.text_keahlian)
        keahlianViewModel.text.observe(this, Observer {
            textView.text = it
        })*/
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)

        val recyclerView= root.findViewById(R.id.listKeahlianRecycler) as RecyclerView
        val databaseHelper = DatabaseHelper(activity!!)
        val databaseQueryHelper = KeahlianQueryHelper(databaseHelper)

        getSemuaKeahlian(recyclerView, databaseQueryHelper)
        recyclerView.layoutManager=layoutManager
        root.fab_keahlian.setOnClickListener { view ->
//            Toast.makeText(context,"onClick", Toast.LENGTH_LONG).show()
            val intent = Intent(context, InputDataActivity::class.java)
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