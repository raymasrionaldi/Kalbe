package com.xsis.android.batch217.ui.timesheet_report

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.fragments.TimesheetReportFragmentAdapter
import com.xsis.android.batch217.utils.CustomViewPager
import com.xsis.android.batch217.utils.OnBackPressedListener

class TimesheetReportFragment : Fragment(), OnBackPressedListener {

    var root:View?=null

//    private lateinit var viewModel: TimesheetReportViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_report_timesheet, container, false)

        activity!!.title = getString(R.string.title_report_timesheet)

        val fragmentAdapter = TimesheetReportFragmentAdapter(context!!, childFragmentManager)
        val viewPager = root!!.findViewById(R.id.viewPagerTimesheetReport) as CustomViewPager
        viewPager.adapter = fragmentAdapter

        viewPager.setSwipePagingEnabled(false)
        return root
    }

    override fun onBackPressed(): Boolean {
        val viewPager = view!!.findViewById(R.id.viewPagerTimesheetReport) as CustomViewPager
        if (viewPager.currentItem !=0) {
            viewPager.setCurrentItem(0, true)
            return true
        }
        return false
    }
}
