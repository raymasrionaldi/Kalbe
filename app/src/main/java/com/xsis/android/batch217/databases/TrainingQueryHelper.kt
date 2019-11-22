package com.xsis.android.batch217.databases

import android.content.ContentValues
import android.database.Cursor
import com.xsis.android.batch217.models.Training
import com.xsis.android.batch217.utils.*

class TrainingQueryHelper(val databaseHelper: DatabaseHelper) {

    private fun getSemuaTraining(): Cursor {
        val db = databaseHelper.readableDatabase

        val queryRead = "SELECT * FROM $TABEL_TRAINING WHERE $IS_DELETED = 'false'"

        return db.rawQuery(queryRead, null)
    }

    private fun konversiCursorKeListTrainingModel(cursor: Cursor): ArrayList<Training> {
        var listTraining = ArrayList<Training>()

        for (c in 0 until cursor.count) {
            cursor.moveToPosition(c)

            val training = Training()
            training.idTraining = cursor.getInt(0)
            training.codeTraining = cursor.getString(1)
            training.namaTraining = cursor.getString(2)
            training.isDeleted = cursor.getString(3)

            listTraining.add(training)
        }

        return listTraining
    }

    fun readSemuaTrainingModels(): List<Training> {
        var listTraining = ArrayList<Training>()

        val cursor = getSemuaTraining()
        if (cursor.count > 0) {
            listTraining = konversiCursorKeListTrainingModel(cursor)
        }

        return listTraining
    }

    fun cariTrainingModels(keyword: String): List<Training> {
        var listTraining = ArrayList<Training>()
        if (keyword.isNotEmpty()) {
            val db = databaseHelper.readableDatabase
            val queryCari =
                "SELECT * FROM $TABEL_TRAINING WHERE $NAMA_TRAINING LIKE '%$keyword%' AND " +
                        "$IS_DELETED = 'false'"

            val cursor = db.rawQuery(queryCari, null)
            if (cursor.count > 0) {
                listTraining = konversiCursorKeListTrainingModel(cursor)
            }
        }
        return listTraining
    }

    fun tambahTraining(model: Training): Long {
        val db = databaseHelper.writableDatabase

        val values = ContentValues()
        values.put(CODE_TRAINING, model.codeTraining)
        values.put(NAMA_TRAINING, model.namaTraining)
        values.put(IS_DELETED, "false")

        return db.insert(TABEL_TRAINING, null, values)
    }

    fun editTraining(model: Training): Int {
        val db = databaseHelper.writableDatabase

        val values = ContentValues()
        values.put(CODE_TRAINING, model.codeTraining)
        values.put(NAMA_TRAINING, model.namaTraining)

        return db.update(
            TABEL_TRAINING,
            values,
            "$ID_TRAINING = ?",
            arrayOf(model.idTraining.toString())
        )
    }

    fun hapusTraining(id: Int): Int {
        val db = databaseHelper.writableDatabase

        val values = ContentValues()
        values.put(IS_DELETED, "true")

        return db.update(TABEL_TRAINING, values, "$ID_TRAINING = ?", arrayOf(id.toString()))
    }

    fun cekTrainingCodeSudahAda(code: String): Int {
        val db = databaseHelper.readableDatabase
        val queryCari =
            "SELECT * FROM $TABEL_TRAINING WHERE $CODE_TRAINING LIKE '$code' AND " +
                    "$IS_DELETED = 'false'"

        val cursor = db.rawQuery(queryCari, null)

        return cursor.count
    }
/*
    fun cekTrainingNamaSudahAda(nama: String): Int {
        val db = databaseHelper.readableDatabase
        val queryCari =
            "SELECT * FROM $TABEL_TRAINING WHERE $NAMA_TRAINING = '$nama' AND " +
                    "$IS_DELETED = 'false'"

        val cursor = db.rawQuery(queryCari, null)

        return cursor.count
    }

 */
}