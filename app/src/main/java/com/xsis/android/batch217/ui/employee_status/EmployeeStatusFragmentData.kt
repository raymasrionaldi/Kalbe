package com.xsis.android.batch217.ui.employee_status

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
import com.xsis.android.batch217.adapters.ListEmployeeStatusAdapter
import com.xsis.android.batch217.adapters.fragments.EmployeeStatusFragmentAdapter
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.EmployeeStatusQueryHelper
import com.xsis.android.batch217.models.EmployeeStatus

class EmployeeStatusFragmentData(context: Context, val fm: FragmentManager) : Fragment() {
    var recyclerView: RecyclerView? = null
    var databaseQueryHelper: EmployeeStatusQueryHelper? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val customView = inflater.inflate(
            R.layout.fragment_data_employee_status,
            container,
            false
        )
        setHasOptionsMenu(true)

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        recyclerView = customView.findViewById(R.id.listEmployeeStatusRecycler) as RecyclerView
        recyclerView!!.layoutManager = layoutManager

        val dividerItemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        recyclerView!!.addItemDecoration(dividerItemDecoration)

        val buttonAdd = customView.findViewById(R.id.buttonAddEmployeeStatus) as FloatingActionButton
        buttonAdd.setOnClickListener {
            addData()
        }

        val databaseHelper = DatabaseHelper(context!!)
        databaseQueryHelper = EmployeeStatusQueryHelper(databaseHelper)

        //getSemuaEmployeeStatus(recyclerView!!, databaseQueryHelper!!)

        return customView
    }

    fun addData() {
        val viewPager = view!!.parent as ViewPager
        val adapter = viewPager.adapter!! as EmployeeStatusFragmentAdapter
        val fragment = fm.fragments[1] as EmployeeStatusFragmentForm
        fragment.modeAdd()
        adapter.notifyDataSetChanged()
        viewPager.setCurrentItem(1, true)
    }

    fun getSemuaEmployeeStatus(
        recyclerView: RecyclerView,
        databaseQueryHelper: EmployeeStatusQueryHelper
    ) {
        val listEmployeeStatus = databaseQueryHelper.readSemuaEmployeeStatusModels()
        tampilkanListEmployeeStatus(listEmployeeStatus, recyclerView)
    }

    fun tampilkanListEmployeeStatus(
        listEmployeeStatus: List<EmployeeStatus>,
        recyclerView: RecyclerView
    ) {
        val adapterEmployeeStatus = ListEmployeeStatusAdapter(context!!, listEmployeeStatus, fm)
        recyclerView.adapter = adapterEmployeeStatus
        adapterEmployeeStatus.notifyDataSetChanged()
    }

    fun updateContent() {
        //getSemuaEmployeeStatus(recyclerView!!, databaseQueryHelper!!)
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

    fun search(keyword: String, databaseQueryHelper: EmployeeStatusQueryHelper) {
        val listEmployeeStatus = databaseQueryHelper.cariEmployeeStatusModels(keyword)
        tampilkanListEmployeeStatus(listEmployeeStatus, recyclerView!!)
    }
}
