package com.xsis.android.batch217.ui.prf_status

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.fragments.PRFStatusFragmentAdapter
import com.xsis.android.batch217.utils.CustomViewPager
import com.xsis.android.batch217.utils.OnBackPressedListener

class PRFStatusFragment : Fragment(), OnBackPressedListener {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val customView = inflater.inflate(R.layout.fragment_prf_status, container, false)
        activity!!.title = "PRF Status"

        val viewPagerAdapter = PRFStatusFragmentAdapter(context!!, childFragmentManager)
        val viewPager = customView.findViewById(R.id.viewPagerPRFStatus) as CustomViewPager
        viewPager.adapter = viewPagerAdapter
        viewPager.setSwipePagingEnabled(false)

        val slidingTab = customView.findViewById(R.id.slidingTabsPRFStatus) as TabLayout
        slidingTab.setupWithViewPager(viewPager)
        slidingTab.touchables.forEach { view -> view.isEnabled = false  }

        return customView
    }

    override fun onBackPressed(): Boolean {
        val viewPager = view!!.findViewById(R.id.viewPagerPRFStatus) as CustomViewPager
        if (viewPager.currentItem !=0) {
            viewPager.setCurrentItem(0, true)
            return true
        }
        return false
    }
}