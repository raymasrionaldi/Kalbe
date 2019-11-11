package com.xsis.android.batch217.databases

import android.database.Cursor
import com.xsis.android.batch217.models.PRFCandidate
import com.xsis.android.batch217.utils.ID_FROM_PRF
import com.xsis.android.batch217.utils.IS_DELETED
import com.xsis.android.batch217.utils.NAMA_PRF_CANDIDATE
import com.xsis.android.batch217.utils.TABEL_PRF_CANDIDATE

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

    fun cariPRFCandidateModels(keyword: String): List<PRFCandidate> {
        var listPRFCandidate = ArrayList<PRFCandidate>()
        if (keyword.isNotBlank()) {
            val db = databaseHelper.readableDatabase
            val queryCari =
                "SELECT * FROM $TABEL_PRF_CANDIDATE WHERE $NAMA_PRF_CANDIDATE LIKE '%$keyword%' AND " +
                        "$IS_DELETED = 'false'"

            val cursor = db.rawQuery(queryCari, null)
            if (cursor.count > 0) {
                listPRFCandidate = konversiCursorKeListPRFCandidateModel(cursor)
            }
        }
        return listPRFCandidate
    }

}