package com.xsis.android.batch217.adapters.fragments

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.xsis.android.batch217.ui.leave_request.LeaveRequestFragmentApproval
import com.xsis.android.batch217.ui.leave_request.LeaveRequestFragmentData
import com.xsis.android.batch217.ui.leave_request.LeaveRequestFragmentDetail
import com.xsis.android.batch217.ui.leave_request.LeaveRequestFragmentHistory

class LeaveRequestDetailFragmentAdapter(val context: Context, val fm: FragmentManager, val id_detail:Int):
    FragmentPagerAdapter(fm){
    override fun getItem(position: Int): Fragment {
        if (position == 0) {
            return LeaveRequestFragmentDetail(context, fm,id_detail)
        } else if (position == 1) {
            return LeaveRequestFragmentApproval(context, fm)
        } else
            return Fragment()
    }

    override fun getCount(): Int {
        //ada 2 tab menu
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Detail"
            1 -> "Approval"
            else -> ""
        }
    }

}