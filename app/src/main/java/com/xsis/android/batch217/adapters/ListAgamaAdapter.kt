package com.xsis.android.batch217.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.xsis.android.batch217.R
import com.xsis.android.batch217.models.Agama
import com.xsis.android.batch217.utils.showPopupMenuUbahHapus
import com.xsis.android.batch217.viewholders.ViewHolderListAgama
import kotlinx.android.synthetic.main.popup_layout.view.*

class ListAgamaAdapter(val context: Context?, val listAgama :List<Agama>): RecyclerView.Adapter<ViewHolderListAgama>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderListAgama {
        val customView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_dot_layout,parent,false)

        return  ViewHolderListAgama(customView)
    }

    override fun getItemCount(): Int {
        return listAgama.size
    }

    override fun onBindViewHolder(holder: ViewHolderListAgama, position: Int) {
        holder.setModel(listAgama[position])

        holder.bukaMenu.setOnClickListener { view ->
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