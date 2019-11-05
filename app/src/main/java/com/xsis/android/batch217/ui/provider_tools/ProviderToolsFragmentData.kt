package com.xsis.android.batch217.ui.provider_tools

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
import com.xsis.android.batch217.adapters.ListProviderToolsAdapter
import com.xsis.android.batch217.adapters.fragments.ProviderToolsFragmentAdapter
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.ProviderToolsQueryHelper
import com.xsis.android.batch217.models.ProviderTools

class ProviderToolsFragmentData(context: Context, val fm: FragmentManager): Fragment() {
    var recyclerView: RecyclerView? = null
    var databaseQueryHelper: ProviderToolsQueryHelper? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val customView = inflater.inflate(
            R.layout.fragment_data_provider_tools,
            container,
            false
        )

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        recyclerView = customView.findViewById(R.id.listProviderToolsRecycler) as RecyclerView
        recyclerView!!.layoutManager = layoutManager

        val dividerItemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        recyclerView!!.addItemDecoration(dividerItemDecoration)

        val buttonAdd =
            customView.findViewById(R.id.buttonAddProviderTools) as FloatingActionButton
        buttonAdd.setOnClickListener {
            addData()
        }

        val databaseHelper = DatabaseHelper(context!!)
        databaseQueryHelper = ProviderToolsQueryHelper(databaseHelper)

        getSemuaProviderTools(recyclerView!!, databaseQueryHelper!!)

        return customView
    }

    fun addData() {
        val viewPager = view!!.parent as ViewPager
        val adapter = viewPager.adapter!! as ProviderToolsFragmentAdapter
        val fragment = fm.fragments[1] as ProviderToolsFragmentForm
        fragment.modeAdd()
        adapter.notifyDataSetChanged()
        viewPager.setCurrentItem(1, true)
    }

    fun getSemuaProviderTools(
        recyclerView: RecyclerView,
        databaseQueryHelper: ProviderToolsQueryHelper
    ) {
        val listProviderTools = databaseQueryHelper.readSemuaProviderToolsModels()
        tampilkanListProviderTools(listProviderTools, recyclerView)
    }

    fun tampilkanListProviderTools(
        listProviderTools: List<ProviderTools>,
        recyclerView: RecyclerView
    ) {
        val adapterProviderTools = ListProviderToolsAdapter(context!!, listProviderTools, fm)
        recyclerView.adapter = adapterProviderTools
        adapterProviderTools.notifyDataSetChanged()
    }

    fun updateContent() {
        getSemuaProviderTools(recyclerView!!, databaseQueryHelper!!)
    }

}