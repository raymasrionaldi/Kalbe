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
    val annualLeaveQuota = 4
    val regularLeaveQuota = 16

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
            leaveRequest.start = cursor.getString(3)
            leaveRequest.end = cursor.getString(4)
            leaveRequest.address = cursor.getString(5)
            leaveRequest.contact = cursor.getString(6)
            leaveRequest.reason = cursor.getString(7)
//            leaveRequest.approvalState
//            leaveRequest.approvalName
            leaveRequest.idLeaveType = cursor.getInt(1)
            leaveRequest.leaveType = cursor.getString(10)
//            leaveRequest.quota
            leaveRequest.idCutiKhusus = cursor.getInt(2)
            leaveRequest.leaveName = cursor.getString(12)
            leaveRequest.isDeleted = cursor.getString(8)

            listLeaveRequest.add(leaveRequest)
        }
        return listLeaveRequest
    }

    private fun konversiCursorKeListLeaveTypeModel(cursor: Cursor): ArrayList<LeaveRequest> {
        var listLeaveType = ArrayList<LeaveRequest>()

        for (c in 0 until cursor.count) {
            cursor.moveToPosition(c)

            var leaveType = LeaveRequest()
            leaveType.idLeaveType = cursor.getInt(0)
            leaveType.leaveType = cursor.getString(1)
            listLeaveType.add(leaveType)
        }
        return listLeaveType
    }

    private fun konversiCursorKeListCutiKhususModel(cursor: Cursor): ArrayList<LeaveRequest> {
        var listCutiKhusus = ArrayList<LeaveRequest>()

        for (c in 0 until cursor.count) {
            cursor.moveToPosition(c)

            var cutiKhusus = LeaveRequest()
            cutiKhusus.idCutiKhusus = cursor.getInt(0)
            cutiKhusus.leaveName = cursor.getString(1)
            cutiKhusus.quotaCutiKhusus=cursor.getInt(2)
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
            println("BULAN# $queryCari")

            val cursor = db.rawQuery(queryCari, null)
            if (cursor.count > 0) {
                listLeaveRequest = konversiCursorKeListLeaveRequestModel(cursor)
            }
        }
        return listLeaveRequest
    }

    fun getLeaveRequestDetailById(id: Int): LeaveRequest {
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
        } else {
            return LeaveRequest()
        }
        return listLeaveRequest
    }

    fun getApprovalById(id: Int): List<LeaveRequest> {
        var listLeaveApproval = ArrayList<LeaveRequest>()
        val db = databaseHelper.readableDatabase
        val queryCari = "SELECT " +
                "a.approval_name,a.approval_state " +
                "FROM $TABEL_LEAVE_APPROVAL a " +
                "WHERE a.$ID_LEAVE ='$id'"

        val cursor = db.rawQuery(queryCari, null)
        if (cursor.count > 0) {
            listLeaveApproval = konversiCursorKeListApprovalModel(cursor)
        }
        return listLeaveApproval
    }

    fun getLatestApprovalStateById(id: Int): LeaveRequest{
        var latestApproval = LeaveRequest()
        val db = databaseHelper.readableDatabase
        val queryCari = "SELECT " +
                "a.approval_state " +
                "FROM $TABEL_LEAVE_APPROVAL a " +
                "WHERE a.$ID_LEAVE ='$id' ORDER BY $ID_LEAVE_APPROVAL DESC LIMIT 1"

        val cursor = db.rawQuery(queryCari, null)
        if (cursor.count > 0) {
            latestApproval = konversiCursorKeApprovalModel(cursor)
        }
        return latestApproval
    }

    private fun konversiCursorKeApprovalModel(cursor: Cursor): LeaveRequest {

        var approvalModel = LeaveRequest()
        for (c in 0 until cursor.count) {
            cursor.moveToPosition(c)
            approvalModel.approvalState = cursor.getString(0)
        }
        return approvalModel
    }

    private fun konversiCursorKeListApprovalModel(cursor: Cursor): ArrayList<LeaveRequest> {
        var listCutiKhusus = ArrayList<LeaveRequest>()

        for (c in 0 until cursor.count) {
            cursor.moveToPosition(c)

            var cutiKhusus = LeaveRequest()
            cutiKhusus.approvalName = cursor.getString(0)
            cutiKhusus.approvalState = cursor.getString(1)
            listCutiKhusus.add(cutiKhusus)
        }
        return listCutiKhusus
    }

    private fun konversiCursorKeListDateRangeModel(cursor: Cursor): ArrayList<LeaveRequest> {
        var listDateRange = ArrayList<LeaveRequest>()

        for (c in 0 until cursor.count) {
            cursor.moveToPosition(c)

            var dateRange = LeaveRequest()
            dateRange.start = cursor.getString(0)
            dateRange.end = cursor.getString(1)
            listDateRange.add(dateRange)
        }
        return listDateRange
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
        values.put(ID_LEAVE_TYPE, model.idLeaveType)
        values.put(ID_CUTI_KHUSUS, model.idCutiKhusus)
        values.put(START, model.start)
        values.put(END, model.end)
        values.put(ADDRESS, model.address)
        values.put(CONTACT, model.contact)
        values.put(REASON, model.reason)
        values.put(IS_DELETED, model.isDeleted)


        return db.insert(TABEL_LEAVE_REQUEST, null, values)
    }

    fun updateLeaveRequest(model: LeaveRequest): Int {
        val db = databaseHelper.writableDatabase

        val values = ContentValues()
        values.put(ID_LEAVE_TYPE, model.idLeaveType)
        values.put(ID_CUTI_KHUSUS, model.idCutiKhusus)
        values.put(START, model.start)
        values.put(END, model.end)
        values.put(ADDRESS, model.address)
        values.put(CONTACT, model.contact)
        values.put(REASON, model.reason)

        return db.update(
            TABEL_LEAVE_REQUEST,
            values,
            "$ID_LEAVE = ?",
            arrayOf(model.idLeaveRequest.toString())
        )
    }

    fun hapusLeaveRequest(id: Int): Int {
        val db = databaseHelper.writableDatabase

        val values = ContentValues()
        values.put(IS_DELETED, "true")

        return db.update(TABEL_LEAVE_REQUEST, values, "$ID_LEAVE = ?", arrayOf(id.toString()))
    }

    /*GET YEAR*/
    /*fun getRegisteredDate(year: Int): List<LeaveRequest>{
        var listLeaveDate = ArrayList<LeaveRequest>()

        val db = databaseHelper.readableDatabase
        val queryCari = "SELECT " +
                "start,end " +
                "FROM $TABEL_LEAVE_REQUEST " +
                "WHERE start LIKE '%$year' OR end LIKE '%$year'"

        val cursor = db.rawQuery(queryCari, null)
        if (cursor.count > 0) {
            listLeaveDate = konversiCursorKeListDateRangeModel(cursor)
        }

        return listLeaveDate
    }*/


    /*GET PREV YEAR LEAVE QUOTA*/

    fun getLeaveDateRangeByYear(year:Int): List<LeaveRequest> {
        var listLeaveDate = ArrayList<LeaveRequest>()

        val db = databaseHelper.readableDatabase
        val queryCari = "SELECT " +
                "start,end " +
                "FROM $TABEL_LEAVE_REQUEST " +
                "WHERE start LIKE '%$year'"

        val cursor = db.rawQuery(queryCari, null)
        if (cursor.count > 0) {
            listLeaveDate = konversiCursorKeListDateRangeModel(cursor)
        }
        return listLeaveDate
    }

    fun getHariLiburNasional(year:Int): ArrayList<String> {
        var listLiburNasional = ArrayList<String>()

        listLiburNasional.add("January 01, ${year.toString()}")
        listLiburNasional.add("May 01, ${year.toString()}")
        listLiburNasional.add("June 01, ${year.toString()}")
        listLiburNasional.add("August 17, ${year.toString()}")
        listLiburNasional.add("June 01, ${year.toString()}")
        listLiburNasional.add("December 24, ${year.toString()}")
        listLiburNasional.add("December 25, ${year.toString()}")

        when(year){
            2018->{
                //https://publicholidays.co.id/id/2018-dates/
                listLiburNasional.add("February 16, ${year.toString()}")
                listLiburNasional.add("March 17, ${year.toString()}")
                listLiburNasional.add("March 30, ${year.toString()}")
                listLiburNasional.add("April 14, ${year.toString()}")
                listLiburNasional.add("May 10, ${year.toString()}")
                listLiburNasional.add("May 29, ${year.toString()}")
                listLiburNasional.add("June 11, ${year.toString()}")
                listLiburNasional.add("June 12, ${year.toString()}")
                listLiburNasional.add("June 13, ${year.toString()}")
                listLiburNasional.add("June 14, ${year.toString()}")
                listLiburNasional.add("June 15, ${year.toString()}")
                listLiburNasional.add("June 18, ${year.toString()}")
                listLiburNasional.add("June 20, ${year.toString()}")
                listLiburNasional.add("June 27, ${year.toString()}")
                listLiburNasional.add("August 22, ${year.toString()}")
                listLiburNasional.add("September 11, ${year.toString()}")
                listLiburNasional.add("November 20, ${year.toString()}")

            }
            2019->{
                //https://publicholidays.co.id/id/2019-dates/
                listLiburNasional.add("February 05, ${year.toString()}")
                listLiburNasional.add("March 07, ${year.toString()}")
                listLiburNasional.add("April 14, ${year.toString()}")
                listLiburNasional.add("April 03, ${year.toString()}")
                listLiburNasional.add("April 19, ${year.toString()}")
                listLiburNasional.add("May 19, ${year.toString()}")
                listLiburNasional.add("May 30, ${year.toString()}")
                listLiburNasional.add("June 03, ${year.toString()}")
                listLiburNasional.add("June 04, ${year.toString()}")
                listLiburNasional.add("June 05, ${year.toString()}")
                listLiburNasional.add("June 06, ${year.toString()}")
                listLiburNasional.add("June 07, ${year.toString()}")
                listLiburNasional.add("August 11, ${year.toString()}")
                listLiburNasional.add("September 01, ${year.toString()}")
                listLiburNasional.add("November 09, ${year.toString()}")
            }
        }

        return listLiburNasional
    }
}