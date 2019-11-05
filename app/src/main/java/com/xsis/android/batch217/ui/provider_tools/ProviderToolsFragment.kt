package com.xsis.android.batch217.ui.provider_tools

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.tabs.TabLayout
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.fragments.ProviderToolsFragmentAdapter
import com.xsis.android.batch217.ui.agama.ProviderToolsViewModel
import com.xsis.android.batch217.ui.keahlian.KeahlianViewModel
import com.xsis.android.batch217.utils.CustomViewPager

class ProviderToolsFragment : Fragment() {

    private lateinit var providerToolsViewModel: ProviderToolsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        providerToolsViewModel =
            ViewModelProviders.of(this).get(ProviderToolsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_provider_tools, container, false)

        val fragmentAdapter = ProviderToolsFragmentAdapter(context!!, childFragmentManager)
        val viewPager = root.findViewById(R.id.viewPagerProviderTools) as CustomViewPager
        viewPager.adapter = fragmentAdapter

        viewPager.setSwipePagingEnabled(false)

        val slidingTabs = root.findViewById(R.id.slidingTabsProviderTools) as TabLayout

        slidingTabs.setupWithViewPager(viewPager)
        slidingTabs.touchables.forEach { view -> view.isEnabled = false }

        return root
    }
}