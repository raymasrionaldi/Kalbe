package com.xsis.android.batch217.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.xsis.android.batch217.R
import com.xsis.android.batch217.models.Timesheet
import com.xsis.android.batch217.viewholders.ViewHolderListTimesheetCollect

class ListTimeSheetCollectAdapter(val context: Context?,
                        val listTimesheet: List<Timesheet>): RecyclerView.Adapter<ViewHolderListTimesheetCollect>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderListTimesheetCollect {
        val customView = LayoutInflater.from(parent.context).inflate(R.layout.list_layout,parent,false)

        return ViewHolderListTimesheetCollect(customView)
    }

    override fun getItemCount(): Int {
        return listTimesheet.size
    }

    override fun onBindViewHolder(holder: ViewHolderListTimesheetCollect, position: Int) {
        val model = listTimesheet[position]
        holder.setModelTimesheetCollect(model)
        holder.layoutList.setOnClickListener{
            //no action
        }
    }

}