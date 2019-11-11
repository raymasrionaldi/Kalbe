package com.xsis.android.batch217.ui.leave_request

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
import com.xsis.android.batch217.adapters.fragments.ListLeaveRequestAdapter
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.LeaveRequestQueryHelper
import com.xsis.android.batch217.databases.PositionLevelQueryHelper
import com.xsis.android.batch217.models.LeaveRequest

class LeaveRequestFragmentHistory(context: Context, val fm: FragmentManager):Fragment() {
    var recyclerView: RecyclerView? = null
    var databaseQueryHelper: LeaveRequestQueryHelper? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val customView = inflater.inflate(
            R.layout.fragment_history_leave_request,
            container,
            false
        )

        setHasOptionsMenu(true)

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val databaseHelper = DatabaseHelper(context!!)
        databaseQueryHelper = LeaveRequestQueryHelper(databaseHelper)

        recyclerView = customView.findViewById(R.id.listLeaveRequestRecycler) as RecyclerView
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
                search(keyword, databaseQueryHelper!!)
                return true
            }
        })
    }

    fun search(keyword: String, databaseQueryHelper: LeaveRequestQueryHelper) {
        val listLeaveRequest = databaseQueryHelper.cariLeaveRequestModels(keyword)
        tampilkanListLeaveRequest(listLeaveRequest, recyclerView!!)
    }

    fun tampilkanListLeaveRequest(
        listLeaveRequest: List<LeaveRequest>,
        recyclerView: RecyclerView
    ) {
        context?.let {
            val adapterLeaveRequest = ListLeaveRequestAdapter(context!!, listLeaveRequest, fm)
            recyclerView.adapter = adapterLeaveRequest
            adapterLeaveRequest.notifyDataSetChanged()
        }
    }
}