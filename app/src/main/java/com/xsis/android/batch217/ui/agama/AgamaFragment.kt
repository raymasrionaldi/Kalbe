package com.xsis.android.batch217.ui.agama

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xsis.android.batch217.R

import com.xsis.android.batch217.adapters.ListAgamaAdapter
import com.xsis.android.batch217.databases.AgamaQueryHelper
import com.xsis.android.batch217.databases.CadanganDbHelper
import com.xsis.android.batch217.models.Agama
import kotlinx.android.synthetic.main.fragment_agama.view.*


class AgamaFragment : Fragment() {

    private lateinit var agamaViewModel: AgamaViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        agamaViewModel = ViewModelProviders.of(this).get(AgamaViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_agama, container, false)
        //        val textView: TextView = root.findViewById(R.id.text_agama)
        //        agamaViewModel.text.observe(this, Observer {
        //            textView.text = it
        //        })

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        //listAgamaRecycler.layoutManager=layoutManager

        val recyclerView= root.findViewById(R.id.listAgamaRecycler) as RecyclerView
        recyclerView.layoutManager=layoutManager

        root.fab.setOnClickListener { view ->
            Toast.makeText(context,"onClick", Toast.LENGTH_LONG).show()
        }

        val databaseHelper = CadanganDbHelper(activity!!)
        val databaseQueryHelper = AgamaQueryHelper(databaseHelper)

        getSemuaAgama(recyclerView, databaseQueryHelper)

        return root
    }

    fun getSemuaAgama(recyclerView: RecyclerView, databaseQueryHelper:AgamaQueryHelper){
        val listAgama= databaseQueryHelper.readSemuaAgamaModels()
        tampilkanListAgama(listAgama,recyclerView)
    }

    fun tampilkanListAgama(listAgama:List<Agama>,recyclerView: RecyclerView){
        val adapterAgama = ListAgamaAdapter(context,listAgama)
        recyclerView.adapter = adapterAgama
        adapterAgama.notifyDataSetChanged()
    }
}