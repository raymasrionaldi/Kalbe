package com.xsis.android.batch217.ui.prf_request

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.ListPRFWinAdapter
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.PRFWinQueryHelper
import com.xsis.android.batch217.models.PRFWin

class WinPRFData(context: Context, val fm: FragmentManager):Fragment() {
    var fragment = this
    var recyclerView: RecyclerView? = null
    val databaseHelper = DatabaseHelper(context)
    var databaseQueryHelper = PRFWinQueryHelper(databaseHelper)
    var kata = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)

        val customView = inflater.inflate(R.layout.fragment_prf_win_data,container,false)
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        recyclerView = customView.findViewById(R.id.listPRFWinRecycler)
        recyclerView!!.layoutManager = layoutManager
        val dividerItemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        recyclerView!!.addItemDecoration(dividerItemDecoration)


        return customView
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main, menu)

        val myActionMenuItem = menu.findItem(R.id.action_search)
        val searchView = myActionMenuItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(keyword: String): Boolean {
                search(keyword, databaseQueryHelper)
                kata = keyword
                return true
            }
        })
    }

    fun search(keyword: String, databaseQueryHelper: PRFWinQueryHelper) {
        val listPRFWin = databaseQueryHelper.cariPRFWinModels(keyword)
        tampilkanListPRFWin(listPRFWin, recyclerView!!)
    }

    fun search2() {
        val listProject = databaseQueryHelper.cariPRFWinModels(kata)
        tampilkanListPRFWin(listProject, recyclerView!!)
    }

    override fun onResume() {
        super.onResume()
        search2()
    }

    fun tampilkanListPRFWin(listPRFWin: List<PRFWin>,recyclerView: RecyclerView) {
        val adapterPRFWin = ListPRFWinAdapter(context!!, listPRFWin,fm)
        recyclerView.adapter = adapterPRFWin
        adapterPRFWin.notifyDataSetChanged()
    }
}