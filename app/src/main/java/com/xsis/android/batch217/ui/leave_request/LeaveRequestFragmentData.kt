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
import java.time.Duration
import java.time.LocalDate
import java.util.*
import java.util.concurrent.TimeUnit

class LeaveRequestFragmentData(context: Context, val fm: FragmentManager) : Fragment() {
    val currentYear = Calendar.getInstance().get(Calendar.YEAR)
    var regularLeaveQuota = 0
    var annualLeaveQuota = 0

    internal lateinit var databaseQueryHelper: LeaveRequestQueryHelper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val customView = inflater.inflate(
            R.layout.fragment_data_leave_request,
            container,
            false
        )

        val databaseHelper = DatabaseHelper(context!!)
        databaseQueryHelper = LeaveRequestQueryHelper(databaseHelper)

        regularLeaveQuota = databaseQueryHelper.regularLeaveQuota
        annualLeaveQuota = databaseQueryHelper.annualLeaveQuota


        val prevLeaveVal = hitungSisaPrevYearLeave()
        val leaveAlreadyTakenVal = hitungLeaveAlreadyTaken()
        val remainingQuota =
            prevLeaveVal + regularLeaveQuota - (leaveAlreadyTakenVal + annualLeaveQuota)

        val prevLeave: TextView = customView.findViewById(R.id.prevYearQuota) as TextView
        prevLeave.text = prevLeaveVal.toString()

        val regularLeave: TextView = customView.findViewById(R.id.regularQuota) as TextView
        regularLeave.text = regularLeaveQuota.toString()

        val annualLeave: TextView = customView.findViewById(R.id.annualQuota) as TextView
        annualLeave.text = annualLeaveQuota.toString()

        val takenLeave: TextView = customView.findViewById(R.id.takenQuota) as TextView
        takenLeave.text = leaveAlreadyTakenVal.toString()

        val remainingLeave: TextView = customView.findViewById(R.id.remainingQuota) as TextView
        remainingLeave.text = remainingQuota.toString()

        return customView
    }

    fun getPrevYearLeave(): List<LeaveRequest> {
        val prevYear = currentYear - 1
        val listModel = databaseQueryHelper!!.getLeaveDateRangeByYear(prevYear)
        return listModel
    }

    fun getLeaveTaken(): List<LeaveRequest> {
        val listModel = databaseQueryHelper!!.getLeaveDateRangeByYear(currentYear)
        return listModel
    }

    fun isHariBesar() {

    }


    fun hitungSisaPrevYearLeave(): Int {
        val listModel = getPrevYearLeave()
        var prevYearQuota = 0
        var prevYearLeaveTaken = 0
        var lamaHariLeave = 0

        listModel.forEach { model ->
            /*TODO find iterate trough date,
            *  check isHariBesar && isWeekEnd  */
            val dateStart = SimpleDateFormat(DATE_PATTERN).parse(model.start)
            val dateEnd = SimpleDateFormat(DATE_PATTERN).parse(model.end)
            /*val ddStart= dateStart.getDate()
              val ddEnd= dateStart.getDate()
            for(i in ddStart until ddEnd){
                if(!isHariBesar("")&&!isWeekEnd()){
                    lamaHariLeave+=1
            }
            }*/
            var rentangWaktu = Math.abs(dateStart.time - dateEnd.time)
            var lamaHariLeave =
                TimeUnit.DAYS.convert(rentangWaktu, TimeUnit.MILLISECONDS).toInt() + 1
            /* println("BULAN# ${model.start} - ${model.end}")
             println("BULAN# lamahariLeave= $lamaHariLeave")*/
            prevYearLeaveTaken += lamaHariLeave
        }
        prevYearQuota = regularLeaveQuota - (prevYearLeaveTaken + annualLeaveQuota)
        return prevYearQuota
    }


    fun hitungLeaveAlreadyTaken(): Int {
        val listModel = getLeaveTaken()
        var leaveTaken = 0

        listModel.forEach { model ->
            val dateStart = SimpleDateFormat(DATE_PATTERN).parse(model.start)
            val dateEnd = SimpleDateFormat(DATE_PATTERN).parse(model.end)
            var rentangWaktu = Math.abs(dateStart.time - dateEnd.time)

            var lamaHariLeave = TimeUnit.DAYS.convert(rentangWaktu, TimeUnit.MILLISECONDS).toInt() + 1
            println("BULAN# ${model.start} - ${model.end}")
            println("BULAN# lamahariLeave= $lamaHariLeave")
            leaveTaken += lamaHariLeave
        }
        return leaveTaken
    }
}