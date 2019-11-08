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

    fun getSemuaTipeIdentitas(recyclerView: RecyclerView, queryHelper:TipeIdentitasQueryHelper){
        val listTipeIdentitas = queryHelper.readSemuaTipeIdentitasModels()
        tampilkanListTipeIdentitas(listTipeIdentitas, recyclerView)
    }

    fun tampilkanListTipeIdentitas(listTipeIdentitas:List<TipeIdentitas>,recyclerView: RecyclerView){
        val adapter = ListTipeIdentitasAdapter(context!!, this, listTipeIdentitas)
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    fun pindahFragment(){
//        val bundle = Bundle()
//        bundle.putString("judul", null)
//
//        val fragment = TipeIdentitasTambahFragment()
//        fragment.arguments = bundle
//        val fragmentManager = getActivity()!!.getSupportFragmentManager()
//        val fragmentTransaction = fragmentManager.beginTransaction()
////        fragmentTransaction.remove(TipeIdentitasFragment())
////        fragmentTransaction.show(fragment).commit()
//        fragmentTransaction.replace(this.id, fragment)
//        fragmentTransaction.addToBackStack(null)
//        fragmentTransaction.commit()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main, menu)

        val myActionMenuItem = menu.findItem(R.id.action_search)
        var searchView = myActionMenuItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

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
                return true
            }
        })
    }

    fun search(keyword:String,databaseQueryHelper: TipeIdentitasQueryHelper){
        val listTipeIdentitas= databaseQueryHelper.cariTipeIdentitasModels(keyword)
        tampilkanListTipeIdentitas(listTipeIdentitas,recyclerView!!)
    }

    override fun onResume() {
        super.onResume()
        refreshList()
    }



}