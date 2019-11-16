package com.xsis.android.batch217.databases

import android.content.ContentValues
import android.database.Cursor
import com.xsis.android.batch217.models.PRFRequest
import com.xsis.android.batch217.models.ProjectCreate
import com.xsis.android.batch217.models.TypeNama
import com.xsis.android.batch217.models.TypePRF
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
                "WHERE $PLACEMENT LIKE '$placement' "
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

    fun updateIsi(id: Int,
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
                "SET $TANGGAL = '$tanggal', $PLACEMENT = '$placement', $TYPE = '$type', $PID = '$pid', $LOCATION = '$location', $PERIOD = '$period', " +
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

    fun readTypePRFNew(): List<TypePRF> {
        var listTypePRF = ArrayList<TypePRF>()
        val db = databaseHelper.readableDatabase
        val queryReadType = "SELECT * FROM $TABEL_TYPE_PRF WHERE $IS_DELETED = 'false'"
        val cursor = db.rawQuery(queryReadType, null)

        for (i in 0 until cursor.count) {
            cursor.moveToPosition(i)
            val typepRF = TypePRF()
            typepRF.id_type_prf = cursor.getInt(0)
            typepRF.nama_type_prf = cursor.getString(1)
            typepRF.deskripsi = cursor.getString(2)
            typepRF.is_deleted = cursor.getString(3)

            listTypePRF.add(typepRF)
        }

        return listTypePRF
    }

    fun readPIDPRFNew(): List<ProjectCreate> {
        val listPID = ArrayList<ProjectCreate>()
        val db = databaseHelper.readableDatabase
        val queryReadType = "SELECT * FROM $TABEL_PROJECT_CREATE"
        val cursor = db.rawQuery(queryReadType, null)

        for (i in 0 until cursor.count) {
            cursor.moveToPosition(i)
            val pidPRF = ProjectCreate()
            pidPRF.idProjectCreate = cursor.getInt(0)
            pidPRF.PID = cursor.getString(1)

            listPID.add(pidPRF)
        }

        return listPID
    }

    fun tambahPRFRequest(model: PRFRequest): Long {
        val db = databaseHelper.writableDatabase

        val values = ContentValues()
        values.put(TANGGAL, model.tanggal)
        values.put(TYPE, model.type)
        values.put(PLACEMENT, model.placement)
        values.put(PID, model.pid)
        values.put(LOCATION, model.location)
        values.put(PERIOD, model.period)
        values.put(USER_NAME, model.user_name)
        values.put(TELP_NUMBER, model.telp_number)
        values.put(EMAIL, model.email)
        values.put(NOTEBOOK, model.notebook)
        values.put(OVERTIME, model.overtime)
        values.put(BAST, model.bast)
        values.put(BILLING, model.billing)
        values.put(IS_DELETED, "false")

        return db.insert(TABEL_PRF_REQUEST, null, values)
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

    fun cariType(nama: String): Int {
        var pilihType = 0
        val db = databaseHelper.readableDatabase
        val queryCari = "SELECT $ID_TYPE_PRF FROM $TABEL_TYPE_PRF " +
                "WHERE $NAMA_TYPE_PRF = '$nama' "
        val cursor = db.rawQuery(queryCari, null)
        cursor.moveToFirst()
        pilihType = cursor.getInt(0)

        return pilihType
    }

    fun cariPid(pid: String): Int {
        var pilihPid = 0
        val db = databaseHelper.readableDatabase
        val queryCari = "SELECT $ID_PROJECT_CREATE FROM $TABEL_PROJECT_CREATE " +
                "WHERE $PID_CREATE = '$pid' "
        val cursor = db.rawQuery(queryCari, null)
        cursor.moveToFirst()
        pilihPid = cursor.getInt(0)

        return pilihPid
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

    fun checkTahapPRF(id:Int):String{
        var check = ""
        val db = databaseHelper.readableDatabase
        val queryCari = "SELECT * FROM $TABEL_PRF_REQUEST " +
                "WHERE $TABEL_PRF_REQUEST.$ID_PRF_REQUEST = '$id' "
        val cursor = db.rawQuery(queryCari, null)
        cursor.moveToFirst()
        val t1 = cursor.getString(15)
        val t2 = cursor.getString(16)
        val t3 = cursor.getString(17)
        val t4 = cursor.getString(18)
        if (t1=="false"){
            check = TAHAP1_STATUS
        } else if (t2 == "false"){
            check = TAHAP2_STATUS
        } else if (t3 == "false"){
            check = TAHAP3_STATUS
        } else if (t4 == "false"){
            check = TAHAP4_STATUS
        }
        println("INI CHECK : $check")
        return check

    }

    fun updateCheckPRF(id:Int): String {
        val check = checkTahapPRF(id)
        println("INI CHECK : $check")
        val db = databaseHelper.writableDatabase
        val values = ContentValues()
        values.put(check, "true")
        db.update(TABEL_PRF_REQUEST, values, "$ID_PRF_REQUEST = ?", arrayOf(id.toString()))
        return check
    }

}