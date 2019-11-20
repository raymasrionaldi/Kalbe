package com.xsis.android.batch217.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.*
import androidx.appcompat.view.ActionMode
import androidx.recyclerview.widget.RecyclerView
import com.xsis.android.batch217.R
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.TimesheetQueryHelper
import com.xsis.android.batch217.models.Timesheet
import com.xsis.android.batch217.ui.timesheet.timesheet_collection.TimesheetCollectionDetailActivity
import com.xsis.android.batch217.utils.*
import com.xsis.android.batch217.viewholders.ViewHolderListTimesheetCollect

class ListTimeSheetCollectAdapter(
    val context: Context,
    val listTimesheet: List<Timesheet>
): RecyclerView.Adapter<ViewHolderListTimesheetCollect>() {

    var actionMode: ActionMode? = null
    var databaseHelper: DatabaseHelper? = null
    var databaseQueryHelper: TimesheetQueryHelper? = null
    var selectedTimesheets = ArrayList<Int>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolderListTimesheetCollect {
        val customView = LayoutInflater.from(parent.context).inflate(R.layout.list_layout, parent, false)

        return ViewHolderListTimesheetCollect(customView)
    }

    override fun getItemCount(): Int {
        return listTimesheet.size
    }

    override fun onBindViewHolder(holder: ViewHolderListTimesheetCollect, position: Int) {
        val model = listTimesheet[position]
        holder.setModelTimesheetCollect(model)

        holder.layoutList.setOnClickListener {
            val intent = Intent(context, TimesheetCollectionDetailActivity::class.java)
            intent.putExtra(ID_TIMESHEET, model.id_timesheet)
            context!!.startActivity(intent)
        }

        holder.inisial.setOnClickListener { view ->
            val pos = holder.adapterPosition

            if (!selectedTimesheets.contains(pos)) {
                holder.inisial.isSelected = true
                holder.itemView.setBackgroundColor(Color.GRAY)

                selectedTimesheets.add(pos)
            } else {
                holder.inisial.isSelected = false
                holder.itemView.setBackgroundColor(Color.WHITE)
                selectedTimesheets.remove(pos)
            }
        }
    }

    fun getSelectedList(): ArrayList<Int>{
        return selectedTimesheets
    }

}
