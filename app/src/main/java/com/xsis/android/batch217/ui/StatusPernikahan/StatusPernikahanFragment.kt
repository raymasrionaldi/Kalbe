package com.xsis.android.batch217.ui.StatusPernikahan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.fragments.StatusPernikahanFragmentAdapter
import com.xsis.android.batch217.utils.CustomViewPager
import com.xsis.android.batch217.utils.OnBackPressedListener

class StatusPernikahanFragment: Fragment(), OnBackPressedListener {

    private lateinit var statusPernikahanViewModel: StatusPernikahanViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        statusPernikahanViewModel = ViewModelProviders.of(this).get(StatusPernikahanViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_status_pernikahan, container, false)

        activity!!.title = getString(R.string.menu_status_pernikahan)

        val fragmentAdapter = StatusPernikahanFragmentAdapter(context!!, childFragmentManager)
        val viewPager = root.findViewById(R.id.viewPagerStatusPernikahan) as CustomViewPager
        viewPager.adapter = fragmentAdapter
        viewPager.setSwipePagingEnabled(false)
        return root
    }

    override fun onBackPressed(): Boolean {
        val viewPager = view!!.findViewById(R.id.viewPagerStatusPernikahan) as CustomViewPager
        if (viewPager.currentItem !=0) {
            viewPager.setCurrentItem(0, true)
            return true
        }
        return false
    }
}