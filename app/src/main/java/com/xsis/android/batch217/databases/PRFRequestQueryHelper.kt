package com.xsis.android.batch217.databases

import android.database.Cursor
import com.xsis.android.batch217.models.PRFRequest
import com.xsis.android.batch217.utils.IS_DELETED
import com.xsis.android.batch217.utils.PLACEMENT
import com.xsis.android.batch217.utils.TABEL_PRF_REQUEST

class PRFRequestQueryHelper (val databaseHelper: DatabaseHelper) {

    private fun getSemuaPRFRequest(): Cursor {
        val db = databaseHelper.readableDatabase

        val queryRead = "SELECT * FROM $TABEL_PRF_REQUEST WHERE $IS_DELETED = 'false'"

        return db.rawQuery(queryRead, null)
    }

    private fun konversiCursorKeListPRFRequestModel(cursor: Cursor): ArrayList<PRFRequest> {
        var listPRFRequest = ArrayList<PRFRequest>()

        for (c in 0 until cursor.count) {
            cursor.moveToPosition(c)

            val prfRequest = PRFRequest()
            prfRequest.id_prf_request = cursor.getInt(0)
            prfRequest.tanggal = cursor.getString(1)
            prfRequest.type = cursor.getString(2)
            prfRequest.placement = cursor.getString(3)
            prfRequest.pid = cursor.getString(4)
            prfRequest.location = cursor.getString(5)
            prfRequest.period = cursor.getString(6)
            prfRequest.user_name = cursor.getString(7)
            prfRequest.telp_number = cursor.getString(8)
            prfRequest.notebook = cursor.getString(9)
            prfRequest.overtime = cursor.getString(10)
            prfRequest.bast = cursor.getString(11)
            prfRequest.billing = cursor.getString(12)
            prfRequest.is_Deleted = cursor.getString(13)

            listPRFRequest.add(prfRequest)
        }

        return listPRFRequest
    }

    fun readSemuaPRFRequestModels(): List<PRFRequest> {
        var listPRFRequest = ArrayList<PRFRequest>()

        val cursor = getSemuaPRFRequest()
        if (cursor.count > 0) {
            listPRFRequest = konversiCursorKeListPRFRequestModel(cursor)
        }

        return listPRFRequest
    }

    fun cariPRFRequestModels(keyword: String): List<PRFRequest> {
        var listPRFRequest = ArrayList<PRFRequest>()
        if (keyword.isNotBlank()) {
            val db = databaseHelper.readableDatabase
            val queryCari =
                "SELECT * FROM $TABEL_PRF_REQUEST WHERE $PLACEMENT LIKE '%$keyword%' AND " +
                        "$IS_DELETED = 'false'"

            val cursor = db.rawQuery(queryCari, null)
            if (cursor.count > 0) {
                listPRFRequest = konversiCursorKeListPRFRequestModel(cursor)
            }
        }
        return listPRFRequest
    }

}