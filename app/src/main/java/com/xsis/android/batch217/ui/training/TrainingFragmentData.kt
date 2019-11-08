package com.xsis.android.batch217.ui.training

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
import com.xsis.android.batch217.adapters.ListTrainingAdapter
import com.xsis.android.batch217.adapters.fragments.TrainingFragmentAdapter
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.TrainingQueryHelper
import com.xsis.android.batch217.models.Training

class TrainingFragmentData(context: Context, val fm: FragmentManager) : Fragment() {
    var recyclerView: RecyclerView? = null
    var databaseQueryHelper: TrainingQueryHelper? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val customView = inflater.inflate(
            R.layout.fragment_data_training,
            container,
            false
        )
        setHasOptionsMenu(true)

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        recyclerView = customView.findViewById(R.id.listTrainingRecycler) as RecyclerView
        recyclerView!!.layoutManager = layoutManager

        val dividerItemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        recyclerView!!.addItemDecoration(dividerItemDecoration)

        val buttonAdd = customView.findViewById(R.id.buttonAddTraining) as FloatingActionButton
        buttonAdd.setOnClickListener {
            addData()
        }

        val databaseHelper = DatabaseHelper(context!!)
        databaseQueryHelper = TrainingQueryHelper(databaseHelper)

//        getSemuaTraining(recyclerView!!, databaseQueryHelper!!)

        return customView
    }

    fun addData() {
        val viewPager = view!!.parent as ViewPager
        val adapter = viewPager.adapter!! as TrainingFragmentAdapter
        val fragment = fm.fragments[1] as TrainingFragmentForm
        fragment.modeAdd()
        adapter.notifyDataSetChanged()
        viewPager.setCurrentItem(1, true)
    }

    fun getSemuaTraining(
        recyclerView: RecyclerView,
        databaseQueryHelper: TrainingQueryHelper
    ) {
        val listTraining = databaseQueryHelper.readSemuaTrainingModels()
        tampilkanListTraining(listTraining, recyclerView)
    }

    fun tampilkanListTraining(
        listTraining: List<Training>,
        recyclerView: RecyclerView
    ) {
        context?.let {
            val adapterTraining = ListTrainingAdapter(context!!, listTraining, fm)
            recyclerView.adapter = adapterTraining
            adapterTraining.notifyDataSetChanged()
        }

    }

    fun updateContent() {
//        getSemuaTaining(recyclerView!!, databaseQueryHelper!!)
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

    fun search(keyword: String, databaseQueryHelper: TrainingQueryHelper) {
        val listTraining = databaseQueryHelper.cariTrainingModels(keyword)
        tampilkanListTraining(listTraining, recyclerView!!)
    }
}
