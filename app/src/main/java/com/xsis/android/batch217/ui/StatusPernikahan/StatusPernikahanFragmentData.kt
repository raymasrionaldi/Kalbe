package com.xsis.android.batch217.ui.StatusPernikahan

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
import com.xsis.android.batch217.adapters.ListStatusPernikahanAdapter
import com.xsis.android.batch217.adapters.fragments.StatusPernikahanFragmentAdapter
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.StatusPernikahanQueryHelper
import com.xsis.android.batch217.models.StatusPernikahan

class StatusPernikahanFragmentData(context: Context, val fm: FragmentManager) : Fragment() {
    var recyclerView: RecyclerView? = null
    var databaseQueryHelper: StatusPernikahanQueryHelper? = null
    var SEARCH_KEYWORD :String =""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val customView = inflater.inflate(
            R.layout.fragment_data_status_pernikahan,
            container,
            false
        )
        setHasOptionsMenu(true)

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        recyclerView = customView.findViewById(R.id.listStatusPernikahanRecycler) as RecyclerView
        recyclerView!!.layoutManager = layoutManager

        val dividerItemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        recyclerView!!.addItemDecoration(dividerItemDecoration)

        val buttonAdd = customView.findViewById(R.id.buttonAddStatusPernikahan) as FloatingActionButton
        buttonAdd.setOnClickListener {
            addData()
        }

        val databaseHelper = DatabaseHelper(context!!)
        databaseQueryHelper = StatusPernikahanQueryHelper(databaseHelper)

        return customView
    }

    fun addData() {
        val viewPager = view!!.parent as ViewPager
        val adapter = viewPager.adapter!! as StatusPernikahanFragmentAdapter
        val fragment = fm.fragments[1] as StatusPernikahanFrgamentForm
        fragment.modeAdd()
        adapter.notifyDataSetChanged()
        viewPager.setCurrentItem(1, true)
    }

    fun getSemuaStatusPernikahan(
        recyclerView: RecyclerView,
        databaseQueryHelper: StatusPernikahanQueryHelper
    ) {
        val listStatusPernikahan = databaseQueryHelper.readSemuaStatusPernikahanModels()
        tampilkanListStatusPernikahan(listStatusPernikahan, recyclerView)
    }

    fun tampilkanListStatusPernikahan(
        listStatusPernikahan: List<StatusPernikahan>,
        recyclerView: RecyclerView
    ) {
        val adapterStatusPernikahan = ListStatusPernikahanAdapter(context!!, listStatusPernikahan, fm)
        recyclerView.adapter = adapterStatusPernikahan
        adapterStatusPernikahan.notifyDataSetChanged()
    }

    fun updateContent() {
        search(SEARCH_KEYWORD,databaseQueryHelper!!)
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

    fun search(keyword: String, databaseQueryHelper: StatusPernikahanQueryHelper) {
        val listStatusPernikahan = databaseQueryHelper.cariStatusPernikahanModels(keyword)
        tampilkanListStatusPernikahan(listStatusPernikahan, recyclerView!!)
    }
}
