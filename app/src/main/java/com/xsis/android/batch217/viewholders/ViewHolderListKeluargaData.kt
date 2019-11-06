package com.xsis.android.batch217.viewholders

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.ramiz.nameinitialscircleimageview.NameInitialsCircleImageView
import com.xsis.android.batch217.R
import com.xsis.android.batch217.models.KeluargaData
import com.xsis.android.batch217.models.TipeIdentitas

class ViewHolderListKeluargaData(itemView:View): RecyclerView.ViewHolder(itemView) {
    val layoutList = itemView.findViewById(R.id.layoutList) as LinearLayout
    val isiText = itemView.findViewById(R.id.isiTeks) as TextView
    val inisial = itemView.findViewById(R.id.gambarLingkaran) as NameInitialsCircleImageView
    val bukaMenu = itemView.findViewById(R.id.bukaMenu) as ImageView

    fun setModel(model: KeluargaData){
        isiText.text = model.jenisKeluarga
        val image = NameInitialsCircleImageView.ImageInfo
            .Builder(model.idKeluargaData.toString())
            .setTextColor(android.R.color.black)
            .setCircleBackgroundColorRes(R.color.warnaAbu)
            .build()
        inisial.setImageInfo(image)
    }
}