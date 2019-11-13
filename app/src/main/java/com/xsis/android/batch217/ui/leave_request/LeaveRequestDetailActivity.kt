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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leave_request_detail)

        //get id detail
        var bundle: Bundle? = intent.extras
        var idDetail:Int = 0
        bundle?.let {
            idDetail = bundle!!.getInt(ID_LEAVE)
//          println("BULAN_ACTIVITY# ${idDetail.toString()}")
        }

        val fragmentAdapter =
            LeaveRequestDetailFragmentAdapter(context, supportFragmentManager, idDetail)

        val viewPager = findViewById(R.id.viewPagerDetailLeaveRequest) as CustomViewPager
        viewPager.adapter = fragmentAdapter
        viewPager.setSwipePagingEnabled(true)

        val slidingTabs = findViewById(R.id.slidingTabsDetailLeaveRequest) as TabLayout
        slidingTabs.setupWithViewPager(viewPager)
        slidingTabs.touchables.forEach { view -> view.isEnabled = false }

        buttonEditLeaveRequest.setOnClickListener {
            val intent = Intent(context, LeaveRequestEditActivity::class.java)
            intent.putExtra(ID_LEAVE, idDetail)
            startActivityForResult(intent, REQUEST_CODE_LEAVE_REQUEST)
        }

        buttonDeleteLeaveRequest.setOnClickListener {
            /*val intent = Intent(context, LeaveRequestEditActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_LEAVE_REQUEST)*/
        }
    }

}
