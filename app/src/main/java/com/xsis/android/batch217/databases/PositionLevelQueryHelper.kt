package com.xsis.android.batch217.databases

import android.content.ContentValues
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
        if (keyword.isNotBlank()) {
            val db = databaseHelper.readableDatabase
            val queryCari =
                "SELECT * FROM $TABEL_POSITION_LEVEL WHERE $NAMA_POSITION LIKE '%$keyword%' AND " +
                        "$IS_DELETED = 'false'"

            val cursor = db.rawQuery(queryCari, null)
            if (cursor.count > 0) {
                listPositionLevel = konversiCursorKeListPositionLevelModel(cursor)
            }
        }
        return listPositionLevel
    }

    fun tambahPostionLevel(model: PositionLevel): Long {
        val db = databaseHelper.writableDatabase

        val values = ContentValues()
        values.put(NAMA_POSITION, model.namaPosition)
        values.put(DES_POSITION, model.desPosition)
        values.put(IS_DELETED, "false")

        return db.insert(TABEL_POSITION_LEVEL, null, values)
    }

    fun editPositionLevel(model: PositionLevel): Int {
        val db = databaseHelper.writableDatabase

        val values = ContentValues()
        values.put(NAMA_POSITION, model.namaPosition)
        values.put(DES_POSITION, model.desPosition)

        return db.update(
            TABEL_COMPANY,
            values,
            "$ID_POSITION = ?",
            arrayOf(model.idPostionLevel.toString())
        )
    }

    fun hapusPositionLevel(id: Int): Int {
        val db = databaseHelper.writableDatabase

        val values = ContentValues()
        values.put(IS_DELETED, "true")

        return db.update(TABEL_POSITION_LEVEL, values, "$ID_POSITION = ?", arrayOf(id.toString()))
    }

    fun cekPositionLevelSudahAda(nama: String): Int {
        val db = databaseHelper.readableDatabase
        val queryCari =
            "SELECT * FROM $TABEL_POSITION_LEVEL WHERE $NAMA_POSITION = '$nama' AND " +
                    "$IS_DELETED = 'false'"

        val cursor = db.rawQuery(queryCari, null)

        return cursor.count
    }
}