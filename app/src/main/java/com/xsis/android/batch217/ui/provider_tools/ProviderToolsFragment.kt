package com.xsis.android.batch217.ui.provider_tools

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.xsis.android.batch217.R
import com.xsis.android.batch217.ui.agama.ProviderToolsViewModel
import com.xsis.android.batch217.ui.keahlian.KeahlianViewModel

class ProviderToolsFragment : Fragment() {

    private lateinit var providerToolsViewModel: ProviderToolsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        providerToolsViewModel =
            ViewModelProviders.of(this).get(ProviderToolsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_provider_tools, container, false)
        val textView: TextView = root.findViewById(R.id.text_provider_tools)
        providerToolsViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}