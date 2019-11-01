package com.xsis.android.batch217.ui.jenjang_pendidikan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.xsis.android.batch217.R

class JenjangPendidikanFragment : Fragment() {

    private lateinit var jenjangPendidikanViewModel: JenjangPendidikanViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        jenjangPendidikanViewModel =
            ViewModelProviders.of(this).get(JenjangPendidikanViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_agama, container, false)
        val textView: TextView = root.findViewById(R.id.text_agama)
        jenjangPendidikanViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}