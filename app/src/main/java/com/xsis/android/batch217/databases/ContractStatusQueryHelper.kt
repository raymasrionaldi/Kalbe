package com.xsis.android.batch217.databases

import android.content.ContentValues
import android.database.Cursor
import com.xsis.android.batch217.models.ContractStatus
import com.xsis.android.batch217.utils.*

class ContractStatusQueryHelper(val databaseHelper: DatabaseHelper) {

    private fun getSemuaContractStatus(): Cursor {
        val db = databaseHelper.readableDatabase

        val queryRead = "SELECT * FROM $TABEL_CONTRACT_STATUS WHERE $IS_DELETED = 'false'"

        return db.rawQuery(queryRead, null)

    }

    private fun konversiCursorKeListKontrakModel(cursor: Cursor): ArrayList<ContractStatus> {
        var listKontrakKerja = ArrayList<ContractStatus>()

        for (c in 0 until cursor.count) {
            cursor.moveToPosition(c)

            val kontrak = ContractStatus()
            kontrak.idContract = cursor.getInt(0)
            kontrak.namaContract = cursor.getString(1)
            kontrak.desContract = cursor.getString(2)
            kontrak.isDeleted = cursor.getString(3)

            listKontrakKerja.add(kontrak)
        }
        println("INI DATA")
        listKontrakKerja.forEach { println("${it.idContract},${it.namaContract}, ${it.desContract}") }
        return listKontrakKerja
    }

    fun readSemuaKontrakModels(): List<ContractStatus> {
        var listKontrakkerja = ArrayList<ContractStatus>()
        val cursor = getSemuaContractStatus()
        if (cursor.count > 0) {
            listKontrakkerja = konversiCursorKeListKontrakModel(cursor)
        }

        return listKontrakkerja
    }

    fun cariContractStatusModels(keyword: String): List<ContractStatus> {
        var listKontrakKerja = ArrayList<ContractStatus>()

        if (keyword.isNotBlank()) {
            val db = databaseHelper.readableDatabase
            val queryCari =
                "SELECT * FROM $TABEL_CONTRACT_STATUS WHERE $NAMA_CONTRACT LIKE '%$keyword%' AND " +
                        "$IS_DELETED = 'false'"

            val cursor = db.rawQuery(queryCari, null)
            if (cursor.count > 0) {
                listKontrakKerja = konversiCursorKeListKontrakModel(cursor)
            }

        }

        return listKontrakKerja
    }

    fun tambahContractStatus(model: ContractStatus): Long {
        val db = databaseHelper.writableDatabase

        val konten = ContentValues()
        konten.put(NAMA_CONTRACT, model.namaContract)
        konten.put(DES_CONTRACT, model.desContract)
        konten.put(IS_DELETED, "false")

        return db.insert(TABEL_CONTRACT_STATUS, null, konten)

    }

    fun editContractStatus(model: ContractStatus): Int {
        val db = databaseHelper.writableDatabase

        val konten = ContentValues()
        konten.put(NAMA_CONTRACT, model.namaContract)
        konten.put(DES_CONTRACT, model.desContract)

        return db.update(
            TABEL_CONTRACT_STATUS, konten, "$ID_CONTRACT = ?",
            arrayOf(model.idContract.toString())
        )
    }

    fun cekContractStatus(nama: String): Int {
        val db = databaseHelper.readableDatabase
        val queryCari = "SELECT * FROM $TABEL_CONTRACT_STATUS WHERE $NAMA_CONTRACT = '$nama' AND " +
                "$IS_DELETED = 'false'"
        val cursor = db.rawQuery(queryCari, null)

        return cursor.count
    }

    fun hapusContractStatus(id: Int): Int {
        val db = databaseHelper.writableDatabase

        val values = ContentValues()
        values.put(IS_DELETED, "true")

        return db.update(TABEL_CONTRACT_STATUS, values, "$ID_CONTRACT= ?", arrayOf(id.toString()))
    }


}