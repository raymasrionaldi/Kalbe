package com.xsis.android.batch217.ui.keahlian

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.xsis.android.batch217.R

class KeahlianFragment : Fragment() {

    private lateinit var keahlianViewModel: KeahlianViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        keahlianViewModel =
            ViewModelProviders.of(this).get(KeahlianViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_keahlian, container, false)
        val textView: TextView = root.findViewById(R.id.text_keahlian)
        keahlianViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}