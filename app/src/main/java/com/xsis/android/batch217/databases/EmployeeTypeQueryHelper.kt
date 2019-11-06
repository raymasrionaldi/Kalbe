package com.xsis.android.batch217.databases

import android.content.ContentValues
import android.database.Cursor
import com.xsis.android.batch217.models.EmployeeType
import com.xsis.android.batch217.utils.*

class EmployeeTypeQueryHelper(val databaseHelper: DatabaseHelper) {

    private fun getSemuaEmpType(): Cursor {
        val db = databaseHelper.readableDatabase

        val queryRead = "SELECT * FROM $TABEL_EMPLOYEE_TYPE WHERE $IS_DELETED = 'false'"

        return db.rawQuery(queryRead, null)
    }

    private fun konversiCursorKeListEmpTypeModel(cursor: Cursor): ArrayList<EmployeeType> {
        var listEmpType = ArrayList<EmployeeType>()

        for (c in 0 until cursor.count) {
            cursor.moveToPosition(c)

            val empType = EmployeeType()
            empType.id_emp_type = cursor.getInt(0)
            empType.nama_emp_type = cursor.getString(1)
            empType.des_emp_type = cursor.getString(2)
            empType.is_Deleted = cursor.getString(3)

            listEmpType.add(empType)
        }

        return listEmpType
    }

    fun readSemuaEmpTypeModels(): List<EmployeeType> {
        var listEmpType = ArrayList<EmployeeType>()

        val cursor = getSemuaEmpType()
        if (cursor.count > 0) {
            listEmpType = konversiCursorKeListEmpTypeModel(cursor)
        }

        return listEmpType
    }

    fun cariEmpTypeModels(keyword: String): List<EmployeeType> {
        var listEmpType = ArrayList<EmployeeType>()
        if (keyword.isNotBlank()) {
            val db = databaseHelper.readableDatabase
            val queryCari =
                "SELECT * FROM $TABEL_EMPLOYEE_TYPE WHERE $NAMA_EMP_TYPE LIKE '%$keyword%' AND " +
                        "$IS_DELETED = 'false'"

            val cursor = db.rawQuery(queryCari, null)
            if (cursor.count > 0) {
                listEmpType= konversiCursorKeListEmpTypeModel(cursor)
            }
        }
        return listEmpType
    }

    fun tambahEmpType(model: EmployeeType): Long {
        val db = databaseHelper.writableDatabase

        val values = ContentValues()
        values.put(NAMA_EMP_TYPE, model.nama_emp_type)
        values.put(DES_EMP_TYPE, model.des_emp_type)
        values.put(IS_DELETED, "false")

        return db.insert(TABEL_EMPLOYEE_TYPE, null, values)
    }

    fun editEmpType(model: EmployeeType): Int {
        val db = databaseHelper.writableDatabase

        val values = ContentValues()
        values.put(NAMA_EMP_TYPE, model.nama_emp_type)
        values.put(DES_EMP_TYPE, model.des_emp_type)

        return db.update(
            TABEL_EMPLOYEE_TYPE,
            values,
            "$ID_EMP_TYPE = ?",
            arrayOf(model.id_emp_type.toString())
        )
    }

    fun hapusEmpType(id: Int): Int {
        val db = databaseHelper.writableDatabase

        val values = ContentValues()
        values.put(IS_DELETED, "true")

        return db.update(TABEL_EMPLOYEE_TYPE, values, "$ID_EMP_TYPE = ?", arrayOf(id.toString()))
    }

    fun cekEmpTypeSudahAda(nama: String): Int {
        val db = databaseHelper.readableDatabase
        val queryCari =
            "SELECT * FROM $TABEL_EMPLOYEE_TYPE WHERE $NAMA_EMP_TYPE LIKE '$nama' AND " +
                    "$IS_DELETED = 'false'"

        val cursor = db.rawQuery(queryCari, null)

        return cursor.count
    }
}