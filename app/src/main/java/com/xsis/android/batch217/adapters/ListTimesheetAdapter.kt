package com.xsis.android.batch217.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.fragments.TimesheetHistoryAdapter
import com.xsis.android.batch217.models.Timesheet
import com.xsis.android.batch217.ui.timesheet.timesheet_history.FragmentDetailHistoryTimesheet
import com.xsis.android.batch217.viewholders.ViewHolderListTimesheet

class ListTimesheetAdapter (
    val context: Context,
    val listProject: List<Timesheet>,
    val fm: FragmentManager
) : RecyclerView.Adapter<ViewHolderListTimesheet>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderListTimesheet {
        val customLayout =
            LayoutInflater.from(parent.context).inflate(R.layout.list_layout, parent, false)
        return ViewHolderListTimesheet(customLayout)
    }

    override fun getItemCount(): Int {
        return listProject.size
    }

    override fun onBindViewHolder(holder: ViewHolderListTimesheet, position: Int) {
        val model = listProject[position]
        holder.setModel(model)

        holder.layoutList.setOnClickListener { view ->
            val fragment = fm.fragments[1] as FragmentDetailHistoryTimesheet
            val viewPager = fragment.view!!.parent as ViewPager
            val adapter = viewPager.adapter!! as TimesheetHistoryAdapter

            fragment.detail(model)
            adapter.notifyDataSetChanged()
            viewPager.setCurrentItem(1, true)
        }
    }
}