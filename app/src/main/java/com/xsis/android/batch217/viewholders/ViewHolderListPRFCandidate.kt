package com.xsis.android.batch217.viewholders

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.ramiz.nameinitialscircleimageview.NameInitialsCircleImageView
import com.xsis.android.batch217.R
import com.xsis.android.batch217.models.PRFCandidate
import com.xsis.android.batch217.utils.ambilSemuaInisial

class ViewHolderListPRFCandidate(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var layoutList = itemView.findViewById(R.id.layoutList) as LinearLayout
    var teksUtama = itemView.findViewById(R.id.textUtama) as TextView
    var teksTambahan = itemView.findViewById(R.id.textTambahan) as TextView
    var inisial = itemView.findViewById(R.id.gambarLingkaran) as NameInitialsCircleImageView
    var bukaMenu = itemView.findViewById(R.id.bukaMenu) as ImageView

    fun setModel(model: PRFCandidate) {
        val nama = model.nama_prf_candidate
        val position = model.namaPosition
        teksUtama.text = nama
        teksTambahan.text = position

        val image = NameInitialsCircleImageView.ImageInfo
            .Builder(ambilSemuaInisial(nama!!))
            .setTextColor(android.R.color.black)
            .setCircleBackgroundColorRes(R.color.warnaAbu)
            .build()
        inisial.setImageInfo(image)
    }
}