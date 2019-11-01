package com.xsis.android.batch217.ui.tipe_tes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.xsis.android.batch217.R

class TipeTesFragment : Fragment() {

    private lateinit var tipeTesViewModel: TipeTesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        tipeTesViewModel =
            ViewModelProviders.of(this).get(TipeTesViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_tipe_tes, container, false)
        val textView: TextView = root.findViewById(R.id.text_tipe_tes)
        tipeTesViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}