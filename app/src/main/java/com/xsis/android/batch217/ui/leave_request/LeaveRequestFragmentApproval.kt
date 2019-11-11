package com.xsis.android.batch217.ui.leave_request

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.xsis.android.batch217.R

class LeaveRequestFragmentApproval(context: Context, val fm: FragmentManager): Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val customView = inflater.inflate(
            R.layout.fragment_approval_leave_request,
            container,
            false
        )
        return customView
    }
}