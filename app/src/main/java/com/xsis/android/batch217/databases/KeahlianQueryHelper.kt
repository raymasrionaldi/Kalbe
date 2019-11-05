package com.xsis.android.batch217.databases

import android.content.ContentValues
import android.database.Cursor
import com.xsis.android.batch217.models.Agama
import com.xsis.android.batch217.models.Keahlian
import com.xsis.android.batch217.utils.*

class KeahlianQueryHelper(val databaseHelper: DatabaseHelper) {

    fun getSemuaKeahlian(): Cursor {
        val db = databaseHelper.readableDatabase

        val queryRead = "SELECT * FROM $TABEL_KEAHLIAN WHERE $IS_DELETED = 'false'"

        return db.rawQuery(queryRead, null)
    }

    private fun konversiCursorKeListKeahlianModel(cursor: Cursor): ArrayList<Keahlian> {
        var listKeahlian = ArrayList<Keahlian>()

        for (c in 0 until cursor.count) {
            cursor.moveToPosition(c)

            val keahlian = Keahlian()
            keahlian.id_keahlian = cursor.getInt(0)
            keahlian.nama_keahlian = cursor.getString(1)
            keahlian.des_keahlian = cursor.getString(2)
//            keahlian.is_deleted = cursor.getString(3)

            listKeahlian.add(keahlian)
        }

        return listKeahlian
    }

    fun readSemuaKeahlianModels(): List<Keahlian> {
        var listKeahlian = ArrayList<Keahlian>()

        val cursor = getSemuaKeahlian()
        if (cursor.count > 0) {
            listKeahlian = konversiCursorKeListKeahlianModel(cursor)
        }

        return listKeahlian
    }

    fun readNamaKeahlian(nama: String): List<Keahlian> {
        var listKeahlian = ArrayList<Keahlian>()

        val db = databaseHelper.readableDatabase
        val queryCari = "SELECT * FROM $TABEL_KEAHLIAN " +
                "WHERE $NAMA_KEAHLIAN = '$nama' "
        val cursor = db.rawQuery(queryCari, null)

        if (cursor.count > 0) {
            listKeahlian = konversiCursorKeListKeahlianModel(cursor)
        }

        return listKeahlian
    }

    fun updateKeahlian(nama: String, des: String): List<Keahlian> {
        var listKeahlian = ArrayList<Keahlian>()

        val db = databaseHelper.writableDatabase
        val queryUpdate = "UPDATE $TABEL_KEAHLIAN " +
                "SET $DES_KEAHLIAN = '$des', $IS_DELETED = 'false' " +
                "WHERE $NAMA_KEAHLIAN = '$nama' AND $IS_DELETED = 'true'"
        val cursor = db.rawQuery(queryUpdate, null)
        if (cursor.count > 0) {
            listKeahlian = konversiCursorKeListKeahlianModel(cursor)
        }
        println(queryUpdate)
        return listKeahlian
    }

    fun cariKeahlianModels(keyword: String): List<Keahlian> {
        var listKeahlian = ArrayList<Keahlian>()

        if (keyword.isNotBlank()) {
            val db = databaseHelper.readableDatabase
            val queryCari =
                "SELECT * FROM $TABEL_KEAHLIAN WHERE ($NAMA_KEAHLIAN LIKE '%$keyword%' OR $DES_KEAHLIAN LIKE '%$keyword%') AND is_deleted='false'"

            val cursor = db.rawQuery(queryCari, null)
            if (cursor.count > 0) {
                listKeahlian = konversiCursorKeListKeahlianModel(cursor)
            }
        }

        return listKeahlian
    }

    fun hapusKeahlian(id: Int): Int {
        val db = databaseHelper.writableDatabase

        val values = ContentValues()
        values.put(IS_DELETED, "true")

        return db.update(TABEL_KEAHLIAN, values, "$ID_KEAHLIAN = ?", arrayOf(id.toString()))
    }
}