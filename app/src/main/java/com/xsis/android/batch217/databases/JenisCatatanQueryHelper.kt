package com.xsis.android.batch217.databases

import android.content.ContentValues
import android.database.Cursor
import com.xsis.android.batch217.models.JenisCatatan
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
        if (keyword.isNotBlank()) {
            val db = databaseHelper.readableDatabase
            val queryCari =
                "SELECT * FROM $TABEL_CATATAN WHERE $NAMA_CATATAN LIKE '%$keyword%' AND " +
                        "$IS_DELETED = 'false'"

            val cursor = db.rawQuery(queryCari, null)
            if (cursor.count > 0) {
                listJenisCatatan = konversiCursorKeListJenisCatatanModel(cursor)
            }
        }
        return listJenisCatatan
    }

    fun tambahJenisCatatan(model: JenisCatatan): Long {
        val db = databaseHelper.writableDatabase

        val values = ContentValues()
        values.put(NAMA_CATATAN, model.nama_catatan)
        values.put(DES_CATATAN, model.des_catatan)
        values.put(IS_DELETED, "false")

        return db.insert(TABEL_CATATAN, null, values)
    }

    fun editJenisCatatan(model: JenisCatatan): Int {
        val db = databaseHelper.writableDatabase
        //println("model = ${model.nama_catatan} ${model.des_catatan}")
        val values = ContentValues()
        values.put(NAMA_CATATAN, model.nama_catatan)
        values.put(DES_CATATAN, model.des_catatan)

        return db.update(
            TABEL_CATATAN,
            values,
            "$ID_CATATAN = ?",
            arrayOf(model.id_catatan.toString())
        )
    }

    fun hapusJenisCatatan(id: Int): Int {
        val db = databaseHelper.writableDatabase

        val values = ContentValues()
        values.put(IS_DELETED, "true")

        return db.update(TABEL_CATATAN, values, "$ID_CATATAN = ?", arrayOf(id.toString()))
    }

    fun cekJenisCatatanSudahAda(nama: String): Int {
        val db = databaseHelper.readableDatabase
        val queryCari =
            "SELECT * FROM $TABEL_CATATAN WHERE $NAMA_CATATAN LIKE '$nama' AND " +
                    "$IS_DELETED = 'false'"

        val cursor = db.rawQuery(queryCari, null)

        return cursor.count
    }
}

