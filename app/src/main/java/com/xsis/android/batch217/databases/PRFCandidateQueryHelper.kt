package com.xsis.android.batch217.databases

import android.content.ContentValues
import android.database.Cursor
import com.xsis.android.batch217.models.EmployeePosition
import com.xsis.android.batch217.models.PRFCandidate
import com.xsis.android.batch217.models.SRF
import com.xsis.android.batch217.utils.*

class PRFCandidateQueryHelper(val databaseHelper: DatabaseHelper) {
    private fun getSemuaPRFCandidate(id: Int): Cursor {
        val db = databaseHelper.readableDatabase

        val queryRead =
//            "SELECT * FROM $TABEL_PRF_CANDIDATE WHERE $IS_DELETED = 'false' AND $ID_FROM_PRF = $id"
            "SELECT a.*, b.$NAMA_EMPLOYEE_POSITION " +
            "FROM $TABEL_PRF_CANDIDATE a, $TABEL_EMPLOYEE_POSITION b " +
            "WHERE a.$POSITION = b.$ID_EMPLOYEE_POSITION " +
            "AND a.$ID_FROM_PRF = $id"

        return db.rawQuery(queryRead, null)
    }

    private fun konversiCursorKeListPRFCandidateModel(cursor: Cursor): ArrayList<PRFCandidate> {
        var listPRFCandidate = ArrayList<PRFCandidate>()

        for (c in 0 until cursor.count) {
            cursor.moveToPosition(c)

            val prfCandidate = PRFCandidate()
            prfCandidate.id_from_prf = cursor.getInt(0)
            prfCandidate.id_prf_candidate = cursor.getInt(1)
            prfCandidate.nama_prf_candidate = cursor.getString(2)
            prfCandidate.batch = cursor.getString(3)
            prfCandidate.position = cursor.getString(4)
            prfCandidate.placement_date = cursor.getString(5)
            prfCandidate.srf_number = cursor.getString(6)
            prfCandidate.allowence_candidate = cursor.getString(7)
            prfCandidate.status_candidate = cursor.getString(8)
            prfCandidate.sign_contract_date = cursor.getString(9)
            prfCandidate.notes = cursor.getString(10)
            prfCandidate.is_Deleted = cursor.getString(11)

            prfCandidate.namaPosition = cursor.getString(12)

            listPRFCandidate.add(prfCandidate)
        }

        return listPRFCandidate
    }

    fun readSemuaPRFCandidateModels(id: Int): List<PRFCandidate> {
        var listPRFCandidate = ArrayList<PRFCandidate>()

        val cursor = getSemuaPRFCandidate(id)
        if (cursor.count > 0) {
            listPRFCandidate = konversiCursorKeListPRFCandidateModel(cursor)
        }

        return listPRFCandidate
    }

    fun readNamaPRFCandidate(nama: String): List<PRFCandidate> {
        var listPRFCandidate = ArrayList<PRFCandidate>()

        val db = databaseHelper.readableDatabase
        val queryCari = "SELECT * FROM $TABEL_PRF_CANDIDATE " +
                "WHERE $NAMA_PRF_CANDIDATE LIKE '$nama' "
        val cursor = db.rawQuery(queryCari, null)

        if (cursor.count > 0) {
            listPRFCandidate = konversiCursorKeListPRFCandidateModel(cursor)
        }

        return listPRFCandidate
    }

    fun updatePRFCandidate(
        name: String,
        batch: String,
        position: String,
        placementDate: String,
        srfNumber: String,
        customAllowence: String,
        candidateStatus: String,
        signContractDate: String,
        notes: String
    ): List<PRFCandidate> {
        var listPRFCandidate = ArrayList<PRFCandidate>()

        val db = databaseHelper.writableDatabase
        val queryUpdate = "UPDATE $TABEL_PRF_CANDIDATE " +
                "SET $BATCH = '$batch', $POSITION = '$position', $PLACEMENT_DATE = '$placementDate', $SRF_NUMBER = '$srfNumber', " +
                "$ALLOWENCE_CANDIDATE = '$customAllowence', $STATUS_CANDIDATE = $candidateStatus, $SIGN_CONTRACT_DATE = '$signContractDate', $NOTES = '$notes', $IS_DELETED = 'false' " +
                "WHERE $NAMA_PRF_CANDIDATE = '$name' AND $IS_DELETED = 'true'"
        val cursor = db.rawQuery(queryUpdate, null)
        if (cursor.count > 0) {
            listPRFCandidate = konversiCursorKeListPRFCandidateModel(cursor)
        }
        println(queryUpdate)
        return listPRFCandidate
    }

    fun readUpdate(id: Int, nama: String): List<PRFCandidate> {
        var listPRFCandidate = ArrayList<PRFCandidate>()

        val db = databaseHelper.writableDatabase
        val queryUpdate = "SELECT * FROM $TABEL_PRF_CANDIDATE " +
                "WHERE $NAMA_PRF_CANDIDATE = '$nama' AND $ID_PRF_REQUEST != '$id'"
        val cursor = db.rawQuery(queryUpdate, null)
        if (cursor.count > 0) {
            listPRFCandidate = konversiCursorKeListPRFCandidateModel(cursor)
        }

        println(queryUpdate)
        return listPRFCandidate
    }

    fun updateIsi(
        id: Int,
        name: String,
        batch: String,
        position: String,
        placementDate: String,
        srfNumber: String,
        customAllowence: String,
        candidateStatus: String,
        signContractDate: String,
        notes: String
    ): List<PRFCandidate> {
        var listPRFCandidate = ArrayList<PRFCandidate>()

        val db = databaseHelper.writableDatabase
        val queryUpdate = "UPDATE $TABEL_PRF_CANDIDATE " +
                "SET $NAMA_PRF_CANDIDATE = $name, $BATCH = '$batch', $POSITION = '$position', $PLACEMENT_DATE = '$placementDate', $SRF_NUMBER = '$srfNumber', " +
                "$ALLOWENCE_CANDIDATE = '$customAllowence', $STATUS_CANDIDATE = $candidateStatus, $SIGN_CONTRACT_DATE = '$signContractDate', $NOTES = '$notes', $IS_DELETED = 'false' " +
                "WHERE $ID_PRF_CANDIDATE = $id"
        val cursor = db.rawQuery(queryUpdate, null)
        if (cursor.count > 0) {
            listPRFCandidate = konversiCursorKeListPRFCandidateModel(cursor)
        }
        println(queryUpdate)
        return listPRFCandidate
    }

    fun tambahPRFCandidate(model: PRFCandidate): Long {
        val db = databaseHelper.writableDatabase

        val values = ContentValues()
        values.put(ID_FROM_PRF, model.id_from_prf)
        values.put(NAMA_PRF_CANDIDATE, model.nama_prf_candidate)
        values.put(BATCH, model.batch)
        values.put(POSITION, model.position)
        values.put(PLACEMENT_DATE, model.placement_date)
        values.put(SRF_NUMBER, model.srf_number)
        values.put(ALLOWENCE_CANDIDATE, model.allowence_candidate)
        values.put(STATUS_CANDIDATE, model.status_candidate)
        values.put(SIGN_CONTRACT_DATE, model.sign_contract_date)
        values.put(NOTES, model.notes)
        values.put(IS_DELETED, "false")

        return db.insert(TABEL_PRF_CANDIDATE, null, values)
    }

    fun readEmployeePosition(): List<EmployeePosition> {
        val listEmployeePosition = ArrayList<EmployeePosition>()
        val db = databaseHelper.readableDatabase
        val queryReadPosition =
            "SELECT * FROM $TABEL_EMPLOYEE_POSITION"
        val cursor = db.rawQuery(queryReadPosition, null)
        println("$queryReadPosition")
        for (i in 0 until cursor.count) {
            cursor.moveToPosition(i)
            val employeePosition = EmployeePosition()
            employeePosition.id_employee_position = cursor.getInt(0)
            employeePosition.nama_employee_position = cursor.getString(1)
            employeePosition.deskripsi_employee_position = cursor.getString(2)
            employeePosition.is_deleted = cursor.getString(3)

            listEmployeePosition.add(employeePosition)
        }

        return listEmployeePosition
    }

    fun cariEmployeePosition(position: String): Int {
        var pilihPosition = 0
        val db = databaseHelper.readableDatabase
        val queryCari = "SELECT $ID_EMPLOYEE_POSITION FROM $TABEL_EMPLOYEE_POSITION " +
                "WHERE $NAMA_EMPLOYEE_POSITION = '$position' "
        val cursor = db.rawQuery(queryCari, null)
        cursor.moveToFirst()
        pilihPosition = cursor.getInt(0)

        return pilihPosition
    }

    fun readSrfNumber(): List<SRF> {
        val listSrfNumber = ArrayList<SRF>()
        val db = databaseHelper.readableDatabase
        val queryReadPosition = "SELECT $ID_SRF FROM $TABEL_SRF"
        val cursor = db.rawQuery(queryReadPosition, null)
        for (i in 0 until cursor.count ) {
            cursor.moveToPosition(i)
            val srfNumber = SRF()
            srfNumber.id_srf = cursor.getString(0)

            listSrfNumber.add(srfNumber)
        }
        return listSrfNumber
    }

}