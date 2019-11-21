package com.xsis.android.batch217.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.fragments.PRFStatusFragmentAdapter
import com.xsis.android.batch217.models.PRFStatus
import com.xsis.android.batch217.ui.prf_status.PRFStatusFragmentForm
import com.xsis.android.batch217.viewholders.ViewHolderListPRFStatus

class ListPRFStatusAdapter(val context: Context, val listPRFStatus:List<PRFStatus>, val fm: FragmentManager): RecyclerView.Adapter<ViewHolderListPRFStatus>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderListPRFStatus {
        val customLayout =
            LayoutInflater.from(parent.context).inflate(R.layout.list_layout_dua, parent, false)
        return ViewHolderListPRFStatus(customLayout)
    }

    override fun getItemCount(): Int {
        return listPRFStatus.size
    }

    override fun onBindViewHolder(holder: ViewHolderListPRFStatus, position: Int) {
        val model = listPRFStatus[position]
        val ID = model.idPRFStatus

        holder.setModel(model)

        holder.layoutList.setOnClickListener {
            val fragment = fm.fragments[1] as PRFStatusFragmentForm
            val viewPager = fragment.view!!.parent as ViewPager
            val adapter = viewPager.adapter as PRFStatusFragmentAdapter

            fragment.setIDValue(ID)
            adapter.notifyDataSetChanged()
            viewPager.setCurrentItem(1, true)
        }
    }
}