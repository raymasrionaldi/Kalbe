package com.xsis.android.batch217.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.fragments.SRFFragmentAdapter
import com.xsis.android.batch217.models.SRF
import com.xsis.android.batch217.ui.srf.SRFFragmentForm
import com.xsis.android.batch217.viewholders.ViewHolderListSRF

class ListSRFAdapter (
    val context: Context,
    val listSRF: List<SRF>,
    val fm: FragmentManager
) : RecyclerView.Adapter<ViewHolderListSRF>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderListSRF {
        val customLayout = LayoutInflater.from(parent.context).inflate(R.layout.list_layout, parent, false)
        return ViewHolderListSRF(customLayout)
    }

    override fun getItemCount(): Int {
        return listSRF.size
    }

    override fun onBindViewHolder(holder: ViewHolderListSRF, position: Int) {
        val model = listSRF[position]
        holder.setModel(model)

        holder.layoutList.setOnClickListener { view ->
            val fragment = fm.fragments[1] as SRFFragmentForm
            val viewPager = fragment.view!!.parent as ViewPager
            val adapter = viewPager.adapter!! as SRFFragmentAdapter

            fragment.modeEdit(model)
            adapter.notifyDataSetChanged()
            viewPager.setCurrentItem(1, true)
        }
    }
}
