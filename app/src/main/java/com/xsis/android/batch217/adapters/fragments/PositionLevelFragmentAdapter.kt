package com.xsis.android.batch217.adapters.fragments

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.xsis.android.batch217.ui.position_level.PositionLevelFragmentData
import com.xsis.android.batch217.ui.position_level.PositionLevelFragmentForm

class PositionLevelFragmentAdapter(val context: Context,val fm: FragmentManager) :
    FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        if (position == 0) {
            return PositionLevelFragmentData(context, fm)
        } else if (position == 1) {
            return PositionLevelFragmentForm(context, fm)
        } else
            return Fragment()
    }

    override fun getCount(): Int {
        //ada 2 tab menu
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Data"
            1 -> "Form"
            else -> ""
        }
    }
}