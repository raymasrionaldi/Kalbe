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
        val customView =
            LayoutInflater.from(parent.context).inflate(R.layout.list_layout, parent, false)

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

//            if (actionMode == null) {
//                actionMode =
//                    (context as AppCompatActivity).startSupportActionMode(actionModeCallbacks)
//            } else if (selectedTimesheets.isEmpty()) {
//                actionMode!!.finish()
//            }
        }
    }

//    private val actionModeCallbacks = object : ActionMode.Callback {
//        override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
//            mode.menuInflater.inflate(R.menu.collection, menu)
//            return true
//        }
//
//        override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
//            return false
//        }
//
//        override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
//            if (item.itemId == R.id.collection) {
//                if (databaseHelper == null) {
//                    databaseHelper = DatabaseHelper(context)
//                }
//                if (databaseQueryHelper == null) {
//                    databaseQueryHelper = TimesheetQueryHelper(databaseHelper!!)
//                }
//                AlertDialog.Builder(context, R.style.AlertDialogTheme)
//                    .setCancelable(true)
//                    .setTitle("")
//                    .setMessage("Are you sure to collect the data ?")
//                    .setPositiveButton("COLLECTING") { dialog, which ->
//                        changeProgress(COLLECTED)
//                    }
//                    .setNegativeButton("CANCEL") { dialog, which ->
//                        dialog.cancel()
//                        (context as AppCompatActivity).finish()
//                    }
//                    .create().show()
//            }
//            return true
//        }
//
//        override fun onDestroyActionMode(mode: ActionMode) {
//            //hilangkan selected
//            selectedTimesheets.clear()
//            notifyDataSetChanged()
//            actionMode = null
//        }
//    }
//
//    private fun changeProgress(progress: String) {
//        val ids = ArrayList<Int>()
//        selectedTimesheets.forEach { index -> ids.add(listTimesheet[index].id_timesheet) }
//
//        val isSucceed = databaseQueryHelper!!.changeProgress(ids, progress)
//
//        val message = if (isSucceed) {
//            "DATA HAS BEEN ${progress.toUpperCase()}"
//        } else {
//            "AN ERROR OCCURRED"
//        }
//
//        androidx.appcompat.app.AlertDialog.Builder(context, R.style.AlertDialogTheme)
//            .setCancelable(false)
//            .setTitle(message)
//            .setPositiveButton("CLOSE") { dialog, which ->
//                (context as AppCompatActivity).finish()
//            }
//            .create().show()
//    }

    fun getSelectedList(): ArrayList<Int>{
        return selectedTimesheets
    }


}
