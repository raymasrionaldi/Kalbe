package com.xsis.android.batch217.ui.back_office_position

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.xsis.android.batch217.ui.back_office_position.BackOfficePositionViewModel
import com.xsis.android.batch217.R

class BackOfficePositionFragment : Fragment() {

    private lateinit var backOfficePositionViewModel: BackOfficePositionViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        backOfficePositionViewModel =
            ViewModelProviders.of(this).get(BackOfficePositionViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_back_office_position, container, false)
        val textView: TextView = root.findViewById(R.id.text_back_office_position)
        backOfficePositionViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}