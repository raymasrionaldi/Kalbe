package com.xsis.android.batch217.ui.employe_status

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.xsis.android.batch217.R

class EmployeStatusFragment: Fragment() {
    private lateinit var employeStatusViewModel: EmployeStatusViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        employeStatusViewModel =
            ViewModelProviders.of(this).get(EmployeStatusViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_employe_status, container, false)
        val textView: TextView = root.findViewById(R.id.text_employe_status)
        employeStatusViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}