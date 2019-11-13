package com.xsis.android.batch217.databases

import android.content.ContentValues
import android.database.Cursor
import com.xsis.android.batch217.models.Company
import com.xsis.android.batch217.models.StatusTimesheet
import com.xsis.android.batch217.models.Timesheet
import com.xsis.android.batch217.utils.*

class TimesheetQueryHelper(val databaseHelper: DatabaseHelper) {
    private val companyQueryHelper = CompanyQueryHelper(databaseHelper)
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
            timesheet.progress_timesheet = cursor.getString(10)
            timesheet.is_Deleted = cursor.getString(11)

            listTimesheet.add(timesheet)
        }

        return listTimesheet
    }

    fun readSemuaTimesheetModels(): List<Timesheet> {
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

    fun hapusTimesheet(id: Int): Int {
        val db = databaseHelper.writableDatabase

        val values = ContentValues()
        values.put(IS_DELETED, "true")

        return db.update(TABEL_TIMESHEET, values, "$ID_TIMESHEET = ?", arrayOf(id.toString()))
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
        return listStatusTimesheet
    }

    fun tampilkanTimesheet(): List<Timesheet> {
        val db = databaseHelper.readableDatabase

        val queryRead = "SELECT * FROM $TABEL_TIMESHEET"
        val cursor = db.rawQuery(queryRead, null)


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
            timesheet.progress_timesheet = cursor.getString(10)
            timesheet.is_Deleted = cursor.getString(11)

            listTimesheet.add(timesheet)
        }

        return listTimesheet
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
        return listCompany
    }

    fun getSemuaCompany(): List<Company> {
        return companyQueryHelper.readSemuaCompanyModels()
    }

    fun getTimesheetBerdasarkanWaktuDanProgress(
        bulan: String,
        tahun: String,
        progress: String
    ): List<Timesheet> {
        var listTimesheet = ArrayList<Timesheet>()
        val db = databaseHelper.readableDatabase
        val queryCari =
            "SELECT * FROM $TABEL_TIMESHEET WHERE $REPORT_DATE_TIMESHEET LIKE '$bulan%$tahun' AND " +
                    "$PROGRESS_TIMESHEET = '$progress' AND $IS_DELETED = 'false'"

        val cursor = db.rawQuery(queryCari, null)
        if (cursor.count > 0) {
            listTimesheet = konversiCursorKeListTimesheetModel(cursor)
        }
        return listTimesheet
    }

    fun editTimesheet(model: Timesheet): Int {
        val db = databaseHelper.writableDatabase

        val values = ContentValues()
        values.put(STATUS_TIMESHEET, model.status_timesheet)
        values.put(CLIENT_DATABASE, model.client_timesheet)
        values.put(REPORT_DATE_TIMESHEET, model.reportDate_timesheet)
        values.put(START_REPORT_DATE_TIMESHEET, model.startReportDate_timesheet)
        values.put(END_REPORT_DATE_TIMESHEET, model.endReportDate_timesheet)
        values.put(OVERTIME_TIMESHEET, model.endOvertime_timesheet)
        values.put(START_REPORT_OVERTIME, model.starOvertime_timesheet)
        values.put(END_REPORT_OVERTIME, model.endOvertime_timesheet)
        values.put(NOTES_TIMESHEET, model.notes_timesheet)
        values.put(PROGRESS_TIMESHEET, model.progress_timesheet)


        return db.update(
            TABEL_TIMESHEET,
            values,
            "$ID_TIMESHEET = ?",
            arrayOf(model.id_timesheet.toString())
        )
    }

    fun tambahTimesheet(model: Timesheet): Long {
        val db = databaseHelper.writableDatabase

        val values = ContentValues()
        values.put(STATUS_TIMESHEET, model.status_timesheet)
        values.put(CLIENT_DATABASE, model.client_timesheet)
        values.put(REPORT_DATE_TIMESHEET, model.reportDate_timesheet)
        values.put(START_REPORT_DATE_TIMESHEET, model.startReportDate_timesheet)
        values.put(END_REPORT_DATE_TIMESHEET, model.endReportDate_timesheet)
        values.put(OVERTIME_TIMESHEET, model.overtime_timesheet)
        values.put(START_REPORT_OVERTIME, model.starOvertime_timesheet)
        values.put(END_REPORT_OVERTIME, model.endOvertime_timesheet)
        values.put(NOTES_TIMESHEET, model.notes_timesheet)
        values.put(PROGRESS_TIMESHEET, model.progress_timesheet)
        values.put(IS_DELETED, "false")

        return db.insert(TABEL_TIMESHEET, null, values)
    }

    fun getTimesheetById(id: Int): Timesheet {
        val db = databaseHelper.readableDatabase

        val queryRead = "SELECT * FROM $TABEL_TIMESHEET WHERE $ID_TIMESHEET = $id"

        val cursor = db.rawQuery(queryRead, null)
        if (cursor.count == 1) {
            return konversiCursorKeListTimesheetModel(cursor)[0]
        }
        return Timesheet()
    }

    fun changeProgress(ids: ArrayList<Int>, progress: String): Boolean {
        val db = databaseHelper.writableDatabase

        val values = ContentValues()
        values.put(PROGRESS_TIMESHEET, progress)
        var count = 0
        for (id in ids) {
            count += db.update(TABEL_TIMESHEET, values, "$ID_TIMESHEET = ?", arrayOf(id.toString()))
        }
        return count == ids.size
    }

}