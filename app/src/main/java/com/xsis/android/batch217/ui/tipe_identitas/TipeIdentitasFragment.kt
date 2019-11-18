package com.xsis.android.batch217.ui.tipe_identitas

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.ListTipeIdentitasAdapter
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.TipeIdentitasQueryHelper
import com.xsis.android.batch217.models.TipeIdentitas
import com.xsis.android.batch217.ui.jenjang_pendidikan.InputPendidikanActivity
import com.xsis.android.batch217.utils.CustomViewPager
import kotlinx.android.synthetic.main.fragment_tipe_identitas.view.*


class TipeIdentitasFragment:Fragment() {
    private lateinit var tipeIdentitasViewModel:TipeIdentitasViewModel
    private var recyclerView: RecyclerView?=null
    var databaseHelper :DatabaseHelper?=null
    var databaseQueryHelper: TipeIdentitasQueryHelper?= null
    val fragment = this
    var kata = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        tipeIdentitasViewModel = ViewModelProviders.of(this).get(TipeIdentitasViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_tipe_identitas, container, false)
        setHasOptionsMenu(true)

        activity!!.title = getString(R.string.menu_tipe_identitas)

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        recyclerView = root.findViewById(R.id.listTipeIdentitasRecycler) as RecyclerView
        recyclerView!!.layoutManager = layoutManager
        val dividerItemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        recyclerView!!.addItemDecoration(dividerItemDecoration)

        root.fab.setOnClickListener{view->
            val intent = Intent(context, TipeIdentitasTambahActivity::class.java)
            startActivity(intent)
        }

        databaseHelper = DatabaseHelper(context!!)
        databaseQueryHelper = TipeIdentitasQueryHelper(databaseHelper!!)

        return root
    }

    fun refreshList() {
        getActivity()!!.invalidateOptionsMenu()
    }

    fun tampilkanListTipeIdentitas(listTipeIdentitas:List<TipeIdentitas>,recyclerView: RecyclerView){
        val adapter = ListTipeIdentitasAdapter(context!!, this, listTipeIdentitas)
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main, menu)

        val myActionMenuItem = menu.findItem(R.id.action_search)
        var searchView = myActionMenuItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                Log.e("Fragment queryText", query)
                return false
            }
            override fun onQueryTextChange(keyword: String): Boolean {
                search(keyword,TipeIdentitasQueryHelper(DatabaseHelper(context!!)))
                kata = keyword
                return true
            }
        })
    }

    fun search(keyword:String,databaseQueryHelper: TipeIdentitasQueryHelper){
        val listTipeIdentitas= databaseQueryHelper.cariTipeIdentitasModels(keyword)
        tampilkanListTipeIdentitas(listTipeIdentitas,recyclerView!!)
    }

    fun search2(){
        val listTipeIdentitas= databaseQueryHelper!!.cariTipeIdentitasModels(kata)
        tampilkanListTipeIdentitas(listTipeIdentitas,recyclerView!!)
    }

    override fun onResume() {
        super.onResume()
//        refreshList()
        search2()
    }



}