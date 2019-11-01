package com.xsis.android.batch217.viewholders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.ramiz.nameinitialscircleimageview.NameInitialsCircleImageView
import com.xsis.android.batch217.R
import com.xsis.android.batch217.utils.ambilDuaInisial

class ExampleViewHolderListDot(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val isiText = itemView.findViewById(R.id.isiTeks) as TextView
    val inisial = itemView.findViewById(R.id.gambarLingkaran) as NameInitialsCircleImageView
    val bukaMenu = itemView.findViewById(R.id.bukaMenu) as ImageView

    fun setModel(model: String) {
        isiText.text = model
        val image = NameInitialsCircleImageView.ImageInfo
            .Builder(ambilDuaInisial(model))
            .setTextColor(android.R.color.black)
            .setCircleBackgroundColorRes(R.color.warnaAbu)
            .build()
        inisial.setImageInfo(image)
    }

}