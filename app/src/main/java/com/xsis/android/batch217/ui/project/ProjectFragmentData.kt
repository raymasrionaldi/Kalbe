package com.xsis.android.batch217.ui.project

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
import com.xsis.android.batch217.adapters.ListPositionLevelAdapter
import com.xsis.android.batch217.adapters.fragments.PositionLevelFragmentAdapter
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.PositionLevelQueryHelper
import com.xsis.android.batch217.models.PositionLevel

class ProjectFragmentData(context: Context, val fm: FragmentManager) : Fragment() {
    var recyclerView: RecyclerView? = null
    var databaseQueryHelper: PositionLevelQueryHelper? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val customView = inflater.inflate(
            R.layout.fragment_data_position_level,
            container,
            false
        )
        setHasOptionsMenu(true)

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        recyclerView = customView.findViewById(R.id.listPositionLevelRecycler) as RecyclerView
        recyclerView!!.layoutManager = layoutManager

        val dividerItemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        recyclerView!!.addItemDecoration(dividerItemDecoration)

        val buttonAdd =
            customView.findViewById(R.id.buttonAddPositionLevel) as FloatingActionButton
        buttonAdd.setOnClickListener {
            addData()
        }

        val databaseHelper = DatabaseHelper(context!!)
        databaseQueryHelper = PositionLevelQueryHelper(databaseHelper)

//        getSemuaPositionLevel(recyclerView!!, databaseQueryHelper!!)

        return customView
    }

    fun addData() {
        TODO("ganti pakai activity")
/*        val viewPager = view!!.parent as ViewPager
        val adapter = viewPager.adapter!! as PositionLevelFragmentAdapter
        val fragment = fm.fragments[1] as PositionLevelFragmentForm
        fragment.modeAdd()
        adapter.notifyDataSetChanged()
        viewPager.setCurrentItem(1, true)*/
    }

/*    fun getSemuaPositionLevel(
        recyclerView: RecyclerView,
        databaseQueryHelper: PositionLevelQueryHelper
    ) {
        val listProject = databaseQueryHelper.readSemuaProjectModels()
        tampilkanListPositionLevel(listProject, recyclerView)
    }*/

    fun tampilkanListProject(
        listPositionLevel: List<PositionLevel>,
        recyclerView: RecyclerView
    ) {
        context?.let {
            val adapterPositionLevel = ListPositionLevelAdapter(context!!, listPositionLevel, fm)
            recyclerView.adapter = adapterPositionLevel
            adapterPositionLevel.notifyDataSetChanged()
        }
    }

    fun updateContent() {
//        getSemuaPositionLevel(recyclerView!!, databaseQueryHelper!!)
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
//                search(keyword, databaseQueryHelper!!)
                return true
            }
        })
    }

    /*fun search(keyword: String, databaseQueryHelper: PositionLevelQueryHelper) {
        val listProject = databaseQueryHelper.cariProjectModels(keyword)
        tampilkanListProject(listProject, recyclerView!!)
    }*/
}