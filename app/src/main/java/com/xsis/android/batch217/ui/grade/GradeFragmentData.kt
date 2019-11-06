package com.xsis.android.batch217.ui.grade

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.ListGradeAdapter
import com.xsis.android.batch217.adapters.fragments.GradeFragmentAdapter
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.GradeQueryHelper
import com.xsis.android.batch217.models.Grade

class GradeFragmentData(context: Context, val fm: FragmentManager) : Fragment() {
    var recyclerView: RecyclerView? = null
    var databaseQueryHelper: GradeQueryHelper? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val customView = inflater.inflate(
            R.layout.fragment_data_grade,
            container,
            false
        )
        setHasOptionsMenu(true)

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        recyclerView = customView.findViewById(R.id.listGradeRecycler) as RecyclerView
        recyclerView!!.layoutManager = layoutManager

        val dividerItemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        recyclerView!!.addItemDecoration(dividerItemDecoration)

        val buttonAdd =
            customView.findViewById(R.id.buttonAddGrade) as FloatingActionButton
        buttonAdd.setOnClickListener {
            addData()
        }

        val databaseHelper = DatabaseHelper(context!!)
        databaseQueryHelper =GradeQueryHelper(databaseHelper)

//        getSemuaGrade(recyclerView!!, databaseQueryHelper!!)

        return customView
    }

    fun addData() {
        val viewPager = view!!.parent as ViewPager
        val adapter = viewPager.adapter!! as GradeFragmentAdapter
        val fragment = fm.fragments[1] as GradeFragmentForm
        fragment.modeAdd()
        adapter.notifyDataSetChanged()
        viewPager.setCurrentItem(1, true)
    }

    fun getSemuaGrade(
        recyclerView: RecyclerView,
        databaseQueryHelper: GradeQueryHelper
    ) {
        val listGrade = databaseQueryHelper.readSemuaGradeModels()
        tampilkanListGrade(listGrade, recyclerView)
    }

    fun tampilkanListGrade(
        listGrade: List<Grade>,
        recyclerView: RecyclerView
    ) {
        val adapterGrade = ListGradeAdapter(context!!, listGrade, fm)
        recyclerView.adapter = adapterGrade
        adapterGrade.notifyDataSetChanged()
    }

    fun updateContent() {
//        getSemuaGrade(recyclerView!!, databaseQueryHelper!!)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main, menu)

        val myActionMenuItem = menu.findItem(R.id.action_search)
        val searchView = myActionMenuItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(keyword: String): Boolean {
                search(keyword, databaseQueryHelper!!)
                return true
            }
        })
    }

    fun search(keyword: String, databaseQueryHelper: GradeQueryHelper) {
        val listGrade = databaseQueryHelper.cariGradeModels(keyword)
        tampilkanListGrade(listGrade, recyclerView!!)
    }
}