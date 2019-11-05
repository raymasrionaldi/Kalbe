package com.xsis.android.batch217.ui.jenis_catatan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.fragments.JenisCatatanFragmentAdapter
import com.xsis.android.batch217.utils.CustomViewPager


class JenisCatatanFragment: Fragment() {

    private lateinit var jenisCatatanViewModel: JenisCatatanViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        jenisCatatanViewModel =
            ViewModelProviders.of(this).get(JenisCatatanViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_jenis_catatan, container, false)
        val fragmentAdapter = JenisCatatanFragmentAdapter(context!!, childFragmentManager)
        val viewPager = root.findViewById(R.id.viewPagerJenisCatatan) as CustomViewPager
        viewPager.adapter = fragmentAdapter

        viewPager.setSwipePagingEnabled(false)

        return root
    }
}