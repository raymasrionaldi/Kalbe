package com.xsis.android.batch217.databases

import android.content.ContentValues
import android.database.Cursor
import com.xsis.android.batch217.models.BackOfficePosition
import com.xsis.android.batch217.models.Company
import com.xsis.android.batch217.models.PositionLevel
import com.xsis.android.batch217.utils.*

class BackOfficeQueryHelper(val databaseHelper: DatabaseHelper) {
    private fun getSemuaBackOffice(): Cursor {
        val db = databaseHelper.readableDatabase

        val queryRead = "SELECT * FROM $TABEL_BACKOFFICE_POSITION WHERE $IS_DELETED = 'false'"

        return db.rawQuery(queryRead, null)
    }
    private fun konversiCursorKeListBackOfficeModel(cursor: Cursor): ArrayList<BackOfficePosition> {
        var listCompany = ArrayList<BackOfficePosition>()

        for (c in 0 until cursor.count) {
            cursor.moveToPosition(c)

            val backOffice = BackOfficePosition()
            backOffice.idBackOffice = cursor.getInt(0)
            backOffice.codeBackOffice = cursor.getString(1)
            backOffice.namaBackOffice = cursor.getString(2)
            backOffice.levelBackOffice = cursor.getString(3)
            backOffice.companyBackOffice = cursor.getString(4)
            backOffice.notesBackOffice = cursor.getString(5)
            backOffice.isDeleted = cursor.getString(6)
            backOffice.namaCompany = cursor.getString(7)

            listCompany.add(backOffice)
        }

        return listCompany
    }
    fun readSemuaBackOfficeModels(): List<BackOfficePosition> {
        var listBackOffice = ArrayList<BackOfficePosition>()

        val cursor = getSemuaBackOffice()
        if (cursor.count > 0) {
            listBackOffice = konversiCursorKeListBackOfficeModel(cursor)
        }

        return listBackOffice
    }
    fun tambahBackOffice(model: BackOfficePosition): Long {
        val db = databaseHelper.writableDatabase

        val values = ContentValues()
        values.put(CODE_BACKOFFICE, model.codeBackOffice)
        values.put(NAMA_BACKOFFICE, model.namaBackOffice)
        values.put(LEVEL_BACKOFFICE, model.levelBackOffice)
        values.put(COMPANY_BACKOFFICE, model.companyBackOffice)
        values.put(NOTE_BACKOFFICE, model.notesBackOffice)
        values.put(IS_DELETED, "false")

        return db.insert(TABEL_BACKOFFICE_POSITION, null, values)
    }
    fun editBackOffice(model: BackOfficePosition): Int {
        val db = databaseHelper.writableDatabase

        val values = ContentValues()
        values.put(CODE_BACKOFFICE, model.codeBackOffice)
        values.put(NAMA_BACKOFFICE, model.namaBackOffice)
        values.put(LEVEL_BACKOFFICE, model.levelBackOffice)
        values.put(COMPANY_BACKOFFICE, model.companyBackOffice)
        values.put(NOTE_BACKOFFICE, model.notesBackOffice)

        return db.update(
            TABEL_BACKOFFICE_POSITION,
            values,
            "$ID_BACKOFFICE = ?",
            arrayOf(model.idBackOffice.toString())
        )
    }
    fun hapusBackOffice(id: Int): Int {
        val db = databaseHelper.writableDatabase

        val values = ContentValues()
        values.put(IS_DELETED, "true")

        return db.update(TABEL_BACKOFFICE_POSITION, values, "$ID_BACKOFFICE = ?", arrayOf(id.toString()))
    }

    fun cekBackOfficeSudahAda(nama: String): Int {
        val db = databaseHelper.readableDatabase
        val queryCari =
            "SELECT * FROM $TABEL_BACKOFFICE_POSITION WHERE $NAMA_BACKOFFICE LIKE '$nama' AND " +
                    " $IS_DELETED = 'false'"

        val cursor = db.rawQuery(queryCari, null)

        return cursor.count
    }
    fun cekBackOfficeSudahAda2(code: String): Int {
        val db = databaseHelper.readableDatabase
        val queryCari =
            "SELECT * FROM $TABEL_BACKOFFICE_POSITION WHERE $CODE_BACKOFFICE LIKE '$code' AND " +
                    " $IS_DELETED = 'false'"

        val cursor = db.rawQuery(queryCari, null)

        return cursor.count
    }

    fun tampilkanLevelBackOffice(): List<PositionLevel> {
        val db = databaseHelper.readableDatabase

        val queryRead = "SELECT *FROM $TABEL_POSITION_LEVEL WHERE $IS_DELETED = 'false'"
        val cursor = db.rawQuery(queryRead, null)

        var listPositionLevel = ArrayList<PositionLevel>()

        for (c in 0 until cursor.count) {
            cursor.moveToPosition(c)
//bikin mutable list
            //isi data pertama pake hardcore "status*"
            //tambahkan list dari database
            val positionLevel = PositionLevel()
            positionLevel.idPostionLevel= cursor.getInt(0)
            positionLevel.namaPosition = cursor.getString(1)
            positionLevel.desPosition = cursor.getString(2)




            listPositionLevel.add(positionLevel)
        }
        return listPositionLevel
    }
    fun tampilkanCompanyBackOffice(): List<Company> {
        val db = databaseHelper.readableDatabase

        val queryRead = "SELECT * FROM $TABEL_COMPANY WHERE $IS_DELETED = 'false'"
        val cursor = db.rawQuery(queryRead, null)

        var listCompany = ArrayList<Company>()

        for (c in 0 until cursor.count) {
            cursor.moveToPosition(c)
//bikin mutable list
            //isi data pertama pake hardcore "status*"
            //tambahkan list dari database
            val company = Company()
            company.idCompany = cursor.getInt(0)
            company.namaCompany = cursor.getString(1)
            company.kotaCompany = cursor.getString(2)
            company.kdPosCompany = cursor.getString(3)
            company.jlnCompany = cursor.getString(4)
            company.buildingCompany = cursor.getString(5)
            company.floorCompany = cursor.getString(6)



            listCompany.add(company)
        }
        return listCompany
    }
    fun cariBackOfficeModels(keyword: String): List<BackOfficePosition> {
        var listBackOfficePosition = ArrayList<BackOfficePosition>()
        if (keyword.isNotBlank()) {
            val db = databaseHelper.readableDatabase
            val queryCari =
                "SELECT a.*, b.$NAMA_COMPANY FROM $TABEL_BACKOFFICE_POSITION a,$TABEL_COMPANY b WHERE a.$LEVEL_BACKOFFICE LIKE '%$keyword%' AND " +
                        "a.$COMPANY_BACKOFFICE = b.$ID_COMPANY AND a.$IS_DELETED = 'false'"

            val cursor = db.rawQuery(queryCari, null)
            if (cursor.count > 0) {
                listBackOfficePosition = konversiCursorKeListBackOfficeModel(cursor)
            }
        }
        return listBackOfficePosition

    }



}