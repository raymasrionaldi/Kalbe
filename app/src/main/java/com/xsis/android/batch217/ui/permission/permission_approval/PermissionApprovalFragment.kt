package com.xsis.android.batch217.ui.permission.permission_approval

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.ListPermissionApprovalAdapter
import com.xsis.android.batch217.adapters.ListTimSubAdapter
import com.xsis.android.batch217.adapters.ListTipeTesAdapter
import com.xsis.android.batch217.adapters.fragments.AgamaFragmentAdapter
import com.xsis.android.batch217.adapters.fragments.TimesheetHistoryAdapter
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.PermissionQueryHelper
import com.xsis.android.batch217.databases.TimesheetQueryHelper
import com.xsis.android.batch217.databases.TipeTesQueryHelper
import com.xsis.android.batch217.models.Permission
import com.xsis.android.batch217.models.Timesheet
import com.xsis.android.batch217.models.TipeTes
import com.xsis.android.batch217.ui.timesheet.timesheet_approval.TimesheetApprovalProcessActivity
import com.xsis.android.batch217.ui.timesheet.timesheet_history.TimesheetHistoryViewModel
import com.xsis.android.batch217.utils.*
import kotlinx.android.synthetic.main.activity_data_submit.*
import kotlinx.android.synthetic.main.activity_employee_training_form.*
import kotlinx.android.synthetic.main.activity_input_prfrequest.*
import kotlinx.android.synthetic.main.fragment_timesheet_approval.*
import kotlinx.android.synthetic.main.fragment_timesheet_submission.*
import java.util.*
import kotlin.collections.ArrayList

class PermissionApprovalFragment() : Fragment() {
    var recyclerView: RecyclerView? = null
    var databaseQueryHelper: PermissionQueryHelper? = null
    var SEARCH_KEYWORD :String =""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val customView = inflater.inflate(R.layout.fragment_data_approval_permission, container, false)

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView = customView.findViewById(R.id.listPermissionApprovalRecycler) as RecyclerView
        recyclerView!!.layoutManager = layoutManager

        activity!!.title = getString(R.string.permission_approval)

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
        val listPermission= databaseQueryHelper.cariPermissionModels(keyword)
        tampilkanListPermission(listPermission,recyclerView!!)
    }

    fun tampilkanListPermission(listPermission: List<Permission>, recyclerView: RecyclerView) {
        context?.let {
            val adapterPermission = ListPermissionApprovalAdapter(context!!, listPermission)
            recyclerView.adapter = adapterPermission
            adapterPermission.notifyDataSetChanged()
        }

    }


}