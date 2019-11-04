package com.xsis.android.batch217.ui.company

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
import com.xsis.android.batch217.adapters.ListCompanyAdapter
import com.xsis.android.batch217.adapters.fragments.CompanyFragmentAdapter
import com.xsis.android.batch217.databases.CompanyQueryHelper
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.models.Company

class CompanyFragmentData(context: Context, val fm: FragmentManager) : Fragment() {
    var recyclerView: RecyclerView? = null
    var databaseQueryHelper: CompanyQueryHelper? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val customView = inflater.inflate(
            R.layout.fragment_data_company,
            container,
            false
        )

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        recyclerView = customView.findViewById(R.id.listCompanyRecycler) as RecyclerView
        recyclerView!!.layoutManager = layoutManager

        val dividerItemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        recyclerView!!.addItemDecoration(dividerItemDecoration)

        val buttonAdd =
            customView.findViewById(R.id.buttonAddCompany) as FloatingActionButton
        buttonAdd.setOnClickListener {
            addData()
        }

        val databaseHelper = DatabaseHelper(context!!)
        databaseQueryHelper = CompanyQueryHelper(databaseHelper)

        getSemuaCompany(recyclerView!!, databaseQueryHelper!!)

        return customView
    }

    fun addData() {
        val viewPager = view!!.parent as ViewPager
        val adapter = viewPager.adapter!! as CompanyFragmentAdapter
        val fragment = fm.fragments[1] as CompanyFragmentForm
        fragment.modeAdd()
        adapter.notifyDataSetChanged()
        viewPager.setCurrentItem(1, true)
    }

    fun getSemuaCompany(
        recyclerView: RecyclerView,
        databaseQueryHelper: CompanyQueryHelper
    ) {
        val listCompany = databaseQueryHelper.readSemuaCompanyModels()
        tampilkanListCompany(listCompany, recyclerView)
    }

    fun tampilkanListCompany(
        listCompany: List<Company>,
        recyclerView: RecyclerView
    ) {
        val adapterCompany = ListCompanyAdapter(context!!, listCompany, fm)
        recyclerView.adapter = adapterCompany
        adapterCompany.notifyDataSetChanged()
    }

    fun updateContent() {
        getSemuaCompany(recyclerView!!, databaseQueryHelper!!)
    }
}