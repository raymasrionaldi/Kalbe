package com.xsis.android.batch217.databases

import android.content.ContentValues
import android.database.Cursor
import com.xsis.android.batch217.models.SRF
import com.xsis.android.batch217.utils.*

class SRFQueryHelper(val databaseHelper: DatabaseHelper) {

    private fun getSemuaSRF(): Cursor {
        val db = databaseHelper.readableDatabase

        val queryRead = "SELECT $TABEL_SRF.*, $TABEL_COMPANY.$NAMA_COMPANY, $TABEL_GRADE.$NAMA_GRADE " +
                "FROM $TABEL_SRF, $TABEL_GRADE, $TABEL_COMPANY " +
                "WHERE $TABEL_SRF.$ID_COMPANY = $TABEL_COMPANY.$ID_COMPANY " +
                "AND $TABEL_SRF.$ID_GRADE = $TABEL_GRADE.$ID_GRADE " +
                "AND $TABEL_SRF.$IS_DELETED = 'false'"

        return db.rawQuery(queryRead, null)
    }

    private fun konversiCursorKeListSRFModel(cursor: Cursor): ArrayList<SRF> {
        var listSRF = ArrayList<SRF>()

        for (c in 0 until cursor.count) {
            cursor.moveToPosition(c)

            val srf = SRF()
            srf.id_srf = cursor.getString(0)
            srf.jenis_srf = cursor.getString(1)
            srf.jumlah_kebutuhan = cursor.getInt(2)
            srf.id_company = cursor.getString(3)
            srf.id_grade = cursor.getString(4)
            srf.nama_user = cursor.getString(5)
            srf.email_user = cursor.getString(6)
            srf.sales_price = cursor.getString(7)
            srf.lokasi = cursor.getString(8)
            srf.catatan = cursor.getString(9)
            srf.is_deleted = cursor.getString(10)
            srf.nama_company = cursor.getString(11)
            srf.nama_grade = cursor.getString(12)
            listSRF.add(srf)
        }

        return listSRF
    }

    fun readSemuaSRFModels(): List<SRF> {
        var listSRF = ArrayList<SRF>()

        val cursor = getSemuaSRF()
        if (cursor.count > 0) {
            listSRF = konversiCursorKeListSRFModel(cursor)
        }

        return listSRF
    }

    fun readListClient(): List<String> {
        val listClientPosition = ArrayList<String>()
        val db = databaseHelper.readableDatabase
        val queryReadPosition = "SELECT $NAMA_COMPANY FROM $TABEL_COMPANY WHERE $IS_DELETED = 'false'"
        val cursor = db.rawQuery(queryReadPosition, null)
        if (cursor.count > 0) {
            for (i in 0 until cursor.count) {
                cursor.moveToPosition(i)
                listClientPosition.add(cursor.getString(0))
            }
        }
        return listClientPosition
    }

    fun readListGrade(): List<String> {
        val listClientPosition = ArrayList<String>()
        val db = databaseHelper.readableDatabase
        val queryReadPosition = "SELECT $NAMA_GRADE FROM $TABEL_GRADE WHERE $IS_DELETED = 'false'"
        val cursor = db.rawQuery(queryReadPosition, null)
        if (cursor.count > 0) {
            for (i in 0 until cursor.count) {
                cursor.moveToPosition(i)
                listClientPosition.add(cursor.getString(0))
            }
        }
        return listClientPosition
    }

    fun cariSRFModels(keyword: String): List<SRF> {
        var listSRF = ArrayList<SRF>()
        if (keyword.isNotBlank()) {
            val db = databaseHelper.readableDatabase
            val queryCari =
                "SELECT $TABEL_SRF.*, $TABEL_COMPANY.$NAMA_COMPANY, $TABEL_GRADE.$NAMA_GRADE " +
                        "FROM $TABEL_SRF,$TABEL_COMPANY,$TABEL_GRADE " +
                        "WHERE $TABEL_SRF.$ID_COMPANY = $TABEL_COMPANY.$ID_COMPANY " +
                        "AND $TABEL_SRF.$ID_GRADE = $TABEL_GRADE.$ID_GRADE " +
                        "AND $TABEL_COMPANY.$NAMA_COMPANY LIKE '%$keyword%' " +
                        "AND $TABEL_SRF.$IS_DELETED = 'false'"
            println(queryCari)
            val cursor = db.rawQuery(queryCari, null)
            if (cursor.count > 0) {
                listSRF= konversiCursorKeListSRFModel(cursor)
            }
        }
        return listSRF
    }

    fun tambahSRF(model: SRF): Long {
        val db = databaseHelper.writableDatabase

        val values = ContentValues()
        values.put(JENIS_SRF, model.jenis_srf)
        values.put(JUM_KEBUTUHAN, model.jumlah_kebutuhan)
        values.put(ID_COMPANY, model.id_company)
        values.put(ID_GRADE, model.id_grade)
        values.put(NAMA_USER, model.nama_user)
        values.put(EMAIL_USER, model.email_user)
        values.put(SALES_PRICE, model.sales_price)
        values.put(LOKASI, model.lokasi)
        values.put(CATATAN, model.catatan)
        values.put(IS_DELETED, "false")

        return db.insert(TABEL_SRF, null, values)
    }

    fun editEmpType(model: SRF): Int {
        val db = databaseHelper.writableDatabase

        val values = ContentValues()
        values.put(JENIS_SRF, model.jenis_srf)
        values.put(JUM_KEBUTUHAN, model.jumlah_kebutuhan)
        values.put(ID_COMPANY, model.id_company)
        values.put(ID_GRADE, model.id_grade)
        values.put(NAMA_USER, model.nama_user)
        values.put(EMAIL_USER, model.email_user)
        values.put(SALES_PRICE, model.sales_price)
        values.put(LOKASI, model.lokasi)
        values.put(CATATAN, model.catatan)

        return db.update(
            TABEL_SRF, values, "$ID_SRF = ?", arrayOf(model.id_srf.toString())
        )
    }

    fun hapusSRF(id: String): Int {
        val db = databaseHelper.writableDatabase

        val values = ContentValues()
        values.put(IS_DELETED, "true")

        return db.update(TABEL_SRF, values, "$ID_SRF = ?", arrayOf(id.toString()))
    }

/*    fun cekEmpTypeSudahAda(nama: String): Int {
        val db = databaseHelper.readableDatabase
        val queryCari =
            "SELECT * FROM $TABEL_EMPLOYEE_TYPE WHERE $NAMA_EMP_TYPE LIKE '$nama' AND " +
                    "$IS_DELETED = 'false'"

        val cursor = db.rawQuery(queryCari, null)

        return cursor.count
    }*/
}