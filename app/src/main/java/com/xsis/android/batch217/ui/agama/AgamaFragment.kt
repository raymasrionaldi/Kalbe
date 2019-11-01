package com.xsis.android.batch217.ui.agama

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.xsis.android.batch217.R

class AgamaFragment : Fragment() {

    private lateinit var agamaViewModel: AgamaViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        agamaViewModel =
            ViewModelProviders.of(this).get(AgamaViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_agama, container, false)
        val textView: TextView = root.findViewById(R.id.text_agama)
        agamaViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}