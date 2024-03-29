package com.xsis.android.batch217.ui.company

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.fragments.CompanyFragmentAdapter
import com.xsis.android.batch217.utils.CustomViewPager
import com.xsis.android.batch217.utils.OnBackPressedListener

class CompanyFragment : Fragment(), OnBackPressedListener {

//    private lateinit var companyViewModel: CompanyViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        companyViewModel =
//            ViewModelProviders.of(this).get(CompanyViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_company, container, false)

        activity!!.title = getString(R.string.menu_company)

        val fragmentAdapter = CompanyFragmentAdapter(context!!, childFragmentManager)
        val viewPager = root.findViewById(R.id.viewPagerCompany) as CustomViewPager

        //tambah tab di atas fragment
        viewPager.adapter = fragmentAdapter

        //nonaktifkan slide
        viewPager.setSwipePagingEnabled(false)

        val slidingTabs = root.findViewById(R.id.slidingTabsCompany) as TabLayout
        slidingTabs.setupWithViewPager(viewPager)

        //nonaktifkan klik tab
        slidingTabs.touchables.forEach { view -> view.isEnabled = false }

        return root
    }

    override fun onBackPressed(): Boolean {
        val viewPager = view!!.findViewById(R.id.viewPagerCompany) as CustomViewPager
        if (viewPager.currentItem !=0) {
            viewPager.setCurrentItem(0, true)
            return true
        }
        return false
    }
}