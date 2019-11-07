package com.xsis.android.batch217.ui.timesheet.timesheet_entry

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.tabs.TabLayout
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.fragments.TimesheetEntryAdapter
import com.xsis.android.batch217.utils.CustomViewPager

class TimesheetEntryFragment: Fragment() {
    private lateinit var timesheetEntryViewModel: TimesheetEntryViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        timesheetEntryViewModel =
            ViewModelProviders.of(this).get(TimesheetEntryViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_entry_timesheet, container, false)
//        val textView: TextView = root.findViewById(R.id.text_training)
//        trainingViewModel.text.observe(this, Observer {
//            textView.text = it
//        })
        val tabTimesheetEntry = TimesheetEntryAdapter(
            context!!,
            childFragmentManager
        )
        val viewPager = root.findViewById(R.id.viewPagerTimesheetEntry) as CustomViewPager
        viewPager.adapter = tabTimesheetEntry
        viewPager.setSwipePagingEnabled(false)
        viewPager.setOnTouchListener { v, event -> true  }

        val slidingTabs = root.findViewById(R.id.slidingTabsTimesheetEntry) as TabLayout

        slidingTabs.setupWithViewPager(viewPager)
        slidingTabs.touchables.forEach { view -> view.isEnabled = false }

        return root
    }
}