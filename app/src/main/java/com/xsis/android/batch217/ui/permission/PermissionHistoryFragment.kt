package com.xsis.android.batch217.ui.permission

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.ListPermissionHistoryAdapter
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.PermissionQueryHelper
import com.xsis.android.batch217.models.Permission

class PermissionHistoryFragment() : Fragment() {
    var recyclerView: RecyclerView? = null
    var databaseQueryHelper: PermissionQueryHelper? = null
    var SEARCH_KEYWORD :String =""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val customView = inflater.inflate(R.layout.fragment_data_history_permission, container, false)

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView = customView.findViewById(R.id.listPermissionHistoryRecycler) as RecyclerView
        recyclerView!!.layoutManager = layoutManager

        activity!!.title = getString(R.string.permission_list)

        setHasOptionsMenu(true)

        val databaseHelper = DatabaseHelper(context!!)
        databaseQueryHelper = PermissionQueryHelper(databaseHelper)

        return customView
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.only_search, menu)

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

    fun search(keyword:String,databaseQueryHelper: PermissionQueryHelper){
        val listPermission= databaseQueryHelper.cariPermissionModelsBuatHistory(keyword)
        tampilkanListPermission(listPermission,recyclerView!!)
    }

    fun tampilkanListPermission(listPermission: List<Permission>, recyclerView: RecyclerView) {
        context?.let {
            val adapterPermission = ListPermissionHistoryAdapter(context!!, listPermission)
            recyclerView.adapter = adapterPermission
            adapterPermission.notifyDataSetChanged()
        }

    }

    override fun onResume() {
        super.onResume()
        refreshList()
    }

    fun refreshList() {
        activity!!.invalidateOptionsMenu()
    }


}