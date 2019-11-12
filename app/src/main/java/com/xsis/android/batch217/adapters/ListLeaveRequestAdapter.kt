package com.xsis.android.batch217.adapters.fragments

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.xsis.android.batch217.R
import com.xsis.android.batch217.models.LeaveRequest
import com.xsis.android.batch217.ui.leave_request.LeaveRequestDetailActivity
import com.xsis.android.batch217.ui.leave_request.LeaveRequestFragmentDetail
import com.xsis.android.batch217.ui.position_level.PositionLevelFragmentForm
import com.xsis.android.batch217.utils.ID_LEAVE
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

        holder.layoutList.setOnClickListener { view ->
            val intent= Intent(context, LeaveRequestDetailActivity::class.java)
            intent.putExtra(ID_LEAVE, model.idLeaveRequest)
            context.startActivity(intent)

            //            val fragment = fm.fragments[1] as LeaveRequestFragmentDetail
            //            val viewPager = fragment.view!!.parent as ViewPager
            //            val adapter = viewPager.adapter!! as LeaveRequestDetailFragmentAdapter
            //
            //            fragment.setDetail(model)
            //            adapter.notifyDataSetChanged()
            //            viewPager.setCurrentItem(1, true)
        }
    }


}