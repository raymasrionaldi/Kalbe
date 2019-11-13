package com.xsis.android.batch217.ui.timesheet.timesheet_history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.tabs.TabLayout
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.fragments.TimesheetHistoryAdapter
import com.xsis.android.batch217.utils.CustomViewPager
import com.xsis.android.batch217.utils.OnBackPressedListener

class TimesheetHistoryFragment: Fragment(), OnBackPressedListener {
    private lateinit var timesheetHistoryViewModel: TimesheetHistoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        timesheetHistoryViewModel =
            ViewModelProviders.of(this).get(TimesheetHistoryViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_history_timesheet, container, false)
//        val textView: TextView = root.findViewById(R.id.text_training)
//        trainingViewModel.text.observe(this, Observer {
//            textView.text = it
//        })
        activity!!.title = getString(R.string.timesheet)
        val tabTimesheetEntry = TimesheetHistoryAdapter(
            context!!,
            childFragmentManager
        )
        val viewPager = root.findViewById(R.id.viewPagerTimesheetHistory) as CustomViewPager
        viewPager.adapter = tabTimesheetEntry
        viewPager.setSwipePagingEnabled(false)
        viewPager.setOnTouchListener { v, event -> true  }

        val slidingTabs = root.findViewById(R.id.slidingTabsTimesheetHistory) as TabLayout

        slidingTabs.setupWithViewPager(viewPager)
        slidingTabs.touchables.forEach { view -> view.isEnabled = false }

        return root
    }
    override fun onBackPressed(): Boolean {
        val viewPager = view!!.findViewById(R.id.viewPagerTimesheetHistory) as CustomViewPager
        if (viewPager.currentItem !=0) {
            viewPager.setCurrentItem(0, true)
            return true
        }
        return false
    }
}