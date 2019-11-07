package com.xsis.android.batch217.databases

import android.content.ContentValues
import android.database.Cursor
import com.xsis.android.batch217.models.Company
import com.xsis.android.batch217.models.KeluargaData
import com.xsis.android.batch217.models.KeluargaDetail
import com.xsis.android.batch217.utils.*

class KeluargaQueryHelper(val databaseHelper: DatabaseHelper) {
    private fun getSemuaKeluargaData(): Cursor {
        val db = databaseHelper.readableDatabase
        val queryRead = "SELECT * FROM $TABEL_KELUARGA_DATA "
        return db.rawQuery(queryRead, null)
    }
    private fun getSemuaKeluargaDetail(id:Int): Cursor {
        val db = databaseHelper.readableDatabase
        val queryRead = "SELECT * FROM $TABEL_KELUARGA_DETAIL WHERE $ID_JENIS=$id"
        return db.rawQuery(queryRead, null)
    }
    private fun konversiCursorKeListKeluargaData(cursor: Cursor): ArrayList<KeluargaData> {
        var listKeluargaData = ArrayList<KeluargaData>()

        for (c in 0 until cursor.count) {
            cursor.moveToPosition(c)

            val keluargaData = KeluargaData()
            keluargaData.idKeluargaData = cursor.getInt(0)
            keluargaData.jenisKeluarga = cursor.getString(1)

            listKeluargaData.add(keluargaData)
        }

        return listKeluargaData
    }
    private fun konversiCursorKeListKeluargaDetail(cursor: Cursor): ArrayList<KeluargaDetail> {
        var listKeluargaDetail = ArrayList<KeluargaDetail>()

        for (c in 0 until cursor.count) {
            cursor.moveToPosition(c)

            val keluargaDetail = KeluargaDetail()
            keluargaDetail.idJenisKeluarga = cursor.getInt(0)
            keluargaDetail.idAnggota = cursor.getInt(1)
            keluargaDetail.anggotaKeluarga = cursor.getString(2)

            listKeluargaDetail.add(keluargaDetail)
        }

        return listKeluargaDetail
    }

    fun readSemuaKeluargaData(): List<KeluargaData> {
        var listKeluargaData = ArrayList<KeluargaData>()

        val cursor = getSemuaKeluargaData()
        if (cursor.count > 0) {
            listKeluargaData = konversiCursorKeListKeluargaData(cursor)
        }

        return listKeluargaData
    }
    fun readSemuaKeluargaDetail(id:Int): List<KeluargaDetail> {
        var listKeluargaDetail = ArrayList<KeluargaDetail>()

        val cursor = getSemuaKeluargaDetail(id)
        if (cursor.count > 0) {
            listKeluargaDetail = konversiCursorKeListKeluargaDetail(cursor)
        }

        return listKeluargaDetail
    }
    fun cariKeluargaData(keyword: String): List<KeluargaData> {
        var listKeluargaData = ArrayList<KeluargaData>()
        if (keyword.isNotBlank()) {
            val db = databaseHelper.readableDatabase
            val queryCari =
                "SELECT * FROM $TABEL_KELUARGA_DATA WHERE $JENIS_KELUARGA LIKE '%$keyword%'"

            val cursor = db.rawQuery(queryCari, null)
            if (cursor.count > 0) {
                listKeluargaData = konversiCursorKeListKeluargaData(cursor)
            }
        }
        return listKeluargaData
    }

    fun tambahKeluargaData(keluargaData: KeluargaData, listKeluargaDetail: ArrayList<String>) {
        //WARNING 1 : Belum cek jika ada nama jenis keluarga yang sama
        //WARNING 2 : Belum cek jika ada anggota keluarga yang sama
        //WARNING 3 : Simpan data listKeluargaDetail di edit text apa ?

        val db = databaseHelper.writableDatabase
        val db_read = databaseHelper.readableDatabase

        //1.Insert ke tabel keluarga data
        val values_data = ContentValues()
        values_data.put(JENIS_KELUARGA, keluargaData.jenisKeluarga)
        db.insert(TABEL_COMPANY, null, values_data)

        //2.Insert ke tabel keluarga detail
        //2.1. cari id
        var list = ArrayList<KeluargaData>()
        val queryRead = "SELECT * FROM $TABEL_KELUARGA_DATA WHERE $JENIS_KELUARGA='${keluargaData.jenisKeluarga}'"
        val cursor = db_read.rawQuery(queryRead, null)
        if (cursor.count>0){list  = konversiCursorKeListKeluargaData(cursor)}
        val id = list[0].idKeluargaData

        //2.2. Insert ke tabel keluarga detail
        val values_detail = ContentValues()
        for (i in 0 until listKeluargaDetail.size){
            values_detail.put(ID_JENIS, id)
            values_detail.put(ID_ANGGOTA, i)
            values_detail.put(NAMA_ANGGOTA, listKeluargaDetail[i])
        }
        db.insert(TABEL_KELUARGA_DETAIL, null, values_detail)

    }

    fun editKeluarga(model: KeluargaData) {

    }

    fun hapusKeluarga(id: Int) {
        val db = databaseHelper.writableDatabase

        val queryDeleteData = "DELETE FROM $TABEL_KELUARGA_DATA WHERE $ID_JENIS=$id"
        val queryDeleteDetail = "DELETE FROM $TABEL_KELUARGA_DETAIL WHERE $ID_JENIS=$id"

        db.execSQL(queryDeleteData)
        db.execSQL(queryDeleteDetail)
    }

    fun cekKeluargaDataSudahAda(nama: String): Int {
        val db = databaseHelper.readableDatabase
        val queryCari = "SELECT * FROM $TABEL_KELUARGA_DATA WHERE $JENIS_KELUARGA LIKE '$nama' "
        val cursor = db.rawQuery(queryCari, null)

        return cursor.count
    }

    fun cekKeluargaDetailSudahAda(nama: String, id:Int): Int {
        val db = databaseHelper.readableDatabase
        val queryCari = "SELECT * FROM $TABEL_KELUARGA_DETAIL WHERE $NAMA_ANGGOTA LIKE '$nama' AND $ID_JENIS=$id"
        val cursor = db.rawQuery(queryCari, null)

        return cursor.count
    }
}