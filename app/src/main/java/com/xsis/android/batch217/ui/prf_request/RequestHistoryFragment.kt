package com.xsis.android.batch217.ui.prf_request

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayout

import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.fragments.RequestHistoryFragmentAdapter
import com.xsis.android.batch217.utils.CustomViewPager

class RequestHistoryFragment : Fragment() {

    private lateinit var requestHistoryViewModel: RequestHistoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requestHistoryViewModel =
            ViewModelProviders.of(this).get(RequestHistoryViewModel::class.java)
        val root = inflater.inflate(R.layout.request_history_fragment, container, false)
        val tabKontrak = RequestHistoryFragmentAdapter(
            context!!,
            childFragmentManager
        )
        val viewPager = root.findViewById(R.id.viewPagerRequestHistory) as CustomViewPager
        viewPager.adapter = tabKontrak
        viewPager.setSwipePagingEnabled(true)
//        viewPager.setOnTouchListener { v, event -> true  }

        val slidingTabs = root.findViewById(R.id.slidingTabsRequestHistory) as TabLayout

        slidingTabs.setupWithViewPager(viewPager)
        slidingTabs.touchables.forEach { view -> view.isEnabled = true }

        return root
    }

}
