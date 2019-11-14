package com.xsis.android.batch217.ui.leave_request

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.fragments.LeaveRequestFragmentAdapter
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.LeaveRequestQueryHelper
import com.xsis.android.batch217.models.LeaveRequest
import com.xsis.android.batch217.utils.DATE_PATTERN
import java.text.SimpleDateFormat
import java.util.concurrent.TimeUnit

class LeaveRequestFragmentData(context: Context, val fm: FragmentManager):Fragment() {
    var regularLeaveQuota=0
    var annualLeaveQuota=0

    internal lateinit var databaseQueryHelper: LeaveRequestQueryHelper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?{
        val customView = inflater.inflate(
            R.layout.fragment_data_leave_request,
            container,
            false
        )

        val databaseHelper= DatabaseHelper(context!!)
        databaseQueryHelper = LeaveRequestQueryHelper(databaseHelper)

        regularLeaveQuota=databaseQueryHelper.regularLeaveQuota
        annualLeaveQuota=databaseQueryHelper.annualLeaveQuota


        var prevLeaveVal= hitungSisaPrevYearLeave()
//        var leaveAlreadyTakenVal = hitungLeaveAlreadyTaken()

        val prevLeave: TextView = customView.findViewById(R.id.prevYearQuota) as TextView
            prevLeave.text=prevLeaveVal.toString()

        val regularLeave: TextView = customView.findViewById(R.id.regularQuota) as TextView
            regularLeave.text=regularLeaveQuota.toString()

        val annualLeave: TextView = customView.findViewById(R.id.annualQuota) as TextView
            annualLeave.text=annualLeaveQuota.toString()

        val takenLeave: TextView = customView.findViewById(R.id.takenQuota) as TextView
            takenLeave.text="0"

        val remainingLeave: TextView = customView.findViewById(R.id.remainingQuota) as TextView
            remainingLeave.text="0"

        return customView
    }

    fun getPrevYearLeave():List<LeaveRequest>{
        val listModel = databaseQueryHelper!!.getPrevYearLeave()
        return listModel
    }

    /*fun getLeaveThisYear():List<LeaveRequest>{
        val listModel = databaseQueryHelper!!.getCurYearLeave()
        return listModel
    }*/

    fun hitungSisaPrevYearLeave():Int{
        val listModel=getPrevYearLeave()
        var remainingPrevYearQuota=0
        var prevYearLeaveTaken=0

        listModel.forEach {model->
            println("BULAN# ${model.start}")
            val dateStart= SimpleDateFormat(DATE_PATTERN).parse(model.start)
            val dateEnd=SimpleDateFormat(DATE_PATTERN).parse(model.end)
            var rentangWaktu= Math.abs(dateStart.time-dateEnd.time) //TODO check tanpa .toInt
            var lamaHariLeave= TimeUnit.DAYS.convert(rentangWaktu, TimeUnit.MILLISECONDS).toInt()
            prevYearLeaveTaken +=lamaHariLeave
        }
        remainingPrevYearQuota=regularLeaveQuota-(prevYearLeaveTaken+annualLeaveQuota)
        return remainingPrevYearQuota
    }

    /*fun hitungLeaveAlreadyTaken():Int{
        val listModel=getLeaveThisYear()
        var leaveTaken=0

        return leaveTaken
    }*/
}