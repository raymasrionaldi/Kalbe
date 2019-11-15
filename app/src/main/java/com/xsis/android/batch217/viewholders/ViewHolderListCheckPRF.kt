package com.xsis.android.batch217.viewholders

import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.github.ramiz.nameinitialscircleimageview.NameInitialsCircleImageView
import com.xsis.android.batch217.R
import com.xsis.android.batch217.models.CheckPRF
import com.xsis.android.batch217.utils.ambilSemuaInisial

class ViewHolderListCheckPRF (itemView: View): RecyclerView.ViewHolder(itemView){
    val layoutList = itemView.findViewById(R.id.linearLayoutListDotDua) as LinearLayout
    val teksUtama = itemView.findViewById(R.id.textUtama) as TextView
    val teksTambahan = itemView.findViewById(R.id.textTambahan) as TextView
    val inisial = itemView.findViewById(R.id.gambarLingkaran) as NameInitialsCircleImageView
    val bukaMenu = itemView.findViewById(R.id.bukaMenu) as ImageView


    fun setModel(model: CheckPRF){
        bukaMenu.isVisible = false
        teksUtama.text = model.placement
        teksTambahan.text = "${model.type}, ${model.pid}"
        println(model.placement)
        println("${model.type}, ${model.pid}")

        val image = NameInitialsCircleImageView.ImageInfo
            .Builder(ambilSemuaInisial(model.placement!!))
            .setTextColor(android.R.color.black)
            .setCircleBackgroundColorRes(R.color.warnaAbu)
            .build()
        inisial.setImageInfo(image)
    }
}