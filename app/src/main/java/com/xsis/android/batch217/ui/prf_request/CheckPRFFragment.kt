package com.xsis.android.batch217.ui.prf_request

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.fragments.CheckPRFFragmentAdapter
import com.xsis.android.batch217.utils.CustomViewPager
import com.xsis.android.batch217.utils.OnBackPressedListener

class CheckPRFFragment : Fragment(), OnBackPressedListener {
    var viewPager: ViewPager? = null
    private lateinit var viewModel: CheckPrfViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.check_prf_fragment, container, false)
        activity!!.title = getString(R.string.prf_check)

        val fragmentAdapter = CheckPRFFragmentAdapter(context!!, childFragmentManager)
        val viewPager = root.findViewById(R.id.viewPagerCheckPRF) as CustomViewPager
        viewPager.adapter = fragmentAdapter

        viewPager.setSwipePagingEnabled(false)

        val slidingTabs = root.findViewById(R.id.slidingTabsCheckPRF) as TabLayout

        slidingTabs.setupWithViewPager(viewPager)
        slidingTabs.touchables.forEach { view -> view.isEnabled = false }

        return root
    }

    override fun onBackPressed(): Boolean {
        if (viewPager!!.currentItem !=0) {
            viewPager!!.setCurrentItem(0, true)
            return true
        }
        return false
    }

}
