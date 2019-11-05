package com.xsis.android.batch217.ui.jenis_catatan

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
import com.xsis.android.batch217.adapters.ListJenisCatatanAdapter
import com.xsis.android.batch217.adapters.fragments.JenisCatatanFragmentAdapter
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.JenisCatatanQueryHelper
import com.xsis.android.batch217.models.JenisCatatan

class JenisCatatanFragmentData(context: Context, val fm: FragmentManager) : Fragment() {
    var recyclerView: RecyclerView? = null
    var databaseQueryHelper: JenisCatatanQueryHelper? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val customView = inflater.inflate(
            R.layout.fragment_data_jenis_catatan,
            container,
            false
        )

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        recyclerView = customView.findViewById(R.id.listJenisCatatanRecycler) as RecyclerView
        recyclerView!!.layoutManager = layoutManager

        val dividerItemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        recyclerView!!.addItemDecoration(dividerItemDecoration)

        val buttonAdd =
            customView.findViewById(R.id.buttonAddJenisCatatan) as FloatingActionButton
        buttonAdd.setOnClickListener {
            addData()
        }

        val databaseHelper = DatabaseHelper(context!!)
        databaseQueryHelper = JenisCatatanQueryHelper(databaseHelper)

        getSemuaJenisCatatan(recyclerView!!, databaseQueryHelper!!)

        return customView
    }

    fun addData() {
        val viewPager = view!!.parent as ViewPager
        val adapter = viewPager.adapter!! as JenisCatatanFragmentAdapter
        val fragment = fm.fragments[1] as JenisCatatanFragmentForm
        fragment.modeAdd()
        adapter.notifyDataSetChanged()
        viewPager.setCurrentItem(1, true)
    }

    fun getSemuaJenisCatatan(
        recyclerView: RecyclerView,
        databaseQueryHelper: JenisCatatanQueryHelper
    ) {
        val listJenisCatatan = databaseQueryHelper.readSemuaJenisCatatanModels()
        tampilkanListJenisCatatan(listJenisCatatan, recyclerView)
    }

    fun tampilkanListJenisCatatan(
        listJenisCatatan: List<JenisCatatan>,
        recyclerView: RecyclerView
    ) {
        val adapterJenisCatatan = ListJenisCatatanAdapter(context!!, listJenisCatatan, fm)
        recyclerView.adapter = adapterJenisCatatan
        adapterJenisCatatan.notifyDataSetChanged()
    }

    fun updateContent() {
        getSemuaJenisCatatan(recyclerView!!, databaseQueryHelper!!)
    }
}