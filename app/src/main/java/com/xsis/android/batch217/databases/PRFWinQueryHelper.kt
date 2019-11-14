package com.xsis.android.batch217.databases

import android.database.Cursor
import com.xsis.android.batch217.models.PRFWin
import com.xsis.android.batch217.utils.*

class PRFWinQueryHelper(val databaseHelper: DatabaseHelper) {
    fun cariPRFWinModels(keyword: String): List<PRFWin> {
        var listPRFWin = ArrayList<PRFWin>()
        if (keyword.isNotBlank()) {
            val db = databaseHelper.readableDatabase
            val queryCari =
                "SELECT * FROM $TABEL_PRF_REQUEST " +
                        "WHERE ($PLACEMENT LIKE '%$keyword%' " +
                        "OR $TYPE LIKE '%$keyword%' " +
                        "OR $PID LIKE '%$keyword%' )" +
                        "AND $WIN_STATUS = 'false'"

            val cursor = db.rawQuery(queryCari, null)
            println("cursor.count = ${cursor.count}")
            if (cursor.count > 0) {
                listPRFWin = konversiCursorKeListPRFWin(cursor)
            }
        }
        return listPRFWin
    }

    fun konversiCursorKeListPRFWin(cursor:Cursor): ArrayList<PRFWin>{
        val listPRFRequest = ArrayList<PRFWin>()

        for (c in 0 until cursor.count) {
            cursor.moveToPosition(c)

            val prfRequest = PRFWin()
            prfRequest.id_prf_request = cursor.getInt(0)
            prfRequest.tanggal = cursor.getString(1)
            prfRequest.type = cursor.getString(2)
            prfRequest.placement = cursor.getString(3)
            prfRequest.pid = cursor.getString(4)
            prfRequest.location = cursor.getString(5)
            prfRequest.period = cursor.getString(6)
            prfRequest.user_name = cursor.getString(7)
            prfRequest.telp_number = cursor.getString(8)
            prfRequest.email = cursor.getString(9)
            prfRequest.notebook = cursor.getString(10)
            prfRequest.overtime = cursor.getString(11)
            prfRequest.bast = cursor.getString(12)
            prfRequest.billing = cursor.getString(13)
            prfRequest.is_Deleted = cursor.getString(14)
            prfRequest.tahap_1 = cursor.getString(15)
            prfRequest.tahap_2 = cursor.getString(16)
            prfRequest.tahap_3 = cursor.getString(17)
            prfRequest.tahap_4 = cursor.getString(18)
            prfRequest.win_status = cursor.getString(19)

            listPRFRequest.add(prfRequest)
        }

        return listPRFRequest
    }
}