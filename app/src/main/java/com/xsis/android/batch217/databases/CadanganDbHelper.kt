package com.xsis.android.batch217.databases

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.xsis.android.batch217.utils.*

class CadanganDbHelper(val context: Context):
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object{
        internal val DATABASE_NAME = "database_batch217.db"
        internal val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase?) {
        /*========================================================*/
        /*                      CREATE TABEL                      */
        /*========================================================*/
        var query="CREATE TABLE '$TABEL_AGAMA' (" +
                "'$ID_AGAMA' INTEGER PRIMARY KEY AUTOINCREMENT," +
                "'$NAMA_AGAMA' TEXT," +
                "'$DES_AGAMA' TEXT," +
                "'$IS_DELETED' TEXT"+
                ");"

        db!!.execSQL(query)


        query= "CREATE TABLE '$TABEL_BACKOFFICE_POSITION' (" +
                "'$ID_BACKOFFICE' INTEGER PRIMARY KEY AUTOINCREMENT," +
                "'$CODE_BACKOFFICE' TEXT," +
                "'$NAMA_BACKOFFICE' TEXT," +
                "'$LEVEL_BACKOFFICE' TEXT," +
                "'$COMPANY_BACKOFFICE' TEXT," +
                "'$NOTE_BACKOFFICE' TEXT," +
                "'$IS_DELETED' TEXT" +
                ");"

        db!!.execSQL(query)

        query= "CREATE TABLE '$TABEL_COMPANY' (" +
                "'$ID_COMPANY' INTEGER PRIMARY KEY AUTOINCREMENT," +
                "'$NAMA_COMPANY' TEXT," +
                "'$KOTA_COMPANY' TEXT," +
                "'$KDPOS_COMPANY' INTEGER," +
                "'$JLN_COMPANY' INTEGER," +
                "'$BUILDING_COMPANY' TEXT," +
                "'$IS_DELETED' TEXT" +
                ");"

        db!!.execSQL(query)

        query= "CREATE TABLE $TABEL_CONTRACT_STATUS (" +
                " '$ID_CONTRACT' INTEGER PRIMARY KEY AUTOINCREMENT," +
                " '$NAMA_CONTRACT' TEXT," +
                " '$DES_CONTRACT' TEXT," +
                " '$IS_DELETED' TEXT" +
                ");"

        db!!.execSQL(query)

        query= "CREATE TABLE '$TABEL_EMPLOYEE_STATUS' (" +
                " '$ID_EMP' INTEGER PRIMARY KEY AUTOINCREMENT," +
                " '$NAMA_EMP' TEXT," +
                " '$DES_EMP' TEXT," +
                " '$IS_DELETED' TEXT" +
                ");"

        db!!.execSQL(query)

        query= "CREATE TABLE '$TABEL_EMPLOYEE_TYPE' (" +
                " '$ID_EMP_TYPE' INTEGER PRIMARY KEY AUTOINCREMENT," +
                " '$NAMA_EMP_TYPE' TEXT," +
                " '$DES_EMP_TYPE' TEXT," +
                " '$IS_DELETED' TEXT" +
                ");"
        db!!.execSQL(query)

        query= "CREATE TABLE 'grade' (" +
                " 'id_grade' INTEGER PRIMARY KEY AUTOINCREMENT," +
                " 'nama_grade' TEXT," +
                " 'des_grade' TEXT," +
                " 'is_deleted_grade' TEXT" +
                ");"
        db!!.execSQL(query)

        query= "CREATE TABLE 'jadwal' (" +
                " 'id_jadwal' INTEGER PRIMARY KEY AUTOINCREMENT," +
                " 'nama_jadwal' TEXT," +
                " 'des_jadwal' TEXT," +
                " 'is_deleted_jadwal' TEXT" +
                ");"
        db!!.execSQL(query)

        query= "CREATE TABLE 'jenis_catatan' (" +
                " 'id_catatan' INTEGER PRIMARY KEY AUTOINCREMENT," +
                " 'nama_catatan' TEXT," +
                " 'des_catatan' TEXT," +
                " 'is_deleted_catatan' TEXT" +
                ");"
        db!!.execSQL(query)

        query= "CREATE TABLE '$TABEL_KEAHLIAN' (" +
                " '$ID_KEAHLIAN' INTEGER PRIMARY KEY AUTOINCREMENT," +
                " '$NAMA_KEAHLIAN' TEXT," +
                " '$DES_KEAHLIAN' TEXT," +
                " '$IS_DELETED' TEXT" +
                ");"
        db!!.execSQL(query)

        query= "CREATE TABLE 'pendidikan' (" +
                " 'id_pendidikan' INTEGER PRIMARY KEY AUTOINCREMENT," +
                " 'nama_pendidikan' TEXT," +
                " 'des_pendidikan' TEXT," +
                " 'is_deleted_pendidikan' TEXT" +
                ");"
        db!!.execSQL(query)

        query= "CREATE TABLE 'periode' (" +
                " 'id_periode' INTEGER PRIMARY KEY AUTOINCREMENT," +
                " 'nama_periode' TEXT," +
                " 'des_periode' TEXT," +
                " 'is_deleted_periode' TEXT" +
                ");"
        db!!.execSQL(query)

        query= "CREATE TABLE 'position_level' (" +
                " 'id_position' INTEGER PRIMARY KEY AUTOINCREMENT," +
                " 'nama_position' TEXT," +
                " 'des_position' TEXT," +
                " 'is_deleted_position' TEXT" +
                ");"
        db!!.execSQL(query)

        query= "CREATE TABLE 'prf_status' (" +
                " 'id_prf' INTEGER PRIMARY KEY AUTOINCREMENT," +
                " 'nama_prf' TEXT," +
                " 'des_prf' TEXT," +
                " 'is_deleted_prf' TEXT" +
                ");"
        db!!.execSQL(query)

        query= "CREATE TABLE 'provider_tools' (" +
                " 'id_provider' INTEGER PRIMARY KEY AUTOINCREMENT," +
                " 'nama_provider' TEXT," +
                " 'des_provider' TEXT," +
                " 'is_deleted_provider' TEXT" +
                ");"
        db!!.execSQL(query)

        query= "CREATE TABLE 'status_pernikahan' (" +
                " 'id_status' INTEGER PRIMARY KEY AUTOINCREMENT," +
                " 'nama_status' TEXT," +
                " 'des_status' TEXT," +
                " 'is_deleted_status' TEXT" +
                ");"
        db!!.execSQL(query)

        query= "CREATE TABLE 'tipe_identitas' (" +
                " 'id_identitas' INTEGER PRIMARY KEY AUTOINCREMENT," +
                " 'nama_identitas' TEXT," +
                " 'des_identitas' TEXT," +
                " 'is_deleted_identitas' TEXT" +
                ");"
        db!!.execSQL(query)

        query= "CREATE TABLE 'tipe_tes' (" +
                " 'id_tes' INTEGER PRIMARY KEY AUTOINCREMENT," +
                " 'nama_tes' TEXT," +
                " 'des_tes' TEXT," +
                " 'is_deleted_tes' TEXT" +
                ");"
        db!!.execSQL(query)

        query= "CREATE TABLE 'tipe_tes' (" +
                " 'id_tes' INTEGER PRIMARY KEY AUTOINCREMENT," +
                " 'nama_tes' TEXT," +
                " 'des_tes' TEXT," +
                " 'is_deleted_tes' TEXT" +
                ");"
        db!!.execSQL(query)


        /*========================================================*/
        /*                      PRELOAD DATA RECORD               */
        /*========================================================*/
        var queryInsert= "INSERT INTO '$TABEL_AGAMA'" +
                "($NAMA_AGAMA,$DES_AGAMA,$IS_DELETED) " +
                "VALUES " +
                "('Islam','Agama Islam','FALSE')," +
                "('Kristen','Agama Kristen','FALSE')," +
                "('Konfusianisme','Agama Konfusianisme','FALSE')"
        db!!.execSQL(queryInsert)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

}