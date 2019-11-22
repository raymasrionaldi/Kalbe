package com.xsis.android.batch217.databases

import android.content.ContentValues
import android.database.Cursor
import com.xsis.android.batch217.models.Permission
import com.xsis.android.batch217.models.Timesheet
import com.xsis.android.batch217.models.TipeIdentitas
import com.xsis.android.batch217.utils.*

class PermissionQueryHelper(val databaseHelper: DatabaseHelper) {

    fun getSemuaPermission(): Cursor {
        val db = databaseHelper.readableDatabase

        val queryRead = "SELECT * FROM $TABEL_PERMISSION WHERE $STATUS_PERMISSION = 'wait'"

        return db.rawQuery(queryRead, null)
    }

    private fun konversiCursorKeListPermissionModel(cursor: Cursor): ArrayList<Permission>{
        var listPermission = ArrayList<Permission>()

        for(c in 0 until cursor.count){
            cursor.moveToPosition(c)

            val permission = Permission()
            permission.id_permission = cursor.getInt(0)
            permission.nama_pegawai = cursor.getString(1)
            permission.devartement = cursor.getString(2)
            permission.jabatan = cursor.getString(3)
            permission.tanggal_permission = cursor.getString(4)
            permission.jam_permission = cursor.getString(5)
            permission.ket_tidak_masuk = cursor.getString(6)
            permission.ket_sakit = cursor.getString(7)
            permission.ket_datang_terlambat = cursor.getString(8)
            permission.ket_pulang_awal = cursor.getString(9)
            permission.ket_dll = cursor.getString(10)
            permission.status_permission = cursor.getString(11)

            listPermission.add(permission)
        }

        return listPermission
    }

    fun readSemuaPermissionModels(): List<Permission>{
        var listPermission = ArrayList<Permission>()

        val cursor = getSemuaPermission()
        if(cursor.count > 0){
            listPermission = konversiCursorKeListPermissionModel(cursor)
        }

        return listPermission
    }

    fun cariPermissionModels(keyword:String): List<Permission>{
        var listPermission = ArrayList<Permission>()
        if (keyword.isNotBlank()) {
            val db = databaseHelper.readableDatabase
            val queryCari =
                "SELECT * FROM $TABEL_PERMISSION WHERE $TANGGAL_PERMISSION LIKE '%$keyword%' AND " +
                        "$STATUS_PERMISSION = 'wait'"

            val cursor = db.rawQuery(queryCari, null)
            if (cursor.count > 0) {
                listPermission = konversiCursorKeListPermissionModel(cursor)
            }
        }
        return listPermission
    }

    fun cariPermissionModelsBuatHistory(keyword:String): List<Permission>{
        var listPermission = ArrayList<Permission>()
        if (keyword.isNotBlank()) {
            val db = databaseHelper.readableDatabase
            val queryCari =
                "SELECT * FROM $TABEL_PERMISSION WHERE $TANGGAL_PERMISSION LIKE '%$keyword%' AND " +
                        "$STATUS_PERMISSION = 'Approved' OR $STATUS_PERMISSION='Rejected'"

            val cursor = db.rawQuery(queryCari, null)
            if (cursor.count > 0) {
                listPermission = konversiCursorKeListPermissionModel(cursor)
            }
        }
        return listPermission
    }

    fun loadPermission(id:Int): Permission {
        var listPermission = ArrayList<Permission>()

        val db = databaseHelper.readableDatabase
        val queryLoad = "SELECT * FROM $TABEL_PERMISSION WHERE $ID_PERMISSION=$id"
        val cursor = db.rawQuery(queryLoad, null)
        println("query load $queryLoad")
        println("cursor $cursor")
        if (cursor.count > 0){
            listPermission = konversiCursorKeListPermissionModel(cursor)
        }
        return listPermission[0]

    }
//    fun hapusPermission(id: Int): Int {
//        val db = databaseHelper.writableDatabase
//
//        val values = ContentValues()
//        values.put(IS_DELETED, "true")
//        return db.update(TABEL_TIPE_TES, values, "$ID_TES = ?", arrayOf(id.toString()))
//    }

    fun tambahPermission(model: Permission): Long {
        val db = databaseHelper.writableDatabase

        val values = ContentValues()
        values.put(NAMA_PEGAWAI, model.nama_pegawai)
        values.put(DEVARTEMENT, model.devartement)
        values.put(JABATAN, model.jabatan)
        values.put(TANGGAL_PERMISSION, model.tanggal_permission)
        values.put(JAM_PERMISSION, model.jam_permission)
        values.put(KET_SATU, "Tidak Masuk")
        values.put(KET_DUA, "Sakit")
        values.put(KET_TIGA, "Datang Terlambat")
        values.put(KET_EMPAT, "Pulang Awal")
        values.put(KET_LIMA, model.ket_dll)
        values.put(STATUS_PERMISSION, "wait")

        return db.insert(TABEL_PERMISSION, null, values)
    }
//
//    fun editTipeTes(model: TipeTes): Int {
//        val db = databaseHelper.writableDatabase
//
//        val values = ContentValues()
//        values.put(NAMA_TES, model.nama_tipe_tes)
//        values.put(DES_TES, model.deskripsi_tipe_tes)
//
//        return db.update(
//            TABEL_TIPE_TES,
//            values,
//            "$ID_TES = ?",
//            arrayOf(model.id_tipe_tes.toString())
//        )
//    }

//    fun cekTipeTesSudahAda(nama: String): Int {
//        val db = databaseHelper.readableDatabase
//        val queryCari =
//            "SELECT * FROM $TABEL_TIPE_TES WHERE $NAMA_TES LIKE '$nama' AND " +
//                    "$IS_DELETED = 'false'"
//
//        val cursor = db.rawQuery(queryCari, null)
//
//        return cursor.count
//    }
}