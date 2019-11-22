package com.xsis.android.batch217.viewholders

import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.ramiz.nameinitialscircleimageview.NameInitialsCircleImageView
import com.xsis.android.batch217.R
import com.xsis.android.batch217.models.Permission
import com.xsis.android.batch217.models.Timesheet

class ViewHolderListPermission(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var layoutList = itemView.findViewById(R.id.layoutList) as LinearLayout
    var teksUtama = itemView.findViewById(R.id.textUtama) as TextView
    var teksTambahan = itemView.findViewById(R.id.textTambahan) as TextView
    var inisial = itemView.findViewById(R.id.gambarLingkaran) as NameInitialsCircleImageView

    fun setModelPermission(model: Permission) {
        val date = model.tanggal_permission
        val namaPegawai = model.nama_pegawai
        teksUtama.text = date
        teksTambahan.text = namaPegawai

        val image = NameInitialsCircleImageView.ImageInfo
            .Builder(model.id_permission.toString())
            .setTextColor(android.R.color.black)
            .setCircleBackgroundColorRes(R.color.warnaAbu)
            .build()
        inisial.setImageInfo(image)
    }

    fun setModelPermissionHistory(model: Permission){
        val date = model.tanggal_permission
        val status = model.status_permission
        teksUtama.text = date
        teksTambahan.text = status

        val image = NameInitialsCircleImageView.ImageInfo
            .Builder(model.id_permission.toString())
            .setTextColor(android.R.color.black)
            .setCircleBackgroundColorRes(R.color.warnaAbu)
            .build()
        inisial.setImageInfo(image)
    }
}
