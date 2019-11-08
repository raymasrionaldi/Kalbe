package com.xsis.android.batch217.ui.agama

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.fragments.AgamaFragmentAdapter
import com.xsis.android.batch217.utils.CustomViewPager
import com.xsis.android.batch217.utils.OnBackPressedListener

class AgamaFragment : Fragment(), OnBackPressedListener {

    //private lateinit var agamaViewModel: AgamaViewModel
    var root:View?=null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        agamaViewModel =
//            ViewModelProviders.of(this).get(AgamaViewModel::class.java)
        root = inflater.inflate(R.layout.fragment_agama, container, false)

        activity!!.title = getString(R.string.menu_agama)

        val fragmentAdapter = AgamaFragmentAdapter(context!!, childFragmentManager)
        val viewPager = root!!.findViewById(R.id.viewPagerAgama) as CustomViewPager
        viewPager.adapter = fragmentAdapter

        viewPager.setSwipePagingEnabled(false)

        return root
    }

    override fun onBackPressed(): Boolean {
        val viewPager = view!!.findViewById(R.id.viewPagerAgama) as CustomViewPager
        if (viewPager.currentItem !=0) {
            viewPager.setCurrentItem(0, true)
            return true
        }
        return false
    }
}