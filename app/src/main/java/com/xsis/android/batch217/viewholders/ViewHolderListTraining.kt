package com.xsis.android.batch217.viewholders

import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.ramiz.nameinitialscircleimageview.NameInitialsCircleImageView
import com.xsis.android.batch217.R
import com.xsis.android.batch217.models.Training
import com.xsis.android.batch217.utils.ambilSemuaInisial

class ViewHolderListTraining(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var layoutList = itemView.findViewById(R.id.layoutList) as LinearLayout
    var teksUtama = itemView.findViewById(R.id.textUtama2) as TextView
   // var teksTambahan = itemView.findViewById(R.id.textTambahan) as TextView
    var inisial = itemView.findViewById(R.id.gambarLingkaran2) as NameInitialsCircleImageView

    fun setModel(model: Training) {
        val nama = model.namaTraining
        teksUtama.text = nama
     //   teksTambahan.text = ""

        val image = NameInitialsCircleImageView.ImageInfo
            .Builder(ambilSemuaInisial(nama!!))
            .setTextColor(android.R.color.black)
            .setCircleBackgroundColorRes(R.color.warnaAbu)
            .build()
        inisial.setImageInfo(image)
    }
}