package com.xsis.android.batch217.databases

import android.database.Cursor
import com.xsis.android.batch217.models.Agama
import com.xsis.android.batch217.utils.DES_AGAMA
import com.xsis.android.batch217.utils.NAMA_AGAMA
import com.xsis.android.batch217.utils.TABEL_AGAMA

class AgamaQueryHelper(val databaseHelperCadangan: CadanganDbHelper) {

    private fun getSemuaAgama(): Cursor {
        val db = databaseHelperCadangan.readableDatabase

        val queryRead = "SELECT * FROM $TABEL_AGAMA"

        return db.rawQuery(queryRead, null)
    }

    private fun konversiCursorKeListAgamaModel(cursor: Cursor): ArrayList<Agama>{
        var listAgama = ArrayList<Agama>()

        for(c in 0 until cursor.count){
            cursor.moveToPosition(c)

            val agama = Agama()
            agama.id_agama = cursor.getInt(0)
            agama.nama_agama = cursor.getString(1)
            agama.des_agama = cursor.getString(2)
            agama.is_Deleted = cursor.getString(3)

            listAgama.add(agama)
        }

        return listAgama
    }

    fun readSemuaAgamaModels(): List<Agama>{
        var listAgama = ArrayList<Agama>()

        val cursor = getSemuaAgama()
        if(cursor.count > 0){
            listAgama = konversiCursorKeListAgamaModel(cursor)
        }

        return listAgama
    }

    fun cariAgamaModels(keyword:String): List<Agama>{
        var listAgama = ArrayList<Agama>()

        val db = databaseHelperCadangan.readableDatabase
        val queryCari = "SELECT * FROM $TABEL_AGAMA WHERE $NAMA_AGAMA LIKE '%$keyword%' OR " +
                "$DES_AGAMA LIKE '%$keyword%'"

        val cursor =db.rawQuery(queryCari,null)
        if(cursor.count > 0){
            listAgama = konversiCursorKeListAgamaModel(cursor)
        }

        return listAgama
    }
}