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
import com.xsis.android.batch217.utils.OnBackPressedListener

class RequestHistoryFragment : Fragment(), OnBackPressedListener {

    private lateinit var requestHistoryViewModel: RequestHistoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requestHistoryViewModel =
            ViewModelProviders.of(this).get(RequestHistoryViewModel::class.java)
        val root = inflater.inflate(R.layout.request_history_fragment, container, false)

        activity!!.title = getString(R.string.menu_PRF)

        val tabData = RequestHistoryFragmentAdapter(
            context!!,
            childFragmentManager
        )
        val viewPager = root.findViewById(R.id.viewPagerRequestHistory) as CustomViewPager
        viewPager.adapter = tabData
        viewPager.setSwipePagingEnabled(false)
//        viewPager.setOnTouchListener { v, event -> true  }

        val slidingTabs = root.findViewById(R.id.slidingTabsRequestHistory) as TabLayout

        slidingTabs.setupWithViewPager(viewPager)
        slidingTabs.touchables.forEach { view -> view.isEnabled = false }

        return root
    }

    override fun onBackPressed(): Boolean {
        val viewPager = view!!.findViewById(R.id.viewPagerRequestHistory) as CustomViewPager
        if (viewPager.currentItem !=0) {
            viewPager.setCurrentItem(0, true)
            return true
        }
        return false
    }

}
