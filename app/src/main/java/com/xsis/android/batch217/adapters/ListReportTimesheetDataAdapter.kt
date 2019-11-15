package com.xsis.android.batch217.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.fragments.TimesheetHistoryAdapter
import com.xsis.android.batch217.adapters.fragments.TimesheetReportFragmentAdapter
import com.xsis.android.batch217.models.Timesheet
import com.xsis.android.batch217.ui.timesheet.timesheet_history.FragmentDetailHistoryTimesheet
import com.xsis.android.batch217.ui.timesheet_report.TimesheetReportFragmentProgress
import com.xsis.android.batch217.viewholders.ViewHolderListTimesheet

class ListReportTimesheetDataAdapter(
    val context: Context,
    val listTimesheet: List<Timesheet>,
    val fm: FragmentManager
) : RecyclerView.Adapter<ViewHolderListTimesheet>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderListTimesheet {
        val customLayout =
            LayoutInflater.from(parent.context).inflate(R.layout.list_layout, parent, false)
        return ViewHolderListTimesheet(customLayout)
    }

    override fun getItemCount(): Int {
        return listTimesheet.size
    }

    override fun onBindViewHolder(holder: ViewHolderListTimesheet, position: Int) {
        val model = listTimesheet[position]
        holder.setModel(model)

        val ID = model.id_timesheet

        holder.layoutList.setOnClickListener { view ->
            val fragment = fm.fragments[1] as TimesheetReportFragmentProgress
            val viewPager = fragment.view!!.parent as ViewPager
            val adapter = viewPager.adapter!! as TimesheetReportFragmentAdapter

//            fragment.detail(model)
            fragment.bawaID(ID)
            adapter.notifyDataSetChanged()
            viewPager.setCurrentItem(1, true)
        }
    }
}