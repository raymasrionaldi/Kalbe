package com.xsis.android.batch217.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.recyclerview.widget.RecyclerView
import com.xsis.android.batch217.R
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.TimesheetQueryHelper
import com.xsis.android.batch217.models.Timesheet
import com.xsis.android.batch217.ui.timesheet.timesheet_approval.TimesheetApprovalDetailActivity
import com.xsis.android.batch217.ui.timesheet.timesheet_approval.TimesheetApprovalProcessActivity
import com.xsis.android.batch217.utils.APPROVED
import com.xsis.android.batch217.utils.ID_TIMESHEET
import com.xsis.android.batch217.utils.REJECTED
import com.xsis.android.batch217.viewholders.ViewHolderListTimesheetApproval


class ListTimesheetApprovalAdapter(
    val context: Context,
    val listTimesheet: List<Timesheet>
) : RecyclerView.Adapter<ViewHolderListTimesheetApproval>() {
    var actionMode: ActionMode? = null
    var databaseHelper: DatabaseHelper? = null
    var databaseQueryHelper: TimesheetQueryHelper? = null
    var selectedTimesheets = ArrayList<Int>()

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
        return listTimesheet.size
    }

    override fun onBindViewHolder(holder: ViewHolderListTimesheetApproval, position: Int) {
        val model = listTimesheet[position]
        holder.setModel(model)
        //cek yang dicentangi
        holder.checkBox.isChecked = selectedTimesheets.contains(position)

        holder.layoutList.setOnClickListener {
            val intent = Intent(context, TimesheetApprovalDetailActivity::class.java)
            intent.putExtra(ID_TIMESHEET, model.id_timesheet)
            context.startActivity(intent)
        }
        holder.checkBox.setOnClickListener { view ->
            val pos = holder.adapterPosition

            //ubah kondisi
            if (!selectedTimesheets.contains(pos)) {
                holder.checkBox.isChecked = true
                selectedTimesheets.add(pos)
            } else {
                holder.checkBox.isChecked = false
                selectedTimesheets.remove(pos)
            }

            //munculkan CAB bila ada data yang dicentang
            //hancurkan CAB bila tidak ada data yang dicentang
            if (actionMode == null) {
                actionMode =
                    (context as AppCompatActivity).startSupportActionMode(actionModeCallbacks)
            } else if (selectedTimesheets.isEmpty()) {
                actionMode!!.finish()
            }
        }
    }

    private val actionModeCallbacks = object : ActionMode.Callback {
        override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
            mode.menuInflater.inflate(R.menu.approve, menu)
            return true
        }

        override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
            return false
        }

        override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
            if (item.itemId == R.id.approved) {
                if (databaseHelper == null) {
                    databaseHelper = DatabaseHelper(context)
                }
                if (databaseQueryHelper == null) {
                    databaseQueryHelper = TimesheetQueryHelper(databaseHelper!!)
                }
                AlertDialog.Builder(context)
                    .setCancelable(true)
                    .setTitle("DATA APPROVAL")
                    .setMessage("Are you sure to process timesheet ?")
                    .setPositiveButton("APPROVE") { dialog, which ->
                        changeProgress(APPROVED)
                    }
                    .setNegativeButton("REJECT") { dialog, which ->
                        changeProgress(REJECTED)
                    }
                    .create().show()
            }
            return true
        }

        override fun onDestroyActionMode(mode: ActionMode) {
            //hilangkan tanda centang
            selectedTimesheets.clear()
            notifyDataSetChanged()
            actionMode = null
        }
    }

    private fun changeProgress(progress: String) {
        var ids = ArrayList<Int>()
        selectedTimesheets.forEach { index -> ids.add(listTimesheet[index].id_timesheet) }

        val isSucceed = databaseQueryHelper!!.changeProgress(ids, progress)

        val message = if (isSucceed) {
            "DATA HAS BEEN ${progress.toUpperCase()}"
        } else {
            "DATA CANNOT BE PROCESSED"
        }

        AlertDialog.Builder(context)
            .setCancelable(false)
            .setTitle(message)
            .setPositiveButton("CLOSE") { dialog, which ->
                (context as TimesheetApprovalProcessActivity).finish()
            }
            .create().show()
    }
}