package com.xsis.android.batch217.databases

import android.content.ContentValues
import android.database.Cursor
import com.xsis.android.batch217.models.Company
import com.xsis.android.batch217.utils.*

class CompanyQueryHelper(val databaseHelper: DatabaseHelper) {

    private fun getSemuaCompany(): Cursor {
        val db = databaseHelper.readableDatabase

        val queryRead = "SELECT * FROM $TABEL_COMPANY WHERE $IS_DELETED = 'false'"

        return db.rawQuery(queryRead, null)
    }

    private fun konversiCursorKeListCompanyModel(cursor: Cursor): ArrayList<Company> {
        var listCompany = ArrayList<Company>()

        for (c in 0 until cursor.count) {
            cursor.moveToPosition(c)

            val company = Company()
            company.idCompany = cursor.getInt(0)
            company.namaCompany = cursor.getString(1)
            company.kotaCompany = cursor.getString(2)
            company.kdPosCompany = cursor.getString(3)
            company.jlnCompany = cursor.getString(4)
            company.buildingCompany = cursor.getString(5)
            company.floorCompany = cursor.getString(6)
            company.isDeleted = cursor.getString(7)

            listCompany.add(company)
        }

        return listCompany
    }

    fun readSemuaCompanyModels(): List<Company> {
        var listCompany = ArrayList<Company>()

        val cursor = getSemuaCompany()
        if (cursor.count > 0) {
            listCompany = konversiCursorKeListCompanyModel(cursor)
        }

        return listCompany
    }

    fun cariCompanyModels(keyword: String): List<Company> {
        var listCompany = ArrayList<Company>()
        if (keyword.isNotBlank()) {
            val db = databaseHelper.readableDatabase
            val queryCari =
                "SELECT * FROM $TABEL_COMPANY WHERE $NAMA_COMPANY LIKE '%$keyword%' AND " +
                        "$IS_DELETED = 'false'"

            val cursor = db.rawQuery(queryCari, null)
            if (cursor.count > 0) {
                listCompany = konversiCursorKeListCompanyModel(cursor)
            }
        }
        return listCompany
    }

    fun tambahCompany(model: Company): Long {
        val db = databaseHelper.writableDatabase

        val values = ContentValues()
        values.put(NAMA_COMPANY, model.namaCompany)
        values.put(KOTA_COMPANY, model.kotaCompany)
        values.put(KDPOS_COMPANY, model.kdPosCompany)
        values.put(JLN_COMPANY, model.jlnCompany)
        values.put(BUILDING_COMPANY, model.buildingCompany)
        values.put(FLOOR_COMPANY, model.floorCompany)
        values.put(IS_DELETED, "false")

        return db.insert(TABEL_COMPANY, null, values)
    }

    fun editCompany(model: Company): Int {
        val db = databaseHelper.writableDatabase

        val values = ContentValues()
        values.put(NAMA_COMPANY, model.namaCompany)
        values.put(KOTA_COMPANY, model.kotaCompany)
        values.put(KDPOS_COMPANY, model.kdPosCompany)
        values.put(JLN_COMPANY, model.jlnCompany)
        values.put(BUILDING_COMPANY, model.buildingCompany)
        values.put(FLOOR_COMPANY, model.floorCompany)

        return db.update(
            TABEL_COMPANY,
            values,
            "$ID_COMPANY = ?",
            arrayOf(model.idCompany.toString())
        )
    }

    fun hapusCompany(id: Int): Int {
        val db = databaseHelper.writableDatabase

        val values = ContentValues()
        values.put(IS_DELETED, "true")

        return db.update(TABEL_COMPANY, values, "$ID_COMPANY = ?", arrayOf(id.toString()))
    }

    fun cekCompanySudahAda(nama: String): Int {
        val db = databaseHelper.readableDatabase
        val queryCari =
            "SELECT * FROM $TABEL_COMPANY WHERE $NAMA_COMPANY LIKE '$nama' AND " +
                    "$IS_DELETED = 'false'"

        val cursor = db.rawQuery(queryCari, null)

        return cursor.count
    }
}