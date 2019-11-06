package com.xsis.android.batch217.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.xsis.android.batch217.R
import com.xsis.android.batch217.models.KeluargaDetail
import com.xsis.android.batch217.viewholders.ViewHolderListKeluargaDetail

class ListKeluargaDetailAdapter(val context: Context, val listKeluargaDetail:List<KeluargaDetail>): RecyclerView.Adapter<ViewHolderListKeluargaDetail>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolderListKeluargaDetail {
        val customLayout =
            LayoutInflater.from(parent.context).inflate(R.layout.list_layout_dua, parent, false)
        return ViewHolderListKeluargaDetail(customLayout)
    }

    override fun getItemCount(): Int {
        return listKeluargaDetail.size
    }

    override fun onBindViewHolder(holder: ViewHolderListKeluargaDetail, position: Int) {
        val model = listKeluargaDetail[position]
        holder.setModel(model)
    }
}