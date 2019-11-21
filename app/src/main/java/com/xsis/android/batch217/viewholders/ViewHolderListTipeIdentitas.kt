package com.xsis.android.batch217.viewholders

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import androidx.core.view.marginBottom
import androidx.recyclerview.widget.RecyclerView
import com.github.ramiz.nameinitialscircleimageview.NameInitialsCircleImageView
import com.xsis.android.batch217.R
import com.xsis.android.batch217.models.TipeIdentitas

class ViewHolderListTipeIdentitas(itemView:View): RecyclerView.ViewHolder(itemView) {
    val linearLayout = itemView.findViewById(R.id.linearLayoutListDotLayout) as LinearLayout
    val isiText = itemView.findViewById(R.id.isiTeks) as TextView
    val inisial = itemView.findViewById(R.id.gambarLingkaran) as NameInitialsCircleImageView
    val bukaMenu = itemView.findViewById(R.id.bukaMenu) as ImageView

    fun setModel(model:TipeIdentitas, position:Int){
        isiText.text = model.nama_TipeIdentitas
        val image = NameInitialsCircleImageView.ImageInfo
            .Builder(position.toString())
            .setTextColor(android.R.color.black)
            .setCircleBackgroundColorRes(R.color.warnaAbu)
            .build()
        inisial.setImageInfo(image)
    }

    fun setPaddingList(){
        linearLayout.setPadding(0,0,0,40)
    }

    fun addLinearLayout(){
        val linearLayoutCreate = LinearLayout(itemView.context)
        val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        lp.setMargins(0,0,0,40)
        linearLayoutCreate.layoutParams = lp
        linearLayout.addView(linearLayoutCreate)
    }
}