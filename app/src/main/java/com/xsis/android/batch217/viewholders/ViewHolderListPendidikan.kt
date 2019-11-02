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
    val idPendidikan: NameInitialsCircleImageView
    val namaPendidikan: TextView
    val bukaMenu : ImageView


    init{
        idPendidikan = itemView.findViewById(R.id.gambarLingkaran) as NameInitialsCircleImageView
        namaPendidikan= itemView.findViewById(R.id.isiTeks)
        bukaMenu = itemView.findViewById(R.id.bukaMenu) as ImageView


    }

    fun setPendidikan(pendidikan: Pendidikan){
        namaPendidikan.text = pendidikan.nama
        val image = NameInitialsCircleImageView.ImageInfo
            .Builder(idPendidikan.toString())
            .setTextColor(android.R.color.black)
            .setCircleBackgroundColorRes(R.color.warnaAbu)
            .build()
        idPendidikan.setImageInfo(image)
    }
}