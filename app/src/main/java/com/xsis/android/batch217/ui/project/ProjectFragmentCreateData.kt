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
import com.xsis.android.batch217.adapters.ListProjectCreateAdapter
import com.xsis.android.batch217.adapters.fragments.ProjectCreateFragmentAdapter
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.KeluargaQueryHelper
import com.xsis.android.batch217.databases.ProjectCreateQueryHelper
import com.xsis.android.batch217.models.ProjectCreate
import com.xsis.android.batch217.utils.TAB_PROJECT_CREATE
import java.util.*
import kotlin.concurrent.schedule

class ProjectFragmentCreateData(context: Context, val fm: FragmentManager): Fragment() {
    var recyclerView: RecyclerView? = null
    var add: FloatingActionButton? = null
    var ID = 0

    val databaseHelper = DatabaseHelper(context)
    val databaseQueryHelper = ProjectCreateQueryHelper(databaseHelper)
    var kata = ""


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)


        val customView = inflater.inflate(R.layout.fragment_data_project_create,container,false)
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        add = customView.findViewById(R.id.buttonCreateProject)
        recyclerView = customView.findViewById(R.id.listProjectRecycler2)
        recyclerView!!.layoutManager = layoutManager

        val dividerItemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        recyclerView!!.addItemDecoration(dividerItemDecoration)

        add!!.setOnClickListener {
            pindahKeFragmentForm()
        }

//        Timer().schedule(1000){
//            if (TAB_PROJECT_CREATE == 1){
//                pindahKeFragmentForm()
//            }
//        }

        return customView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    fun pindahKeFragmentForm(){
        val fragment = fm.fragments[1] as ProjectFragmentCreateForm
        val viewPager = fragment.view!!.parent as ViewPager
        val adapter = viewPager.adapter!! as ProjectCreateFragmentAdapter

        fragment.bawaID(ID)
        adapter.notifyDataSetChanged()
        viewPager.setCurrentItem(1, true)
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

