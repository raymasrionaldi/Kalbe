package com.xsis.android.batch217.databases

import android.content.ContentValues
import android.database.Cursor
import com.xsis.android.batch217.models.*
import com.xsis.android.batch217.utils.*

class EmployeeTrainingQueryHelper(val databaseHelper: DatabaseHelper) {

    fun getSemuaEmployeeTraining(): Cursor {
        val db = databaseHelper.readableDatabase

        val queryRead = "SELECT * FROM $TABEL_EMPLOYEE_TRAINING WHERE $IS_DELETED = 'false'"

        return db.rawQuery(queryRead, null)
    }

    private fun konversiCursorKeListEmployeeTrainingModel(cursor: Cursor): ArrayList<EmployeeTraining> {
        var listEmployeeTraining = ArrayList<EmployeeTraining>()

        for (c in 0 until cursor.count) {
            cursor.moveToPosition(c)

            val employeeTraining = EmployeeTraining()
            employeeTraining.idEmployeeTraining = cursor.getInt(0)
            employeeTraining.namaTrainee = cursor.getString(1)
            employeeTraining.namaEmployeeTraining = cursor.getString(2)
            employeeTraining.namaEmployeeTO = cursor.getString(3)
            employeeTraining.dateEmployeeTraining = cursor.getString(4)
            employeeTraining.typeEmployeeTraining = cursor.getString(5)
            employeeTraining.typeEmployeeCertification = cursor.getString(6)
            //employeeTraining.isDeleted = cursor.getString(7)

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

    fun readNamaEmployeeTraining(nama: String): List<EmployeeTraining> {
        var listEmployeeTraining = ArrayList<EmployeeTraining>()

        val db = databaseHelper.readableDatabase
        val queryCari = "SELECT * FROM $TABEL_EMPLOYEE_TRAINING " +
                "WHERE $NAMA_TRAINEE = '$nama' "
        val cursor = db.rawQuery(queryCari, null)

        if (cursor.count > 0) {
            listEmployeeTraining = konversiCursorKeListEmployeeTrainingModel(cursor)
        }

        return listEmployeeTraining
    }

    fun updateEmployeeTraining(nama: String, des: String): List<EmployeeTraining> {
        var listEmployeeTraining = ArrayList<EmployeeTraining>()

        val db = databaseHelper.writableDatabase
        val queryUpdate = "UPDATE $TABEL_EMPLOYEE_TRAINING " +
                "SET $NAMA_EMPLOYEE_TRAINING = '$des', $IS_DELETED = 'false' " +
                "WHERE $NAMA_TRAINEE = '$nama' AND $IS_DELETED = 'true'"
        val cursor = db.rawQuery(queryUpdate, null)
        if (cursor.count > 0) {
            listEmployeeTraining = konversiCursorKeListEmployeeTrainingModel(cursor)
        }
        println(queryUpdate)
        return listEmployeeTraining
    }

    fun readUpdate(id:Int, nama:String):List<EmployeeTraining>{
        var listEmployeeTraining = ArrayList<EmployeeTraining>()

        val db = databaseHelper.writableDatabase
        val queryUpdate = "SELECT * FROM $TABEL_EMPLOYEE_TRAINING " +
                "WHERE $NAMA_TRAINEE = '$nama' AND $ID_EMPLOYEE_TRAINING != '$id'"
        val cursor = db.rawQuery(queryUpdate, null)
        if (cursor.count > 0){
            listEmployeeTraining = konversiCursorKeListEmployeeTrainingModel(cursor)
        }

        println(queryUpdate)
        return listEmployeeTraining
    }

    fun updateDelete(id:Int, nama: String, des: String):List<EmployeeTraining>{
        var listEmployeeTraining = ArrayList<EmployeeTraining>()

        val db = databaseHelper.writableDatabase
        val queryUpdate = "UPDATE $TABEL_EMPLOYEE_TRAINING " +
                "SET $NAMA_EMPLOYEE_TRAINING = '$des', $NAMA_TRAINEE = '$nama' " +
                "WHERE $ID_EMPLOYEE_TRAINING = '$id'"
        val cursor = db.rawQuery(queryUpdate, null)
        if (cursor.count > 0){
            listEmployeeTraining = konversiCursorKeListEmployeeTrainingModel(cursor)
        }
        println(queryUpdate)
        return listEmployeeTraining
    }


    fun cariEmployeeTrainingModels(keyword: String): List<EmployeeTraining> {
        var listEmployeeTraining = ArrayList<EmployeeTraining>()
        if (keyword.isNotBlank()) {
            val db = databaseHelper.readableDatabase
            val queryCari =
                "SELECT * FROM $TABEL_EMPLOYEE_TRAINING WHERE $NAMA_TRAINEE LIKE '%$keyword%' AND " +
                        "$IS_DELETED = 'false'"

            val cursor = db.rawQuery(queryCari, null)
            if (cursor.count > 0) {
                listEmployeeTraining = konversiCursorKeListEmployeeTrainingModel(cursor)
            }
        }
        return listEmployeeTraining
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
            "SELECT * FROM $TABEL_EMPLOYEE_TRAINING WHERE $NAMA_TRAINEE LIKE '$nama' AND " +
                    "$IS_DELETED = 'false'"

        val cursor = db.rawQuery(queryCari, null)

        return cursor.count
    }

    fun tambahEmployeeTraining(model: EmployeeTraining): Long {
        val db = databaseHelper.writableDatabase

        val values = ContentValues()
        values.put(NAMA_TRAINEE, model.namaTrainee)
        values.put(NAMA_EMPLOYEE_TRAINING, model.namaEmployeeTraining)
        values.put(NAMA_EMPLOYEE_TRAINING_ORG, model.namaEmployeeTO)
        values.put(DATE_EMPLOYEE_TRAINING, model.dateEmployeeTraining)
        values.put(TYPE_EMPLOYEE_TRAINING, model.typeEmployeeTraining)
        values.put(TYPE_EMPLOYEE_CERTIFICATION, model.typeEmployeeCertification)
        values.put(IS_DELETED, "false")

        return db.insert(TABEL_EMPLOYEE_TRAINING, null, values)
    }

    fun editEmployeeTraining(model: EmployeeTraining) : Int {
        val db = databaseHelper.writableDatabase

        val values = ContentValues()
        values.put(NAMA_TRAINEE, model.namaTrainee)
        values.put(NAMA_EMPLOYEE_TRAINING, model.namaEmployeeTraining)
        values.put(NAMA_EMPLOYEE_TRAINING_ORG, model.namaEmployeeTO)
        values.put(DATE_EMPLOYEE_TRAINING, model.dateEmployeeTraining)
        values.put(TYPE_EMPLOYEE_TRAINING, model.typeEmployeeTraining)
        values.put(TYPE_EMPLOYEE_CERTIFICATION, model.typeEmployeeCertification)

        return db.update(
            TABEL_EMPLOYEE_TRAINING,
            values,
            "$ID_EMPLOYEE_TRAINING = ?",
            arrayOf(model.idEmployeeTraining.toString())
        )
    }

    fun tampilkanNamaTraining(): List<NamaTraining> {
        val db = databaseHelper.readableDatabase

        val queryRead = "SELECT * FROM $TABEL_NAMA_TRAINING"
        val cursor = db.rawQuery(queryRead, null)


        var listNamaTraining = ArrayList<NamaTraining>()

        for (c in 0 until cursor.count) {
            cursor.moveToPosition(c)

            val namaTraining = NamaTraining()
            namaTraining.idNamaTraining = cursor.getInt(0)
            namaTraining.namaNyaTraining = cursor.getString(1)

            listNamaTraining.add(namaTraining)
        }
        return  listNamaTraining
    }

    fun tampilkanNamaTrainingOrganizer(): List<NamaTrainingOrganizer> {
        val db = databaseHelper.readableDatabase

        val queryRead = "SELECT * FROM $TABEL_NAMA_TRAINING_ORGANIZER"
        val cursor = db.rawQuery(queryRead, null)


        var listNamaTrainingOrganizer = ArrayList<NamaTrainingOrganizer>()

        for (c in 0 until cursor.count) {
            cursor.moveToPosition(c)

            val namaTrainingOrganizer = NamaTrainingOrganizer()
            namaTrainingOrganizer.idNamaTrainingOrganizer = cursor.getInt(0)
            namaTrainingOrganizer.namaNyaTrainingOrganizer = cursor.getString(1)

            listNamaTrainingOrganizer.add(namaTrainingOrganizer)
        }
        return  listNamaTrainingOrganizer
    }

    fun tampilkanTrainingType(): List<TypeTraining> {
        val db = databaseHelper.readableDatabase

        val queryRead = "SELECT * FROM $TABEL_TRAINING_TYPE"
        val cursor = db.rawQuery(queryRead, null)


        var listTrainingType = ArrayList<TypeTraining>()

        for (c in 0 until cursor.count) {
            cursor.moveToPosition(c)

            val trainingType = TypeTraining()
            trainingType.idTypeTraining = cursor.getInt(0)
            trainingType.namaTypeTraining = cursor.getString(1)

            listTrainingType.add(trainingType)
        }
        return  listTrainingType
    }

    fun tampilkanCertificationType(): List<CertificationType> {
        val db = databaseHelper.readableDatabase

        val queryRead = "SELECT * FROM $TABEL_CERTIFICATION_TYPE"
        val cursor = db.rawQuery(queryRead, null)


        var listCertificationType = ArrayList<CertificationType>()

        for (c in 0 until cursor.count) {
            cursor.moveToPosition(c)

            val certificationType = CertificationType()
            certificationType.idTypeCertification = cursor.getInt(0)
            certificationType.namaTypeCertification = cursor.getString(1)

            listCertificationType.add(certificationType)
        }
        return  listCertificationType
    }

}
