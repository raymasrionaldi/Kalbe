package com.xsis.android.batch217.viewholders

import android.util.TypedValue
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.ramiz.nameinitialscircleimageview.NameInitialsCircleImageView
import com.xsis.android.batch217.R
import com.xsis.android.batch217.models.Timesheet
import com.xsis.android.batch217.utils.ambilDuaInisial

class ViewHolderListTimesheet(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var layoutList = itemView.findViewById(R.id.layoutList) as LinearLayout
    var teksUtama = itemView.findViewById(R.id.textUtama) as TextView
    var teksTambahan = itemView.findViewById(R.id.textTambahan) as TextView
    var inisial = itemView.findViewById(R.id.gambarLingkaran) as NameInitialsCircleImageView

    fun setModel(model: Timesheet) {
        val tanggal = model.reportDate_timesheet
        val status = "${model.status_timesheet}"
        teksUtama.text = tanggal
        teksTambahan.text = status

        teksTambahan.setTextSize(TypedValue.COMPLEX_UNIT_SP,12f )

        val image = NameInitialsCircleImageView.ImageInfo
            .Builder(model.id_timesheet.toString())
            .setTextColor(android.R.color.black)
            .setCircleBackgroundColorRes(R.color.warnaAbu)
            .build()
        inisial.setImageInfo(image)
    }


}