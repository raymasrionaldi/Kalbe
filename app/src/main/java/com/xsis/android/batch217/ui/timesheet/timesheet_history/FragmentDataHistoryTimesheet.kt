package com.xsis.android.batch217.ui.timesheet.timesheet_history

import android.content.Context
import android.content.Intent
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
import com.xsis.android.batch217.adapters.ListTimesheetAdapter
import com.xsis.android.batch217.adapters.fragments.TimesheetHistoryAdapter
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.TimesheetQueryHelper
import com.xsis.android.batch217.models.Timesheet
import com.xsis.android.batch217.ui.timesheet.timesheet_entry.EntryTimesheetActivity

class FragmentDataHistoryTimesheet (context: Context, val fm: FragmentManager): Fragment() {
    var recyclerView: RecyclerView? = null
    var databaseQueryHelper: TimesheetQueryHelper? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val customView = inflater.inflate(
            R.layout.fragment_data_history_timesheet,
            container,
            false
        )

        setHasOptionsMenu(true)

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        recyclerView = customView.findViewById(R.id.listDataHistoryTimesheet) as RecyclerView
        recyclerView!!.layoutManager = layoutManager

        val dividerItemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        recyclerView!!.addItemDecoration(dividerItemDecoration)

        val buttonAdd =
            customView.findViewById(R.id.buttonPlusHistoryTimesheet) as FloatingActionButton
        buttonAdd.setOnClickListener {
            addData()
        }

        val databaseHelper = DatabaseHelper(context!!)
        databaseQueryHelper = TimesheetQueryHelper(databaseHelper)

//        getSemuaProject(recyclerView!!, databaseQueryHelper!!)

        return customView
    }

    fun addData() {
        val intent = Intent(context, EntryTimesheetActivity::class.java)
        startActivity(intent)
    }

/*    fun getSemuaProject(
        recyclerView: RecyclerView,
        databaseQueryHelper: ProjectQueryHelper
    ) {
        val listProject = databaseQueryHelper.readSemuaProjectModels()
        tampilkanListProject(listProject, recyclerView)
    }*/

    fun tampilkanListTimesheet(
        listProject: List<Timesheet>,
        recyclerView: RecyclerView
    ) {
        context?.let {
            val adapterProject = ListTimesheetAdapter(context!!, listProject, fm)
            recyclerView.adapter = adapterProject
            adapterProject.notifyDataSetChanged()
        }
    }
    fun updateContent() {
        //getSemuaEmployeeTraining(recyclerView!!, databaseQueryHelper!!)
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

    fun search(keyword: String, databaseQueryHelper: TimesheetQueryHelper) {
        val listProject = databaseQueryHelper.cariTimesheetModels(keyword)
        tampilkanListTimesheet(listProject, recyclerView!!)
    }

    override fun onResume() {
        super.onResume()

        activity!!.invalidateOptionsMenu()
    }
}



