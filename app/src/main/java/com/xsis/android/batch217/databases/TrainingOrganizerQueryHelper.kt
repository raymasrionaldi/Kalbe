package com.xsis.android.batch217.databases

import android.content.ContentValues
import android.database.Cursor
import com.xsis.android.batch217.models.TrainingOrganizer
import com.xsis.android.batch217.utils.*

class TrainingOrganizerQueryHelper (val databaseHelper: DatabaseHelper) {
    private fun getSemuaContractStatus(): Cursor {
        val db = databaseHelper.readableDatabase

        val queryRead = "SELECT * FROM $TABEL_TRAINING_ORG WHERE $IS_DELETED = 'false'"

        return db.rawQuery(queryRead, null)

    }
    private fun konversiCursorKeListTrainingOrgModel(cursor: Cursor): ArrayList<TrainingOrganizer> {
        var listTrainingOrganizer = ArrayList<TrainingOrganizer>()

        for (c in 0 until cursor.count) {
            cursor.moveToPosition(c)

            val kontrak = TrainingOrganizer()
            kontrak.idTrainingOrganizer = cursor.getInt(0)
            kontrak.namaTrainingOrganizer = cursor.getString(1)
            kontrak.desTrainingOrganizer = cursor.getString(2)
            kontrak.isDeleted = cursor.getString(3)

            listTrainingOrganizer.add(kontrak)
        }
        println("INI DATA")
        listTrainingOrganizer.forEach { println("${it.idTrainingOrganizer},${it.namaTrainingOrganizer}, ${it.desTrainingOrganizer}") }
        return listTrainingOrganizer
    }
    fun readSemuaTrainingOrgModels(): List<TrainingOrganizer> {
        var listTrainingOrganizer = ArrayList<TrainingOrganizer>()
        val cursor = getSemuaContractStatus()
        if (cursor.count > 0) {
            listTrainingOrganizer = konversiCursorKeListTrainingOrgModel(cursor)
        }

        return listTrainingOrganizer
    }

    fun cariTrainingOrgModels(keyword: String): List<TrainingOrganizer> {
        var listTrainingOrganizer = ArrayList<TrainingOrganizer>()

        if (keyword.isNotBlank()) {
            val db = databaseHelper.readableDatabase
            val queryCari =
                "SELECT * FROM $TABEL_TRAINING_ORG WHERE $NAMA_TRAINING_ORG LIKE '%$keyword%' AND " +
                        "$IS_DELETED = 'false'"

            val cursor = db.rawQuery(queryCari, null)
            if (cursor.count > 0) {
                listTrainingOrganizer = konversiCursorKeListTrainingOrgModel(cursor)
            }

        }

        return listTrainingOrganizer
    }

    fun tambahTrainingOrg(model: TrainingOrganizer): Long {
        val db = databaseHelper.writableDatabase

        val konten = ContentValues()
        konten.put(NAMA_TRAINING_ORG, model.namaTrainingOrganizer)
        konten.put(DES_TRAINING_ORG, model.desTrainingOrganizer)
        konten.put(IS_DELETED, "false")

        return db.insert(TABEL_TRAINING_ORG, null, konten)

    }

    fun editTrainingOrg(model: TrainingOrganizer): Int {
        val db = databaseHelper.writableDatabase

        val konten = ContentValues()
        konten.put(NAMA_TRAINING_ORG, model.namaTrainingOrganizer)
        konten.put(DES_TRAINING_ORG, model.desTrainingOrganizer)

        return db.update(
            TABEL_TRAINING_ORG, konten, "$ID_TRAINING_ORG = ?",
            arrayOf(model.idTrainingOrganizer.toString())
        )
    }

    fun cekTrainingOrg(nama: String): Int {
        val db = databaseHelper.readableDatabase
        val queryCari = "SELECT * FROM $TABEL_TRAINING_ORG WHERE $NAMA_TRAINING_ORG LIKE '$nama' AND " +
                "$IS_DELETED = 'false'"
        val cursor = db.rawQuery(queryCari, null)

        return cursor.count
    }

    fun hapusTrainingOrg(id: Int): Int {
        val db = databaseHelper.writableDatabase

        val values = ContentValues()
        values.put(IS_DELETED, "true")

        return db.update(TABEL_TRAINING_ORG, values, "$ID_TRAINING_ORG= ?", arrayOf(id.toString()))
    }
}