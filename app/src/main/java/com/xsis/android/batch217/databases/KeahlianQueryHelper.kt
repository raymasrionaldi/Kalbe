package com.xsis.android.batch217.databases

import android.database.Cursor
import com.xsis.android.batch217.models.Agama
import com.xsis.android.batch217.models.Keahlian
import com.xsis.android.batch217.utils.DES_KEAHLIAN
import com.xsis.android.batch217.utils.NAMA_KEAHLIAN
import com.xsis.android.batch217.utils.TABEL_KEAHLIAN

class KeahlianQueryHelper(val databaseHelper: DatabaseHelper) {

    fun getSemuaKeahlian(): Cursor {
        val db = databaseHelper.readableDatabase

        val queryRead = "SELECT * FROM $TABEL_KEAHLIAN"

        return db.rawQuery(queryRead, null)
    }

    private fun konversiCursorKeListKeahlianModel(cursor: Cursor): ArrayList<Keahlian>{
        var listKeahlian = ArrayList<Keahlian>()

        for(c in 0 until cursor.count){
            cursor.moveToPosition(c)

            val keahlian = Keahlian()
            keahlian.id_keahlian = cursor.getInt(0)
            keahlian.nama_keahlian = cursor.getString(1)
            keahlian.des_keahlian = cursor.getString(2)
            keahlian.is_deleted_keahlian = cursor.getString(3)

            listKeahlian.add(keahlian)
        }

        return listKeahlian
    }

    fun readSemuaKeahlianModels(): List<Keahlian>{
        var listKeahlian = ArrayList<Keahlian>()

        val cursor = getSemuaKeahlian()
        if(cursor.count > 0){
            listKeahlian = konversiCursorKeListKeahlianModel(cursor)
        }

        return listKeahlian
    }

    fun cariKeahlianModels(keyword:String): List<Keahlian>{
        var listKeahlian = ArrayList<Keahlian>()

        val db = databaseHelper.readableDatabase
        val queryCari = "SELECT * FROM $TABEL_KEAHLIAN WHERE $NAMA_KEAHLIAN LIKE '%$keyword%' OR " +
                "$DES_KEAHLIAN LIKE '%$keyword%'"

        val cursor =db.rawQuery(queryCari,null)
        if(cursor.count > 0){
            listKeahlian = konversiCursorKeListKeahlianModel(cursor)
        }

        return listKeahlian
    }
}