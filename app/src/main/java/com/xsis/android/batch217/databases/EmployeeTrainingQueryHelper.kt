package com.xsis.android.batch217.databases

import android.content.ContentValues
import android.database.Cursor
import com.xsis.android.batch217.models.EmployeeTraining
import com.xsis.android.batch217.utils.*

class EmployeeTrainingQueryHelper(val databaseHelper: DatabaseHelper) {

    private fun getSemuaEmployeeTraining(): Cursor {
        val db = databaseHelper.readableDatabase

        val queryRead = "SELECT * $TABEL_EMPLOYEE_TRAINING WHERE $IS_DELETED = 'false'"

        return db.rawQuery(queryRead, null)
    }

    private fun konversiCursorKeListEmployeeTrainingModel(cursor: Cursor): ArrayList<EmployeeTraining> {
        var listEmployeeTraining = ArrayList<EmployeeTraining>()

        for (c in 0 until cursor.count) {
            cursor.moveToPosition(c)

            val employeeTraining = EmployeeTraining()
            employeeTraining.idEmployeeTraining = cursor.getInt(0)
            employeeTraining.namaEmployeeTraining = cursor.getString(1)
            employeeTraining.namaEmployeeTO = cursor.getString(2)
            employeeTraining.dateEmployeeTraining = cursor.getString(3)
            employeeTraining.typeEmployeeTraining = cursor.getString(4)
            employeeTraining.typeEmployeeCerification = cursor.getString(5)
            employeeTraining.isDeleted = cursor.getString(6)

            listEmployeeTraining.add(employeeTraining)
        }

        return listEmployeeTraining
    }

    fun readSemuaEmployeeTrainingModels(): List<EmployeeTraining> {
        var listEmployeeTraining = ArrayList<EmployeeTraining>()

        val cursor = getSemuaEmployeeTraining()
        if (cursor.count > 0) {
            listEmployeeTraining = konversiCursorKeListEmployeeTrainingModel(cursor)
        }

        return listEmployeeTraining
    }

    fun cariEmployeeTrainingModels(keyword: String): List<EmployeeTraining> {
        var listEmployeeTraining = ArrayList<EmployeeTraining>()
        if (keyword.isNotBlank()) {
            val db = databaseHelper.readableDatabase
            val queryCari =
                "SELECT * FROM $TABEL_EMPLOYEE_TRAINING WHERE $NAMA_EMPLOYEE_TRAINING LIKE '%$keyword%' AND " +
                        "$IS_DELETED = 'false'"

            val cursor = db.rawQuery(queryCari, null)
            if (cursor.count > 0) {
                listEmployeeTraining = konversiCursorKeListEmployeeTrainingModel(cursor)
            }
        }
        return listEmployeeTraining
    }

    fun tambahEmployeeTraining(model: EmployeeTraining): Long {
        val db = databaseHelper.writableDatabase

        val values = ContentValues()
        values.put(NAMA_EMPLOYEE_TRAINING, model.namaEmployeeTraining)
        values.put(NAMA_EMPLOYEE_TRAINING_ORG, model.namaEmployeeTO)
        values.put(DATE_EMPLOYEE_TRAINING, model.dateEmployeeTraining)
        values.put(TYPE_EMPLOYEE_TRAINING, model.typeEmployeeTraining)
        values.put(TYPE_EMPLOYEE_CERTIFICATION, model.typeEmployeeCerification)
        values.put(IS_DELETED, "false")

        return db.insert(TABEL_EMPLOYEE_TRAINING, null, values)
    }

    fun editEmployeeTraining(model: EmployeeTraining) : Int {
        val db = databaseHelper.writableDatabase

        val values = ContentValues()
        values.put(NAMA_EMPLOYEE_TRAINING, model.namaEmployeeTraining)
        values.put(NAMA_EMPLOYEE_TRAINING_ORG, model.namaEmployeeTO)
        values.put(DATE_EMPLOYEE_TRAINING, model.dateEmployeeTraining)
        values.put(TYPE_EMPLOYEE_TRAINING, model.typeEmployeeTraining)
        values.put(TYPE_EMPLOYEE_CERTIFICATION, model.typeEmployeeCerification)

        return db.update(
            TABEL_EMPLOYEE_TRAINING,
            values,
            "$ID_EMPLOYEE_TRAINING = ?",
            arrayOf(model.idEmployeeTraining.toString())
        )
    }

    fun hapusEmployeeTraining(id: Int): Int {
        val db = databaseHelper.writableDatabase

        val values = ContentValues()
        values.put(IS_DELETED, "true")

        return db.update(TABEL_EMPLOYEE_TRAINING, values, "$ID_EMPLOYEE_TRAINING = ?", arrayOf(id.toString()))
    }

    fun cekEmployeeTrainingSudahAda(nama: String): Int {
        val db = databaseHelper.readableDatabase
        val queryCari =
            "SELECT * FROM $TABEL_EMPLOYEE_TRAINING WHERE $NAMA_EMPLOYEE_TRAINING LIKE '$nama' AND " +
                    "$IS_DELETED = 'false'"

        val cursor = db.rawQuery(queryCari, null)

        return cursor.count
    }
}