package com.xsis.android.batch217.databases

import android.database.Cursor
import com.xsis.android.batch217.models.ContractStatus
import com.xsis.android.batch217.utils.IS_DELETED
import com.xsis.android.batch217.utils.TABEL_CONTRACT_STATUS

class ContractStatusQueryHelper (val databaseHelper : DatabaseHelper) {

    private fun getSemuaContractStatus() : Cursor {
        val db = databaseHelper.readableDatabase

        val queryRead = "SELECT * FROM $TABEL_CONTRACT_STATUS WHERE $IS_DELETED = 'false'"

        return db.rawQuery(queryRead, null)

    }
    private fun konversiCursorKeListKontrakModel(cursor: Cursor): ArrayList<ContractStatus>{
        var listKontrakKerja = ArrayList<ContractStatus>()

        for(c in 0 until cursor.count){
            cursor.moveToPosition(c)

            val Kontrak =  ContractStatus()
                Kontrak.idContract = cursor.getInt(0)
                Kontrak.namaContract = cursor.getString(1)
                Kontrak.desContract = cursor.getString(2)
                Kontrak.isDeleted = cursor.getString(3)

            listKontrakKerja.add(ContractStatus())
        }

        return listKontrakKerja
    }
    fun readSemuaKontrakModels():List<ContractStatus>{
        var listKontrakkerja = ArrayList<ContractStatus>()
        val cursor = getSemuaContractStatus()
        if(cursor.count > 0){
            listKontrakkerja = konversiCursorKeListKontrakModel(cursor)
        }

        return listKontrakkerja
    }


}