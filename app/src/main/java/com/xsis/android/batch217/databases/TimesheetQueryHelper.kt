package com.xsis.android.batch217.databases

import android.database.Cursor
import com.xsis.android.batch217.models.Company
import com.xsis.android.batch217.models.StatusTimesheet
import com.xsis.android.batch217.models.Timesheet
import com.xsis.android.batch217.utils.*

class TimesheetQueryHelper (val databaseHelper: DatabaseHelper) {
    private fun getSemuaTimesheet(): Cursor {
        val db = databaseHelper.readableDatabase

        val queryRead = "SELECT * FROM $TABEL_TIMESHEET WHERE $IS_DELETED = 'false'"

        return db.rawQuery(queryRead, null)
    }

    private fun konversiCursorKeListTimesheetModel(cursor: Cursor): ArrayList<Timesheet> {

        var listTimesheet = ArrayList<Timesheet>()

        for (c in 0 until cursor.count) {
            cursor.moveToPosition(c)

            val timesheet = Timesheet()
            timesheet.id_timesheet = cursor.getInt(0)
            timesheet.status_timesheet = cursor.getString(1)
            timesheet.client_timesheet = cursor.getString(2)
            timesheet.reportDate_timesheet = cursor.getString(3)
            timesheet.startReportDate_timesheet = cursor.getString(4)
            timesheet.endReportDate_timesheet = cursor.getString(5)
            timesheet.overtime_timesheet = cursor.getString(6)
            timesheet.starOvertime_timesheet = cursor.getString(7)
            timesheet.endOvertime_timesheet = cursor.getString(8)
            timesheet.notes_timesheet = cursor.getString(9)
            timesheet.is_Deleted = cursor.getString(10)

            listTimesheet.add(timesheet)
        }

        return listTimesheet
    }

    fun readSemuaEmployeeStatusModels(): List<Timesheet> {
        var listTimesheet = ArrayList<Timesheet>()

        val cursor = getSemuaTimesheet()
        if (cursor.count > 0) {
            listTimesheet = konversiCursorKeListTimesheetModel(cursor)
        }

        return listTimesheet
    }

    fun cariTimesheetModels(keyword: String): List<Timesheet> {
        var listTimesheet = ArrayList<Timesheet>()
        if (keyword.isNotBlank()) {
            val db = databaseHelper.readableDatabase
            val queryCari =
                "SELECT * FROM $TABEL_TIMESHEET WHERE $REPORT_DATE_TIMESHEET LIKE '%$keyword%' AND " +
                        "$IS_DELETED = 'false'"

            val cursor = db.rawQuery(queryCari, null)
            if (cursor.count > 0) {
                listTimesheet = konversiCursorKeListTimesheetModel(cursor)
            }
        }
        return listTimesheet
    }
    fun tampilkanStatusTimesheet(): List<StatusTimesheet> {
        val db = databaseHelper.readableDatabase

        val queryRead = "SELECT * FROM $TABEL_STATUS_TIMESHEET"
        val cursor = db.rawQuery(queryRead, null)


        var listStatusTimesheet = ArrayList<StatusTimesheet>()

        for (c in 0 until cursor.count) {
            cursor.moveToPosition(c)

            val stsTimesheet = StatusTimesheet()
            stsTimesheet.idStsTimesheet = cursor.getInt(0)
            stsTimesheet.namaStsTimesheet = cursor.getString(1)


           listStatusTimesheet.add(stsTimesheet)
        }
        return  listStatusTimesheet
    }
    fun tampilkanClientTimesheet(): List<Company> {
        val db = databaseHelper.readableDatabase

        val queryRead = "SELECT * FROM $TABEL_COMPANY WHERE $IS_DELETED = 'false'"
        val cursor = db.rawQuery(queryRead, null)

        var listCompany = ArrayList<Company>()

        for (c in 0 until cursor.count) {
            cursor.moveToPosition(c)
//bikin mutable list
            //isi data pertama pake hardcore "status*"
            //tambahkan list dari database
            val company = Company()
            company.idCompany = cursor.getInt(0)
            company.namaCompany = cursor.getString(1)
            company.kotaCompany = cursor.getString(2)
            company.kdPosCompany = cursor.getString(3)
            company.jlnCompany = cursor.getString(4)
            company.buildingCompany = cursor.getString(5)
            company.floorCompany = cursor.getString(6)



            listCompany.add(company)
        }
        return  listCompany
    }
}