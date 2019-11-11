package com.xsis.android.batch217.ui.project

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
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.ListProjectAdapter
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.ProjectQueryHelper
import com.xsis.android.batch217.models.Project

class ProjectFragmentData(context: Context, val fm: FragmentManager) : Fragment() {
    var recyclerView: RecyclerView? = null
    var databaseQueryHelper: ProjectQueryHelper? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val customView = inflater.inflate(
            R.layout.fragment_data_project,
            container,
            false
        )

        setHasOptionsMenu(true)

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        recyclerView = customView.findViewById(R.id.listProjectRecycler) as RecyclerView
        recyclerView!!.layoutManager = layoutManager

        val dividerItemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        recyclerView!!.addItemDecoration(dividerItemDecoration)

        val buttonAdd =
            customView.findViewById(R.id.buttonAddProject) as FloatingActionButton
        buttonAdd.setOnClickListener {
            addData()
        }

        val databaseHelper = DatabaseHelper(context!!)
        databaseQueryHelper = ProjectQueryHelper(databaseHelper)

//        getSemuaProject(recyclerView!!, databaseQueryHelper!!)

        return customView
    }

    fun addData() {
        val intent = Intent(context, ProjectFormActivity::class.java)
        startActivity(intent)
    }

/*    fun getSemuaProject(
        recyclerView: RecyclerView,
        databaseQueryHelper: ProjectQueryHelper
    ) {
        val listProject = databaseQueryHelper.readSemuaProjectModels()
        tampilkanListProject(listProject, recyclerView)
    }*/

    fun tampilkanListProject(
        listProject: List<Project>,
        recyclerView: RecyclerView
    ) {
        context?.let {
            val adapterProject = ListProjectAdapter(context!!, listProject, fm)
            recyclerView.adapter = adapterProject
            adapterProject.notifyDataSetChanged()
        }
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

    fun search(keyword: String, databaseQueryHelper: ProjectQueryHelper) {
        val listProject = databaseQueryHelper.cariProjectModels(keyword)
        tampilkanListProject(listProject, recyclerView!!)
    }

    override fun onResume() {
        super.onResume()

        activity!!.invalidateOptionsMenu()
    }
}