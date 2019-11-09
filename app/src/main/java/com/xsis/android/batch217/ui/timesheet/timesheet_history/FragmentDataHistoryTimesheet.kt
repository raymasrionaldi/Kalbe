package com.xsis.android.batch217.ui.timesheet.timesheet_history

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.fragments.TimesheetHistoryAdapter
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.TimesheetQueryHelper

class FragmentDataHistoryTimesheet (context: Context, val fm: FragmentManager): Fragment() {
        var recyclerView: RecyclerView? = null
    var databaseQueryHelper: TimesheetQueryHelper? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val customView = inflater.inflate(R.layout.fragment_data_history_timesheet, container, false)
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView = customView.findViewById(R.id.listDataHistoryTimesheet) as RecyclerView
        recyclerView!!.layoutManager = layoutManager

        val dividerItemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        recyclerView!!.addItemDecoration(dividerItemDecoration)

        val myFab = customView.findViewById(R.id.buttonPlusHistoryTimesheet) as FloatingActionButton
        myFab.setOnClickListener {
            var viewPager=view!!.parent as ViewPager
            viewPager.setCurrentItem(1,true)
//            val adapter = viewPager.adapter!! as TimesheetHistoryAdapter
//            val fragment = fm.fragments[1] as FragmentDetailHistoryTimesheet
//            fragment.modeAdd()
//            adapter.notifyDataSetChanged()
//            viewPager.setCurrentItem(1, true)
        }
        val databaseHelper = DatabaseHelper(context!!)
        databaseQueryHelper = TimesheetQueryHelper(databaseHelper)

        return customView

    }
}



