package com.xsis.android.batch217.ui.agama

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.fragments.AgamaFragmentAdapter
import com.xsis.android.batch217.utils.CustomViewPager

class AgamaFragment : Fragment() {

    //private lateinit var agamaViewModel: AgamaViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        agamaViewModel =
//            ViewModelProviders.of(this).get(AgamaViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_agama, container, false)
        val fragmentAdapter = AgamaFragmentAdapter(context!!, childFragmentManager)
        val viewPager = root.findViewById(R.id.viewPagerAgama) as CustomViewPager
        viewPager.adapter = fragmentAdapter

        viewPager.setSwipePagingEnabled(false)

        return root
    }
}