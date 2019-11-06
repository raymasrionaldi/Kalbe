package com.xsis.android.batch217.databases

import android.database.Cursor
import com.xsis.android.batch217.models.TipeIdentitas
import com.xsis.android.batch217.utils.*

class TipeIdentitasQueryHelper(val databasehelper:DatabaseHelper) {

    private fun getSemuaTipeIdentitas():Cursor{
        val db = databasehelper.readableDatabase
        val queryRead = "SELECT * FROM $TABEL_TIPE_IDENTITAS where $IS_DELETED='false' "
        return db.rawQuery(queryRead,null)
    }

    fun readSemuaTipeIdentitasModels(): List<TipeIdentitas>{
        var listTipeIdentitas = ArrayList<TipeIdentitas>()

        val cursor = getSemuaTipeIdentitas()
        if (cursor.count > 0){
            listTipeIdentitas = konversiCursorKeListTipeIdentitasModel(cursor)
        }
        return listTipeIdentitas
    }

    fun loadTipeIdentitas(id:Int):TipeIdentitas{
        var listTipeIdentitas = ArrayList<TipeIdentitas>()

        val db = databasehelper.readableDatabase
        val queryLoad = "SELECT * FROM $TABEL_TIPE_IDENTITAS WHERE $ID_IDENTITAS=$id"
        val cursor = db.rawQuery(queryLoad, null)
        if (cursor.count > 0){
            listTipeIdentitas = konversiCursorKeListTipeIdentitasModel(cursor)
        }
        return listTipeIdentitas[0]

    }

    fun konversiCursorKeListTipeIdentitasModel(cursor: Cursor):ArrayList<TipeIdentitas>{
        val listTipeIdentitas = ArrayList<TipeIdentitas>()

        for (i in 0 until cursor.count){
            cursor.moveToPosition(i)

            val tipeIdentitas = TipeIdentitas()
            tipeIdentitas.id_TipeIdentitas = cursor.getInt(0)
            tipeIdentitas.nama_TipeIdentitas = cursor.getString(1)
            tipeIdentitas.des_TipeIdentitas = cursor.getString(2)
            tipeIdentitas.is_deleted = cursor.getString(3)

            listTipeIdentitas.add(tipeIdentitas)
        }
        return listTipeIdentitas
    }

    fun cariTipeIdentitasModels(keyword:String): List<TipeIdentitas>{
        var listTipeIdentitas = ArrayList<TipeIdentitas>()

        if (keyword.isNotBlank()){
            val db = databasehelper.readableDatabase
            val queryCari = "SELECT * FROM $TABEL_TIPE_IDENTITAS WHERE ($NAMA_IDENTITAS LIKE '%$keyword%' OR $DES_IDENTITAS LIKE '%$keyword%') AND is_deleted='false'"

            val cursor =db.rawQuery(queryCari,null)
            if(cursor.count > 0){
                listTipeIdentitas = konversiCursorKeListTipeIdentitasModel(cursor)
            }
        }

        return listTipeIdentitas
    }

    fun updateTipeIdentitas(nama:String, des:String):List<TipeIdentitas> {
        var listTipeIdentitas = ArrayList<TipeIdentitas>()

        val db = databasehelper.writableDatabase
        val queryUpdate = "UPDATE $TABEL_TIPE_IDENTITAS " +
                "SET $DES_IDENTITAS = '$des', $IS_DELETED = 'false' " +
                "WHERE $NAMA_IDENTITAS = '$nama' AND $IS_DELETED = 'true'"
        val cursor = db.rawQuery(queryUpdate, null)
        if (cursor.count > 0) {
            listTipeIdentitas = konversiCursorKeListTipeIdentitasModel(cursor)
        }
        println(queryUpdate)
        return listTipeIdentitas
    }

    fun updateTipeIdentitas_0 (nama:String, des:String, id:Int): Boolean{//: List<TipeIdentitas>{
        var listTipeIdentitas = ArrayList<TipeIdentitas>()
        val listTipeIdentitasAll = readSemuaTipeIdentitasModels()

        val names = ArrayList<String>()
        listTipeIdentitasAll.forEach{
            if (it.id_TipeIdentitas != id){
                names.add(it.nama_TipeIdentitas)
            }
        }

//        var listTipeIdentitas = ArrayList<TipeIdentitas>()
        var ada = false
        if (names.contains(nama)){
            ada = true
        }

        if (ada){
            //Toast
            println("adaaaaa")
        } else{
            val db = databasehelper.writableDatabase
            val queryUpdate = "UPDATE $TABEL_TIPE_IDENTITAS SET $NAMA_IDENTITAS='$nama', $DES_IDENTITAS='$des' WHERE $ID_IDENTITAS=$id"
            println("UPDATE $TABEL_TIPE_IDENTITAS SET $NAMA_IDENTITAS='$nama', $DES_IDENTITAS='$des' WHERE $ID_IDENTITAS=$id")
            val cursor = db.rawQuery(queryUpdate,null)
            if (cursor.count > 0){
                listTipeIdentitas = konversiCursorKeListTipeIdentitasModel(cursor)
            }
        }
        return ada
    }

    fun readNamaTipeIdentitas(nama:String):List<TipeIdentitas>{
        var listTipeIdentitas = ArrayList<TipeIdentitas>()

        val db = databasehelper.readableDatabase
        val queryCari = "SELECT * FROM $TABEL_TIPE_IDENTITAS " +
                "WHERE $NAMA_IDENTITAS = '$nama' "
        val cursor = db.rawQuery(queryCari, null)

        if (cursor.count > 0){
            listTipeIdentitas = konversiCursorKeListTipeIdentitasModel(cursor)
        }

        return listTipeIdentitas
    }
}