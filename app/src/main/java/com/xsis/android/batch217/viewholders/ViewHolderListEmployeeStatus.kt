package com.xsis.android.batch217.viewholders

import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.ramiz.nameinitialscircleimageview.NameInitialsCircleImageView
import com.xsis.android.batch217.R
import com.xsis.android.batch217.models.Company
import com.xsis.android.batch217.models.EmployeeStatus
import com.xsis.android.batch217.utils.ambilDuaInisial

class ViewHolderListEmployeeStatus(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var layoutList = itemView.findViewById(R.id.layoutList) as LinearLayout
    var teksUtama = itemView.findViewById(R.id.textUtama) as TextView
    var teksTambahan = itemView.findViewById(R.id.textTambahan) as TextView
    var inisial = itemView.findViewById(R.id.gambarLingkaran) as NameInitialsCircleImageView

    fun setModel(model: EmployeeStatus) {
        val nama = model.nama_employee_status
        teksUtama.text = nama
        teksTambahan.text = ""

        val image = NameInitialsCircleImageView.ImageInfo
            .Builder(ambilDuaInisial(nama!!))
            .setTextColor(android.R.color.black)
            .setCircleBackgroundColorRes(R.color.warnaAbu)
            .build()
        inisial.setImageInfo(image)
    }
}