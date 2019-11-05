package com.xsis.android.batch217.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.xsis.android.batch217.R
import com.xsis.android.batch217.models.JenisCatatan
import com.xsis.android.batch217.viewholders.ViewHolderListJenisCatatan

class ListJenisCatatanAdapter(
    val context: Context,
    val listJenisCatatan: List<JenisCatatan>,
    val fm: FragmentManager
) : RecyclerView.Adapter<ViewHolderListJenisCatatan>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderListJenisCatatan {
        val customLayout =
            LayoutInflater.from(parent.context).inflate(R.layout.list_dot_layout, parent, false)
        return ViewHolderListJenisCatatan(customLayout)
    }

    override fun getItemCount(): Int {
        return listJenisCatatan.size
    }

    override fun onBindViewHolder(holder: ViewHolderListJenisCatatan, position: Int) {
        val model = listJenisCatatan[position]
        holder.setModel(model)
    }
}