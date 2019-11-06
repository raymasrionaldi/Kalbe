package com.xsis.android.batch217.ui.keluarga

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
import com.xsis.android.batch217.adapters.ListKeluargaDataAdapter
import com.xsis.android.batch217.adapters.fragments.CompanyFragmentAdapter
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.KeluargaQueryHelper
import com.xsis.android.batch217.models.KeluargaData
import com.xsis.android.batch217.ui.company.CompanyFragmentForm

class KeluargaFragmentData(context: Context, val fm: FragmentManager) : Fragment() {
    var recyclerView: RecyclerView? = null
    var databaseQueryHelper: KeluargaQueryHelper? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val customView = inflater.inflate(R.layout.fragment_data_keluarga,container,false)
        setHasOptionsMenu(true)

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        recyclerView = customView.findViewById(R.id.listKeluargaRecycler) as RecyclerView
        recyclerView!!.layoutManager = layoutManager

        val dividerItemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        recyclerView!!.addItemDecoration(dividerItemDecoration)

        val buttonAdd =customView.findViewById(R.id.buttonAddKeluarga) as FloatingActionButton
        buttonAdd.setOnClickListener {
            addData()
        }

        val databaseHelper = DatabaseHelper(context!!)
        databaseQueryHelper = KeluargaQueryHelper(databaseHelper)

        return customView
    }

    fun addData() {
        val viewPager = view!!.parent as ViewPager
        val adapter = viewPager.adapter!! as CompanyFragmentAdapter
        val fragment = fm.fragments[1] as CompanyFragmentForm
        fragment.modeAdd()
        adapter.notifyDataSetChanged()
        viewPager.setCurrentItem(1, true)
    }

    fun tampilkanListKeluargaData(
        listKeluargaData: List<KeluargaData>,
        recyclerView: RecyclerView
    ) {
        val adapterKeluargaData = ListKeluargaDataAdapter(context!!, listKeluargaData, fm)
        recyclerView.adapter = adapterKeluargaData
        adapterKeluargaData.notifyDataSetChanged()
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

    fun search(keyword: String, databaseQueryHelper: KeluargaQueryHelper) {
        val listKeluargaData = databaseQueryHelper.cariKeluargaData(keyword)
        tampilkanListKeluargaData(listKeluargaData, recyclerView!!)
    }
}