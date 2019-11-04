package com.xsis.android.batch217.ui.tipe_tes

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
import com.xsis.android.batch217.adapters.ListTipeTesAdapter
import com.xsis.android.batch217.adapters.fragments.TipeTesFragmentAdapter
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.TipeTesQueryHelper
import com.xsis.android.batch217.models.TipeTes

class TipeTesFragmentData(context: Context, val fm: FragmentManager) : Fragment() {
    var recyclerView: RecyclerView? = null
    var databaseQueryHelper: TipeTesQueryHelper? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val customView = inflater.inflate(
            R.layout.fragment_data_tipe_tes,
            container,
            false
        )

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        recyclerView = customView.findViewById(R.id.listTipeTesRecycler) as RecyclerView
        recyclerView!!.layoutManager = layoutManager

        val dividerItemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        recyclerView!!.addItemDecoration(dividerItemDecoration)

        val buttonAdd =
            customView.findViewById(R.id.buttonTambahTipeTes) as FloatingActionButton
        buttonAdd.setOnClickListener {
            addData()
        }

        val databaseHelper = DatabaseHelper(context!!)
        databaseQueryHelper = TipeTesQueryHelper(databaseHelper)

        getSemuaTipeTes(recyclerView!!, databaseQueryHelper!!)

        return customView
    }

    fun addData() {
        val viewPager = view!!.parent as ViewPager
        val adapter = viewPager.adapter!! as TipeTesFragmentAdapter
        val fragment = fm.fragments[1] as TipeTesFragmentForm
        fragment.modeAdd()
        adapter.notifyDataSetChanged()
        viewPager.setCurrentItem(1, true)
    }

    fun getSemuaTipeTes(
        recyclerView: RecyclerView,
        databaseQueryHelper: TipeTesQueryHelper
    ) {
        val listTipeTes = databaseQueryHelper.readSemuaTipeTesModels()
        tampilkanListTipeTes(listTipeTes, recyclerView)
    }

    fun tampilkanListTipeTes(
        listTipeTes: List<TipeTes>,
        recyclerView: RecyclerView
    ) {
        val adapterTipeTes = ListTipeTesAdapter(context!!, listTipeTes, fm)
        recyclerView.adapter = adapterTipeTes
        adapterTipeTes.notifyDataSetChanged()
    }

    fun updateContent() {
        getSemuaTipeTes(recyclerView!!, databaseQueryHelper!!)
    }
}
