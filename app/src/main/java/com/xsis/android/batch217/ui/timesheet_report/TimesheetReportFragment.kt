package com.xsis.android.batch217.ui.timesheet_report

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.fragments.AgamaFragmentAdapter
import com.xsis.android.batch217.utils.CustomViewPager

class TimesheetReportFragment : Fragment() {

    var root:View?=null

    private lateinit var viewModel: TimesheetReportViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_agama, container, false)

        activity!!.title = getString(R.string.menu_agama)

        val fragmentAdapter = AgamaFragmentAdapter(context!!, childFragmentManager)
        val viewPager = root!!.findViewById(R.id.viewPagerAgama) as CustomViewPager
        viewPager.adapter = fragmentAdapter

        viewPager.setSwipePagingEnabled(false)
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TimesheetReportViewModel::class.java)
        // TODO: Use the ViewModel
    }
}
