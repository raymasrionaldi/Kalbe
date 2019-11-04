package com.xsis.android.batch217.databases

import android.database.Cursor
import com.xsis.android.batch217.models.PositionLevel
import com.xsis.android.batch217.utils.*

class PositionLevelQueryHelper(val databaseHelper: DatabaseHelper) {

    private fun getSemuaPositionLevel(): Cursor {
        val db = databaseHelper.readableDatabase

        val queryRead = "SELECT * FROM $TABEL_POSITION_LEVEL WHERE $IS_DELETED = 'false'"

        return db.rawQuery(queryRead, null)
    }

    private fun konversiCursorKeListPositionLevelModel(cursor: Cursor): ArrayList<PositionLevel> {
        var listPositionLevel = ArrayList<PositionLevel>()

        for (c in 0 until cursor.count) {
            cursor.moveToPosition(c)

            val positionLevel = PositionLevel()
            positionLevel.idPostionLevel = cursor.getInt(0)
            positionLevel.namaPosition = cursor.getString(1)
            positionLevel.desPosition = cursor.getString(2)
            positionLevel.isDeleted = cursor.getString(3)

            listPositionLevel.add(positionLevel)
        }

        return listPositionLevel
    }

    fun readSemuaPositionLevelModels(): List<PositionLevel> {
        var listPositionLevel = ArrayList<PositionLevel>()

        val cursor = getSemuaPositionLevel()
        if (cursor.count > 0) {
            listPositionLevel = konversiCursorKeListPositionLevelModel(cursor)
        }

        return listPositionLevel
    }

    fun cariPositionLevelModels(keyword: String): List<PositionLevel> {
        var listPositionLevel = ArrayList<PositionLevel>()

        val db = databaseHelper.readableDatabase
        val queryCari =
            "SELECT * FROM $TABEL_POSITION_LEVEL WHERE $NAMA_POSITION LIKE '%$keyword%' AND " +
                    "$IS_DELETED = 'false'"

        val cursor = db.rawQuery(queryCari, null)
        if (cursor.count > 0) {
            listPositionLevel = konversiCursorKeListPositionLevelModel(cursor)
        }

        return listPositionLevel
    }
}