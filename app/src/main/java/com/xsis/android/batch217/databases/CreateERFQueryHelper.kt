package com.xsis.android.batch217.databases

import android.content.ContentValues
import android.database.Cursor
import com.xsis.android.batch217.models.ERF
import com.xsis.android.batch217.utils.*

class CreateERFQueryHelper (val databaseHelper:DatabaseHelper) {

    /*private fun konversiCursorKeListERFModel(cursor: Cursor):ArrayList<ERF>{
        var listPendidikan = ArrayList<ERF>()
        for (i in 0 until cursor.count){
            cursor.moveToPosition(i)

            val pendidikan = ERF()
            pendidikan.id_pendidikan = cursor.getInt(0)
            pendidikan.nama_pendidikan = cursor.getString(1)
            pendidikan.des_pendidikan = cursor.getString(2)
            pendidikan.is_Deleted = cursor.getString(3)
            listPendidikan.add(pendidikan)
        }
        return listPendidikan
    }*/

    fun tambahERF(model: ERF): Long {
        val db = databaseHelper.writableDatabase

        val values = ContentValues()
        values.put(ID_PRF_REQUEST, model.id_prf_request)
        values.put(NAMA_RESOURCE, model.nama_resource)
        values.put(ID_KEAHLIAN, model.id_keahlian)
        values.put(TGL_TERAKHIR, model.tgl_terakhir)
        values.put(ALASAN, model.alasan)
        values.put(USER_KEMBALI, model.user_kembali)
        values.put(TGL_KEMBALI, model.tgl_kembali)
        values.put(NAMA_TERIMA, model.nama_terima)
        values.put(TGL_TERIMA, model.tgl_terima)

        return db.insert(TABEL_ERF, null, values)
    }

    fun readListKeahlian(): List<String> {
        val listKeahlian = ArrayList<String>()
        val db = databaseHelper.readableDatabase
        val queryReadPosition = "SELECT $NAMA_KEAHLIAN FROM $TABEL_KEAHLIAN WHERE $IS_DELETED = 'false'"
        val cursor = db.rawQuery(queryReadPosition, null)
        if (cursor.count > 0) {
            for (i in 0 until cursor.count) {
                cursor.moveToPosition(i)
                listKeahlian.add(cursor.getString(0))
            }
        }
        return listKeahlian
    }


    fun cariClient(id:Int): String {
        var pilihClient = ""
        val db = databaseHelper.readableDatabase
        val queryCari = "SELECT $PLACEMENT FROM $TABEL_PRF_REQUEST " +
                "WHERE $ID_PRF_REQUEST LIKE '$id' "
        val cursor = db.rawQuery(queryCari, null)
        cursor.moveToFirst()
        pilihClient = cursor.getString(0)

        return pilihClient
    }

    fun cariKeahlian(nama:String): Int {
        var pilihKeahlian = 0
        val db = databaseHelper.readableDatabase
        val queryCari = "SELECT $ID_KEAHLIAN FROM $TABEL_KEAHLIAN " +
                "WHERE $NAMA_KEAHLIAN = '$nama' "
        val cursor = db.rawQuery(queryCari, null)
        cursor.moveToFirst()
        pilihKeahlian = cursor.getInt(0)

        return pilihKeahlian
    }

}