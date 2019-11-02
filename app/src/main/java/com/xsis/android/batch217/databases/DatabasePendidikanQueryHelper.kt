package com.xsis.android.batch217.databases

import android.database.Cursor
import com.xsis.android.batch217.models.Pendidikan
import com.xsis.android.batch217.utils.ID_PENDIDIKAN
import com.xsis.android.batch217.utils.IS_DELETED
import com.xsis.android.batch217.utils.NAMA_PENDIDIKAN
import com.xsis.android.batch217.utils.TABEL_PENDIDIKAN

class DatabasePendidikanQueryHelper(val databaseHelper:DatabaseHelper) {


    private fun readAllPendidikan(): Cursor {
        val db = databaseHelper.readableDatabase
        val queryRead = "SELECT * FROM $TABEL_PENDIDIKAN"
        return db.rawQuery(queryRead,null)
    }

    private fun konversiCursorKeListPendidikanModel(cursor: Cursor):ArrayList<Pendidikan>{
        var listPendidikan = ArrayList<Pendidikan>()
        for (i in 0 until cursor.count){
            cursor.moveToPosition(i)

            val pendidikan = Pendidikan()
            pendidikan.id = cursor.getInt(0)
            pendidikan.nama = cursor.getString(1)
            pendidikan.des = cursor.getString(2)
            pendidikan.is_deleted = cursor.getString(3)
            listPendidikan.add(pendidikan)
        }
        return listPendidikan
    }

    fun cariPendidikanModels(keyword:String):List<Pendidikan>{
        var listPendidikan = ArrayList<Pendidikan>()

        val db = databaseHelper.readableDatabase
        val queryCari = "SELECT * FROM $TABEL_PENDIDIKAN " +
                "WHERE $ID_PENDIDIKAN LIKE '%$keyword%' " +
                "OR $NAMA_PENDIDIKAN LIKE '%$keyword%'" +
                "AND $IS_DELETED = 'false'"
        val cursor = db.rawQuery(queryCari, null)

        if (cursor.count > 0){
            listPendidikan = konversiCursorKeListPendidikanModel(cursor)
        }

        return listPendidikan
    }

    fun readSemuaPendidikanModels():List<Pendidikan>{
        var listPendidikan = ArrayList<Pendidikan>()

        val cursor = readAllPendidikan()
        if (cursor.count > 0){
            listPendidikan = konversiCursorKeListPendidikanModel(cursor)
        }

        return listPendidikan
    }
}