package com.xsis.android.batch217.viewholders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.ramiz.nameinitialscircleimageview.NameInitialsCircleImageView
import com.xsis.android.batch217.R
import com.xsis.android.batch217.models.ProviderTools

class ViewHolderListProviderTools(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var teksUtama = itemView.findViewById(R.id.isiTeks) as TextView

    var id = itemView.findViewById(R.id.gambarLingkaran) as NameInitialsCircleImageView

    fun setModel(model: ProviderTools) {
        val nama = model.nama_provider
        teksUtama.text = nama


        val image = NameInitialsCircleImageView.ImageInfo
            .Builder(model.id_provider.toString())
            .setTextColor(android.R.color.black)
            .setCircleBackgroundColorRes(R.color.warnaAbu)
            .build()
        id.setImageInfo(image)
    }
}