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
import androidx.viewpager.widget.ViewPager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.ListCheckPRFAdapter
import com.xsis.android.batch217.databases.CheckPRFQueryHelper
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.models.CheckPRF

class CheckPRFData (context: Context, val fm: FragmentManager) : Fragment() {
    var fragment = this
    var recyclerView: RecyclerView? = null
    val databaseHelper = DatabaseHelper(context)
    var databaseQueryHelper = CheckPRFQueryHelper(databaseHelper)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val customView = inflater.inflate(
            R.layout.fragment_data_check_prf,
            container,
            false
        )
        setHasOptionsMenu(true)

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        recyclerView = customView.findViewById(R.id.listCheckPRFRecycler) as RecyclerView
        recyclerView!!.layoutManager = layoutManager

        val dividerItemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        recyclerView!!.addItemDecoration(dividerItemDecoration)

        //getSemuaSRF(recyclerView!!, databaseQueryHelper!!)

        return customView
    }

    fun tampilkanListCheckPRF(listPRFWin: List<CheckPRF>, recyclerView: RecyclerView) {
        val adapterPRFWin = ListCheckPRFAdapter(context!!, listPRFWin,fm)
        recyclerView.adapter = adapterPRFWin
        adapterPRFWin.notifyDataSetChanged()
    }

    fun updateContent() {
//        getSemuaCompany(recyclerView!!, databaseQueryHelper!!)
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
                return true
            }
        })
    }

    fun search(keyword: String, databaseQueryHelper: CheckPRFQueryHelper) {
        val listSRF = databaseQueryHelper.cariCheckPRFModels(keyword)
        tampilkanListCheckPRF(listSRF, recyclerView!!)
    }
}