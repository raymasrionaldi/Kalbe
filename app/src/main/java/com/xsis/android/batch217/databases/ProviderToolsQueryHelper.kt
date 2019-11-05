package com.xsis.android.batch217.databases

import android.database.Cursor
import com.xsis.android.batch217.models.ProviderTools
import com.xsis.android.batch217.utils.IS_DELETED
import com.xsis.android.batch217.utils.NAMA_PROVIDER
import com.xsis.android.batch217.utils.TABEL_PROVIDER

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

        val db = databaseHelper.readableDatabase
        val queryCari =
            "SELECT * FROM $TABEL_PROVIDER WHERE $NAMA_PROVIDER LIKE '%$keyword%' AND " +
                    "$IS_DELETED = 'false'"

        val cursor = db.rawQuery(queryCari, null)
        if (cursor.count > 0) {
            listProviderTools = konversiCursorKeListProviderToolsModel(cursor)
        }

        return listProviderTools
    }
}