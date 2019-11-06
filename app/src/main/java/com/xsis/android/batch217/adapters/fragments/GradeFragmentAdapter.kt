package com.xsis.android.batch217.adapters.fragments

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.xsis.android.batch217.ui.grade.GradeFragmentData
import com.xsis.android.batch217.ui.grade.GradeFragmentForm

class GradeFragmentAdapter(val context: Context,val fm: FragmentManager) :
    FragmentPagerAdapter(fm) {
    override fun getItem(grade: Int): Fragment {
        if (grade == 0) {
            return GradeFragmentData(context, fm)
        } else if (grade == 1) {
            return GradeFragmentForm(context, fm)
        } else
            return Fragment()
    }

    override fun getCount(): Int {
        //ada 2 tab menu
        return 2
    }

    override fun getPageTitle(grade: Int): CharSequence? {
        return when (grade) {
            0 -> "Data"
            1 -> "Form"
            else -> ""
        }
    }
}