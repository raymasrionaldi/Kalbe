package com.xsis.android.batch217.ui.employee_training

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
import com.xsis.android.batch217.adapters.ListEmployeeTrainingAdapter
import com.xsis.android.batch217.adapters.fragments.EmployeeTrainingFragmentAdapter
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.EmployeeTrainingQueryHelper
import com.xsis.android.batch217.models.EmployeeTraining

class EmployeeTrainingFragmentData(context: Context, val fm: FragmentManager) : Fragment() {
    var recyclerView: RecyclerView? = null
    var databaseQueryHelper: EmployeeTrainingQueryHelper? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val customView = inflater.inflate(
            R.layout.fragment_data_employee_training,
            container,
            false
        )
        setHasOptionsMenu(true)

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        recyclerView = customView.findViewById(R.id.listEmployeeTrainingRecycler) as RecyclerView
        recyclerView!!.layoutManager = layoutManager

        val dividerItemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        recyclerView!!.addItemDecoration(dividerItemDecoration)

        val buttonAdd =
            customView.findViewById(R.id.buttonAddEmployeeTraining) as FloatingActionButton
        buttonAdd.setOnClickListener {
            addData()
        }

        val databaseHelper = DatabaseHelper(context!!)
        databaseQueryHelper = EmployeeTrainingQueryHelper(databaseHelper)

        //getSemuaEmployeeTraining(recyclerView!!, databaseQueryHelper!!)

        return customView
    }

    fun addData() {
        val viewPager = view!!.parent as ViewPager
        val adapter = viewPager.adapter!! as EmployeeTrainingFragmentAdapter
        val fragment = fm.fragments[1] as EmployeeTrainingFragmentForm
        fragment.modeAdd()
        adapter.notifyDataSetChanged()
        viewPager.setCurrentItem(1, true)
    }

    fun getSemuaEmployeeTraining(
        recyclerView: RecyclerView,
        databaseQueryHelper: EmployeeTrainingQueryHelper
    ) {
        val listEmployeeTraining = databaseQueryHelper.readSemuaEmployeeTrainingModels()
        tampilkanListEmployeeTraining(listEmployeeTraining, recyclerView)
    }

    fun tampilkanListEmployeeTraining(
        listEmployeeTraining: List<EmployeeTraining>,
        recyclerView: RecyclerView
    ) {
        val adapterEmployeeTraining = ListEmployeeTrainingAdapter(context!!, listEmployeeTraining, fm)
        recyclerView.adapter = adapterEmployeeTraining
        adapterEmployeeTraining.notifyDataSetChanged()
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

    fun search(keyword: String, databaseQueryHelper: EmployeeTrainingQueryHelper) {
        val listEmployeeTraining = databaseQueryHelper.cariEmployeeTrainingModels(keyword)
        tampilkanListEmployeeTraining(listEmployeeTraining, recyclerView!!)
    }
}