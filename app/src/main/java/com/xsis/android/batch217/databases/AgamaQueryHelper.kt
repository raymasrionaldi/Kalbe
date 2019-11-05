package com.xsis.android.batch217.databases

import android.content.ContentValues
import android.database.Cursor
import com.xsis.android.batch217.models.Agama
import com.xsis.android.batch217.models.Company
import com.xsis.android.batch217.utils.*

class AgamaQueryHelper(val databaseHelper: DatabaseHelper) {

    private fun getSemuaAgama(): Cursor {
        val db = databaseHelper.readableDatabase

        val queryRead = "SELECT * FROM $TABEL_AGAMA WHERE $IS_DELETED='false'"

        return db.rawQuery(queryRead, null)
    }

    private fun konversiCursorKeListAgamaModel(cursor: Cursor): ArrayList<Agama>{
        var listAgama = ArrayList<Agama>()

        for(c in 0 until cursor.count){
            cursor.moveToPosition(c)

            val agama = Agama()
            agama.id_agama = cursor.getInt(0)
            agama.nama_agama = cursor.getString(1)
            agama.des_agama = cursor.getString(2)
            agama.is_Deleted = cursor.getString(3)

            listAgama.add(agama)
        }

        return listAgama
    }

    fun readSemuaAgamaModels(): List<Agama>{
        var listAgama = ArrayList<Agama>()

        val cursor = getSemuaAgama()
        if(cursor.count > 0){
            listAgama = konversiCursorKeListAgamaModel(cursor)
        }

        return listAgama
    }

    fun cariAgamaModels(keyword: String): List<Agama> {
        var listAgama = ArrayList<Agama>()
        if (keyword.isNotBlank()) {
            val db = databaseHelper.readableDatabase
            val queryCari =
                "SELECT * FROM $TABEL_AGAMA WHERE $NAMA_AGAMA LIKE '%$keyword%' AND " +
                        "$IS_DELETED = 'false'"

            val cursor = db.rawQuery(queryCari, null)
            if (cursor.count > 0) {
                listAgama = konversiCursorKeListAgamaModel(cursor)
            }
        }
        return listAgama
    }

    fun cekAgamaSudahAda(nama: String): Int {
        val db = databaseHelper.readableDatabase
        val queryCari =
            "SELECT * FROM $TABEL_AGAMA WHERE $NAMA_AGAMA= '$nama' AND " +
                    "$IS_DELETED = 'false'"

        val cursor = db.rawQuery(queryCari, null)

        return cursor.count
    }

    fun tambahAgama(model: Agama): Long {
        val db = databaseHelper.writableDatabase

        val values = ContentValues()
        values.put(NAMA_AGAMA, model.nama_agama)
        values.put(DES_AGAMA, model.des_agama)
        values.put(IS_DELETED, "false")

        return db.insert(TABEL_AGAMA, null, values)
    }

    fun editAgama(model: Agama): Int {
        val db = databaseHelper.writableDatabase

        val values = ContentValues()
        values.put(NAMA_AGAMA, model.nama_agama)
        values.put(DES_AGAMA, model.des_agama)

        return db.update(
            TABEL_AGAMA,
            values,
            "$ID_AGAMA = ?",
            arrayOf(model.id_agama.toString())
        )
    }

    fun hapusAgama(id: Int): Int {
        val db = databaseHelper.writableDatabase

        val values = ContentValues()
        values.put(IS_DELETED, "true")

        return db.update(TABEL_AGAMA, values, "$ID_AGAMA = ?", arrayOf(id.toString()))
    }
}