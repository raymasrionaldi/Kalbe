package com.xsis.android.batch217.ui.jenjang_pendidikan

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.ListPendidikanAdapter
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.PendidikanQueryHelper
import com.xsis.android.batch217.models.Pendidikan
import kotlinx.android.synthetic.main.fragment_jenjang_pendidikan.view.fab

class JenjangPendidikanFragment : Fragment() {
    private lateinit var pendidikanViewModel: JenjangPendidikanViewModel
    private var recyclerView: RecyclerView?=null
    var databaseHelper :DatabaseHelper?=null
    var databaseQueryHelper: PendidikanQueryHelper?= null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        pendidikanViewModel = ViewModelProviders.of(this).get(JenjangPendidikanViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_jenjang_pendidikan, container, false)

//        if(arguments == null){
            setHasOptionsMenu(true)
//        } else {
//            setHasOptionsMenu(false)
//        }

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        recyclerView = root.findViewById(R.id.listPendidikanRecycler) as RecyclerView
        recyclerView!!.layoutManager = layoutManager


        root.fab.setOnClickListener{view->
            pindahFragment()

        }

        databaseHelper = DatabaseHelper(context!!)
        databaseQueryHelper = PendidikanQueryHelper(databaseHelper!!)

//        getSemuaPendidikan(recyclerView!!, PendidikanQueryHelper(DatabaseHelper(context!!)))

        return root
    }
    fun getSemuaPendidikan(recyclerView: RecyclerView, queryHelper:PendidikanQueryHelper){
        val listPendidikan = queryHelper.readSemuaPendidikanModels()
        tampilkanListPendidikan(listPendidikan,recyclerView)
    }

    fun pindahFragment(){
        setHasOptionsMenu(false)
        val fragment = JenjangPendidikanInputFragment()
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
                search(keyword,PendidikanQueryHelper(DatabaseHelper(context!!)))

                /*TODO
                1. do search based on active fragment table
                2. send list result to active fragment
                3.
                */
                return true
            }
        })
    }

    fun search(keyword:String,databaseQueryHelper: PendidikanQueryHelper){
        val listPendidikan= databaseQueryHelper.cariPendidikanModels(keyword)
        tampilkanListPendidikan(listPendidikan,recyclerView!!)
    }

    fun tampilkanListPendidikan(listPendidikan:List<Pendidikan>,recyclerView: RecyclerView){
        val adapter = ListPendidikanAdapter(context!!, listPendidikan)
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
    }
}