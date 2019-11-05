package com.xsis.android.batch217.ui.keahlian

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
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
import org.w3c.dom.Text

class KeahlianFragment : Fragment() {
    //membawakan view model
    private lateinit var keahlianViewModel: KeahlianViewModel
    private var recyclerView: RecyclerView? = null
    var databaseQueryHelper: KeahlianQueryHelper? = null
    var databaseHelper: DatabaseHelper? = null
    private var searchView: SearchView? = null
    private var queryTextListener: SearchView.OnQueryTextListener? = null
    var hitung = 1

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
        recyclerView= root.findViewById(R.id.listKeahlianRecycler) as RecyclerView
        //isi recycler view
        recyclerView!!.layoutManager = layoutManager
        setHasOptionsMenu(true)

        //aksi ketika klik floating button
        root.fab_keahlian.setOnClickListener {
            //pindah ke activity input data
            val intent = Intent(context, InputDataKeahlianActivity::class.java)
            startActivity(intent)
        }
        databaseHelper = DatabaseHelper(context!!)
        databaseQueryHelper = KeahlianQueryHelper(databaseHelper!!)
        //panggil isi tabel keahlian
//        getSemuaKeahlian(recyclerView!!, databaseQueryHelper!!)

        return root
    }

    fun search(keyword:String,databaseQueryHelper: KeahlianQueryHelper){
        val listKeahlian= databaseQueryHelper.cariKeahlianModels(keyword)
        tampilkanListKeahlian(listKeahlian,recyclerView!!)
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
                search(keyword,databaseQueryHelper!!)

                /*TODO
                1. do search based on active fragment table
                2. send list result to active fragment
                3.
                */
                return true
            }
        })
    }

    override fun onResume() {
        super.onResume()
        refreshList()
    }

    fun refreshList() {
        getSemuaKeahlian(recyclerView!!, databaseQueryHelper!!)
    }

}