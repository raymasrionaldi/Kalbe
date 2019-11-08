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
import com.xsis.android.batch217.adapters.fragments.ContractStatusFragmentAdapter
import com.xsis.android.batch217.utils.CustomViewPager
import com.xsis.android.batch217.utils.OnBackPressedListener


class ContactStatusFragment : Fragment(),OnBackPressedListener {

    private lateinit var contactStatusViewModel: ContactStatusViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        contactStatusViewModel =
//            ViewModelProviders.of(this).get(ContactStatusViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_contact_status, container, false)
        activity!!.title = getString(R.string.menu_contract_status)
//        val textView: TextView = root.findViewById(R.id.text_contact_status)
//        contactStatusViewModel.text.observe(this, Observer {
//            textView.text = it
//        })
        val tabKontrak = ContractStatusFragmentAdapter(
            context!!,
            childFragmentManager
        )
        val viewPager = root.findViewById(R.id.viewPagerContractStatus) as CustomViewPager
        viewPager.adapter = tabKontrak
        viewPager.setSwipePagingEnabled(false)
        viewPager.setOnTouchListener { v, event -> true  }

        val slidingTabs = root.findViewById(R.id.slidingTabsContractStatus) as TabLayout

        slidingTabs.setupWithViewPager(viewPager)
        slidingTabs.touchables.forEach { view -> view.isEnabled = false }


        return root
    }
    override fun onBackPressed(): Boolean {
        val viewPager = view!!.findViewById(R.id.viewPagerContractStatus) as CustomViewPager
        if (viewPager.currentItem !=0) {
            viewPager.setCurrentItem(0, true)
            return true
        }
        return false
    }
}