package com.xsis.android.batch217.databases

import android.content.ContentValues
import android.database.Cursor
import com.xsis.android.batch217.models.LeaveRequest
import com.xsis.android.batch217.models.Project
import com.xsis.android.batch217.utils.*
import java.util.*
import kotlin.collections.ArrayList

class LeaveRequestQueryHelper(val databaseHelper: DatabaseHelper) {
     val currentYear = Calendar.getInstance().get(Calendar.YEAR)

     fun getSemuaLeaveRequest(): Cursor {
        val db = databaseHelper.readableDatabase

        val queryRead = "SELECT " +
                                    "a.*," +
                                    "b.*," +
                                    "c.*" +
                                "FROM $TABEL_LEAVE_REQUEST a " +
                                "INNER JOIN  $TABEL_LEAVE_TYPE b ON a.$ID_LEAVE_TYPE=b.$ID_LEAVE_TYPE " +
                                "LEFT JOIN $TABEL_CUTI_KHUSUS c ON a.$ID_CUTI_KHUSUS=c.$ID_CUTI_KHUSUS " +
                                "WHERE a.$IS_DELETED ='false'"

        return db.rawQuery(queryRead, null)
    }

    fun readSemuaLeaveRequstModels(): List<LeaveRequest> {
        var listLeaveRequest = ArrayList<LeaveRequest>()

        val cursor = getSemuaLeaveRequest()
        if (cursor.count > 0) {
            listLeaveRequest = konversiCursorKeListLeaveRequestModel(cursor)
        }
        return listLeaveRequest
    }

    private fun konversiCursorKeListLeaveRequestModel(cursor: Cursor): ArrayList<LeaveRequest> {
        var listLeaveRequest = ArrayList<LeaveRequest>()

        for (c in 0 until cursor.count) {
            cursor.moveToPosition(c)

            var leaveRequest = LeaveRequest()
            leaveRequest.idLeaveRequest = cursor.getInt(0)
            leaveRequest.start= cursor.getString(3)
            leaveRequest.end= cursor.getString(4)
            leaveRequest.address= cursor.getString(5)
            leaveRequest.contact = cursor.getString(6)
            leaveRequest.reason = cursor.getString(7)
            leaveRequest.approval1 = cursor.getString(8)
            leaveRequest.approval2 = cursor.getString(9)
            leaveRequest.approval3= cursor.getString(10)

            leaveRequest.idLeaveType= cursor.getInt(1)
            leaveRequest.leaveType =cursor.getString(13)
            leaveRequest.quota

            leaveRequest.idCutiKhusus = cursor.getInt(2)
            leaveRequest.leaveName = cursor.getString(16)
            leaveRequest.isDeleted = cursor.getString(11)

            listLeaveRequest.add(leaveRequest)
        }
        return listLeaveRequest
    }

    private fun konversiCursorKeListLeaveTypeModel(cursor: Cursor):ArrayList<LeaveRequest>{
        var listLeaveType = ArrayList<LeaveRequest>()

        for (c in 0 until cursor.count) {
            cursor.moveToPosition(c)

            var leaveType = LeaveRequest()
            leaveType.idLeaveType= cursor.getInt(0)
            leaveType.leaveType =cursor.getString(1)
            listLeaveType.add(leaveType)
        }
        return listLeaveType
    }

    private fun konversiCursorKeListCutiKhususModel(cursor: Cursor):ArrayList<LeaveRequest>{
        var listCutiKhusus = ArrayList<LeaveRequest>()

        for (c in 0 until cursor.count) {
            cursor.moveToPosition(c)

            var cutiKhusus = LeaveRequest()
            cutiKhusus.idCutiKhusus= cursor.getInt(0)
            cutiKhusus.leaveName =cursor.getString(1)
            listCutiKhusus.add(cutiKhusus)
        }
        return listCutiKhusus
    }

    fun cariLeaveRequestModels(keyword: String): List<LeaveRequest> {
        var listLeaveRequest = ArrayList<LeaveRequest>()
        if (keyword.isNotBlank()) {
            val db = databaseHelper.readableDatabase

            val queryCari = "SELECT " +
                    "a.*," +
                    "b.*," +
                    "c.* " +
                    "FROM $TABEL_LEAVE_REQUEST a " +
                        "INNER JOIN  $TABEL_LEAVE_TYPE b ON a.$ID_LEAVE_TYPE=b.$ID_LEAVE_TYPE " +
                        "LEFT JOIN $TABEL_CUTI_KHUSUS c ON a.$ID_CUTI_KHUSUS=c.$ID_CUTI_KHUSUS " +
                    "WHERE " +
                        "a.$START LIKE '%$keyword%' " +
                        "AND a.$START LIKE '%$currentYear' " +
                        "AND a.$IS_DELETED ='false'"

            val cursor = db.rawQuery(queryCari, null)
            if (cursor.count > 0) {
                listLeaveRequest = konversiCursorKeListLeaveRequestModel(cursor)
            }
        }
        return listLeaveRequest
    }

    fun getLeaveRequestDetailById(id:Int): LeaveRequest{
        var listLeaveRequest = LeaveRequest()
        val db = databaseHelper.readableDatabase
        val queryCari = "SELECT " +
                "a.*," +
                "b.*," +
                "c.* " +
                "FROM $TABEL_LEAVE_REQUEST a " +
                "INNER JOIN  $TABEL_LEAVE_TYPE b ON a.$ID_LEAVE_TYPE=b.$ID_LEAVE_TYPE " +
                "LEFT JOIN $TABEL_CUTI_KHUSUS c ON a.$ID_CUTI_KHUSUS=c.$ID_CUTI_KHUSUS " +
                "WHERE a.$ID_LEAVE ='$id'"

        val cursor = db.rawQuery(queryCari, null)
        if (cursor.count > 0) {
            listLeaveRequest = konversiCursorKeListLeaveRequestModel(cursor)[0]
        }else{
            return LeaveRequest()
        }
        return listLeaveRequest
    }

    fun getLeaveTypeModels(): List<LeaveRequest> {
        var listLeaveType = ArrayList<LeaveRequest>()
        val db = databaseHelper.readableDatabase

        val queryRead = "SELECT * FROM $TABEL_LEAVE_TYPE"

        val cursor = db.rawQuery(queryRead, null)
        if (cursor.count > 0) {
            listLeaveType = konversiCursorKeListLeaveTypeModel(cursor)
        }
        return listLeaveType
    }

    fun getCutiKhususModels(): List<LeaveRequest> {
        var listCutiKhusus = ArrayList<LeaveRequest>()
        val db = databaseHelper.readableDatabase

        val queryRead = "SELECT * FROM $TABEL_CUTI_KHUSUS"

        val cursor = db.rawQuery(queryRead, null)
        if (cursor.count > 0) {
            listCutiKhusus = konversiCursorKeListCutiKhususModel(cursor)
        }
        return listCutiKhusus
    }

    fun tambahLeaveRequest(model: LeaveRequest): Long {
        val db = databaseHelper.writableDatabase

        val values = ContentValues()
            values.put(ID_LEAVE_TYPE,model.idLeaveType)
            values.put(ID_CUTI_KHUSUS,model.idCutiKhusus)
            values.put(START,model.start)
            values.put(END,model.end)
            values.put(ADDRESS,model.address)
            values.put(CONTACT,model.contact)
            values.put(REASON,model.reason)
            values.put(IS_DELETED,model.isDeleted)


        return db.insert(TABEL_LEAVE_REQUEST, null, values)
    }



    /*GET PREV YEAR LEAVE QUOTA*/

    /*fun getPrevYearLeave():Int{
        var prevYearLeave=0

        return prevYearLeave

    }*/
}