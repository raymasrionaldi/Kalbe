package com.xsis.android.batch217.adapters.fragments

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.xsis.android.batch217.R
import com.xsis.android.batch217.models.LeaveRequest
import com.xsis.android.batch217.viewholders.ViewHolderListLeaveRequest

class ListLeaveRequestAdapter(val context: Context,
                              val listLeaveRequest: List<LeaveRequest>,
                              val fm: FragmentManager): RecyclerView.Adapter<ViewHolderListLeaveRequest>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderListLeaveRequest {
        val customLayout =
            LayoutInflater.from(parent.context).inflate(R.layout.list_layout, parent, false)
        return ViewHolderListLeaveRequest(customLayout)
    }

    override fun getItemCount(): Int {
        return listLeaveRequest.size
    }

    override fun onBindViewHolder(holder: ViewHolderListLeaveRequest, position: Int) {
        val model = listLeaveRequest[position]
        holder.setModel(model)

       /* holder.layoutList.setOnClickListener { view ->
            val fragment = fm.fragments[1] as LeaveRequestFragmentForm
            val viewPager = fragment.view!!.parent as ViewPager
            val adapter = viewPager.adapter!! as PositionLevelFragmentAdapter

            fragment.modeEdit(model)
            adapter.notifyDataSetChanged()
            viewPager.setCurrentItem(1, true)
        }*/
    }


}