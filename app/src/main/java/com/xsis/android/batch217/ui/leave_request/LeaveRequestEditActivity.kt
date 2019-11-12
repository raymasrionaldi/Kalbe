package com.xsis.android.batch217.ui.leave_request

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.xsis.android.batch217.R
import com.xsis.android.batch217.databases.DatabaseHelper
import com.xsis.android.batch217.databases.LeaveRequestQueryHelper
import com.xsis.android.batch217.models.LeaveRequest
import com.xsis.android.batch217.utils.ID_LEAVE
import kotlinx.android.synthetic.main.activity_leave_request_add.*
import kotlinx.android.synthetic.main.activity_leave_request_edit.*
import kotlinx.android.synthetic.main.activity_project_form.*

class LeaveRequestEditActivity:AppCompatActivity() {
    val context = this
    var ID_DETAIL:Int?= null
    lateinit var listLeaveType: List<LeaveRequest>
    lateinit var listCutiKhusus: List<LeaveRequest>
    var databaseQueryHelper: LeaveRequestQueryHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leave_request_edit)

        var databaseHelper= DatabaseHelper(context)
        databaseQueryHelper= LeaveRequestQueryHelper(databaseHelper)

        var bundle:Bundle?= intent.extras
        //if bundle not null
        bundle?.let {
            ID_DETAIL=bundle!!.getInt(ID_LEAVE)
        }

        listLeaveType = databaseQueryHelper!!.getLeaveTypeModels()
        val listLeaveType = listLeaveType.map { leave -> leave.leaveType }.toList()
        spinnerLeaveType.item = listLeaveType

        tampilkanDetail(ID_DETAIL!!)
    }

    fun tampilkanDetail(id_detail: Int){
       /* val data = databaseQueryHelper!!.getDetailById(id_detail)

        val index =
            listLeaveType.indexOfFirst { leave -> leave.idLeaveRequest == data.kodeCompProject }
        println("Index $index")
        if (index != -1) {
            spinnerEditLeaveType.setSelection(index)
        }*/

        /*leaveName!!.text=model.leaveName
        startOn!!.text=model.start
        endedOn!!.text=model.end
        address!!.text=model.address
        contactNumber!!.text=model.contact
        reason!!.text=model.reason*/
    }

}