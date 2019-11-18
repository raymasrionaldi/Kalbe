package com.xsis.android.batch217.databases

import android.content.ContentValues
import android.database.Cursor
import com.xsis.android.batch217.models.ProjectCreate
import com.xsis.android.batch217.utils.*

class ProjectCreateQueryHelper(val databaseHelper: DatabaseHelper) {
    //Untuk search
    fun cariProject(keyword:String): List<ProjectCreate>{
        var listProject = ArrayList<ProjectCreate>()

        if (keyword.isNotBlank()){
            val db = databaseHelper.readableDatabase
            val queryCari = "SELECT * FROM $TABEL_PROJECT_CREATE where " +
                    "($CLIENT LIKE '%$keyword%' OR $NO_PO_SPK_KONTRAK LIKE '%$keyword%')" +
                    "AND $PID != 'PID *'"
            val cursor = db.rawQuery(queryCari, null)
            if (cursor.count > 0){
                listProject = konversiCursorKeListProjectCreate(cursor)
            }
        }
        return listProject
    }

    fun konversiCursorKeListProjectCreate(cursor:Cursor): ArrayList<ProjectCreate>{
        var listProject = ArrayList<ProjectCreate>()

        if (cursor.count > 0){
            for (i in 0 until cursor.count){
                cursor.moveToPosition(i)

                val projectCreate = ProjectCreate()
                projectCreate.idProjectCreate = cursor.getInt(0)
                projectCreate.PID = cursor.getString(1)
                projectCreate.noPOSPKKontrak = cursor.getString(2)
                projectCreate.client = cursor.getString(3)
                projectCreate.startDate = cursor.getString(4)
                projectCreate.endDate = cursor.getString(5)
                projectCreate.posisiDiClient = cursor.getString(6)
                projectCreate.jenisOvertime = cursor.getString(7)
                projectCreate.catatanFixRate = cursor.getString(8)
                projectCreate.tanggalBAST = cursor.getString(9)

                listProject.add(projectCreate)
            }
        }
        return listProject

    }

    fun loadDataProjectCreate(id:Int): ProjectCreate{
        val db = databaseHelper.readableDatabase
        val queryLoad = "SELECT * FROM $TABEL_PROJECT_CREATE WHERE $ID_PROJECT_CREATE = $id"
        val cursor = db.rawQuery(queryLoad, null)
        val listProject = konversiCursorKeListProjectCreate(cursor)
        val data = listProject[0]
        return data
    }

    fun addProjectCreate(data:ProjectCreate){
        val db = databaseHelper.writableDatabase
        val values = ContentValues()
        values.put(PID_CREATE, data.PID)
        values.put(NO_PO_SPK_KONTRAK, data.noPOSPKKontrak)
        values.put(CLIENT, data.client)
        values.put(START_DATE_PROJECT_CREATE, data.startDate)
        values.put(END_DATE_PROJECT_CREATE, data.endDate)
        values.put(POSISI_DI_CLIENT, data.posisiDiClient)
        values.put(JENIS_OVERTIME, data.jenisOvertime)
        values.put(CATATAN_FIX_RATE, data.catatanFixRate)
        values.put(tanggal_BAST, data.tanggalBAST)
        db.insert(TABEL_PROJECT_CREATE, null, values)

    }

    fun updateProjectCreate(data:ProjectCreate){
        val db = databaseHelper.writableDatabase

        val values = ContentValues()
        values.put(PID_CREATE, data.PID)
        values.put(NO_PO_SPK_KONTRAK, data.noPOSPKKontrak)
        values.put(CLIENT, data.client)
        values.put(START_DATE_PROJECT_CREATE, data.startDate)
        values.put(END_DATE_PROJECT_CREATE, data.endDate)
        values.put(POSISI_DI_CLIENT, data.posisiDiClient)
        values.put(JENIS_OVERTIME, data.jenisOvertime)
        values.put(CATATAN_FIX_RATE, data.catatanFixRate)
        values.put(tanggal_BAST, data.tanggalBAST)
        db.update(TABEL_PROJECT_CREATE, values, "$ID_PROJECT_CREATE = ${data.idProjectCreate}", null)
    }

    fun deleteProjectCreate(id:Int){
        val db = databaseHelper.writableDatabase
        val queryDelete = "DELETE FROM $TABEL_PROJECT_CREATE WHERE $ID_PROJECT_CREATE=$id"
        db.execSQL(queryDelete)
    }
}