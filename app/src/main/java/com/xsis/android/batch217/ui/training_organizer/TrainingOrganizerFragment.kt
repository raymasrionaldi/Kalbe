package com.xsis.android.batch217.ui.training_organizer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.fragments.TrainingOrganizerFragmentAdapter
import com.xsis.android.batch217.utils.CustomViewPager
import com.xsis.android.batch217.utils.OnBackPressedListener

class TrainingOrganizerFragment: Fragment(), OnBackPressedListener {
    private lateinit var trainingViewModel: TrainingOrganizerViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        trainingViewModel =
//            ViewModelProviders.of(this).get(TrainingOrganizerViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_training_organizer, container, false)
        activity!!.title = getString(R.string.training_organizer)
//        val textView: TextView = root.findViewById(R.id.text_training)
//        trainingViewModel.text.observe(this, Observer {
//            textView.text = it
//        })
        val tabKontrak = TrainingOrganizerFragmentAdapter(
            context!!,
            childFragmentManager
        )
        val viewPager = root.findViewById(R.id.viewPagerTrainingOrg) as CustomViewPager
        viewPager.adapter = tabKontrak
        viewPager.setSwipePagingEnabled(false)
        viewPager.setOnTouchListener { v, event -> true  }

        val slidingTabs = root.findViewById(R.id.slidingTabsTrainingOrg) as TabLayout

        slidingTabs.setupWithViewPager(viewPager)
        slidingTabs.touchables.forEach { view -> view.isEnabled = false }

        return root
    }
    override fun onBackPressed(): Boolean {
        val viewPager = view!!.findViewById(R.id.viewPagerTrainingOrg) as CustomViewPager
        if (viewPager.currentItem !=0) {
            viewPager.setCurrentItem(0, true)
            return true
        }
        return false
    }
}