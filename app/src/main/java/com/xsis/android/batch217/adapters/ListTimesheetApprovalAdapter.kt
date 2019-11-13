package com.xsis.android.batch217.adapters

import android.content.Context
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.xsis.android.batch217.R
import com.xsis.android.batch217.models.Timesheet
import com.xsis.android.batch217.viewholders.ViewHolderListTimesheetApproval


class ListTimesheetApprovalAdapter(
    val context: Context,
    val listProject: List<Timesheet>
) : RecyclerView.Adapter<ViewHolderListTimesheetApproval>() {

    var itemStateArray = SparseBooleanArray()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolderListTimesheetApproval {
        val customLayout =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_timesheet_approval_layout, parent, false)
        return ViewHolderListTimesheetApproval(customLayout)
    }

    override fun getItemCount(): Int {
        return listProject.size
    }

    override fun onBindViewHolder(holder: ViewHolderListTimesheetApproval, position: Int) {
        val model = listProject[position]
        holder.setModel(model)
        //cek yang dicentangi
        holder.checkBox.isChecked = itemStateArray.get(position, false)

        holder.layoutList.setOnClickListener {
            //            val intent = Intent(context, TimesheetApprovalDetailActivity::class.java)
//            context.startActivity()
        }
        holder.checkBox.setOnClickListener {
            val pos = holder.adapterPosition
            if (!itemStateArray.get(pos, false)) {
                holder.checkBox.isChecked = true
                itemStateArray.put(pos, true)
            } else {
                holder.checkBox.isChecked = false
                itemStateArray.put(pos, false)
            }
        }
    }
}