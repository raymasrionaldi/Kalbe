package com.xsis.android.batch217.viewholders

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.ramiz.nameinitialscircleimageview.NameInitialsCircleImageView
import com.xsis.android.batch217.R
import com.xsis.android.batch217.models.TipeTes
import com.xsis.android.batch217.utils.ambilDuaInisial

class ViewHolderListTipeTes(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var teksUtama = itemView.findViewById(R.id.isiTeks) as TextView

    var id = itemView.findViewById(R.id.gambarLingkaran) as NameInitialsCircleImageView

    val bukaMenu = itemView.findViewById(R.id.bukaMenu) as ImageView

    fun setModel(model: TipeTes) {
        val nama = model.nama_tipe_tes
        teksUtama.text = nama


        val image = NameInitialsCircleImageView.ImageInfo
            .Builder(model.id_tipe_tes.toString())
            .setTextColor(android.R.color.black)
            .setCircleBackgroundColorRes(R.color.warnaAbu)
            .build()
        id.setImageInfo(image)
    }
}