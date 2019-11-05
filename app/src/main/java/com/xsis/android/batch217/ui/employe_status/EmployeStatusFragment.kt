package com.xsis.android.batch217.ui.employe_status

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.tabs.TabLayout
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.fragments.EmployeeStatusFragmentAdapter
import com.xsis.android.batch217.utils.CustomViewPager

class EmployeStatusFragment : Fragment() {

    private lateinit var employeeStatusViewModel: EmployeStatusViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        employeeStatusViewModel =
            ViewModelProviders.of(this).get(EmployeStatusViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_employe_status, container, false)

        val fragmentAdapter = EmployeeStatusFragmentAdapter(context!!, childFragmentManager)
        val viewPager = root.findViewById(R.id.viewPagerEmployeeStatus) as CustomViewPager

        //tambah tab di atas fragment
        viewPager.adapter = fragmentAdapter

        //nonaktifkan slide
        viewPager.setSwipePagingEnabled(false)

        val slidingTabs = root.findViewById(R.id.slidingTabsEmployeeStatus) as TabLayout
        slidingTabs.setupWithViewPager(viewPager)

        //nonaktifkan klik tab
        slidingTabs.touchables.forEach { view -> view.isEnabled = false }

        return root
    }
}
