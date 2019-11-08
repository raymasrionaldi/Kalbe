package com.xsis.android.batch217.ui.training_organizer

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
import com.xsis.android.batch217.adapters.ListTrainingOrganizerAdapter
import com.xsis.android.batch217.adapters.fragments.TrainingOrganizerFragmentAdapter
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.TrainingOrganizerQueryHelper
import com.xsis.android.batch217.models.TrainingOrganizer


class FragmentDataTrainingOrganizer(context: Context, val fm: FragmentManager): Fragment() {
    var recyclerView: RecyclerView? = null
    var databaseQueryHelper: TrainingOrganizerQueryHelper? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)

        val customView = inflater.inflate(R.layout.fragment_data_training_organizer, container, false)
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView = customView.findViewById(R.id.listTrainingOrganizer) as RecyclerView
        recyclerView!!.layoutManager = layoutManager

        val dividerItemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        recyclerView!!.addItemDecoration(dividerItemDecoration)

        val myFab = customView.findViewById(R.id.buttonPlusTrainingOrganizer) as FloatingActionButton
        myFab.setOnClickListener {
            var viewPager=view!!.parent as ViewPager
            viewPager.setCurrentItem(1,true)
            val adapter = viewPager.adapter!! as TrainingOrganizerFragmentAdapter
            val fragment = fm.fragments[1] as FragmentFormTrainingOrganizer
            fragment.modeAdd()
            adapter.notifyDataSetChanged()
            viewPager.setCurrentItem(1, true)
        }
        val databaseHelper = DatabaseHelper(context!!)
        databaseQueryHelper = TrainingOrganizerQueryHelper(databaseHelper)
        //        getSemuaKontrak(recyclerView, databaseQueryHelper!!)


        return customView
    }
    fun getSemuaKontrak(
        recyclerView: RecyclerView?,
        databaseQueryHelper: TrainingOrganizerQueryHelper
    ) {
        val listKontrakKerja = databaseQueryHelper.readSemuaTrainingOrgModels()

        tampilkanListKontrakKerja(listKontrakKerja, recyclerView)

    }

    fun tampilkanListKontrakKerja(
        listKontrakKerja: List<TrainingOrganizer>,
        recyclerView: RecyclerView?
    ) {
        context?.let { val adapterKontrakKerja = ListTrainingOrganizerAdapter(context!!,listKontrakKerja, fm)
            if (recyclerView != null) {
                recyclerView.adapter = adapterKontrakKerja
            }
            adapterKontrakKerja.notifyDataSetChanged() }

    }
    fun updateKontrak() {

//        getSemuaKontrak(recyclerView, databaseQueryHelper!!)
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main, menu)

        val myActionMenuItem = menu.findItem(R.id.action_search)
        val searchView = myActionMenuItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                // collapse the view ?
                //menu.findItem(R.id.menu_search).collapseActionView();
//                Log.e("Fragment queryText", query)
                return false
            }
            override fun onQueryTextChange(keyword: String): Boolean {
                // search goes here !!
                // listAdapter.getFilter().filter(query);
                // Log.e("Fragment queryText", keyword)
                search(keyword,databaseQueryHelper!!)

                /*TODO
                1. do search based on active fragment table
                2. send list result to active fragment
                3.
                */
                return true
            }
        })
    }

    fun search(keyword:String,databaseQueryHelper: TrainingOrganizerQueryHelper){
        val kontrakan= databaseQueryHelper.cariTrainingOrgModels(keyword)
        tampilkanListKontrakKerja(kontrakan, recyclerView)
    }

}