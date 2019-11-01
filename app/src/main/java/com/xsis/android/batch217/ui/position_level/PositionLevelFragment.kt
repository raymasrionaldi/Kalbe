package com.xsis.android.batch217.ui.position_level

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.xsis.android.batch217.R
import com.xsis.android.batch217.ui.position_level.PositionLevelViewModel

class PositionLevelFragment : Fragment() {

    private lateinit var positionLevelViewModel: PositionLevelViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        positionLevelViewModel =
            ViewModelProviders.of(this).get(PositionLevelViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_position_level, container, false)
        val textView: TextView = root.findViewById(R.id.text_position_level)
        positionLevelViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}