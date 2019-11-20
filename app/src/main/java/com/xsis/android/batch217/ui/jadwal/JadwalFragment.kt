package com.xsis.android.batch217.ui.jadwal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.fragments.JadwalFragmentAdapter
import com.xsis.android.batch217.utils.CustomViewPager
import com.xsis.android.batch217.utils.OnBackPressedListener

class JadwalFragment : Fragment(), OnBackPressedListener {

    private lateinit var jadwalViewModel: JadwalViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        jadwalViewModel =
            ViewModelProviders.of(this).get(JadwalViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_jadwal, container, false)

        activity!!.title = getString(R.string.menu_jadwal)

        val fragmentAdapter = JadwalFragmentAdapter(context!!, childFragmentManager)
        val viewPager = root.findViewById(R.id.viewPagerJadwal) as CustomViewPager
        viewPager.adapter = fragmentAdapter

        viewPager.setSwipePagingEnabled(false)

        return root
    }

    override fun onBackPressed(): Boolean {
        val viewPager = view!!.findViewById(R.id.viewPagerJadwal) as CustomViewPager
        if (viewPager.currentItem !=0) {
            viewPager.setCurrentItem(0, true)
            return true

        }
        return false
    }

}