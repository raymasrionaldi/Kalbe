package com.xsis.android.batch217.adapters.fragments

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.xsis.android.batch217.ui.prf_request.FragmentCandidateRequestHistory
import com.xsis.android.batch217.ui.prf_request.FragmentDataRequestHistory

class RequestHistoryFragmentAdapter(
    val context: Context,
    val fm: FragmentManager
) : FragmentPagerAdapter(fm)  {
    override fun getItem(position: Int): Fragment {
        if (position == 0){
            return FragmentDataRequestHistory(context,fm)
        }
        else if (position == 1){
            return FragmentCandidateRequestHistory(context,fm)
        }
        else
            return Fragment()
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0 -> "Data"
            1 -> "Candidates"
            else -> ""
        }
    }
}