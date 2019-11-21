package com.xsis.android.batch217.ui.back_office_position

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.fragments.BackOfficeFragmentAdapter
import com.xsis.android.batch217.databases.BackOfficeQueryHelper
import com.xsis.android.batch217.databases.DatabaseHelper

class BackOfficeFragmentData(context: Context, val fm: FragmentManager) : Fragment() {
    var recyclerView: RecyclerView? = null
    var databaseQueryHelper: BackOfficeQueryHelper? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val customView = inflater.inflate(
            R.layout.fragment_data_backoffice,
            container,
            false
        )
        setHasOptionsMenu(true)

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        recyclerView = customView.findViewById(R.id.listBackOfficeRecycler) as RecyclerView
        recyclerView!!.layoutManager = layoutManager

        val dividerItemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        recyclerView!!.addItemDecoration(dividerItemDecoration)

        val buttonAdd =
            customView.findViewById(R.id.buttonAddBackOffice) as FloatingActionButton
        buttonAdd.setOnClickListener {
            addData()

        }

        val databaseHelper = DatabaseHelper(context!!)
        databaseQueryHelper = BackOfficeQueryHelper(databaseHelper)

//        getSemuaCompany(recyclerView!!, databaseQueryHelper!!)

        return customView
    }
    fun addData() {
        val viewPager = view!!.parent as ViewPager
        val adapter = viewPager.adapter!! as BackOfficeFragmentAdapter
        val fragment = fm.fragments[1] as BackOfficeFragmentForm
        fragment.modeAdd()
        adapter.notifyDataSetChanged()
        viewPager.setCurrentItem(1, true)
    }
}