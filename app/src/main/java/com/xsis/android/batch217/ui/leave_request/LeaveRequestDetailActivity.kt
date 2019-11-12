package com.xsis.android.batch217.ui.leave_request

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.fragments.LeaveRequestDetailFragmentAdapter
import com.xsis.android.batch217.utils.CustomViewPager
import com.xsis.android.batch217.utils.ID_LEAVE
import com.xsis.android.batch217.utils.ID_PROJECT
import com.xsis.android.batch217.utils.REQUEST_CODE_LEAVE_REQUEST
import kotlinx.android.synthetic.main.activity_leave_request_detail.*

class LeaveRequestDetailActivity : AppCompatActivity() {
    val context = this
    var ID_DETAIL: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leave_request_detail)
        var bundle: Bundle? = intent.extras
        //if bundle not null
        bundle?.let {
            ID_DETAIL = bundle!!.getInt("idLeaveRequest")
//            println("BULAN_ACTIVITY# ${ID_DETAIL.toString()}")
        }

        val fragmentAdapter =
            LeaveRequestDetailFragmentAdapter(context, supportFragmentManager, ID_DETAIL!!)

        val viewPager = findViewById(R.id.viewPagerDetailLeaveRequest) as CustomViewPager
        viewPager.adapter = fragmentAdapter
        viewPager.setSwipePagingEnabled(true)

        val slidingTabs = findViewById(R.id.slidingTabsDetailLeaveRequest) as TabLayout
        slidingTabs.setupWithViewPager(viewPager)
        slidingTabs.touchables.forEach { view -> view.isEnabled = false }

        buttonEditLeaveRequest.setOnClickListener {
            val intent = Intent(context, LeaveRequestEditActivity::class.java)
            intent.putExtra(ID_LEAVE, ID_DETAIL!!)
            startActivityForResult(intent, REQUEST_CODE_LEAVE_REQUEST)
        }

        buttonDeleteLeaveRequest.setOnClickListener {
            /*val intent = Intent(context, LeaveRequestEditActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_LEAVE_REQUEST)*/
        }
    }

}
