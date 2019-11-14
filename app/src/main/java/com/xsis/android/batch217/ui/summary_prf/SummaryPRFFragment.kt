package com.xsis.android.batch217.ui.summary_prf

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.ListSummaryPRFAdapter
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.PRFRequestQueryHelper
import com.xsis.android.batch217.models.PRFRequest

class SummaryPRFFragment : Fragment() {
    private lateinit var summaryPRFViewModel: SummaryPrfViewModel
    private var recyclerView: RecyclerView? = null
    var databaseQueryHelper: PRFRequestQueryHelper? = null
    var databaseHelper: DatabaseHelper? = null
    private var searchView: SearchView? = null
    private var queryTextListener: SearchView.OnQueryTextListener? = null
    val fragment = this

    private lateinit var viewModel: SummaryPrfViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity!!.title = getString(R.string.summary_prf)

        //ambil KeahlianViewModel untuk fragment_keahlian
        summaryPRFViewModel = ViewModelProviders.of(this).get(SummaryPrfViewModel::class.java)
        //buat inflater
        val root = inflater.inflate(R.layout.summary_prf_fragment, container, false)
        //buat layout manager
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        //set variabel untuk recycler view
        recyclerView= root.findViewById(R.id.listSummaryPRFRecycler) as RecyclerView
        //isi recycler view
        recyclerView!!.layoutManager = layoutManager
        setHasOptionsMenu(true)
        databaseHelper = DatabaseHelper(context!!)
        databaseQueryHelper = PRFRequestQueryHelper(databaseHelper!!)
        return root
    }

    fun search(keyword:String,databaseQueryHelper: PRFRequestQueryHelper){
        val listPRFRequest= databaseQueryHelper.cariPRFRequestModels(keyword)
        tampilkanListKeahlian(listPRFRequest,recyclerView!!)
    }

    fun getSemuaPRFRequest(recyclerView: RecyclerView, databaseQueryHelper:PRFRequestQueryHelper){
        val listPRFRequest = databaseQueryHelper.readSemuaPRFRequestModels()
        tampilkanListKeahlian(listPRFRequest,recyclerView)
    }

    fun tampilkanListKeahlian(listPRFRequest:List<PRFRequest>, recyclerView: RecyclerView){
        val adapterPRFRequest = ListSummaryPRFAdapter(context, fragment,  listPRFRequest)
        recyclerView.adapter = adapterPRFRequest
        adapterPRFRequest.notifyDataSetChanged()
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
