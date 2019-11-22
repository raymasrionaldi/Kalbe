package com.xsis.android.batch217.databases

import android.content.ContentValues
import android.database.Cursor
import android.widget.Toast
import com.xsis.android.batch217.models.Pendidikan
import com.xsis.android.batch217.utils.*

class PendidikanQueryHelper(val databaseHelper:DatabaseHelper) {


    private fun getSemuaPendidikan(): Cursor {
        val db = databaseHelper.readableDatabase
        val queryRead = "select * FROM $TABEL_PENDIDIKAN WHERE $IS_DELETED = 'false'"
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

    fun readPendidikan(nama:String):List<Pendidikan>{
        var listPendidikan = ArrayList<Pendidikan>()

        val db = databaseHelper.readableDatabase
        val queryCari = "SELECT * FROM $TABEL_PENDIDIKAN " +
                "WHERE $NAMA_PENDIDIKAN = '$nama' " +
                "AND $IS_DELETED = 'true'"
        val cursor = db.rawQuery(queryCari, null)

        if (cursor.count > 0){
            listPendidikan = konversiCursorKeListPendidikanModel(cursor)
        }

        return listPendidikan
    }

    fun readNamaPendidikan(nama:String):List<Pendidikan>{
        var listPendidikan = ArrayList<Pendidikan>()

        val db = databaseHelper.readableDatabase
        val queryCari = "SELECT * FROM $TABEL_PENDIDIKAN " +
                "WHERE $NAMA_PENDIDIKAN = '$nama' AND $IS_DELETED = 'false' "
        val cursor = db.rawQuery(queryCari, null)

        if (cursor.count > 0){
            listPendidikan = konversiCursorKeListPendidikanModel(cursor)
        }

        return listPendidikan
    }

    fun updatePendidikan(nama: String, des: String):List<Pendidikan>{
        var listPendidikan = ArrayList<Pendidikan>()

        val db = databaseHelper.writableDatabase
        val queryUpdate = "UPDATE $TABEL_PENDIDIKAN " +
                "SET $DES_PENDIDIKAN = '$des', $IS_DELETED = 'false' " +
                "WHERE $NAMA_PENDIDIKAN = '$nama' AND $IS_DELETED = 'true'"
        val cursor = db.rawQuery(queryUpdate, null)
        if (cursor.count > 0){
            listPendidikan = konversiCursorKeListPendidikanModel(cursor)
        }
        println(queryUpdate)
        return listPendidikan
    }

    fun readUpdate(id:Int, nama:String):List<Pendidikan>{
        var listPendidikan = ArrayList<Pendidikan>()

        val db = databaseHelper.writableDatabase
        val queryUpdate = "SELECT * FROM $TABEL_PENDIDIKAN " +
                "WHERE $NAMA_PENDIDIKAN = '$nama' AND $ID_PENDIDIKAN != '$id'"
        val cursor = db.rawQuery(queryUpdate, null)
        if (cursor.count > 0){
            listPendidikan = konversiCursorKeListPendidikanModel(cursor)
        }

        println(queryUpdate)
        return listPendidikan
    }

    fun updateDelete(id:Int, nama: String, des: String):List<Pendidikan>{
        var listPendidikan = ArrayList<Pendidikan>()

        val db = databaseHelper.writableDatabase
        val queryUpdate = "UPDATE $TABEL_PENDIDIKAN " +
                "SET $DES_PENDIDIKAN = '$des', $NAMA_PENDIDIKAN = '$nama' " +
                "WHERE $ID_PENDIDIKAN = '$id'"
        val cursor = db.rawQuery(queryUpdate, null)
        if (cursor.count > 0){
            listPendidikan = konversiCursorKeListPendidikanModel(cursor)
        }
        println(queryUpdate)
        return listPendidikan
    }

    fun cariPendidikanModels(keyword:String):List<Pendidikan>{
        var listPendidikan = ArrayList<Pendidikan>()

        val db = databaseHelper.readableDatabase
        if(keyword.isNotBlank()) {
            val queryCari = "SELECT * FROM $TABEL_PENDIDIKAN " +
                    "WHERE $NAMA_PENDIDIKAN LIKE '%$keyword%'" +
                    "AND $IS_DELETED = 'false'"
            val cursor = db.rawQuery(queryCari, null)

            if (cursor.count > 0){
                listPendidikan = konversiCursorKeListPendidikanModel(cursor)
            }
        }
        return listPendidikan
    }


    fun readSemuaPendidikanModels():List<Pendidikan>{
        var listPendidikan = ArrayList<Pendidikan>()

        val db = databaseHelper.readableDatabase
        val queryCari = "SELECT * FROM $TABEL_PENDIDIKAN " +
                "WHERE $ID_PENDIDIKAN = '0' "
        val cursor = db.rawQuery(queryCari, null)

        if (cursor.count > 0){
            listPendidikan = konversiCursorKeListPendidikanModel(cursor)
        }

        return listPendidikan
    }

    fun tambahPendidikan(model: Pendidikan): Long {
        val db = databaseHelper.writableDatabase

        val values = ContentValues()
        values.put(NAMA_PENDIDIKAN, model.nama_pendidikan)
        values.put(DES_PENDIDIKAN, model.des_pendidikan)
        values.put(IS_DELETED, "false")

        return db.insert(TABEL_PENDIDIKAN, null, values)
    }

}