package com.xsis.android.batch217.databases

import android.content.ContentValues
import android.database.Cursor
import com.xsis.android.batch217.models.Grade
import com.xsis.android.batch217.utils.*

class GradeQueryHelper(val databaseHelper: DatabaseHelper) {

    private fun getSemuaGrade(): Cursor {
        val db = databaseHelper.readableDatabase

        val queryRead = "SELECT * FROM $TABEL_GRADE WHERE $IS_DELETED = 'false'"

        return db.rawQuery(queryRead, null)
    }

    private fun konversiCursorKeListGradeModel(cursor: Cursor): ArrayList<Grade> {
        var listGrade = ArrayList<Grade>()

        for (c in 0 until cursor.count) {
            cursor.moveToPosition(c)

            val grade = Grade()
            grade.idGrade = cursor.getInt(0)
            grade.namaGrade = cursor.getString(1)
            grade.desGrade = cursor.getString(2)
            grade.isDeleted = cursor.getString(3)

            listGrade.add(grade)
        }

        return listGrade
    }

    fun readSemuaGradeModels(): List<Grade> {
        var listGrade = ArrayList<Grade>()

        val cursor = getSemuaGrade()
        if (cursor.count > 0) {
            listGrade = konversiCursorKeListGradeModel(cursor)
        }

        return listGrade
    }

    fun cariGradeModels(keyword: String): List<Grade> {
        var listGrade = ArrayList<Grade>()
        if (keyword.isNotBlank()) {
            val db = databaseHelper.readableDatabase
            val queryCari =
                "SELECT * FROM $TABEL_GRADE WHERE $NAMA_GRADE LIKE '%$keyword%' AND " +
                        "$IS_DELETED = 'false'"

            val cursor = db.rawQuery(queryCari, null)
            if (cursor.count > 0) {
                listGrade= konversiCursorKeListGradeModel(cursor)
            }
        }
        return listGrade
    }

    fun tambahGrade(model: Grade): Long {
        val db = databaseHelper.writableDatabase

        val values = ContentValues()
        values.put(NAMA_GRADE, model.namaGrade)
        values.put(DES_GRADE, model.desGrade)
        values.put(IS_DELETED, "false")

        return db.insert(TABEL_GRADE, null, values)
    }

    fun editGrade(model: Grade): Int {
        val db = databaseHelper.writableDatabase

        val values = ContentValues()
        values.put(NAMA_GRADE, model.namaGrade)
        values.put(DES_GRADE, model.desGrade)

        return db.update(
            TABEL_GRADE,
            values,
            "$ID_GRADE = ?",
            arrayOf(model.idGrade.toString())
        )
    }

    fun hapusGrade(id: Int): Int {
        val db = databaseHelper.writableDatabase

        val values = ContentValues()
        values.put(IS_DELETED, "true")

        return db.update(TABEL_GRADE, values, "$ID_GRADE = ?", arrayOf(id.toString()))
    }

    fun cekGradeSudahAda(nama: String): Int {
        val db = databaseHelper.readableDatabase
        val queryCari =
            "SELECT * FROM $TABEL_GRADE WHERE $NAMA_GRADE LIKE '$nama' AND " +
                    "$IS_DELETED = 'false'"

        val cursor = db.rawQuery(queryCari, null)

        return cursor.count
    }
}