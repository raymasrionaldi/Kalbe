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
import com.xsis.android.batch217.adapters.ListPRFRequestAdapter
import com.xsis.android.batch217.adapters.fragments.RequestHistoryFragmentAdapter
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.PRFRequestQueryHelper
import com.xsis.android.batch217.models.PRFRequest

class FragmentDataRequestHistory(context: Context, val fm: FragmentManager) : Fragment() {
    var recyclerView: RecyclerView? = null
    var databaseQueryHelper: PRFRequestQueryHelper? = null
    var fragment = this

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val customView = inflater.inflate(
            R.layout.fragment_data_request_history,
            container,
            false
        )
        setHasOptionsMenu(true)

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        recyclerView = customView.findViewById(R.id.listRequestHistoryRecycler) as RecyclerView
        recyclerView!!.layoutManager = layoutManager

        val dividerItemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        recyclerView!!.addItemDecoration(dividerItemDecoration)

        val buttonAdd =
            customView.findViewById(R.id.buttonAddRequestHistory) as FloatingActionButton
        buttonAdd.setOnClickListener {

        }

        val databaseHelper = DatabaseHelper(context!!)
        databaseQueryHelper = PRFRequestQueryHelper(databaseHelper)

        //getSemuaRequestHistory(recyclerView!!, databaseQueryHelper!!)

        return customView
    }

    fun getSemuaRequestHistory(
        recyclerView: RecyclerView,
        databaseQueryHelper: PRFRequestQueryHelper
    ) {
        val listPRFRequest = databaseQueryHelper.readSemuaPRFRequestModels()
        tampilkanListRequestHistory(listPRFRequest, recyclerView)
    }

    fun tampilkanListRequestHistory(
        listEmployeeStatus: List<PRFRequest>,
        recyclerView: RecyclerView
    ) {
        val adapterEmployeeStatus = ListPRFRequestAdapter(context!!,fragment, listEmployeeStatus,fm)
        recyclerView.adapter = adapterEmployeeStatus
        adapterEmployeeStatus.notifyDataSetChanged()
    }

    fun updateContent() {
        //getSemuaEmployeeStatus(recyclerView!!, databaseQueryHelper!!)
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

    fun search(keyword: String, databaseQueryHelper: PRFRequestQueryHelper) {
        val listPRFRequest = databaseQueryHelper.cariPRFRequestModels(keyword)
        tampilkanListRequestHistory(listPRFRequest, recyclerView!!)
    }

    override fun onResume() {
        super.onResume()
        refreshList()
    }

    fun refreshList() {
        getActivity()!!.invalidateOptionsMenu()
    }
}