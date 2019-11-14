package com.xsis.android.batch217.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.fragments.PRFWinFragmentAdapter
import com.xsis.android.batch217.models.PRFWin
import com.xsis.android.batch217.ui.prf_request.WinPRFDetail
import com.xsis.android.batch217.viewholders.ViewHolderListPRFWin

class ListPRFWinAdapter(val context: Context, val listProjectCreate:List<PRFWin>, val fm: FragmentManager): RecyclerView.Adapter<ViewHolderListPRFWin>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderListPRFWin {
        val customLayout =
            LayoutInflater.from(parent.context).inflate(R.layout.list_dot_layout_dua, parent, false)
        return ViewHolderListPRFWin(customLayout)
    }

    override fun getItemCount(): Int {
        return listProjectCreate.size
    }

    override fun onBindViewHolder(holder: ViewHolderListPRFWin, position: Int) {
        val model = listProjectCreate[position]
        holder.setModel(model)
        val ID = model.id_prf_request

        holder.layoutList.setOnClickListener { view ->

            val fragment = fm.fragments[1] as WinPRFDetail
            val viewPager = fragment.view!!.parent as ViewPager
            val adapter = viewPager.adapter!! as PRFWinFragmentAdapter

            fragment.bawaID(ID)
            adapter.notifyDataSetChanged()
            viewPager.setCurrentItem(1, true)
        }
    }
}