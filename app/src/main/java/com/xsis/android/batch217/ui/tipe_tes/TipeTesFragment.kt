package com.xsis.android.batch217.ui.tipe_tes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.fragments.TipeTesFragmentAdapter
import com.xsis.android.batch217.utils.CustomViewPager
import com.xsis.android.batch217.utils.OnBackPressedListener

class TipeTesFragment : Fragment(), OnBackPressedListener {

    private lateinit var tipeTesViewModel: TipeTesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        tipeTesViewModel =
            ViewModelProviders.of(this).get(TipeTesViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_tipe_tes, container, false)

        activity!!.title = getString(R.string.menu_tipe_tes)

        val fragmentAdapter = TipeTesFragmentAdapter(context!!, childFragmentManager)
        val viewPager = root.findViewById(R.id.viewPagerTipeTes) as CustomViewPager
        viewPager.adapter = fragmentAdapter

        viewPager.setSwipePagingEnabled(false)

        return root
    }

    override fun onBackPressed(): Boolean {
        val viewPager = view!!.findViewById(R.id.viewPagerTipeTes) as CustomViewPager
        if (viewPager.currentItem !=0) {
            viewPager.setCurrentItem(0, true)
            return true

        }
        return false
    }


    fun refreshList() {
        //getSemuaPendidikan(recyclerView!!, databaseQueryHelper!!)
        getActivity()!!.invalidateOptionsMenu()
    }

    override fun onResume() {
        super.onResume()
        refreshList()

    }
}