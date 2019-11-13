package com.xsis.android.batch217.ui.project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.fragments.ProjectCreateFragmentAdapter
import com.xsis.android.batch217.utils.CustomViewPager
import com.xsis.android.batch217.utils.OnBackPressedListener

class ProjectFragmentCreate:Fragment(), OnBackPressedListener {
    var modeTab = 0
    var viewPager0: ViewPager? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_project_create, container, false)
        activity!!.title = getString(R.string.menu_project)

        val fragmentAdapter = ProjectCreateFragmentAdapter(context!!, childFragmentManager)

        viewPager0 = root.findViewById(R.id.viewPagerProjectCreate) as CustomViewPager
        viewPager0!!.adapter = fragmentAdapter

        val viewPager = root.findViewById(R.id.viewPagerProjectCreate) as CustomViewPager
        viewPager.adapter = fragmentAdapter

        viewPager.setSwipePagingEnabled(false)

        val slidingTabs = root.findViewById(R.id.slidingTabsProjectCreate) as TabLayout

        slidingTabs.setupWithViewPager(viewPager)
        slidingTabs.touchables.forEach { view -> view.isEnabled = false }

        viewPager.setCurrentItem(modeTab)

        return root
    }

    fun pindahTab(tabIndex:Int){
        //Dipanggil di HomeActivity
        //Untuk pindah tab. Nilainya hanya 0 (tab DATA) atau 1 (tab FORM).
        modeTab = tabIndex
//        viewPager0!!.setCurrentItem(modeTab)
    }




    override fun onBackPressed(): Boolean {
        if (viewPager0!!.currentItem !=0) {
            viewPager0!!.setCurrentItem(0, true)
            return true
        }
        return false
    }
}