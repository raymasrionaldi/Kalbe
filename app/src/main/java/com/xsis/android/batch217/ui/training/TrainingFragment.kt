package com.xsis.android.batch217.ui.training

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.tabs.TabLayout
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.fragments.TrainingFragmentAdapter
import com.xsis.android.batch217.ui.agama.TrainingViewModel
import com.xsis.android.batch217.utils.CustomViewPager

class TrainingFragment : Fragment() {

    private lateinit var trainingViewModel: TrainingViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        trainingViewModel =
            ViewModelProviders.of(this).get(TrainingViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_training, container, false)

        val fragmentAdapter = TrainingFragmentAdapter(context!!, childFragmentManager)
        val viewPager = root.findViewById(R.id.viewPagerTraining) as CustomViewPager

        //tambah tab di atas fragment
        viewPager.adapter = fragmentAdapter

        //nonaktifkan slide
        viewPager.setSwipePagingEnabled(false)

        val slidingTabs = root.findViewById(R.id.slidingTabsTraining) as TabLayout
        slidingTabs.setupWithViewPager(viewPager)

        //nonaktifkan klik tab
        slidingTabs.touchables.forEach { view -> view.isEnabled = false }

        return root
    }
}