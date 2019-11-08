package com.xsis.android.batch217.ui.agama

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.ListAgamaAdapter
import com.xsis.android.batch217.adapters.fragments.AgamaFragmentAdapter
import com.xsis.android.batch217.databases.AgamaQueryHelper
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.models.Agama
import androidx.appcompat.widget.SearchView

class AgamaFragmentData(context: Context, val fm: FragmentManager) : Fragment() {
    var recyclerView: RecyclerView? = null
    var databaseQueryHelper: AgamaQueryHelper? = null
    var SEARCH_KEYWORD :String =""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val customView = inflater.inflate(
            R.layout.fragment_data_agama,
            container,
            false
        )

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        setHasOptionsMenu(true)

        recyclerView = customView.findViewById(R.id.listAgamaRecycler) as RecyclerView
        recyclerView!!.layoutManager = layoutManager

        val dividerItemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        recyclerView!!.addItemDecoration(dividerItemDecoration)

        val buttonAdd =
            customView.findViewById(R.id.buttonTambahAgama) as FloatingActionButton
        buttonAdd.setOnClickListener {
            addData()
        }

        val databaseHelper = DatabaseHelper(context!!)
        databaseQueryHelper = AgamaQueryHelper(databaseHelper)


        return customView
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main, menu)

        val myActionMenuItem = menu.findItem(R.id.action_search)
        val searchView = myActionMenuItem.actionView as SearchView

        searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }
            override fun onQueryTextChange(keyword: String): Boolean {
                SEARCH_KEYWORD=keyword
                search(keyword,databaseQueryHelper!!)

                return true
            }
        })
    }

    fun search(keyword:String,databaseQueryHelper: AgamaQueryHelper){
        val listAgama= databaseQueryHelper.cariAgamaModels(keyword)
        //val listCompany = databaseQueryHelper.cariCompanyModels(keyword)
        tampilkanListAgama(listAgama,recyclerView!!)
    }


    fun addData() {
        val viewPager = view!!.parent as ViewPager
        val adapter = viewPager.adapter!! as AgamaFragmentAdapter
        val fragment = fm.fragments[1] as AgamaFragmentForm

        fragment.modeAdd()
        adapter.notifyDataSetChanged()
        viewPager.setCurrentItem(1, true)
    }

    fun getSemuaAgama(
        recyclerView: RecyclerView,
        databaseQueryHelper: AgamaQueryHelper
    ) {
        val listAgama = databaseQueryHelper.readSemuaAgamaModels()
        tampilkanListAgama(listAgama, recyclerView)
    }

    fun tampilkanListAgama(
        listAgama: List<Agama>,
        recyclerView: RecyclerView
    ) {
        context?.let{
            val adapterAgama = ListAgamaAdapter(context!!, listAgama, fm)
            recyclerView.adapter = adapterAgama
            adapterAgama.notifyDataSetChanged()
        }
    }

    fun updateContent() {
        search(SEARCH_KEYWORD,databaseQueryHelper!!)
    }
}
