package com.xsis.android.batch217.adapters.fragments

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.xsis.android.batch217.ui.employee_training.EmployeeTrainingFragmentData
import com.xsis.android.batch217.ui.employee_training.EmployeeTrainingFragmentForm

class EmployeeTrainingFragmentAdapter(val context: Context, val fm: FragmentManager) :
    FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        if (position == 0) {
            return EmployeeTrainingFragmentData(context, fm)
        } else if (position == 1) {
            return EmployeeTrainingFragmentForm(context, fm)
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

