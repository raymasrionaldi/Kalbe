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
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.ListProjectCreateAdapter
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.KeluargaQueryHelper
import com.xsis.android.batch217.databases.ProjectCreateQueryHelper
import com.xsis.android.batch217.models.ProjectCreate

class ProjectFragmentCreateData(context: Context, val fm: FragmentManager): Fragment() {
    var recyclerView: RecyclerView? = null
    var add: FloatingActionButton? = null

    val databaseHelper = DatabaseHelper(context)
    val databaseQueryHelper = ProjectCreateQueryHelper(databaseHelper)
    var kata = ""


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)


        val customView = inflater.inflate(R.layout.fragment_project_create_data,container,false)
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        add = customView.findViewById(R.id.buttonCreateProject)
        recyclerView = customView.findViewById(R.id.listProjectRecycler2)
        recyclerView!!.layoutManager = layoutManager

        val dividerItemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        recyclerView!!.addItemDecoration(dividerItemDecoration)


//        val listProject = databaseQueryHelper.getAllProject()
//        listProject.forEach{
//            println("client = ${it.client}")
//        }
//        tampilkanListProject(listProject, recyclerView!!)

        add!!.setOnClickListener {

        }


        return customView
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
                search(keyword, databaseQueryHelper)
                kata = keyword
                return true
            }
        })
    }

    fun search(keyword: String, databaseQueryHelper: ProjectCreateQueryHelper) {
        val listProject = databaseQueryHelper.cariProject(keyword)
        tampilkanListProject(listProject, recyclerView!!)
    }

    fun search2() {
        val listProject = databaseQueryHelper.cariProject(kata)
        tampilkanListProject(listProject, recyclerView!!)
    }

    override fun onResume() {
        super.onResume()
        search2()
    }

    fun tampilkanListProject(listProject: List<ProjectCreate>, recyclerView: RecyclerView) {
        val adapterProject = ListProjectCreateAdapter(context!!, listProject, fm)
        recyclerView.adapter = adapterProject
        adapterProject.notifyDataSetChanged()
    }

}

