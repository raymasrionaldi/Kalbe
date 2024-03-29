package com.xsis.android.batch217.viewholders

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.ramiz.nameinitialscircleimageview.NameInitialsCircleImageView
import com.xsis.android.batch217.R
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.models.PRFRequest
import com.xsis.android.batch217.utils.ambilSemuaInisial

class ViewHolderListPRFRequest(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var layoutList = itemView.findViewById(R.id.layoutList) as LinearLayout
    var teksUtama = itemView.findViewById(R.id.textUtama) as TextView
    var teksTambahan = itemView.findViewById(R.id.textTambahan) as TextView
    var inisial = itemView.findViewById(R.id.gambarLingkaran) as NameInitialsCircleImageView
    var bukaMenu = itemView.findViewById(R.id.bukaMenu) as ImageView

    fun setModel(model: PRFRequest) {
        val placement = model.placement
        val pid = model.namaPid
        val type = model.namaType
        teksUtama.text = placement
        teksTambahan.text = "$type , $pid"

        val image = NameInitialsCircleImageView.ImageInfo
            .Builder(ambilSemuaInisial(placement!!))
            .setTextColor(android.R.color.black)
            .setCircleBackgroundColorRes(R.color.warnaAbu)
            .build()
        inisial.setImageInfo(image)
    }

}