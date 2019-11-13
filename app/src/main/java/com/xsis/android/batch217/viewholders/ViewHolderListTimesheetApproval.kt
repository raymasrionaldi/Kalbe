package com.xsis.android.batch217.viewholders

import android.view.View
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.xsis.android.batch217.R
import com.xsis.android.batch217.models.Timesheet

class ViewHolderListTimesheetApproval(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var layoutList = itemView.findViewById(R.id.listTimesheetApprovalLayout) as LinearLayout
    var checkBox = itemView.findViewById(R.id.checkboxTimesheetApproval) as CheckBox
    var textDate = itemView.findViewById(R.id.textDateTimesheetApproval) as TextView
    var textStatus = itemView.findViewById(R.id.textStatusTimesheetApproval) as TextView

    fun setModel(model: Timesheet) {
        val tanggal = model.reportDate_timesheet
        val status = "${model.status_timesheet}"
        textDate.text = tanggal
        textStatus.text = status
    }


}