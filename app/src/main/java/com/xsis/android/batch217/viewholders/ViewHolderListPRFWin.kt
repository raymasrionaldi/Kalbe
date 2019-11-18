package com.xsis.android.batch217.viewholders

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.github.ramiz.nameinitialscircleimageview.NameInitialsCircleImageView
import com.xsis.android.batch217.R
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.PRFRequestQueryHelper
import com.xsis.android.batch217.models.PRFWin
import com.xsis.android.batch217.utils.ambilSemuaInisial
import kotlin.math.PI

class ViewHolderListPRFWin(itemView: View): RecyclerView.ViewHolder(itemView) {
    val layoutList = itemView.findViewById(R.id.linearLayoutListDotDua) as LinearLayout
    val teksUtama = itemView.findViewById(R.id.textUtama) as TextView
    val teksTambahan = itemView.findViewById(R.id.textTambahan) as TextView
    val inisial = itemView.findViewById(R.id.gambarLingkaran) as NameInitialsCircleImageView
    val bukaMenu = itemView.findViewById(R.id.bukaMenu) as ImageView


    fun setModel(model: PRFWin){
        bukaMenu.isVisible = false

        val databaseHelper = DatabaseHelper(itemView.context)
        val databaseQueryHelper = PRFRequestQueryHelper(databaseHelper)

        val listTypePRF = databaseQueryHelper.readTypePRFNew()
        val isilistType = listTypePRF.map {it.nama_type_prf}.toMutableList()
        isilistType.add(0, "Type *")
        val type = isilistType[model.type!!.toInt()]

        val listPID = databaseQueryHelper.readPIDPRFNew()
        val isilistPID = listPID.map {it.PID}.toMutableList()
        isilistPID.add(0, "PID *")
        val PID = isilistPID[model.pid!!.toInt()]

        teksUtama.text = model.placement
        teksTambahan.text = "$type, $PID"
        println(model.placement)
        println("${model.type}, ${model.pid}")
        println("$type, $PID")

        val image = NameInitialsCircleImageView.ImageInfo
            .Builder(ambilSemuaInisial(model.placement!!))
            .setTextColor(android.R.color.black)
            .setCircleBackgroundColorRes(R.color.warnaAbu)
            .build()
        inisial.setImageInfo(image)
    }
}