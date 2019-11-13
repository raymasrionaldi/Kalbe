package com.xsis.android.batch217.ui.leave_request

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout

import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.fragments.AgamaFragmentAdapter
import com.xsis.android.batch217.adapters.fragments.LeaveRequestFragmentAdapter
import com.xsis.android.batch217.ui.agama.AgamaFragmentData
import com.xsis.android.batch217.ui.project.ProjectFormActivity
import com.xsis.android.batch217.utils.*

class LeaveRequestFragment() : Fragment(), OnBackPressedListener {

    var viewPager:CustomViewPager? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //leaveRequstViewModel = ViewModelProviders.of(this).get(LeaveRequestViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_leave_request, container, false)

        activity!!.title = getString(R.string.menu_leave_request)
        val fragmentAdapter = LeaveRequestFragmentAdapter(context!!, childFragmentManager)
        viewPager = root.findViewById(R.id.viewPagerLeaveRequest) as CustomViewPager
        viewPager!!.adapter = fragmentAdapter
        viewPager!!.setSwipePagingEnabled(true)

        val slidingTabs = root.findViewById(R.id.slidingTabsLeaveRequest) as TabLayout
        slidingTabs.setupWithViewPager(viewPager)
        slidingTabs.touchables.forEach { view -> view.isEnabled = false }

        val buttonAdd =
            root.findViewById(R.id.buttonAddLeaveRequest) as FloatingActionButton
        buttonAdd.setOnClickListener {
            addData()
        }

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

    fun addData() {
        val intent = Intent(context, LeaveRequestAddActivity::class.java)
        activity!!.startActivityForResult(intent, REQUEST_CODE_LEAVE_REQUEST)
    }

    /*override fun onResume() {
        super.onResume()
        viewPager!!.setCurrentItem(0, true)
    }*/

}
