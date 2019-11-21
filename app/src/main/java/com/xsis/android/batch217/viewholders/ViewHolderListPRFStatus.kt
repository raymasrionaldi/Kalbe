package com.xsis.android.batch217.viewholders

import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.ramiz.nameinitialscircleimageview.NameInitialsCircleImageView
import com.xsis.android.batch217.R
import com.xsis.android.batch217.models.KeluargaDetail
import com.xsis.android.batch217.models.PRFStatus
import com.xsis.android.batch217.utils.ambilSemuaInisial

class ViewHolderListPRFStatus(itemView: View): RecyclerView.ViewHolder(itemView) {

    var layoutList = itemView.findViewById(R.id.layoutList) as LinearLayout
    var teksUtama = itemView.findViewById(R.id.textUtama2) as TextView
    var inisial = itemView.findViewById(R.id.gambarLingkaran2) as NameInitialsCircleImageView

    fun setModel(model: PRFStatus) {
        val nama = model.namaPRFStatus
        teksUtama.text = nama
        val image = NameInitialsCircleImageView.ImageInfo
            .Builder(ambilSemuaInisial(nama))
            .setTextColor(android.R.color.black)
            .setCircleBackgroundColorRes(R.color.warnaAbu)
            .build()
        inisial.setImageInfo(image)
    }
}