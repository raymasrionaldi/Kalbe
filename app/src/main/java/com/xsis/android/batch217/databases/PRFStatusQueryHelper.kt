package com.xsis.android.batch217.databases

import android.content.ContentValues
import android.database.Cursor
import com.xsis.android.batch217.models.PRFStatus
import com.xsis.android.batch217.utils.*

class PRFStatusQueryHelper(val databaseHelper: DatabaseHelper) {
    val db_read = databaseHelper.readableDatabase
    val db_write = databaseHelper.writableDatabase

    private fun konversiCursorKeList(cursor: Cursor):ArrayList<PRFStatus>{
        val list = ArrayList<PRFStatus>()

        if (cursor.count > 0){
            for (i in 0 until cursor.count){
                cursor.moveToPosition(i)

                val model = PRFStatus()
                model.idPRFStatus = cursor.getInt(0)
                model.namaPRFStatus = cursor.getString(1)
                model.notesPRFStatus = cursor.getString(2)
                list.add(model)
            }
        }
        return list
    }

    fun searchPRFStatus(keyword:String):ArrayList<PRFStatus>{
        var list = ArrayList<PRFStatus>()

        if (keyword.isNotEmpty()){
            val querySearch = "SELECT * FROM $TABEL_PRF WHERE $NAMA_PRF LIKE '%$keyword%' AND $IS_DELETED='false'"
            val cursor = db_read.rawQuery(querySearch, null)
            list = konversiCursorKeList(cursor)
        }

        return list
    }

    fun deleteDataPRFStatus(id:Int){
        val values = ContentValues()
        values.put(IS_DELETED, "true")
        db_write.update(TABEL_PRF, values, "$ID_PRF = ?", arrayOf(id.toString()))
    }

    fun loadDataPRFStatus(id:Int): PRFStatus{
        val queryLoad = "SELECT * FROM $TABEL_PRF WHERE $ID_PRF=$id AND $IS_DELETED='false'"
        val cursor = db_read.rawQuery(queryLoad, null)
        val list = konversiCursorKeList(cursor)

        return list[0]
    }

    fun editDataPRFStatus(id:Int, Nama:String, Notes: String){
        val values = ContentValues()
        values.put(NAMA_PRF, Nama)
        values.put(DES_PRF, Notes)
        db_write.update(TABEL_PRF, values, "$ID_PRF = ?", arrayOf(id.toString()))
    }

    fun addDataPRFStatus(Nama:String, Notes:String){
        val values = ContentValues()
        values.put(NAMA_PRF, Nama)
        values.put(DES_PRF, Notes)
        values.put(IS_DELETED, "false")
        db_write.insert(TABEL_PRF, null, values)
    }

    fun readOtherIDPRFStatus(id:Int):List<String>{
        val queryReadNames = "SELECT * FROM $TABEL_PRF " +
                "WHERE $ID_PRF!=$id " +
                "AND $IS_DELETED='false'"
        val cursor = db_read.rawQuery(queryReadNames, null)
        val list = konversiCursorKeList(cursor)
        println(queryReadNames)

        val names = ArrayList<String>()
        list.forEach { names.add(it.namaPRFStatus.toUpperCase()) }

        return names
    }

}