package com.xsis.android.batch217.databases

import android.database.Cursor
import com.xsis.android.batch217.models.LeaveRequest
import com.xsis.android.batch217.utils.*

class LeaveRequestQueryHelper(val databaseHelper: DatabaseHelper) {

    private fun getSemuaLeaveRequest(): Cursor {
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
            leaveRequest.start= cursor.getString(1)
            leaveRequest.end
            leaveRequest.address
            leaveRequest.contact
            leaveRequest.reason
            leaveRequest.approval1
            leaveRequest.approval2
            leaveRequest.approval3

            leaveRequest.idLeaveType
            leaveRequest.leaveType
            leaveRequest.quota

            leaveRequest.idCutiKhusus
            leaveRequest.leaveName
            leaveRequest.isDeleted

            listLeaveRequest.add(leaveRequest)
        }

        return listLeaveRequest
    }

    fun cariLeaveRequestModels(keyword: String): List<LeaveRequest> {
        var listLeaveRequest = ArrayList<LeaveRequest>()
        if (keyword.isNotBlank()) {
            val db = databaseHelper.readableDatabase
            val queryCari =
                "SELECT * FROM $TABEL_LEAVE_REQUEST WHERE $START LIKE '%$keyword%' AND " +
                        "$IS_DELETED = 'false'"

            val cursor = db.rawQuery(queryCari, null)
            if (cursor.count > 0) {
                listLeaveRequest = konversiCursorKeListLeaveRequestModel(cursor)
            }
        }
        return listLeaveRequest
    }
}