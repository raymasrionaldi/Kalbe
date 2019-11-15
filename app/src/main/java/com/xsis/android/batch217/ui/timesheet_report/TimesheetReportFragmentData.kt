package com.xsis.android.batch217.ui.timesheet_report

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
import com.xsis.android.batch217.adapters.ListReportTimesheetDataAdapter
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.TimesheetQueryHelper
import com.xsis.android.batch217.models.Timesheet

class TimesheetReportFragmentData(context: Context, val fm: FragmentManager): Fragment() {
    var recyclerView: RecyclerView? = null
    var databaseQueryHelper: TimesheetQueryHelper? = null
    var SEARCH_KEYWORD :String =""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val customView = inflater.inflate(
            R.layout.fragment_data_report_timesheet,
            container,
            false
        )

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        setHasOptionsMenu(true)

        recyclerView = customView.findViewById(R.id.listReportTimesheetRecycler) as RecyclerView
        recyclerView!!.layoutManager = layoutManager

        val dividerItemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        recyclerView!!.addItemDecoration(dividerItemDecoration)

        val databaseHelper = DatabaseHelper(context!!)
        databaseQueryHelper = TimesheetQueryHelper(databaseHelper)

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

    fun search(keyword:String,databaseQueryHelper: TimesheetQueryHelper){
        val listTimesheet= databaseQueryHelper.cariTimesheetModels(keyword)
        //val listCompany = databaseQueryHelper.cariCompanyModels(keyword)
        tampilkanListTimesheet(listTimesheet,recyclerView!!)
    }

    fun tampilkanListTimesheet(
        listTimesheet: List<Timesheet>,
        recyclerView: RecyclerView
    ) {
        context?.let{
            val adapterTimesheet = ListReportTimesheetDataAdapter(context!!, listTimesheet, fm)
            recyclerView.adapter = adapterTimesheet
            adapterTimesheet.notifyDataSetChanged()
        }
    }

}