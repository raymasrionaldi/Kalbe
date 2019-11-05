package com.xsis.android.batch217.ui.position_level

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.tabs.TabLayout
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.fragments.PositionLevelFragmentAdapter
import com.xsis.android.batch217.utils.CustomViewPager

class PositionLevelFragment : Fragment() {

    private lateinit var positionLevelViewModel: PositionLevelViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        positionLevelViewModel =
            ViewModelProviders.of(this).get(PositionLevelViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_position_level, container, false)

        val fragmentAdapter = PositionLevelFragmentAdapter(context!!, childFragmentManager)
        val viewPager = root.findViewById(R.id.viewPagerPositionLevel) as CustomViewPager
        viewPager.adapter = fragmentAdapter

        viewPager.setSwipePagingEnabled(false)

        val slidingTabs = root.findViewById(R.id.slidingTabsPositionLevel) as TabLayout

        slidingTabs.setupWithViewPager(viewPager)
        slidingTabs.touchables.forEach { view -> view.isEnabled = false }

        return root
    }
}