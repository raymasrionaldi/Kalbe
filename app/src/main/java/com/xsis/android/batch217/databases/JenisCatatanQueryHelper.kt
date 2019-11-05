package com.xsis.android.batch217.databases

import android.database.Cursor
import com.xsis.android.batch217.models.JenisCatatan
import com.xsis.android.batch217.models.PositionLevel
import com.xsis.android.batch217.utils.*

class JenisCatatanQueryHelper(val databaseHelper: DatabaseHelper) {

    private fun getSemuaJenisCatatan(): Cursor {
        val db = databaseHelper.readableDatabase

        val queryRead = "SELECT * FROM $TABEL_CATATAN WHERE $IS_DELETED = 'false'"

        return db.rawQuery(queryRead, null)
    }

    private fun konversiCursorKeListJenisCatatanModel(cursor: Cursor): ArrayList<JenisCatatan> {
        var listJenisCatatan = ArrayList<JenisCatatan>()

        for (c in 0 until cursor.count) {
            cursor.moveToPosition(c)

            val jenisCatatan = JenisCatatan()
            jenisCatatan.id_catatan = cursor.getInt(0)
            jenisCatatan.nama_catatan = cursor.getString(1)
            jenisCatatan.des_catatan = cursor.getString(2)
            jenisCatatan.is_Deleted = cursor.getString(3)

            listJenisCatatan.add(jenisCatatan)
        }

        return listJenisCatatan
    }

    fun readSemuaJenisCatatanModels(): List<JenisCatatan> {
        var listJenisCatatan = ArrayList<JenisCatatan>()

        val cursor = getSemuaJenisCatatan()
        if (cursor.count > 0) {
            listJenisCatatan = konversiCursorKeListJenisCatatanModel(cursor)
        }

        return listJenisCatatan
    }

    fun cariJenisCatatanModels(keyword: String): List<JenisCatatan> {
        var listJenisCatatan = ArrayList<JenisCatatan>()

        val db = databaseHelper.readableDatabase
        val queryCari =
            "SELECT * FROM $TABEL_CATATAN WHERE $NAMA_CATATAN LIKE '%$keyword%' AND " +
                    "$IS_DELETED = 'false'"

        val cursor = db.rawQuery(queryCari, null)
        if (cursor.count > 0) {
            listJenisCatatan = konversiCursorKeListJenisCatatanModel(cursor)
        }

        return listJenisCatatan
    }
}

