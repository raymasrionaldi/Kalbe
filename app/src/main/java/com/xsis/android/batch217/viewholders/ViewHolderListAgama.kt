package com.xsis.android.batch217.viewholders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.ramiz.nameinitialscircleimageview.NameInitialsCircleImageView
import com.xsis.android.batch217.R
import com.xsis.android.batch217.models.Agama

class ViewHolderListAgama(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val isiText = itemView.findViewById(R.id.isiTeks) as TextView
    val inisial = itemView.findViewById(R.id.gambarLingkaran) as NameInitialsCircleImageView
    val bukaMenu = itemView.findViewById(R.id.bukaMenu) as ImageView

    fun setModel(model: Agama) {
        isiText.text = model.nama_agama
        val image = NameInitialsCircleImageView.ImageInfo
            .Builder(model.id_agama.toString())
            .setTextColor(android.R.color.black)
            .setCircleBackgroundColorRes(R.color.warnaAbu)
            .build()
        inisial.setImageInfo(image)
    }
}