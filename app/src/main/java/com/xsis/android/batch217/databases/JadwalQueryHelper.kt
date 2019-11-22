package com.xsis.android.batch217.databases

import android.content.ContentValues
import android.database.Cursor
import com.xsis.android.batch217.models.Jadwal
import com.xsis.android.batch217.utils.*

class JadwalQueryHelper(val databaseHelper: DatabaseHelper) {

    fun getSemuaJadwal(): Cursor {
        val db = databaseHelper.readableDatabase

        val queryRead = "SELECT * FROM $TABEL_JADWAL WHERE $IS_DELETED = 'false'"

        return db.rawQuery(queryRead, null)
    }

    private fun konversiCursorKeListJadwalModel(cursor: Cursor): ArrayList<Jadwal>{
        var listJadwal = ArrayList<Jadwal>()

        for(c in 0 until cursor.count){
            cursor.moveToPosition(c)

            val jadwal = Jadwal()
            jadwal.id_jadwal = cursor.getInt(0)
            jadwal.nama_jadwal = cursor.getString(1)
            jadwal.deskripsi_jadwal = cursor.getString(2)
            jadwal.is_delete = cursor.getString(3)

            listJadwal.add(jadwal)
        }

        return listJadwal
    }

    fun readSemuaJadwalModels(): List<Jadwal>{
        var listJadwal = ArrayList<Jadwal>()

        val cursor = getSemuaJadwal()
        if(cursor.count > 0){
            listJadwal = konversiCursorKeListJadwalModel(cursor)
        }

        return listJadwal
    }

    fun cariJadwalModels(keyword:String): List<Jadwal>{
        var listJadwal = ArrayList<Jadwal>()

        if (keyword.isNotEmpty()) {
            val db = databaseHelper.readableDatabase
            val queryCari = "SELECT * FROM $TABEL_JADWAL WHERE ($NAMA_JADWAL LIKE '%$keyword%' OR " +
                    "$DES_JADWAL LIKE '%$keyword%') AND $IS_DELETED='false'"

            val cursor = db.rawQuery(queryCari, null)
            if (cursor.count > 0) {
                listJadwal = konversiCursorKeListJadwalModel(cursor)
            }
        }

        return listJadwal
    }

    fun hapusJadwal(id: Int): Int {
        val db = databaseHelper.writableDatabase

        val values = ContentValues()
        values.put(IS_DELETED, "true")
        return db.update(TABEL_JADWAL, values, "$ID_JADWAL = ?", arrayOf(id.toString()))
    }

    fun tambahJadwal(model: Jadwal): Long {
        val db = databaseHelper.writableDatabase

        val values = ContentValues()
        values.put(NAMA_JADWAL, model.nama_jadwal)
        values.put(DES_JADWAL, model.deskripsi_jadwal)
        values.put(IS_DELETED, "false")

        return db.insert(TABEL_JADWAL, null, values)
    }

    fun editJadwal(model: Jadwal): Int {
        val db = databaseHelper.writableDatabase

        val values = ContentValues()
        values.put(NAMA_JADWAL, model.nama_jadwal)
        values.put(DES_JADWAL, model.deskripsi_jadwal)

        return db.update(
            TABEL_JADWAL,
            values,
            "$ID_JADWAL = ?",
            arrayOf(model.id_jadwal.toString())
        )
    }

    fun cekJadwalSudahAda(nama: String): Int {
        val db = databaseHelper.readableDatabase
        val queryCari =
            "SELECT * FROM $TABEL_JADWAL WHERE $NAMA_JADWAL LIKE '$nama' AND " +
                    "$IS_DELETED = 'false'"

        val cursor = db.rawQuery(queryCari, null)

        return cursor.count
    }
}