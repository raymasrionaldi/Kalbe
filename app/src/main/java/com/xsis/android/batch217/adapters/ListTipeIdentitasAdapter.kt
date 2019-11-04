package com.xsis.android.batch217.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.xsis.android.batch217.R
import com.xsis.android.batch217.models.TipeIdentitas
import com.xsis.android.batch217.utils.showPopupMenuUbahHapus
import com.xsis.android.batch217.viewholders.ViewHolderListTipeIdentitas

class ListTipeIdentitasAdapter(val context:Context, val listTipeIdentitas:List<TipeIdentitas>):RecyclerView.Adapter<ViewHolderListTipeIdentitas>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderListTipeIdentitas {
        val customView = LayoutInflater.from(parent.context).inflate(R.layout.list_dot_layout, parent, false)
        return ViewHolderListTipeIdentitas(customView)
    }

    override fun getItemCount(): Int {
        return listTipeIdentitas.size
    }

    override fun onBindViewHolder(holder: ViewHolderListTipeIdentitas, position: Int) {
        holder.setModel(listTipeIdentitas[position])

        holder.bukaMenu.setOnClickListener {view ->
            val window = showPopupMenuUbahHapus(context, view)
            window.setOnItemClickListener{parent, view, position, id ->

            }
            window.show()
        }
    }
}