package com.xsis.android.batch217.ui.contact_status


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.Fragment

import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.ListContractStatusAdapter


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
//        val textView: TextView = root.findViewById(R.id.text_contact_status)
//        contactStatusViewModel.text.observe(this, Observer {
//            textView.text = it
//        })
        val tabKontrak = ListContractStatusAdapter(context!!, fragmentManager!!)
        val viewPager: ViewPager = root.findViewById(R.id.viewPager) as ViewPager
        viewPager.adapter = tabKontrak
        viewPager.setOnTouchListener { v, event -> true  }

        val slidingTabs = root.findViewById(R.id.slidingTabs) as TabLayout

        slidingTabs.setupWithViewPager(viewPager)
        slidingTabs.touchables.forEach { view -> view.isEnabled = false }


        return root
    }
}