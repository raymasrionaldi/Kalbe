package com.xsis.android.batch217.ui.project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.fragments.ProjectFragmentAdapter
import com.xsis.android.batch217.utils.CustomViewPager
import com.xsis.android.batch217.utils.OnBackPressedListener

class ProjectFragment : Fragment(), OnBackPressedListener {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_project, container, false)

        activity!!.title = getString(R.string.menu_project)

        val fragmentAdapter = ProjectFragmentAdapter(context!!, childFragmentManager)
        val viewPager = root.findViewById(R.id.viewPagerProject) as CustomViewPager
        viewPager.adapter = fragmentAdapter

        viewPager.setSwipePagingEnabled(false)

        val slidingTabs = root.findViewById(R.id.slidingTabsProject) as TabLayout

        slidingTabs.setupWithViewPager(viewPager)
        slidingTabs.touchables.forEach { view -> view.isEnabled = false }

        return root
    }

    override fun onBackPressed(): Boolean {
        val viewPager = view!!.findViewById(R.id.viewPagerProject) as CustomViewPager
        if (viewPager.currentItem != 0) {
            viewPager.setCurrentItem(0, true)
            return true
        }
        return false
    }
}