package com.xsis.android.batch217.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.xsis.android.batch217.R
import com.xsis.android.batch217.models.Keahlian
import com.xsis.android.batch217.utils.showPopupMenuUbahHapus
import com.xsis.android.batch217.viewholders.ViewHolderListKeahlian
import kotlinx.android.synthetic.main.popup_layout.view.*

class ListKeahlianAdapter(val context: Context?,
                          val listKeahlian: List<Keahlian>): RecyclerView.Adapter<ViewHolderListKeahlian>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderListKeahlian {
        val customView = LayoutInflater.from(parent.context).inflate(R.layout.list_dot_layout,parent,false)

        return ViewHolderListKeahlian(customView)
    }

    override fun getItemCount(): Int {
        return listKeahlian.size
    }

    override fun onBindViewHolder(holder: ViewHolderListKeahlian, position: Int) {
        holder.setModelKeahlian(listKeahlian[position])

        holder.bukaMenu.setOnClickListener {view ->
            val window = showPopupMenuUbahHapus(
                context!!, view
            )
            window.setOnItemClickListener { parent, view, position, id ->
                //                TODO("buat fungsi ubah dan hapus")
                Toast.makeText(context, view.textMenu.text.toString(), Toast.LENGTH_SHORT).show()
            }

            window.show()
        }
    }


}