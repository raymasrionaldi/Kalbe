package com.xsis.android.batch217.ui.agama

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xsis.android.batch217.R

import com.xsis.android.batch217.adapters.ListAgamaAdapter
import com.xsis.android.batch217.databases.AgamaQueryHelper
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.models.Agama
import kotlinx.android.synthetic.main.fragment_agama.view.*
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuItemCompat.getActionView
import android.annotation.SuppressLint
import android.view.MenuInflater
import androidx.appcompat.widget.Toolbar
import com.xsis.android.batch217.HomeActivity
import java.util.zip.Inflater


class AgamaFragment : Fragment() {
    private var searchView: SearchView? = null
    private var queryTextListener: SearchView.OnQueryTextListener? = null
    private lateinit var agamaViewModel: AgamaViewModel
    private var recyclerView: RecyclerView?=null
    var databaseHelper :DatabaseHelper?=null
    var databaseQueryHelper: AgamaQueryHelper?= null

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

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        recyclerView =root.findViewById(R.id.listAgamaRecycler) as RecyclerView
        recyclerView!!.layoutManager = layoutManager

        setHasOptionsMenu(true)

        root.fab.setOnClickListener { view ->
            //Toast.makeText(context,"onClick", Toast.LENGTH_LONG).show()

            val fragment = TambahAgamaFragment() as Fragment

            getFragmentManager()!!.beginTransaction()
                .replace(R.id.nav_host_fragment, fragment)
                .commit()
        }

         databaseHelper = DatabaseHelper(context!!)
         databaseQueryHelper = AgamaQueryHelper(databaseHelper!!)

        getSemuaAgama(recyclerView!!, databaseQueryHelper!!)

        return root
    }

    fun getSemuaAgama(recyclerView: RecyclerView, databaseQueryHelper: AgamaQueryHelper) {
        val listAgama = databaseQueryHelper.readSemuaAgamaModels()
        tampilkanListAgama(listAgama, recyclerView)
    }

    fun search(keyword:String,databaseQueryHelper: AgamaQueryHelper){
        val listAgama= databaseQueryHelper.cariAgamaModels(keyword)
        tampilkanListAgama(listAgama,recyclerView!!)
    }

    fun tampilkanListAgama(listAgama: List<Agama>, recyclerView: RecyclerView) {
        val adapterAgama = ListAgamaAdapter(context, listAgama)
        recyclerView.adapter = adapterAgama
        adapterAgama.notifyDataSetChanged()
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

}