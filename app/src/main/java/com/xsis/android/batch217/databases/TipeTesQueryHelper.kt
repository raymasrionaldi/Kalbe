package com.xsis.android.batch217.databases

import android.database.Cursor
import com.xsis.android.batch217.models.Keahlian
import com.xsis.android.batch217.models.TipeTes
import com.xsis.android.batch217.utils.*

class TipeTesQueryHelper(val databaseHelper: DatabaseHelper) {

    fun getSemuaTipeTes(): Cursor {
        val db = databaseHelper.readableDatabase

        val queryRead = "SELECT * FROM $TABEL_TIPE_TES"

        return db.rawQuery(queryRead, null)
    }

    private fun konversiCursorKeListTipeTesModel(cursor: Cursor): ArrayList<TipeTes>{
        var listTipeTes = ArrayList<TipeTes>()

        for(c in 0 until cursor.count){
            cursor.moveToPosition(c)

            val tipetes = TipeTes()
            tipetes.id_tipe_tes = cursor.getInt(0)
            tipetes.nama_tipe_tes = cursor.getString(1)
            tipetes.deskripsi_tipe_tes = cursor.getString(2)
            tipetes.is_delete_tipe_tes = cursor.getString(3)

            listTipeTes.add(tipetes)
        }

        return listTipeTes
    }

    fun readSemuaTipeTesModels(): List<TipeTes>{
        var listTipeTes = ArrayList<TipeTes>()

        val cursor = getSemuaTipeTes()
        if(cursor.count > 0){
            listTipeTes = konversiCursorKeListTipeTesModel(cursor)
        }

        return listTipeTes
    }

    fun cariTipeTesModels(keyword:String): List<TipeTes>{
        var listTipeTes = ArrayList<TipeTes>()

        val db = databaseHelper.readableDatabase
        val queryCari = "SELECT * FROM $TABEL_TIPE_TES WHERE $NAMA_TES LIKE '%$keyword%' OR " +
                "$DES_TES LIKE '%$keyword%'"

        val cursor =db.rawQuery(queryCari,null)
        if(cursor.count > 0){
            listTipeTes = konversiCursorKeListTipeTesModel(cursor)
        }

        return listTipeTes
    }
}