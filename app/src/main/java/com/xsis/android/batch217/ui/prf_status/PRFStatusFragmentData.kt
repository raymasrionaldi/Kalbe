package com.xsis.android.batch217.ui.prf_status

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
import com.xsis.android.batch217.adapters.ListPRFStatusAdapter
import com.xsis.android.batch217.adapters.ListProjectCreateAdapter
import com.xsis.android.batch217.adapters.fragments.PRFStatusFragmentAdapter
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.PRFStatusQueryHelper
import com.xsis.android.batch217.models.PRFStatus
import com.xsis.android.batch217.models.ProjectCreate

class PRFStatusFragmentData(context: Context, val fm:FragmentManager) : Fragment() {
    val databaseHelper = DatabaseHelper(context)
    val databaseQueryHelper = PRFStatusQueryHelper(databaseHelper)
    var recycler:RecyclerView? = null
    var addButton:FloatingActionButton? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val customView = inflater.inflate(R.layout.fragment_data_prf_status, container, false)
        recycler = customView.findViewById(R.id.listPRFStatusRecycler)
        addButton = customView.findViewById(R.id.buttonAddPRFStatus)

        setHasOptionsMenu(true)

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recycler!!.layoutManager = layoutManager
        val dividerItemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        recycler!!.addItemDecoration(dividerItemDecoration)

        addButton!!.setOnClickListener { moveToForm() }

        return customView
    }

    fun moveToForm(){
        val fragment = fm.fragments[1] as PRFStatusFragmentForm
        val viewPager = fragment.view!!.parent as ViewPager
        val adapter = viewPager.adapter as PRFStatusFragmentAdapter
        fragment.setIDValue(0)
        adapter.notifyDataSetChanged()
        viewPager.setCurrentItem(1, true)
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

    fun search(keyword: String, databaseQueryHelper: PRFStatusQueryHelper) {
        val list = databaseQueryHelper.searchPRFStatus(keyword)
        tampilkanListPRFStatus(list, recycler!!)
    }

    fun tampilkanListPRFStatus(listPRFStatus: List<PRFStatus>, recyclerView: RecyclerView) {
        val adapter = ListPRFStatusAdapter(context!!, listPRFStatus, fm)
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
    }
}