package com.xsis.android.batch217.databases

import android.database.Cursor
import com.xsis.android.batch217.models.EmployeeStatus
import com.xsis.android.batch217.models.JenisCatatan
import com.xsis.android.batch217.utils.*

class EmployeeStatusQueryHelper (val databaseHelper: DatabaseHelper) {

    private fun getSemuaEmployeeStatus(): Cursor {
        val db = databaseHelper.readableDatabase

        val queryRead = "SELECT * FROM $TABEL_EMPLOYEE_STATUS WHERE $IS_DELETED = 'false'"

        return db.rawQuery(queryRead, null)
    }

    private fun konversiCursorKeListEmployeeStatusModel(cursor: Cursor): ArrayList<EmployeeStatus> {
        var listEmployeeStatus = ArrayList<EmployeeStatus>()

        for (c in 0 until cursor.count) {
            cursor.moveToPosition(c)

            val employeeStatus = EmployeeStatus()
            employeeStatus.id_employee_status = cursor.getInt(0)
            employeeStatus.nama_employee_status = cursor.getString(1)
            employeeStatus.des_employee_status = cursor.getString(2)
            employeeStatus.is_Deleted = cursor.getString(3)

            listEmployeeStatus.add(employeeStatus)
        }

        return listEmployeeStatus
    }

    fun readSemuaEmployeeStatusModels(): List<EmployeeStatus> {
        var listEmployeeStatus = ArrayList<EmployeeStatus>()

        val cursor = getSemuaEmployeeStatus()
        if (cursor.count > 0) {
            listEmployeeStatus = konversiCursorKeListEmployeeStatusModel(cursor)
        }

        return listEmployeeStatus
    }

    fun cariEmployeeStatusModels(keyword: String): List<EmployeeStatus> {
        var listEmployeeStatus = ArrayList<EmployeeStatus>()

        val db = databaseHelper.readableDatabase
        val queryCari =
            "SELECT * FROM $TABEL_EMPLOYEE_STATUS WHERE $NAMA_EMP LIKE '%$keyword%' AND " +
                    "$IS_DELETED = 'false'"

        val cursor = db.rawQuery(queryCari, null)
        if (cursor.count > 0) {
            listEmployeeStatus = konversiCursorKeListEmployeeStatusModel(cursor)
        }

        return listEmployeeStatus
    }
}