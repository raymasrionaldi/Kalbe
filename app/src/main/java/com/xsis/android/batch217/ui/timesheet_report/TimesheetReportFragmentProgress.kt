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
import com.xsis.android.batch217.adapters.ListTimesheetAdapter
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.TimesheetQueryHelper
import com.xsis.android.batch217.models.Timesheet

class TimesheetReportFragmentProgress(context: Context, val fm: FragmentManager): Fragment() {
    var recyclerView: RecyclerView? = null
    var databaseQueryHelper: TimesheetQueryHelper? = null
    var idData: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val customView = inflater.inflate(
            R.layout.fragment_data_progress_timesheet,
            container,
            false
        )

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)


        recyclerView = customView.findViewById(R.id.listReportTimesheetRecyclerProgress) as RecyclerView
        recyclerView!!.layoutManager = layoutManager

        val dividerItemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        recyclerView!!.addItemDecoration(dividerItemDecoration)

        val databaseHelper = DatabaseHelper(context!!)
        databaseQueryHelper = TimesheetQueryHelper(databaseHelper)

//        getDetailProgressTimesheet()

        return customView
    }


    fun getDetailProgressTimesheet(){

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

    fun detail(timesheet: Timesheet){
        idData = timesheet.id_timesheet
        val listTimesheet= databaseQueryHelper!!.readDetailProgressTimesheetById(idData)
        //val listCompany = databaseQueryHelper.cariCompanyModels(keyword)
        tampilkanListTimesheet(listTimesheet,recyclerView!!)
    }

}