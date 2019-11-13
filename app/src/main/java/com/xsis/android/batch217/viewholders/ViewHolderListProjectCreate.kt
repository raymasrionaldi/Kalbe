package com.xsis.android.batch217.viewholders

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.github.ramiz.nameinitialscircleimageview.NameInitialsCircleImageView
import com.xsis.android.batch217.R
import com.xsis.android.batch217.models.ProjectCreate

class ViewHolderListProjectCreate(itemView: View): RecyclerView.ViewHolder(itemView) {
    val layoutList = itemView.findViewById(R.id.linearLayoutListDotDua) as LinearLayout
    val teksUtama = itemView.findViewById(R.id.textUtama) as TextView
    val teksTambahan = itemView.findViewById(R.id.textTambahan) as TextView
    val inisial = itemView.findViewById(R.id.gambarLingkaran) as NameInitialsCircleImageView
    val bukaMenu = itemView.findViewById(R.id.bukaMenu) as ImageView


    fun setModel(model: ProjectCreate, position:Int){
        bukaMenu.isVisible = false
        teksUtama.text = model.noPOSPKKontrak
        teksTambahan.text = model.client

        val image = NameInitialsCircleImageView.ImageInfo
            .Builder((position+1).toString())
            .setTextColor(android.R.color.black)
            .setCircleBackgroundColorRes(R.color.warnaAbu)
            .build()
        println((position+1).toString())
        inisial.setImageInfo(image)
    }

}