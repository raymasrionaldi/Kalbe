package com.xsis.android.batch217.ui.jadwal

import android.content.Context
import android.os.Bundle
import android.util.Log
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
import com.xsis.android.batch217.adapters.ListJadwalAdapter
import com.xsis.android.batch217.adapters.fragments.JadwalFragmentAdapter
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.JadwalQueryHelper
import com.xsis.android.batch217.models.Jadwal

class JadwalFragmentData(context: Context, val fm: FragmentManager) : Fragment() {
    var recyclerView: RecyclerView? = null
    var databaseQueryHelper: JadwalQueryHelper? = null
    var SEARCH_KEYWORD :String =""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val customView = inflater.inflate(
            R.layout.fragment_data_jadwal,
            container,
            false
        )

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        recyclerView = customView.findViewById(R.id.listJadwalRecycler) as RecyclerView
        recyclerView!!.layoutManager = layoutManager

        val dividerItemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        recyclerView!!.addItemDecoration(dividerItemDecoration)

        val buttonAdd = customView.findViewById(R.id.buttonTambahJadwal) as FloatingActionButton
        buttonAdd.setOnClickListener {
            addData()
        }

        setHasOptionsMenu(true)

        val databaseHelper = DatabaseHelper(context!!)
        databaseQueryHelper = JadwalQueryHelper(databaseHelper)

        //getSemuaJadwal(recyclerView!!, databaseQueryHelper!!)

        return customView
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main, menu)

        val myActionMenuItem = menu.findItem(R.id.action_search)
        val searchView = myActionMenuItem.actionView as SearchView

        searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                Log.e("Fragment queryText", query)
                return false
            }
            override fun onQueryTextChange(keyword: String): Boolean {
                search(keyword,databaseQueryHelper!!)
                return true
            }
        })
    }

    fun search(keyword:String,databaseQueryHelper: JadwalQueryHelper){
        val listJadwal= databaseQueryHelper.cariJadwalModels(keyword)
        tampilkanListJadwal(listJadwal,recyclerView!!)
    }

    fun addData() {
        val viewPager = view!!.parent as ViewPager
        val adapter = viewPager.adapter!! as JadwalFragmentAdapter
        val fragment = fm.fragments[1] as JadwalFragmentForm
        fragment.modeAdd()
        adapter.notifyDataSetChanged()
        viewPager.setCurrentItem(1, true)
    }

    fun getSemuaJadwal(recyclerView: RecyclerView, databaseQueryHelper: JadwalQueryHelper) {
        val listJadwal = databaseQueryHelper.readSemuaJadwalModels()
        tampilkanListJadwal(listJadwal, recyclerView)
    }

    fun tampilkanListJadwal(listJadwal: List<Jadwal>, recyclerView: RecyclerView) {
        context?.let {
            val adapterJadwal = ListJadwalAdapter(context!!, listJadwal, fm)
            recyclerView.adapter = adapterJadwal
            adapterJadwal.notifyDataSetChanged()
        }

    }

    fun updateContent() {
        search(SEARCH_KEYWORD,databaseQueryHelper!!)
    }
}
