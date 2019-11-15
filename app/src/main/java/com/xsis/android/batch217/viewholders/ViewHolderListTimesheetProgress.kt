package com.xsis.android.batch217.viewholders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.xsis.android.batch217.R
import com.xsis.android.batch217.models.Timesheet

class ViewHolderListTimesheetProgress(itemView: View): RecyclerView.ViewHolder(itemView) {
    var teksUtama = itemView.findViewById(R.id.textUtamaProgress) as TextView
    var teksTambahan = itemView.findViewById(R.id.textTambahanProgress) as TextView

    fun setModel(model: Timesheet) {
        val state = model.progress_state
        val date = "${model.date_state}"
        teksUtama.text =state
        teksTambahan.text = date
//        teksTambahan.setTextSize(TypedValue.COMPLEX_UNIT_SP,12f )
    }
}