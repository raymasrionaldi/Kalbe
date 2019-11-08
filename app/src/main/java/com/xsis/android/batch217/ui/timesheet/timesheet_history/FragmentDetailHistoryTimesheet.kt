package com.xsis.android.batch217.ui.timesheet.timesheet_history

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.xsis.android.batch217.R

class FragmentDetailHistoryTimesheet (context: Context, val fm: FragmentManager) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val customView =
            inflater.inflate(R.layout.fragment_detail_history_timesheet, container, false)

        return customView
    }
}