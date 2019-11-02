package com.xsis.android.batch217.databases

import android.database.Cursor
import com.xsis.android.batch217.models.Pendidikan
import com.xsis.android.batch217.utils.*

class DatabasePendidikanQueryHelper(val databaseHelper:DatabaseHelper) {


    private fun getSemuaPendidikan(): Cursor {
        val db = databaseHelper.readableDatabase
        val queryRead = "select * FROM $TABEL_PENDIDIKAN"
        return db.rawQuery(queryRead,null)
    }

    private fun konversiCursorKeListPendidikanModel(cursor: Cursor):ArrayList<Pendidikan>{
        var listPendidikan = ArrayList<Pendidikan>()
        for (i in 0 until cursor.count){
            cursor.moveToPosition(i)

            val pendidikan = Pendidikan()
            pendidikan.id_pendidikan = cursor.getInt(0)
            pendidikan.nama_pendidikan = cursor.getString(1)
            pendidikan.des_pendidikan = cursor.getString(2)
            pendidikan.is_Deleted = cursor.getString(3)
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

        val cursor = getSemuaPendidikan()
        if (cursor.count > 0){
            listPendidikan = konversiCursorKeListPendidikanModel(cursor)
        }

        return listPendidikan
    }


}