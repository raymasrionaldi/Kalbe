package com.xsis.android.batch217.adapters.fragments

import android.app.FragmentManager
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import com.xsis.android.batch217.ui.training_organizer.FragmentDataTrainingOrganizer
import com.xsis.android.batch217.ui.training_organizer.FragmentFormTrainingOrganizer

class TimesheetEntryAdapter (
    val context: Context,
    val fm: androidx.fragment.app.FragmentManager
) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        if (position == 0){
            return FragmentDataTrainingOrganizer(context,fm)
        }
        else if (position == 1){
            return FragmentFormTrainingOrganizer(context,fm)
        }
        else
            return Fragment()


    }

    override fun getCount(): Int {
        //ada 2 tab menu
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0 -> "Data"
            1 -> "Form"
            else -> ""
        }
    }
}