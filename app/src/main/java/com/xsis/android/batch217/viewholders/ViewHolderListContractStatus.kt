package com.xsis.android.batch217.viewholders

import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.ramiz.nameinitialscircleimageview.NameInitialsCircleImageView
import com.xsis.android.batch217.R
import com.xsis.android.batch217.models.ContractStatus
import com.xsis.android.batch217.utils.ambilDuaInisial

class ViewHolderListContractStatus(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var layoutList = itemView.findViewById(R.id.layoutList) as LinearLayout
    var teksUtama = itemView.findViewById(R.id.textUtama) as TextView
    var teksTambahan = itemView.findViewById(R.id.textTambahan) as TextView
    var inisial = itemView.findViewById(R.id.gambarLingkaran) as NameInitialsCircleImageView

    fun setModel(model: ContractStatus) {
        val nama = model.namaContract
        teksUtama.text = nama
        teksTambahan.text = ""

        val image = nama?.let { ambilDuaInisial(it) }?.let {
            NameInitialsCircleImageView.ImageInfo
                .Builder(it)
                .setTextColor(android.R.color.black)
                .setCircleBackgroundColorRes(R.color.warnaAbu)
                .build()
        }
        if (image != null) {
            inisial.setImageInfo(image)
        }
    }
}