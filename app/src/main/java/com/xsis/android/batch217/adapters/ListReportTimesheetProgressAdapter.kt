package com.xsis.android.batch217.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.xsis.android.batch217.R
import com.xsis.android.batch217.models.Timesheet
import com.xsis.android.batch217.viewholders.ViewHolderListTimesheet

class ListReportTimesheetProgressAdapter(val context: Context,
                                         val listTimesheet: List<Timesheet>,
                                         val fm: FragmentManager
): RecyclerView.Adapter<ViewHolderListTimesheet>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderListTimesheet {
        val customLayout =
            LayoutInflater.from(parent.context).inflate(R.layout.list_progress_timesheet_layout, parent, false)
        return ViewHolderListTimesheet(customLayout)    }

    override fun getItemCount(): Int {
        return listTimesheet.size    }

    override fun onBindViewHolder(holder: ViewHolderListTimesheet, position: Int) {
        val model = listTimesheet[position]
        holder.setModel(model)    }
}