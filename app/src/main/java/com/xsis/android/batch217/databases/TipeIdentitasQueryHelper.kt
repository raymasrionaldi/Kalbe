package com.xsis.android.batch217.databases

import android.content.ContentValues
import android.database.Cursor
import com.xsis.android.batch217.models.TipeIdentitas
import com.xsis.android.batch217.utils.*

class TipeIdentitasQueryHelper(val databasehelper:DatabaseHelper) {
    fun konversiCursorKeListTipeIdentitasModel(cursor: Cursor):ArrayList<TipeIdentitas>{
        val listTipeIdentitas = ArrayList<TipeIdentitas>()

        if (cursor.count > 0){
            for (i in 0 until cursor.count){
                cursor.moveToPosition(i)

                val tipeIdentitas = TipeIdentitas()
                tipeIdentitas.id_TipeIdentitas = cursor.getInt(0)
                tipeIdentitas.nama_TipeIdentitas = cursor.getString(1)
                tipeIdentitas.des_TipeIdentitas = cursor.getString(2)
                tipeIdentitas.is_deleted = cursor.getString(3)

                listTipeIdentitas.add(tipeIdentitas)
            }
        }
        return listTipeIdentitas
    }

    //Untuk form edit
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

    //Untuk search
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

    fun insertTipeIdentitas(nama:String, des: String){
        val db = databasehelper.writableDatabase

        val content = ContentValues()
        content.put(NAMA_IDENTITAS, nama)
        content.put(DES_IDENTITAS, des)
        content.put(IS_DELETED, "false")
        db.insert(TABEL_TIPE_IDENTITAS, null, content)
    }
    fun updateTipeIdentitas(nama:String, des:String, id:Int):List<TipeIdentitas> {
        var listTipeIdentitas = ArrayList<TipeIdentitas>()

        val db = databasehelper.writableDatabase
        val queryUpdate = "UPDATE $TABEL_TIPE_IDENTITAS " +
                "SET $DES_IDENTITAS = '$des', $NAMA_IDENTITAS = '$nama' " +
                "WHERE $ID_IDENTITAS = $id "
        val cursor = db.rawQuery(queryUpdate, null)
        if (cursor.count > 0) {
            listTipeIdentitas = konversiCursorKeListTipeIdentitasModel(cursor)
        }
        println(queryUpdate)
        return listTipeIdentitas
    }


    fun readNamaTipeIdentitas(nama:String):List<String>{
        val db = databasehelper.readableDatabase
        val queryCari = "SELECT * FROM $TABEL_TIPE_IDENTITAS " +
                "WHERE $NAMA_IDENTITAS = '$nama' AND $IS_DELETED = 'false'"
        val cursor = db.rawQuery(queryCari, null)

        val listTipeIdentitas = konversiCursorKeListTipeIdentitasModel(cursor)
        val listNamaIdentitas = ArrayList<String>()
        listTipeIdentitas.forEach { listNamaIdentitas.add(it.nama_TipeIdentitas) }

        return listNamaIdentitas
    }

    fun readIdIdentitasLain(id:Int, Nama:String):List<String>{

        val db = databasehelper.readableDatabase
        val queryCari = "SELECT * FROM $TABEL_TIPE_IDENTITAS " +
                "WHERE $ID_IDENTITAS != '$id' AND $NAMA_IDENTITAS = '$Nama' " +
                "AND $IS_DELETED = 'false'"
        val cursor = db.rawQuery(queryCari, null)

        val listTipeIdentitas = konversiCursorKeListTipeIdentitasModel(cursor)
        val listNamaIdentitas = ArrayList<String>()
        listTipeIdentitas.forEach { listNamaIdentitas.add(it.nama_TipeIdentitas) }

        return listNamaIdentitas
    }
}