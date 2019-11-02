package com.xsis.android.batch217.viewholders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.ramiz.nameinitialscircleimageview.NameInitialsCircleImageView
import com.xsis.android.batch217.R
import com.xsis.android.batch217.models.Pendidikan
import com.xsis.android.batch217.utils.ambilDuaInisial

class ViewHolderListPendidikan(itemView: View): RecyclerView.ViewHolder(itemView) {

    val isiText = itemView.findViewById(R.id.isiTeks) as TextView
    val inisial = itemView.findViewById(R.id.gambarLingkaran) as NameInitialsCircleImageView
    val bukaMenu = itemView.findViewById(R.id.bukaMenu) as ImageView

    fun setModel(pendidikan: Pendidikan) {
        isiText.text = pendidikan.nama_pendidikan
        val image = NameInitialsCircleImageView.ImageInfo
            .Builder(pendidikan.id_pendidikan.toString())
            .setTextColor(android.R.color.black)
            .setCircleBackgroundColorRes(R.color.warnaAbu)
            .build()
        inisial.setImageInfo(image)
    }
}