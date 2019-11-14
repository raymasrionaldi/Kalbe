package com.xsis.android.batch217.databases

import android.database.Cursor
import com.xsis.android.batch217.models.PRFCandidate
import com.xsis.android.batch217.utils.*

class PRFCandidateQueryHelper(val databaseHelper: DatabaseHelper)  {
    private fun getSemuaPRFCandidate(id: Int): Cursor {
        val db = databaseHelper.readableDatabase

        val queryRead = "SELECT * FROM $TABEL_PRF_CANDIDATE WHERE $IS_DELETED = 'false' AND $ID_FROM_PRF = $id"

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

    fun readNamaPRFCandidate (nama: String): List<PRFCandidate> {
        var listPRFCandidate = ArrayList<PRFCandidate>()

        val db = databaseHelper.readableDatabase
        val queryCari = "SELECT * FROM $TABEL_PRF_CANDIDATE " +
                "WHERE $NAMA_PRF_CANDIDATE = '$nama' "
        val cursor = db.rawQuery(queryCari, null)

        if (cursor.count > 0) {
            listPRFCandidate = konversiCursorKeListPRFCandidateModel(cursor)
        }

        return listPRFCandidate
    }

    fun updatePRFCandidate(name: String,
                           batch: String,
                           position: String,
                           placementDate: String,
                           srfNumber: String,
                           customAllowence: String,
                           candidateStatus: String,
                           signContractDate: String,
                           notes: String): List<PRFCandidate> {
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

    fun readUpdate(id:Int, nama:String):List<PRFCandidate>{
        var listPRFCandidate = ArrayList<PRFCandidate>()

        val db = databaseHelper.writableDatabase
        val queryUpdate = "SELECT * FROM $TABEL_PRF_CANDIDATE " +
                "WHERE $NAMA_PRF_CANDIDATE = '$nama' AND $ID_PRF_REQUEST != '$id'"
        val cursor = db.rawQuery(queryUpdate, null)
        if (cursor.count > 0){
            listPRFCandidate = konversiCursorKeListPRFCandidateModel(cursor)
        }

        println(queryUpdate)
        return listPRFCandidate
    }

    fun updateDelete(id: Int,
                     name: String,
                     batch: String,
                     position: String,
                     placementDate: String,
                     srfNumber: String,
                     customAllowence: String,
                     candidateStatus: String,
                     signContractDate: String,
                     notes: String):List<PRFCandidate>{
        var listPRFCandidate= ArrayList<PRFCandidate>()

        val db = databaseHelper.writableDatabase
        val queryUpdate = "UPDATE $TABEL_PRF_CANDIDATE " +
                "SET $NAMA_PRF_CANDIDATE = $name, $BATCH = '$batch', $POSITION = '$position', $PLACEMENT_DATE = '$placementDate', $SRF_NUMBER = '$srfNumber', " +
                "$ALLOWENCE_CANDIDATE = '$customAllowence', $STATUS_CANDIDATE = $candidateStatus, $SIGN_CONTRACT_DATE = '$signContractDate', $NOTES = '$notes', $IS_DELETED = 'false' " +
                "WHERE $ID_PRF_CANDIDATE = $id"
        val cursor = db.rawQuery(queryUpdate, null)
        if (cursor.count > 0){
            listPRFCandidate = konversiCursorKeListPRFCandidateModel(cursor)
        }
        println(queryUpdate)
        return listPRFCandidate
    }

    fun readEmployeePosition():List<String> {
        val listEmployeePosition = ArrayList<String>()
        val db = databaseHelper.readableDatabase
        val queryReadPosition = "SELECT $NAMA_POSITION FROM $TABEL_EMPLOYEE_POSITION WHERE $IS_DELETED = 'false'"
        val cursor = db.rawQuery(queryReadPosition, null)
        if (cursor.count > 0) {
            for (i in 0 until cursor.count) {
                cursor.moveToPosition(i)
                listEmployeePosition.add(cursor.getString(0))
            }
        }
        return listEmployeePosition
    }

    fun readSrfNumber():List<String> {
        val listSrfNumber = ArrayList<String>()
        val db = databaseHelper.readableDatabase
        val queryReadPosition = "SELECT $ID_SRF FROM $TABEL_SRF WHERE $IS_DELETED = 'false'"
        val cursor = db.rawQuery(queryReadPosition, null)
        if (cursor.count > 0) {
            for (i in 0 until cursor.count) {
                cursor.moveToPosition(i)
                listSrfNumber.add(cursor.getString(0))
            }
        }
        return listSrfNumber
    }

}