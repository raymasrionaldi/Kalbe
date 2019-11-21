package com.xsis.android.batch217.databases

import android.database.Cursor
import com.xsis.android.batch217.models.Periode
import com.xsis.android.batch217.utils.*

class PeriodeQueryHelper(val databaseHelper:DatabaseHelper) {

    private fun konversiCursorKeListPeriodeModel(cursor: Cursor):ArrayList<Periode>{
        var listPeriode = ArrayList<Periode>()
        for (i in 0 until cursor.count){
            cursor.moveToPosition(i)

            val periode = Periode()
            periode.id_periode = cursor.getInt(0)
            periode.nama_periode = cursor.getString(1)
            periode.des_periode = cursor.getString(2)
            periode.is_Deleted = cursor.getString(3)
            listPeriode.add(periode)
        }
        return listPeriode
    }

    fun readNamaPeriode(nama:String):List<Periode>{
        var listPeriode = ArrayList<Periode>()

        val db = databaseHelper.readableDatabase
        val queryCari = "SELECT * FROM $TABEL_PERIODE " +
                "WHERE $NAMA_PERIODE = '$nama' "
        val cursor = db.rawQuery(queryCari, null)

        if (cursor.count > 0){
            listPeriode = konversiCursorKeListPeriodeModel(cursor)
        }

        return listPeriode
    }

    fun updatePeriode(nama: String, des: String):List<Periode>{
        var listPeriode = ArrayList<Periode>()

        val db = databaseHelper.writableDatabase
        val queryUpdate = "UPDATE $TABEL_PERIODE " +
                "SET $DES_PERIODE = '$des', $IS_DELETED = 'false' " +
                "WHERE $NAMA_PERIODE = '$nama' AND $IS_DELETED = 'true'"
        val cursor = db.rawQuery(queryUpdate, null)
        if (cursor.count > 0){
            listPeriode = konversiCursorKeListPeriodeModel(cursor)
        }
        return listPeriode
    }

    fun readUpdate(id:Int, nama:String):List<Periode>{
        var listPeriode = ArrayList<Periode>()

        val db = databaseHelper.writableDatabase
        val queryUpdate = "SELECT * FROM $TABEL_PERIODE " +
                "WHERE $NAMA_PERIODE = '$nama' AND $ID_PERIODE != '$id'"
        val cursor = db.rawQuery(queryUpdate, null)
        if (cursor.count > 0){
            listPeriode = konversiCursorKeListPeriodeModel(cursor)
        }
        return listPeriode
    }

    fun updateDelete(id:Int, nama: String, des: String):List<Periode>{
        var listPeriode = ArrayList<Periode>()

        val db = databaseHelper.writableDatabase
        val queryUpdate = "UPDATE $TABEL_PERIODE " +
                "SET $DES_PERIODE = '$des', $NAMA_PERIODE = '$nama' " +
                "WHERE $ID_PERIODE = '$id'"
        val cursor = db.rawQuery(queryUpdate, null)
        if (cursor.count > 0){
            listPeriode = konversiCursorKeListPeriodeModel(cursor)
        }
        return listPeriode
    }

    fun cariPeriodeModels(keyword:String):List<Periode>{
        var listPeriode = ArrayList<Periode>()

        val db = databaseHelper.readableDatabase
        if(keyword.isNotBlank()) {
            val queryCari = "SELECT * FROM $TABEL_PERIODE " +
                    "WHERE $NAMA_PERIODE LIKE '%$keyword%'" +
                    "AND $IS_DELETED = 'false'"
            val cursor = db.rawQuery(queryCari, null)

            if (cursor.count > 0){
                listPeriode = konversiCursorKeListPeriodeModel(cursor)
            }
        }
        return listPeriode
    }
}