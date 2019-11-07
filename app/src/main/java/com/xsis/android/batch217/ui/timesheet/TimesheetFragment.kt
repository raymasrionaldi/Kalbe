package com.xsis.android.batch217.ui.timesheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.xsis.android.batch217.R

class TimesheetFragment: Fragment() {
    private lateinit var timesheetViewModel: TimesheetViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        timesheetViewModel =
//            ViewModelProviders.of(this).get(TimesheetViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_timesheet, container, false)

        return root
    }
}