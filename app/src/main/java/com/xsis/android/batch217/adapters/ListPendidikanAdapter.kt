package com.xsis.android.batch217.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.xsis.android.batch217.R
import com.xsis.android.batch217.models.Pendidikan
import com.xsis.android.batch217.utils.showPopupMenuUbahHapus
import com.xsis.android.batch217.viewholders.ViewHolderListPendidikan
import kotlinx.android.synthetic.main.popup_layout.view.*

class ListPendidikanAdapter(val context: Context?, val listPendidikan:List<Pendidikan>): RecyclerView.Adapter<ViewHolderListPendidikan>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderListPendidikan {
        val customView = LayoutInflater.from(parent.context).inflate(R.layout.list_dot_layout, parent, false)
        return ViewHolderListPendidikan(customView)
    }

    override fun getItemCount(): Int {
        return listPendidikan.size
    }

    override fun onBindViewHolder(holder: ViewHolderListPendidikan, position: Int) {
        holder.setPendidikan(listPendidikan[position])

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