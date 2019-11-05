package com.xsis.android.batch217.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.fragments.ProviderToolsFragmentAdapter
import com.xsis.android.batch217.models.ProviderTools
import com.xsis.android.batch217.ui.provider_tools.ProviderToolsFragmentForm
import com.xsis.android.batch217.viewholders.ViewHolderListProviderTools

class ListProviderToolsAdapter(val context: Context,
                               val listProviderTools: List<ProviderTools>,
                               val fm: FragmentManager
) : RecyclerView.Adapter<ViewHolderListProviderTools>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderListProviderTools {
        val customLayout =
            LayoutInflater.from(parent.context).inflate(R.layout.list_layout, parent, false)
        return ViewHolderListProviderTools(customLayout)
    }

    override fun getItemCount(): Int {
        return listProviderTools.size
    }

    override fun onBindViewHolder(holder: ViewHolderListProviderTools, position: Int) {
        val model = listProviderTools[position]
        holder.setModel(model)

        holder.layoutList.setOnClickListener { view ->
            val fragment = fm.fragments[1] as ProviderToolsFragmentForm
            val viewPager = fragment.view!!.parent as ViewPager
            val adapter = viewPager.adapter!! as ProviderToolsFragmentAdapter

            fragment.modeEdit(model)
            adapter.notifyDataSetChanged()
            viewPager.setCurrentItem(1, true)
        }
    }
}