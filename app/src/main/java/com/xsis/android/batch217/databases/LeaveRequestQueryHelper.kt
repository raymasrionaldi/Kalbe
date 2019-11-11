package com.xsis.android.batch217.databases

import android.database.Cursor
import com.xsis.android.batch217.models.LeaveRequest
import com.xsis.android.batch217.utils.*

class LeaveRequestQueryHelper(val databaseHelper: DatabaseHelper) {

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
                    "WHERE a.$START LIKE '%$keyword%' AND a.$IS_DELETED ='false'"

            val cursor = db.rawQuery(queryCari, null)
            if (cursor.count > 0) {
                listLeaveRequest = konversiCursorKeListLeaveRequestModel(cursor)
            }
        }
        return listLeaveRequest
    }

    fun getDetailById(id:Int): List<LeaveRequest>{
        var listLeaveRequest = ArrayList<LeaveRequest>()
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
            listLeaveRequest = konversiCursorKeListLeaveRequestModel(cursor)
        }
        return listLeaveRequest
    }
}