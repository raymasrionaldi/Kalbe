package com.xsis.android.batch217.ui.prf_request

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.fragments.PRFWinFragmentAdapter
import com.xsis.android.batch217.utils.CustomViewPager
import com.xsis.android.batch217.utils.OnBackPressedListener

class WinPRFFragment:Fragment(), OnBackPressedListener {
    var viewPager0:ViewPager? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_prf_win, container, false)
        activity!!.title = getString(R.string.prf_win)

        val fragmentAdapter = PRFWinFragmentAdapter(context!!, childFragmentManager)
        viewPager0 = root.findViewById(R.id.viewPagerWinPRF) as CustomViewPager
        viewPager0!!.adapter = fragmentAdapter

        val viewPager = root.findViewById(R.id.viewPagerWinPRF) as CustomViewPager
        viewPager.adapter = fragmentAdapter

        viewPager.setSwipePagingEnabled(false)

        val slidingTabs = root.findViewById(R.id.slidingTabsWinPRF) as TabLayout

        slidingTabs.setupWithViewPager(viewPager)
        slidingTabs.touchables.forEach { view -> view.isEnabled = false }

        return root
    }

    override fun onBackPressed(): Boolean {
        if (viewPager0!!.currentItem !=0) {
            viewPager0!!.setCurrentItem(0, true)
            return true
        }
        return false
    }
}