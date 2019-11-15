package com.xsis.android.batch217.adapters.fragments

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.xsis.android.batch217.ui.prf_request.CheckPRFData
import com.xsis.android.batch217.ui.prf_request.CheckPRFDetail

class CheckPRFFragmentAdapter(val context: Context, val fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        if (position == 0) {
            return CheckPRFData(context, fm)
        } else if (position == 1) {
            return CheckPRFDetail(context, fm)
        } else
            return Fragment()
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "DATA"
            1 -> "DETAIL"
            else -> ""
        }
    }

}