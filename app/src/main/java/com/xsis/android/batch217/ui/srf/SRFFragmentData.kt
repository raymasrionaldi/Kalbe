package com.xsis.android.batch217.ui.srf

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
import com.xsis.android.batch217.adapters.ListSRFAdapter
import com.xsis.android.batch217.adapters.fragments.SRFFragmentAdapter
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.SRFQueryHelper
import com.xsis.android.batch217.models.SRF

class SRFFragmentData (context: Context, val fm: FragmentManager) : Fragment() {
    var recyclerView: RecyclerView? = null
    var databaseQueryHelper: SRFQueryHelper? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val customView = inflater.inflate(
            R.layout.fragment_data_srf,
            container,
            false
        )
        setHasOptionsMenu(true)

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        recyclerView = customView.findViewById(R.id.listSRFRecycler) as RecyclerView
        recyclerView!!.layoutManager = layoutManager

        val dividerItemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        recyclerView!!.addItemDecoration(dividerItemDecoration)

        val buttonAdd = customView.findViewById(R.id.buttonAddSRF) as FloatingActionButton
        buttonAdd.setOnClickListener {
            addData()
        }

        val databaseHelper = DatabaseHelper(context!!)
        databaseQueryHelper = SRFQueryHelper(databaseHelper)

//        getSemuaCompany(recyclerView!!, databaseQueryHelper!!)

        return customView
    }

    fun addData() {
        val viewPager = view!!.parent as ViewPager
        val adapter = viewPager.adapter!! as SRFFragmentAdapter
        val fragment = fm.fragments[1] as SRFFragmentForm
        fragment.modeAdd()
        adapter.notifyDataSetChanged()
        viewPager.setCurrentItem(1, true)
    }

    fun getSemuaSRF(
        recyclerView: RecyclerView,
        databaseQueryHelper: SRFQueryHelper
    ) {
        val listSRF = databaseQueryHelper.readSemuaSRFModels()
        tampilkanListSRF(listSRF, recyclerView)
    }

    fun tampilkanListSRF(
        listCompany: List<SRF>,
        recyclerView: RecyclerView
    ) {
        val adapterSRF = ListSRFAdapter(context!!, listCompany, fm)
        recyclerView.adapter = adapterSRF
        adapterSRF.notifyDataSetChanged()
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
                search(keyword, databaseQueryHelper!!)
                return true
            }
        })
    }

    fun search(keyword: String, databaseQueryHelper: SRFQueryHelper) {
        val listSRF = databaseQueryHelper.cariSRFModels(keyword)
        tampilkanListSRF(listSRF, recyclerView!!)
    }
}
