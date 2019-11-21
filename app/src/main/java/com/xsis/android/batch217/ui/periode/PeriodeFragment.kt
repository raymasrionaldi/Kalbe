package com.xsis.android.batch217.ui.periode

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.ListPeriodeAdapter
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.PeriodeQueryHelper
import com.xsis.android.batch217.models.Periode
import kotlinx.android.synthetic.main.periode_fragment.view.*

class PeriodeFragment : Fragment() {
    private lateinit var periodeViewModel: PeriodeViewModel
    private var recyclerView: RecyclerView?=null
    var databaseHelper : DatabaseHelper?=null
    var databaseQueryHelper: PeriodeQueryHelper?= null
    var fragment = this


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        periodeViewModel = ViewModelProviders.of(this).get(PeriodeViewModel::class.java)
        val root =  inflater.inflate(R.layout.periode_fragment, container, false)

        activity!!.title = getString(R.string.menu_periode)

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        recyclerView = root.findViewById(R.id.listPeriodeRecycler) as RecyclerView
        recyclerView!!.layoutManager = layoutManager

        setHasOptionsMenu(true)

        root.fab.setOnClickListener{view->
            val intent = Intent(context, InputPeriodeActivity::class.java)
            startActivity(intent)
        }

        databaseHelper = DatabaseHelper(context!!)
        databaseQueryHelper = PeriodeQueryHelper(databaseHelper!!)

        return root
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
                search(keyword,PeriodeQueryHelper(DatabaseHelper(context!!)))
                return true
            }
        })
    }

    fun search(keyword:String,databaseQueryHelper: PeriodeQueryHelper){
        val listPendidikan= databaseQueryHelper.cariPeriodeModels(keyword)
        tampilkanListPendidikan(listPendidikan,recyclerView!!)
    }

    fun tampilkanListPendidikan(listPendidikan:List<Periode>, recyclerView: RecyclerView){
        val adapter = ListPeriodeAdapter(context!!, fragment, listPendidikan)
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    fun refreshList() {
        getActivity()!!.invalidateOptionsMenu()
    }

    override fun onResume() {
        super.onResume()
        refreshList()
    }
}
