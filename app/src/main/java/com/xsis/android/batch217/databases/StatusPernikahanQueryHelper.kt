package com.xsis.android.batch217.databases

import android.content.ContentValues
import android.database.Cursor
import com.xsis.android.batch217.models.JenisCatatan
import com.xsis.android.batch217.models.StatusPernikahan
import com.xsis.android.batch217.utils.*

class StatusPernikahanQueryHelper(val databaseHelper: DatabaseHelper) {

    private fun getSemuaStatusPernikahan(): Cursor {
        val db = databaseHelper.readableDatabase

        val queryRead = "SELECT * FROM $TABEL_STATUS_NIKAH WHERE $IS_DELETED = 'false'"

        return db.rawQuery(queryRead, null)
    }

    private fun konversiCursorKeListStatusPernikahanModel(cursor: Cursor): ArrayList<StatusPernikahan> {
        var listStatusPernikahan = ArrayList<StatusPernikahan>()

        for (c in 0 until cursor.count) {
            cursor.moveToPosition(c)

            val statusPernikahan = StatusPernikahan()
            statusPernikahan.idStatusPernikahan = cursor.getInt(0)
            statusPernikahan.namaStatusPernikahan = cursor.getString(1)
            statusPernikahan.desStatusPernikahan = cursor.getString(2)
            statusPernikahan.isDeleted = cursor.getString(3)

            listStatusPernikahan.add(statusPernikahan)
        }

        return listStatusPernikahan
    }

    fun readSemuaStatusPernikahanModels(): List<StatusPernikahan> {
        var listStatusPernikahan = ArrayList<StatusPernikahan>()

        val cursor = getSemuaStatusPernikahan()
        if (cursor.count > 0) {
            listStatusPernikahan = konversiCursorKeListStatusPernikahanModel(cursor)
        }

        return listStatusPernikahan
    }

    fun cariStatusPernikahanModels(keyword: String): List<StatusPernikahan> {
        var listStatusPernikahan = ArrayList<StatusPernikahan>()
        if (keyword.isNotBlank()) {
            val db = databaseHelper.readableDatabase
            val queryCari =
                "SELECT * FROM $TABEL_STATUS_NIKAH WHERE $NAMA_STATUS_NIKAH LIKE '%$keyword%' AND " +
                        "$IS_DELETED = 'false'"

            val cursor = db.rawQuery(queryCari, null)
            if (cursor.count > 0) {
                listStatusPernikahan = konversiCursorKeListStatusPernikahanModel(cursor)
            }
        }
        return listStatusPernikahan
    }

    fun tambahStatusPernikahan(model: StatusPernikahan): Long {
        val db = databaseHelper.writableDatabase

        val values = ContentValues()
        values.put(NAMA_STATUS_NIKAH, model.namaStatusPernikahan)
        values.put(DES_STATUS_NIKAH, model.desStatusPernikahan)
        values.put(IS_DELETED, "false")

        return db.insert(TABEL_STATUS_NIKAH, null, values)
    }

    fun editStatusPernikahan(model: StatusPernikahan): Int {
        val db = databaseHelper.writableDatabase
        val values = ContentValues()
        values.put(NAMA_STATUS_NIKAH, model.namaStatusPernikahan)
        values.put(DES_STATUS_NIKAH, model.desStatusPernikahan)

        return db.update(
            TABEL_STATUS_NIKAH,
            values,
            "$ID_CATATAN = ?",
            arrayOf(model.idStatusPernikahan.toString())
        )
    }

    fun hapusStatusPernikahan(id: Int): Int {
        val db = databaseHelper.writableDatabase

        val values = ContentValues()
        values.put(IS_DELETED, "true")

        return db.update(TABEL_STATUS_NIKAH, values, "$ID_STATUS_NIKAH = ?", arrayOf(id.toString()))
    }

    fun cekStatusPernikahanSudahAda(nama: String): Int {
        val db = databaseHelper.readableDatabase
        val queryCari =
            "SELECT * FROM $TABEL_STATUS_NIKAH WHERE $NAMA_STATUS_NIKAH LIKE '$nama' AND " +
                    "$IS_DELETED = 'false'"

        val cursor = db.rawQuery(queryCari, null)

        return cursor.count
    }
}

