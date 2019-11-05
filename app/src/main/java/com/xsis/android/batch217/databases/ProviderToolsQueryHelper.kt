package com.xsis.android.batch217.databases

import android.content.ContentValues
import android.database.Cursor
import com.xsis.android.batch217.models.ProviderTools
import com.xsis.android.batch217.utils.*

class ProviderToolsQueryHelper(val databaseHelper: DatabaseHelper) {

    private fun getSemuaJenisCatatan(): Cursor {
        val db = databaseHelper.readableDatabase

        val queryRead = "SELECT * FROM $TABEL_PROVIDER WHERE $IS_DELETED = 'false'"

        return db.rawQuery(queryRead, null)
    }

    private fun konversiCursorKeListProviderToolsModel(cursor: Cursor): ArrayList<ProviderTools> {
        var listProviderTools = ArrayList<ProviderTools>()

        for (c in 0 until cursor.count) {
            cursor.moveToPosition(c)

            val jenisCatatan = ProviderTools()
            jenisCatatan.id_provider = cursor.getInt(0)
            jenisCatatan.nama_provider = cursor.getString(1)
            jenisCatatan.des_provider = cursor.getString(2)
            jenisCatatan.is_Deleted = cursor.getString(3)

            listProviderTools.add(jenisCatatan)
        }

        return listProviderTools
    }

    fun readSemuaProviderToolsModels(): List<ProviderTools> {
        var listProviderTools = ArrayList<ProviderTools>()

        val cursor = getSemuaJenisCatatan()
        if (cursor.count > 0) {
            listProviderTools = konversiCursorKeListProviderToolsModel(cursor)
        }

        return listProviderTools
    }

    fun cariProviderToolsModels(keyword: String): List<ProviderTools> {
        var listProviderTools = ArrayList<ProviderTools>()

        if (keyword.isNotBlank()) {
            val db = databaseHelper.readableDatabase
            val queryCari =
                "SELECT * FROM $TABEL_PROVIDER WHERE $NAMA_PROVIDER LIKE '%$keyword%' AND " +
                        "$IS_DELETED = 'false'"

            val cursor = db.rawQuery(queryCari, null)
            if (cursor.count > 0) {
                listProviderTools = konversiCursorKeListProviderToolsModel(cursor)
            }
        }

        return listProviderTools
    }

    fun tambahProviderTools(model: ProviderTools): Long {
        val db = databaseHelper.writableDatabase

        val values = ContentValues()
        values.put(NAMA_PROVIDER, model.nama_provider)
        values.put(DES_PROVIDER, model.des_provider)
        values.put(IS_DELETED, "false")

        return db.insert(TABEL_PROVIDER, null, values)
    }

    fun editProviderTools(model: ProviderTools): Int {
        val db = databaseHelper.writableDatabase

        val values = ContentValues()
        values.put(NAMA_PROVIDER, model.nama_provider)
        values.put(DES_PROVIDER, model.des_provider)

        return db.update(
            TABEL_PROVIDER,
            values,
            "$ID_PROVIDER = ?",
            arrayOf(model.id_provider.toString())
        )
    }

    fun hapusProviderTools(id: Int): Int {
        val db = databaseHelper.writableDatabase

        val values = ContentValues()
        values.put(IS_DELETED, "true")

        return db.update(TABEL_PROVIDER, values, "$ID_PROVIDER = ?", arrayOf(id.toString()))
    }

    fun cekPositionLevelSudahAda(nama: String): Int {
        val db = databaseHelper.readableDatabase
        val queryCari =
            "SELECT * FROM $TABEL_PROVIDER WHERE $NAMA_PROVIDER = '$nama' AND " +
                    "$IS_DELETED = 'false'"

        val cursor = db.rawQuery(queryCari, null)

        return cursor.count
    }
}