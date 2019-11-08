package com.xsis.android.batch217.adapters.fragments

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import com.xsis.android.batch217.ui.timesheet.timesheet_history.FragmentDataHistoryTimesheet
import com.xsis.android.batch217.ui.timesheet.timesheet_history.FragmentDetailHistoryTimesheet
import com.xsis.android.batch217.ui.training_organizer.FragmentDataTrainingOrganizer
import com.xsis.android.batch217.ui.training_organizer.FragmentFormTrainingOrganizer

class TimesheetHistoryAdapter (
    val context: Context,
    val fm: androidx.fragment.app.FragmentManager
) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        if (position == 0){
            return FragmentDataHistoryTimesheet(context,fm)
        }
        else if (position == 1){
            return FragmentDetailHistoryTimesheet(context,fm)
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
            1 -> "Detail Activity"
            else -> ""
        }
    }
}