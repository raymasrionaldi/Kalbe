package com.xsis.android.batch217.databases

import android.content.ContentValues
import android.database.Cursor
import com.xsis.android.batch217.models.Company
import com.xsis.android.batch217.models.Project
import com.xsis.android.batch217.utils.*

class ProjectQueryHelper(val databaseHelper: DatabaseHelper) {

    private val companyQueryHelper = CompanyQueryHelper(databaseHelper)

    private fun getSemuaProject(): Cursor {
        val db = databaseHelper.readableDatabase

        val queryRead =
            "SELECT p.*, c.$NAMA_COMPANY FROM $TABEL_PROJECT p, $TABEL_COMPANY c WHERE" +
                    "p.$IS_DELETED = 'false' AND p.$KODE_COMPANY_PROJECT = c.$ID_COMPANY"

        return db.rawQuery(queryRead, null)
    }

    private fun konversiCursorKeListProjectModel(cursor: Cursor): ArrayList<Project> {
        var listProject = ArrayList<Project>()

        for (c in 0 until cursor.count) {
            cursor.moveToPosition(c)

            val project = Project()
            project.idProject = cursor.getInt(0)
            project.kodeCompProject = cursor.getInt(1)
            project.locationProject = cursor.getString(2)
            project.departmentProject = cursor.getString(3)
            project.userProject = cursor.getString(4)
            project.nameProject = cursor.getString(5)
            project.startProject = cursor.getString(6)
            project.endProject = cursor.getString(7)
            project.roleProject = cursor.getString(8)
            project.phaseProject = cursor.getString(9)
            project.desProject = cursor.getString(10)
            project.techProject = cursor.getString(11)
            project.taskProject = cursor.getString(12)
            project.isDeleted = cursor.getString(13)
            //tambahan
            project.namaCompany = cursor.getString(14)

            listProject.add(project)
        }

        return listProject
    }

    fun readSemuaProjectModels(): List<Project> {
        var listProject = ArrayList<Project>()

        val cursor = getSemuaProject()
        if (cursor.count > 0) {
            listProject = konversiCursorKeListProjectModel(cursor)
        }

        return listProject
    }

    fun cariProjectModels(keyword: String): List<Project> {
        var listProject = ArrayList<Project>()
        if (keyword.isNotBlank()) {
            val db = databaseHelper.readableDatabase
            val queryCari =
                "SELECT p.*, c.$NAMA_COMPANY FROM $TABEL_PROJECT p, $TABEL_COMPANY c WHERE c.$NAMA_COMPANY LIKE '%$keyword%' AND " +
                        "p.$IS_DELETED = 'false' AND p.$KODE_COMPANY_PROJECT = c.$ID_COMPANY"

            val cursor = db.rawQuery(queryCari, null)
            if (cursor.count > 0) {
                listProject = konversiCursorKeListProjectModel(cursor)
            }
        }
        return listProject
    }

    fun cariProjectModelById(id: Int): Project {
        var listProject = ArrayList<Project>()
        if (id != 0) {
            val db = databaseHelper.readableDatabase
            val queryCari =
                "SELECT p.*, c.$NAMA_COMPANY FROM $TABEL_PROJECT p, $TABEL_COMPANY c WHERE p.$ID_PROJECT = $id AND " +
                        "p.$IS_DELETED = 'false' AND p.$KODE_COMPANY_PROJECT = c.$ID_COMPANY"

            val cursor = db.rawQuery(queryCari, null)
            if (cursor.count == 1) {
                return konversiCursorKeListProjectModel(cursor)[0]
            } else
                return Project()
        }
        return Project()
    }

    fun tambahProject(model: Project): Long {
        val db = databaseHelper.writableDatabase

        val values = ContentValues()
        values.put(KODE_COMPANY_PROJECT, model.kodeCompProject)
        values.put(LOCATION_PROJECT, model.locationProject)
        values.put(DEPARTMENT_PROJECT, model.departmentProject)
        values.put(USER_PROJECT, model.userProject)
        values.put(NAME_PROJECT, model.nameProject)
        values.put(START_PROJECT, model.startProject)
        values.put(END_PROJECT, model.endProject)
        values.put(ROLE_PROJECT, model.roleProject)
        values.put(PHASE_PROJECT, model.phaseProject)
        values.put(DES_PROJECT, model.desProject)
        values.put(TECH_PROJECT, model.techProject)
        values.put(TASK_PROJECT, model.taskProject)
        values.put(IS_DELETED, "false")

        return db.insert(TABEL_PROJECT, null, values)
    }

    fun editProject(model: Project): Int {
        val db = databaseHelper.writableDatabase

        val values = ContentValues()
        values.put(KODE_COMPANY_PROJECT, model.kodeCompProject)
        values.put(LOCATION_PROJECT, model.locationProject)
        values.put(DEPARTMENT_PROJECT, model.departmentProject)
        values.put(USER_PROJECT, model.userProject)
        values.put(NAME_PROJECT, model.nameProject)
        values.put(START_PROJECT, model.startProject)
        values.put(END_PROJECT, model.endProject)
        values.put(ROLE_PROJECT, model.roleProject)
        values.put(PHASE_PROJECT, model.phaseProject)
        values.put(DES_PROJECT, model.desProject)
        values.put(TECH_PROJECT, model.techProject)
        values.put(TASK_PROJECT, model.taskProject)

        return db.update(
            TABEL_PROJECT,
            values,
            "$ID_PROJECT = ?",
            arrayOf(model.idProject.toString())
        )
    }

    fun hapusProject(id: Int): Int {
        val db = databaseHelper.writableDatabase

        val values = ContentValues()
        values.put(IS_DELETED, "true")

        return db.update(TABEL_PROJECT, values, "$ID_PROJECT = ?", arrayOf(id.toString()))
    }

    fun getSemuaCompany(): List<Company> {
        return companyQueryHelper.readSemuaCompanyModels()
    }
}