package com.xsis.android.batch217.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.fragments.CheckPRFFragmentAdapter
import com.xsis.android.batch217.models.CheckPRF
import com.xsis.android.batch217.ui.prf_request.CheckPRFDetail
import com.xsis.android.batch217.viewholders.ViewHolderListCheckPRF

class ListCheckPRFAdapter(val context: Context, val listProjectCreate:List<CheckPRF>, val fm: FragmentManager): RecyclerView.Adapter<ViewHolderListCheckPRF>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderListCheckPRF {
        val customLayout =
            LayoutInflater.from(parent.context).inflate(R.layout.list_dot_layout_dua, parent, false)
        return ViewHolderListCheckPRF(customLayout)
    }

    override fun getItemCount(): Int {
        return listProjectCreate.size
    }

    override fun onBindViewHolder(holder: ViewHolderListCheckPRF, position: Int) {
        val model = listProjectCreate[position]
        holder.setModel(model)
        val ID = model.id_prf_request

        holder.layoutList.setOnClickListener { view ->

            val fragment = fm.fragments[1] as CheckPRFDetail
            val viewPager = fragment.view!!.parent as ViewPager
            val adapter = viewPager.adapter!! as CheckPRFFragmentAdapter

            fragment.bawaID(ID)
            adapter.notifyDataSetChanged()
            viewPager.setCurrentItem(1, true)
        }
    }
}