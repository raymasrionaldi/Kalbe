package com.xsis.android.batch217.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.fragments.BackOfficeFragmentAdapter
import com.xsis.android.batch217.models.BackOfficePosition
import com.xsis.android.batch217.ui.back_office_position.BackOfficeFragmentForm
import com.xsis.android.batch217.viewholders.ViewHolderListBackOffice

class ListBackOfficeAdapter (
    val context: Context,
    val listBackOffice: List<BackOfficePosition>,
    val fm: FragmentManager
) : RecyclerView.Adapter<ViewHolderListBackOffice>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderListBackOffice {
        val customLayout =
            LayoutInflater.from(parent.context).inflate(R.layout.list_layout, parent, false)
        return ViewHolderListBackOffice(customLayout)
    }
    override fun getItemCount(): Int {
        return listBackOffice.size
    }
    override fun onBindViewHolder(holder: ViewHolderListBackOffice, position: Int) {
        val model = listBackOffice[position]
        holder.setModel(model)

        holder.layoutList.setOnClickListener { view ->
            val fragment = fm.fragments[1] as BackOfficeFragmentForm
            val viewPager = fragment.view!!.parent as ViewPager
            val adapter = viewPager.adapter!! as BackOfficeFragmentAdapter

            fragment.modeEdit(model)
            adapter.notifyDataSetChanged()
            viewPager.setCurrentItem(1, true)
        }
    }
}