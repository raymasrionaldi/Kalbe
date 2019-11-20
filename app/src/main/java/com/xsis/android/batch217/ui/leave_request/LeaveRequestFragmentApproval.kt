package com.xsis.android.batch217.ui.leave_request

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.xsis.android.batch217.R
import com.xsis.android.batch217.adapters.expandablelist.LeaveRequestExpandableListAdapter
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.LeaveRequestQueryHelper
import com.xsis.android.batch217.models.LeaveRequest

class LeaveRequestFragmentApproval(context: Context, val fm: FragmentManager, val id_detail: Int) :
    Fragment() {

    internal lateinit var menuAdapter: LeaveRequestExpandableListAdapter
    internal lateinit var listDataGroup: MutableList<String>
    internal lateinit var listDataChild: HashMap<String, List<List<String>>>
    internal lateinit var databaseQueryHelper: LeaveRequestQueryHelper
    var listLeaveRequestApproval: ExpandableListView? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val customView = inflater.inflate(
            R.layout.fragment_approval_leave_request,
            container,
            false
        )

        val databaseHelper=DatabaseHelper(context!!)
        databaseQueryHelper = LeaveRequestQueryHelper(databaseHelper)

        listLeaveRequestApproval = customView.findViewById(R.id.listLeaveRequestApproval)

        getDetailApproval(id_detail)

        return customView
    }

    fun getDetailApproval(idDetail: Int) {
        val model = databaseQueryHelper!!.getApprovalById(idDetail)
        viewDetail(model)
    }

    fun viewDetail(listModel: List<LeaveRequest>) {
        prepareData(listModel)
        menuAdapter = LeaveRequestExpandableListAdapter(context!!, listDataGroup, listDataChild)
        listLeaveRequestApproval!!.setAdapter(menuAdapter)
    }

    private fun prepareData(listModel: List<LeaveRequest>) {
        listDataGroup = ArrayList()
        listDataChild = HashMap()
        var i=1
        var j=0
        var group :String?= ""

        listModel.forEach {model->
            group="Approval ${i++}"
            listDataGroup.add(group.toString())
            val child = ArrayList<List<String>>()
            val info = arrayListOf(model.approvalName!!, model.approvalState!!)
            child.add(info)
            listDataChild[listDataGroup[j++]] = child
        }
    }

    /*fun viewDetail(listModel: List<LeaveRequest>) {
        println("BULAN# viewDetail")
        //        (parentFragment as LeaveRequestFragment).changeTitleByFragmentPos(1)
        data = model

        prepareData()
        menuAdapter = LeaveRequestExpandableListAdapter(context!!, listDataGroup, listDataChild)
        listLeaveRequestApproval!!.setAdapter(menuAdapter)
    }*/

    /*private fun prepareData() {
        println("BULAN# prepareData")
        listDataGroup = ArrayList()
        listDataChild = HashMap()

        val group0 = "Approval 1"
        listDataGroup.add(group0)

        val child0 = ArrayList<List<String>>()
        val info01 = arrayListOf("Bulan Dewi", data.approval1!!)
        child0.add(info01)
        listDataChild[listDataGroup[0]] = child0

        val group1 = "Approval 2"
        listDataGroup.add(group1)

        val child1 = ArrayList<List<String>>()
        val info11 = arrayListOf("Nick Furry", data.approval2!!)
        child1.add(info11)
        listDataChild[listDataGroup[1]] = child1

        val group2 = "Approval 3"
        listDataGroup.add(group2)

        val child2 = ArrayList<List<String>>()
        val info21 = arrayListOf("James Bond", data.approval3!!)
        child2.add(info21)
        listDataChild[listDataGroup[2]] = child2
    }*/
}