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
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.fragments.LeaveRequestFragmentAdapter
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.LeaveRequestQueryHelper
import com.xsis.android.batch217.models.LeaveRequest
import com.xsis.android.batch217.utils.*
import java.text.SimpleDateFormat
import java.time.Duration
import java.time.LocalDate
import java.util.*
import java.util.concurrent.TimeUnit

class LeaveRequestFragmentData(context: Context, val fm: FragmentManager) : Fragment() {
    /*val currentYear = Calendar.getInstance().get(Calendar.YEAR)
    var regularLeaveQuota = 0
    var annualLeaveQuota = 0*/

    internal lateinit var databaseQueryHelper: LeaveRequestQueryHelper
    var remainingQuota:Int=0
    var prevLeaveVal:Int=0
    var leaveAlreadyTakenVal:Int=0

    var prevLeave: TextView? = null
    var regularLeave: TextView? =null
    var annualLeave: TextView? =null
    var takenLeave: TextView?=null
    var remainingLeave: TextView? =null


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

        /*regularLeaveQuota = databaseQueryHelper.regularLeaveQuota
        annualLeaveQuota = databaseQueryHelper.annualLeaveQuota*/

        prevLeaveVal = hitungSisaPrevYearLeave(databaseQueryHelper)
        leaveAlreadyTakenVal = hitungLeaveAlreadyTaken(databaseQueryHelper)
        remainingQuota = prevLeaveVal + regularLeaveQuota - (leaveAlreadyTakenVal + annualLeaveQuota)

        println("BULAN#FDATA_prevLeaveVal= $prevLeaveVal")
        println("BULAN#FDATA_leaveAlreadyTakenVal= $leaveAlreadyTakenVal")
        println("BULAN#FDATA_remainingQuota= $remainingQuota")

        prevLeave= customView.findViewById(R.id.prevYearQuota) as TextView
        prevLeave!!.text = prevLeaveVal.toString()

        regularLeave = customView.findViewById(R.id.regularQuota) as TextView
        regularLeave!!.text = regularLeaveQuota.toString()

        annualLeave = customView.findViewById(R.id.annualQuota) as TextView
        annualLeave!!.text = annualLeaveQuota.toString()

        takenLeave= customView.findViewById(R.id.takenQuota) as TextView
        takenLeave!!.text = leaveAlreadyTakenVal.toString()

        remainingLeave = customView.findViewById(R.id.remainingQuota) as TextView
        remainingLeave!!.text = remainingQuota.toString()

        /*val prevLeave: TextView = customView.findViewById(R.id.prevYearQuota) as TextView
        prevLeave.text = prevLeaveVal.toString()

        val regularLeave: TextView = customView.findViewById(R.id.regularQuota) as TextView
        regularLeave.text = regularLeaveQuota.toString()

        val annualLeave: TextView = customView.findViewById(R.id.annualQuota) as TextView
        annualLeave.text = annualLeaveQuota.toString()

        val takenLeave: TextView = customView.findViewById(R.id.takenQuota) as TextView
        takenLeave.text = leaveAlreadyTakenVal.toString()

        val remainingLeave: TextView = customView.findViewById(R.id.remainingQuota) as TextView
        remainingLeave.text = remainingQuota.toString()*/

        return customView
    }

    fun tampilkanData(){
        prevLeave!!.text = prevLeaveVal.toString()
        regularLeave!!.text = regularLeaveQuota.toString()
        annualLeave!!.text = annualLeaveQuota.toString()
        takenLeave!!.text = leaveAlreadyTakenVal.toString()
        remainingLeave!!.text = remainingQuota.toString()
    }

    override fun onResume() {
        super.onResume()

        prevLeaveVal = hitungSisaPrevYearLeave(databaseQueryHelper)
        leaveAlreadyTakenVal = hitungLeaveAlreadyTaken(databaseQueryHelper)
        remainingQuota =
            prevLeaveVal + regularLeaveQuota - (leaveAlreadyTakenVal + annualLeaveQuota)

        tampilkanData()
    }


    /*fun getLeaveTaken(): List<LeaveRequest> {
        val listModel = databaseQueryHelper!!.getLeaveDateRangeByYear(currentYear)
        return listModel
    }*/

    /*fun getPrevYearLeave(): List<LeaveRequest> {
        val prevYear = currentYear - 1
        val listModel = databaseQueryHelper!!.getLeaveDateRangeByYear(prevYear)
        return listModel
    }*/

    /*fun hitungSisaPrevYearLeave(): Int {
        val listModel = getPrevYearLeave()
        var prevYearQuota = 0
        var prevYearLeaveTaken = 0

        listModel.forEach { model ->
            val dateStart = SimpleDateFormat(DATE_PATTERN).parse(model.start)
            val dateEnd = SimpleDateFormat(DATE_PATTERN).parse(model.end)

            var rentangWaktu = Math.abs(dateStart.time - dateEnd.time)
            var lamaHariLeave =
                TimeUnit.DAYS.convert(rentangWaktu, TimeUnit.MILLISECONDS).toInt() + 1

            prevYearLeaveTaken += lamaHariLeave
        }
        prevYearQuota = regularLeaveQuota - (prevYearLeaveTaken + annualLeaveQuota)
        return prevYearQuota
    }*/


    /*fun hitungLeaveAlreadyTaken(): Int {
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
    }*/
}