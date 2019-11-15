package com.xsis.android.batch217.databases

import android.content.ContentValues
import android.database.Cursor
import com.xsis.android.batch217.models.PRFRequest
import com.xsis.android.batch217.models.TypeNama
import com.xsis.android.batch217.utils.*

class PRFRequestQueryHelper (val databaseHelper: DatabaseHelper) {

    private fun getSemuaPRFRequest(): Cursor {
        val db = databaseHelper.readableDatabase

        val queryRead = "SELECT * FROM $TABEL_PRF_REQUEST WHERE $IS_DELETED = 'false'"

        return db.rawQuery(queryRead, null)
    }

    fun getPRFRequestByID(id: Int): PRFRequest {
        val db = databaseHelper.readableDatabase

        val queryRead = "SELECT * FROM $TABEL_PRF_REQUEST WHERE $IS_DELETED = 'false' AND $ID_PRF_REQUEST = $id"

        val cursor = db.rawQuery(queryRead, null)
        if (cursor.count == 1) {
            return konversiCursorKeListPRFRequestModel(cursor)[0]
        }
        return  PRFRequest()
    }

    private fun konversiCursorKeListPRFRequestModel(cursor: Cursor): ArrayList<PRFRequest> {
        var listPRFRequest = ArrayList<PRFRequest>()

        for (c in 0 until cursor.count) {
            cursor.moveToPosition(c)

            val prfRequest = PRFRequest()
            prfRequest.id_prf_request = cursor.getInt(0)
            prfRequest.tanggal = cursor.getString(1)
            prfRequest.type = cursor.getString(2)
            prfRequest.placement = cursor.getString(3)
            prfRequest.pid = cursor.getString(4)
            prfRequest.location = cursor.getString(5)
            prfRequest.period = cursor.getString(6)
            prfRequest.user_name = cursor.getString(7)
            prfRequest.telp_number = cursor.getString(8)
            prfRequest.email = cursor.getString(9)
            prfRequest.notebook = cursor.getString(10)
            prfRequest.overtime = cursor.getString(11)
            prfRequest.bast = cursor.getString(12)
            prfRequest.billing = cursor.getString(13)
            prfRequest.is_Deleted = cursor.getString(14)

            listPRFRequest.add(prfRequest)
        }

        return listPRFRequest
    }

    fun readSemuaPRFRequestModels(): List<PRFRequest> {
        var listPRFRequest = ArrayList<PRFRequest>()

        val cursor = getSemuaPRFRequest()
        if (cursor.count > 0) {
            listPRFRequest = konversiCursorKeListPRFRequestModel(cursor)
        }

        return listPRFRequest
    }

    fun cariPRFRequestModels(keyword: String): List<PRFRequest> {
        var listPRFRequest = ArrayList<PRFRequest>()
        if (keyword.isNotBlank()) {
            val db = databaseHelper.readableDatabase
            val queryCari =
                "SELECT * FROM $TABEL_PRF_REQUEST WHERE $PLACEMENT LIKE '%$keyword%' AND " +
                        "$IS_DELETED = 'false'"

            val cursor = db.rawQuery(queryCari, null)
            if (cursor.count > 0) {
                listPRFRequest = konversiCursorKeListPRFRequestModel(cursor)
            }
        }
        return listPRFRequest
    }

    fun readPlacementPRF (placement: String): List<PRFRequest> {
        var listPRFRequest = ArrayList<PRFRequest>()

        val db = databaseHelper.readableDatabase
        val queryCari = "SELECT * FROM $TABEL_PRF_REQUEST " +
                "WHERE $PLACEMENT = '$placement' "
        val cursor = db.rawQuery(queryCari, null)

        if (cursor.count > 0) {
            listPRFRequest = konversiCursorKeListPRFRequestModel(cursor)
        }

        return listPRFRequest
    }

    fun updatePRFRequest(tanggal: String,
                         type: String,
                         placement: String,
                         pid: String,
                         location: String,
                         period: String,
                         userName: String,
                         telpMobilePhone: String,
                         email: String,
                         notebook: String,
                         overtime: String,
                         bast: String,
                         billing: String): List<PRFRequest> {
        var listPRFRequest = ArrayList<PRFRequest>()

        val db = databaseHelper.writableDatabase
        val queryUpdate = "UPDATE $TABEL_PRF_REQUEST " +
                "SET $TANGGAL = $tanggal $TYPE = '$type', $PID = '$pid', $LOCATION = '$location', $PERIOD = '$period', " +
                "$USER_NAME = '$userName', $TELP_NUMBER = '$telpMobilePhone', $EMAIL = '$email'," +
                "$NOTEBOOK = '$notebook', $OVERTIME = '$overtime', $BAST = '$bast', $BILLING = '$billing'," +
                "  $IS_DELETED = 'false' " +
                "WHERE $PLACEMENT = '$placement' AND $IS_DELETED = 'true'"
        val cursor = db.rawQuery(queryUpdate, null)
        if (cursor.count > 0) {
            listPRFRequest = konversiCursorKeListPRFRequestModel(cursor)
        }
        println(queryUpdate)
        return listPRFRequest
    }

    fun readUpdate(id:Int, placement:String):List<PRFRequest>{
        var listPRFRequest = ArrayList<PRFRequest>()

        val db = databaseHelper.writableDatabase
        val queryUpdate = "SELECT * FROM $TABEL_PRF_REQUEST " +
                "WHERE $PLACEMENT = '$placement' AND $ID_PRF_REQUEST != '$id'"
        val cursor = db.rawQuery(queryUpdate, null)
        if (cursor.count > 0){
            listPRFRequest = konversiCursorKeListPRFRequestModel(cursor)
        }

        println(queryUpdate)
        return listPRFRequest
    }

    fun updateDelete(id: Int,
                     tanggal: String,
                     type: String,
                     placement: String,
                     pid: String,
                     location: String,
                     period: String,
                     userName: String,
                     telpMobilePhone: String,
                     email: String,
                     notebook: String,
                     overtime: String,
                     bast: String,
                     billing: String):List<PRFRequest>{
        var listPRFRequest = ArrayList<PRFRequest>()

        val db = databaseHelper.writableDatabase
        val queryUpdate = "UPDATE $TABEL_PRF_REQUEST " +
                "SET $TANGGAL = $tanggal, $PLACEMENT = $placement $TYPE = '$type', $PID = '$pid', $LOCATION = '$location', $PERIOD = '$period', " +
                "$USER_NAME = '$userName', $TELP_NUMBER = '$telpMobilePhone', $EMAIL = '$email'," +
                "$NOTEBOOK = '$notebook', $OVERTIME = '$overtime', $BAST = '$bast', $BILLING = '$billing'," +
                "  $IS_DELETED = 'false' " +
                "WHERE $ID_PRF_REQUEST = $id"
        val cursor = db.rawQuery(queryUpdate, null)
        if (cursor.count > 0){
            listPRFRequest = konversiCursorKeListPRFRequestModel(cursor)
        }
        println(queryUpdate)
        return listPRFRequest
    }

/*    fun readPID () : List<String> {
        val listPID = ArrayList<String>()
        val db = databaseHelper.readableDatabase
        val queryReadPID = "SELECT $NAMA_PID FROM $TABEL_PID_PRF"
        val cursor = db.rawQuery(queryReadPID, null)
        if (cursor.count > 0) {
            for (i in 1 until cursor.count) {
                cursor.moveToPosition(i)
                listPID.add(cursor.getString(0))
            }
        }
        return  listPID
    }*/

    fun readTypePRF(): List<String> {
        val listTypePRF = ArrayList<String>()
        val db = databaseHelper.readableDatabase
        val queryReadType = "SELECT $NAMA_TYPE_PRF FROM $TABEL_TYPE_PRF WHERE $IS_DELETED = 'false'"
        val cursor = db.rawQuery(queryReadType, null)
        if (cursor.count > 0) {
            for (i in 0 until cursor.count) {
                cursor.moveToPosition(i)
                listTypePRF.add(cursor.getString(0))
            }
        }
        return listTypePRF
    }

    fun readPIDPRF(): List<String> {
        val listPID = ArrayList<String>()
        val db = databaseHelper.readableDatabase
        val queryReadType = "SELECT $PID_CREATE FROM $TABEL_PROJECT_CREATE "
        val cursor = db.rawQuery(queryReadType, null)
        if (cursor.count > 0) {
            for (i in 0 until cursor.count) {
                cursor.moveToPosition(i)
                listPID.add(cursor.getString(0))
            }
        }
        return listPID
    }

    fun getListTypeNama(id: Int): ArrayList<TypeNama> {
        var listTypeNama = ArrayList<TypeNama>()
        val db = databaseHelper.readableDatabase
        val queryGetTypeNama = "SELECT $TABEL_PRF_REQUEST.$TYPE, $TABEL_PRF_CANDIDATE.$NAMA_PRF_CANDIDATE " +
                "FROM $TABEL_PRF_REQUEST " +
                "JOIN $TABEL_PRF_CANDIDATE " +
                "WHERE $TABEL_PRF_REQUEST.$ID_PRF_REQUEST = $TABEL_PRF_CANDIDATE.$ID_FROM_PRF " +
                "AND $TABEL_PRF_CANDIDATE.$ID_FROM_PRF = $id"
        val cursor =  db.rawQuery(queryGetTypeNama, null)
        if (cursor.count  > 0) {
            listTypeNama = konversiCursorKeListTypeNamaModel(cursor)
        }
        return listTypeNama
    }

    fun konversiCursorKeListTypeNamaModel(cursor: Cursor): ArrayList<TypeNama> {
        var listTypeNama = ArrayList<TypeNama>()

        for (c in 0 until cursor.count) {
            cursor.moveToPosition(c)

            val typeNama = TypeNama()
            typeNama.type = cursor.getString(0)
            typeNama.namaCandidate = cursor.getString(1)

            listTypeNama.add(typeNama)
        }

        return listTypeNama
    }

    fun setWinPRF(id:Int){
        val db = databaseHelper.writableDatabase
        val values = ContentValues()
        values.put(WIN_STATUS, "true")
        db.update(TABEL_PRF_REQUEST, values, "$ID_PRF_REQUEST = ?", arrayOf(id.toString()))


//        val queryUpdate = "UPDATE $TABEL_PRF_REQUEST SET $WIN_STATUS = 'true' WHERE $ID_PRF_REQUEST = $id"
//        val cursor = db.rawQuery(queryUpdate, null)
        println("UPDATE $TABEL_PRF_REQUEST SET $WIN_STATUS = 'true' WHERE $ID_PRF_REQUEST = $id")
    }

}