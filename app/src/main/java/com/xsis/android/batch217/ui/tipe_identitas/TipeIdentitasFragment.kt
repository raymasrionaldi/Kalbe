package com.xsis.android.batch217.ui.tipe_identitas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.ListTipeIdentitasAdapter
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.TipeIdentitasQueryHelper
import kotlinx.android.synthetic.main.fragment_tipe_identitas.*
import kotlinx.android.synthetic.main.fragment_tipe_identitas.view.*

class TipeIdentitasFragment:Fragment() {
    private lateinit var tipeIdentitasViewModel:TipeIdentitasViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        tipeIdentitasViewModel = ViewModelProviders.of(this).get(TipeIdentitasViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_tipe_identitas, container, false)

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        val recyclerView = root.findViewById(R.id.listTipeIdentitasRecycler) as RecyclerView
        recyclerView.layoutManager = layoutManager

        root.fab.setOnClickListener{view->
//            Toast.makeText(context, "klik", Toast.LENGTH_SHORT).show()
            pindahFragment()
        }

        getSemuaTipeIdentitas(recyclerView, TipeIdentitasQueryHelper(DatabaseHelper(context!!)))

        return root
    }
    fun getSemuaTipeIdentitas(recyclerView: RecyclerView, queryHelper:TipeIdentitasQueryHelper){
        val listTipeIdentitas = queryHelper.readSemuaTipeIdentitasModels()

        val adapter = ListTipeIdentitasAdapter(context!!, listTipeIdentitas)
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    fun pindahFragment(){
        val fragment = TipeIdentitasTambahFragment()
        val fragmentManager = getActivity()!!.getSupportFragmentManager()
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_tipe_identitas, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()}
}