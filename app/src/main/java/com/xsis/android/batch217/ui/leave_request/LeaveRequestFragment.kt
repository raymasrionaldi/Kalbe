package com.xsis.android.batch217.ui.leave_request

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayout

import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.fragments.LeaveRequestFragmentAdapter
import com.xsis.android.batch217.utils.CustomViewPager
import com.xsis.android.batch217.utils.OnBackPressedListener

class LeaveRequestFragment() : Fragment(), OnBackPressedListener {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        leaveRequstViewModel =
//            ViewModelProviders.of(this).get(LeaveRequestViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_leave_request, container, false)

        activity!!.title = getString(R.string.menu_leave_request)
        val fragmentAdapter = LeaveRequestFragmentAdapter(context!!, childFragmentManager)
        val viewPager = root.findViewById(R.id.viewPagerLeaveRequest) as CustomViewPager
        viewPager.adapter = fragmentAdapter

        viewPager.setSwipePagingEnabled(true)

        val slidingTabs = root.findViewById(R.id.slidingTabsLeaveRequest) as TabLayout

        slidingTabs.setupWithViewPager(viewPager)
        slidingTabs.touchables.forEach { view -> view.isEnabled = false }

        return root
    }

    override fun onBackPressed(): Boolean {
        val viewPager = view!!.findViewById(R.id.viewPagerLeaveRequest) as CustomViewPager
        if (viewPager.currentItem !=0) {
            viewPager.setCurrentItem(0, true)
            return true
        }
        return false
    }

}
