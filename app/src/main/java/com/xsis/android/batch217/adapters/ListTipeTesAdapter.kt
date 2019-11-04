package com.xsis.android.batch217.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.fragments.TipeTesFragmentAdapter
import com.xsis.android.batch217.models.TipeTes
import com.xsis.android.batch217.ui.tipe_tes.TipeTesFragmentForm
import com.xsis.android.batch217.viewholders.ViewHolderListTipeTes

class ListTipeTesAdapter(
    val context: Context,
    val listTipeTes: List<TipeTes>,
    val fm: FragmentManager
) : RecyclerView.Adapter<ViewHolderListTipeTes>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderListTipeTes {
        val customLayout =
            LayoutInflater.from(parent.context).inflate(R.layout.list_dot_layout, parent, false)
        return ViewHolderListTipeTes(customLayout)
    }

    override fun getItemCount(): Int {
        return listTipeTes.size
    }

    override fun onBindViewHolder(holder: ViewHolderListTipeTes, position: Int) {
        val model = listTipeTes[position]
        holder.setModel(model)


    }
}