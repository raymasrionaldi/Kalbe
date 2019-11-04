package com.xsis.android.batch217.viewholders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import com.github.ramiz.nameinitialscircleimageview.NameInitialsCircleImageView
import com.xsis.android.batch217.R
import com.xsis.android.batch217.models.TipeIdentitas

class ViewHolderListTipeIdentitas(itemView:View): RecyclerView.ViewHolder(itemView) {
    val isiText = itemView.findViewById(R.id.isiTeks) as TextView
    val inisial = itemView.findViewById(R.id.gambarLingkaran) as NameInitialsCircleImageView
    val bukaMenu = itemView.findViewById(R.id.bukaMenu) as ImageView

    fun setModel(model:TipeIdentitas){
        isiText.text = model.nama_TipeIdentitas
        val image = NameInitialsCircleImageView.ImageInfo
            .Builder(model.id_TipeIdentitas.toString())
            .setTextColor(android.R.color.black)
            .setCircleBackgroundColorRes(R.color.warnaAbu)
            .build()
        inisial.setImageInfo(image)
    }
}