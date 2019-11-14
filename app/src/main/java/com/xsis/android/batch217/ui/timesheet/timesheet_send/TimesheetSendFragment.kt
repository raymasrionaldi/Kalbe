package com.xsis.android.batch217.ui.timesheet.timesheet_send

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.xsis.android.batch217.R

class TimesheetSendFragment : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val customView = inflater.inflate(R.layout.fragment_timesheet_send, container, false)
        activity!!.title = getString(R.string.timesheet)

        return customView
    }


}