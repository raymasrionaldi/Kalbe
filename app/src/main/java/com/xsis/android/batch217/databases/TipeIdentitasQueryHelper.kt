package com.xsis.android.batch217.databases

import android.database.Cursor
import com.xsis.android.batch217.models.TipeIdentitas
import com.xsis.android.batch217.utils.TABEL_TIPE_IDENTITAS

class TipeIdentitasQueryHelper(val databasehelper:DatabaseHelper) {

    private fun getSemuaTipeIdentitas():Cursor{
        val db = databasehelper.readableDatabase
        val queryRead = "SELECT * FROM $TABEL_TIPE_IDENTITAS"
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
}