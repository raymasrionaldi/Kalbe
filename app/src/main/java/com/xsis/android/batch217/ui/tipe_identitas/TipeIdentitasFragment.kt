package com.xsis.android.batch217.ui.tipe_identitas

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.ListTipeIdentitasAdapter
import com.xsis.android.batch217.databases.AgamaQueryHelper
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.TipeIdentitasQueryHelper
import com.xsis.android.batch217.models.TipeIdentitas
import kotlinx.android.synthetic.main.fragment_tipe_identitas.*
import kotlinx.android.synthetic.main.fragment_tipe_identitas.view.*

class TipeIdentitasFragment:Fragment() {
    private lateinit var tipeIdentitasViewModel:TipeIdentitasViewModel
    private var recyclerView: RecyclerView?=null
    var databaseHelper :DatabaseHelper?=null
    var databaseQueryHelper: TipeIdentitasQueryHelper?= null

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

        setHasOptionsMenu(true)

        root.fab.setOnClickListener{view->
            pindahFragment()
        }

        databaseHelper = DatabaseHelper(context!!)
        databaseQueryHelper = TipeIdentitasQueryHelper(databaseHelper!!)

        getSemuaTipeIdentitas(recyclerView, databaseQueryHelper!!)

        return root
    }
    fun getSemuaTipeIdentitas(recyclerView: RecyclerView, queryHelper:TipeIdentitasQueryHelper){
        val listTipeIdentitas = queryHelper.readSemuaTipeIdentitasModels()
        tampilkanListTipeIdentitas(listTipeIdentitas, recyclerView)
    }

    fun tampilkanListTipeIdentitas(listTipeIdentitas:List<TipeIdentitas>,recyclerView: RecyclerView){
        val adapter = ListTipeIdentitasAdapter(context!!, listTipeIdentitas)
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    fun pindahFragment(){
        val fragment = TipeIdentitasTambahFragment()
        val fragmentManager = getActivity()!!.getSupportFragmentManager()
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(this.id, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main, menu)

        val myActionMenuItem = menu.findItem(R.id.action_search)
        val searchView = myActionMenuItem.actionView as SearchView

        searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                // collapse the view ?
                //menu.findItem(R.id.menu_search).collapseActionView();
                Log.e("Fragment queryText", query)
                return false
            }
            override fun onQueryTextChange(keyword: String): Boolean {
                // search goes here !!
                // listAdapter.getFilter().filter(query);
                // Log.e("Fragment queryText", keyword)
                search(keyword,TipeIdentitasQueryHelper(DatabaseHelper(context!!)))

                /*TODO
                1. do search based on active fragment table
                2. send list result to active fragment
                3.
                */
                return true
            }
        })
    }

    fun search(keyword:String,databaseQueryHelper: TipeIdentitasQueryHelper){
        val listTipeIdentitas= databaseQueryHelper.cariTipeIdentitasModels(keyword)
        tampilkanListTipeIdentitas(listTipeIdentitas,recyclerView!!)
    }
}