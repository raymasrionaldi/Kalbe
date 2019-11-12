package com.xsis.android.batch217.ui.leave_request

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.xsis.android.batch217.R
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.LeaveRequestQueryHelper

class LeaveRequestFragmentDetail(context: Context, val fm: FragmentManager,val id_detail:Int): Fragment() {

    var leaveType: TextView? =null
    var leaveName: TextView? =null
    var startOn: TextView? =null
    var endedOn: TextView? =null
    var address: TextView? =null
    var contactNumber: TextView? =null
    var reason: TextView? =null

    var databaseQueryHelper: LeaveRequestQueryHelper? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val customView = inflater.inflate(
            R.layout.fragment_detail_leave_request,
            container,
            false
        )

        val databaseHelper = DatabaseHelper(context!!)
        databaseQueryHelper = LeaveRequestQueryHelper(databaseHelper)

        leaveType= customView.findViewById(R.id.textLeaveType) as TextView
        leaveName= customView.findViewById(R.id.textLeaveName) as TextView
        startOn= customView.findViewById(R.id.textStart) as TextView
        endedOn= customView.findViewById(R.id.textEnd) as TextView
        address= customView.findViewById(R.id.textAddress) as TextView
        contactNumber = customView.findViewById(R.id.textContactNumber) as TextView
        reason= customView.findViewById(R.id.textReason) as TextView

        tampilkanDetail(id_detail)
        return customView
    }

    fun tampilkanDetail(id_detail: Int){
        val listLeaveRequest = databaseQueryHelper!!.getDetailById(id_detail)
        val model = listLeaveRequest[0]
        leaveType!!.text=model.leaveType
        leaveName!!.text=model.leaveName
        startOn!!.text=model.start
        endedOn!!.text=model.end
        address!!.text=model.address
        contactNumber!!.text=model.contact
        reason!!.text=model.reason
    }
}