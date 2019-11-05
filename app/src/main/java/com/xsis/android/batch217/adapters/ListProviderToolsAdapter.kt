package com.xsis.android.batch217.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.xsis.android.batch217.R
import com.xsis.android.batch217.models.ProviderTools
import com.xsis.android.batch217.viewholders.ViewHolderListProviderTools

class ListProviderToolsAdapter(val context: Context,
                               val listProviderTools: List<ProviderTools>,
                               val fm: FragmentManager
) : RecyclerView.Adapter<ViewHolderListProviderTools>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderListProviderTools {
        val customLayout =
            LayoutInflater.from(parent.context).inflate(R.layout.list_dot_layout, parent, false)
        return ViewHolderListProviderTools(customLayout)
    }

    override fun getItemCount(): Int {
        return listProviderTools.size
    }

    override fun onBindViewHolder(holder: ViewHolderListProviderTools, position: Int) {
        val model = listProviderTools[position]
        holder.setModel(model)
    }
}