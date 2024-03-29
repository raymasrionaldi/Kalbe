package com.xsis.android.batch217.adapters.fragments

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.xsis.android.batch217.R
import com.xsis.android.batch217.models.Timesheet
import com.xsis.android.batch217.viewholders.ViewHolderListTimesheetSend

class ListTimesheetSendAdapter (val context: Context?,
                                val listTimesheet: List<Timesheet>): RecyclerView.Adapter<ViewHolderListTimesheetSend>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderListTimesheetSend {
        val customView = LayoutInflater.from(parent.context).inflate(R.layout.list_layout,parent,false)

        return ViewHolderListTimesheetSend(customView)
    }

    override fun getItemCount(): Int {
        return listTimesheet.size
    }

    override fun onBindViewHolder(holder: ViewHolderListTimesheetSend, position: Int) {
        val model = listTimesheet[position]
        holder.setModelTimesheetSend(model)

        holder.layoutList.setOnClickListener{
            //no action
        }
    }

}