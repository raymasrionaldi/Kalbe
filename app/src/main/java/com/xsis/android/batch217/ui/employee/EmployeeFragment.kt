package com.xsis.android.batch217.ui.employee

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.tabs.TabLayout
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.fragments.EmployeeTypeFragmentAdapter
import com.xsis.android.batch217.adapters.fragments.GradeFragmentAdapter
import com.xsis.android.batch217.utils.CustomViewPager
import com.xsis.android.batch217.utils.OnBackPressedListener

class EmployeeFragment : Fragment(), OnBackPressedListener {

    private lateinit var employeeViewModel: EmployeeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        employeeViewModel =
            ViewModelProviders.of(this).get(EmployeeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_employee, container, false)

        activity!!.title = getString(R.string.menu_employe_type)

        val fragmentAdapter = EmployeeTypeFragmentAdapter(context!!, childFragmentManager)
        val viewPager = root.findViewById(R.id.viewPagerEmployeeType) as CustomViewPager

        //tambah tab di atas fragment
        viewPager.adapter = fragmentAdapter

        //nonaktifkan slide
        viewPager.setSwipePagingEnabled(false)

        val slidingTabs = root.findViewById(R.id.slidingTabsEmployeeType) as TabLayout
        slidingTabs.setupWithViewPager(viewPager)

        //nonaktifkan klik tab
        slidingTabs.touchables.forEach { view -> view.isEnabled = false }

        return root
    }

    override fun onBackPressed(): Boolean {
        val viewPager = view!!.findViewById(R.id.viewPagerEmployeeType) as CustomViewPager
        if (viewPager.currentItem !=0) {
            viewPager.setCurrentItem(0, true)
            return true
        }
        return false
    }
}
