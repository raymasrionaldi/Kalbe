package com.xsis.android.batch217.ui.jenis_catatan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.xsis.android.batch217.R

class JenisCatatanFragment : Fragment() {

    private lateinit var jenisCatatanViewModel: JenisCatatanViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        jenisCatatanViewModel =
            ViewModelProviders.of(this).get(JenisCatatanViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_jenis_catatan, container, false)
        val textView: TextView = root.findViewById(R.id.text_jenis_catatan)
        jenisCatatanViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}