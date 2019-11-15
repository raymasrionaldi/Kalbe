package com.xsis.android.batch217.ui.timesheet_report

import android.content.Context
import android.os.Bundle
import android.provider.ContactsContract
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.ListReportTimesheetProgressAdapter
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.TimesheetQueryHelper
import com.xsis.android.batch217.models.Timesheet

class TimesheetReportFragmentProgress(context: Context, val fm: FragmentManager): Fragment() {
    var recyclerView: RecyclerView? = null
    var databaseQueryHelper: TimesheetQueryHelper? = null
    var databaseHelper:DatabaseHelper? =null
    var ID:Int=0

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

        databaseHelper = DatabaseHelper(context!!)
        databaseQueryHelper = TimesheetQueryHelper(databaseHelper!!)

        println("ini fragment ke 2")
//        getSemuaTimesheetProgressDetail(1,recyclerView!!,databaseQueryHelper!!)
        return customView
    }

    fun tampilkanListTimesheet(
        listTimesheet: List<Timesheet>,
        recyclerView: RecyclerView
    ) {
        println("isContext null = ${context==null}")
        //context?.let{
            listTimesheet.forEach {
                println(it.date_state)
            }
            val adapterTimesheet = ListReportTimesheetProgressAdapter(context!!, listTimesheet)
            recyclerView.adapter = adapterTimesheet
            adapterTimesheet.notifyDataSetChanged()
        //}
    }

    /*fun detail(timesheet: Timesheet){
        println("onClick kirim id ke detail")
        idData = timesheet.id_timesheet
        var listTimesheet= databaseQueryHelper!!.readDetailProgressTimesheetById(idData)
        //val listCompany = databaseQueryHelper.cariCompanyModels(keyword)
        tampilkanListTimesheet(listTimesheet,recyclerView!!)
    }*/

    fun tampilkanListProgressDetail(listProgressDetail:List<Timesheet>, recyclerView: RecyclerView){
        println("isContext null = ${context==null}")
        val adapter = ListReportTimesheetProgressAdapter(context!!, listProgressDetail)
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    fun getSemuaTimesheetProgressDetail(id:Int, recyclerView: RecyclerView, queryHelper: TimesheetQueryHelper){
        print("Tes")
        val listProgressDetail = queryHelper.readDetailProgressTimesheetById(id)
        tampilkanListProgressDetail(listProgressDetail, recyclerView)
    }

    fun bawaID(id:Int){
        ID = id

        databaseHelper = DatabaseHelper(context!!)
        databaseQueryHelper = TimesheetQueryHelper(databaseHelper!!)
        getSemuaTimesheetProgressDetail(ID, recyclerView!!, databaseQueryHelper!!)
    }

}