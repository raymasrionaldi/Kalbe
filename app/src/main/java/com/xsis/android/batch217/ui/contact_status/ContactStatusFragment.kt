package com.xsis.android.batch217.ui.contact_status

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.xsis.android.batch217.R

class ContactStatusFragment : Fragment() {

    private lateinit var contactStatusViewModel: ContactStatusViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        contactStatusViewModel =
            ViewModelProviders.of(this).get(ContactStatusViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_contact_status, container, false)
        val textView: TextView = root.findViewById(R.id.text_contact_status)
        contactStatusViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}