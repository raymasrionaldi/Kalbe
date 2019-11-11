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

class LeaveRequestFragmentData(context: Context, val fm: FragmentManager):Fragment() {
    val regularLeaveQuota= 16
    val annualLeaveQuota=4

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

        val buttonAdd =
            customView.findViewById(R.id.buttonAddLeaveRequest) as FloatingActionButton
        buttonAdd.setOnClickListener {
            addData()
        }

        val prevLeave: TextView = customView.findViewById(R.id.prevYearQuota) as TextView
            prevLeave.text="0"
        val regularLeave: TextView = customView.findViewById(R.id.regularQuota) as TextView
            regularLeave.text=regularLeaveQuota.toString()
        val annualLeave: TextView = customView.findViewById(R.id.annualQuota) as TextView
            annualLeave.text=annualLeaveQuota.toString()
        val takenLeave: TextView = customView.findViewById(R.id.takenQuota) as TextView
            takenLeave.text="0"
        val remainingLeave: TextView = customView.findViewById(R.id.remainingQuota) as TextView
            remainingLeave.text="0"

        /*
        DATE OPERATIONS
         val dateMasuk= SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(masuk)
         val dateKeluar=SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(keluar)
         val rentangWaktu= Math.abs(dateKeluar.time-dateMasuk.time)
         var lamaJamParkir= TimeUnit.HOURS.convert(rentangWaktu, TimeUnit.MILLISECONDS).toInt()
        val lamaMenitParkir= TimeUnit.MINUTES.convert(rentangWaktu, TimeUnit.MILLISECONDS).toInt()
        val lamaDetikParkir= TimeUnit.SECONDS.convert(rentangWaktu, TimeUnit.MILLISECONDS).toInt()
        var sisaDetik=lamaDetikParkir%60
        var sisaMenit=lamaMenitParkir%60
       */
        return customView
    }

    fun addData() {
        val intent=Intent(context,LeaveRequestAddActivity::class.java)
        startActivity(intent)
//        val viewPager = view!!.parent as ViewPager
//        val adapter = viewPager.adapter!! as LeaveRequestFragmentAdapter
//        val fragment = fm.fragments[1] as LeaveRequestFragmentHistory
//        fragment.modeAdd()
//        adapter.notifyDataSetChanged()
//        viewPager.setCurrentItem(1, true)
    }
}