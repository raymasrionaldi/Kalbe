package com.xsis.android.batch217.ui.leave_request

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.fragments.LeaveRequestDetailFragmentAdapter
import com.xsis.android.batch217.utils.CustomViewPager

class LeaveRequestDetailActivity : AppCompatActivity() {
    val context = this
    var ID_DETAIL: Int?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leave_request_detail)
        var bundle:Bundle?= intent.extras
        //if bundle not null
        bundle?.let {
            ID_DETAIL=bundle!!.getInt("idLeaveRequest")
            println("BULAN_ACTIVITY# ${ID_DETAIL.toString()}")
        }

        val fragmentAdapter = LeaveRequestDetailFragmentAdapter(context,supportFragmentManager,ID_DETAIL!!)

        val viewPager = findViewById(R.id.viewPagerDetailLeaveRequest) as CustomViewPager
        viewPager.adapter = fragmentAdapter
        viewPager.setSwipePagingEnabled(true)

        val slidingTabs = findViewById(R.id.slidingTabsDetailLeaveRequest) as TabLayout
        slidingTabs.setupWithViewPager(viewPager)
        slidingTabs.touchables.forEach { view -> view.isEnabled = false }


    }

}
