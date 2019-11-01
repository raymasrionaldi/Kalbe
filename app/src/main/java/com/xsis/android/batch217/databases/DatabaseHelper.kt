package com.xsis.android.batch217.databases

import android.content.Context
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class DatabaseHelper(val context: Context):
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object{
        internal val DATABASE_NAME = "db_batch217.db"
        internal val DATABASE_VERSION = 1
        internal var DATABASE_PATH = ""
    }

    override fun onCreate(db: SQLiteDatabase?) {
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (oldVersion<newVersion){
            deleteDatabase()
        } else{
        }
    }

    //Pengecekan database -> exist or not
    fun checkDatabase():Boolean{
        val dbFile = context.getDatabasePath(DATABASE_NAME)
        if (!dbFile.parentFile.exists() && !dbFile.parentFile.mkdir()) {
            throw IOException("couldn't make databases directory")
        }
        DATABASE_PATH = dbFile.path

        var db: SQLiteDatabase? = null
        try{
            db = SQLiteDatabase.openDatabase(DATABASE_PATH, null, SQLiteDatabase.OPEN_READONLY)
        }catch (ex: SQLException){
            println("checkDatabase SQLException : ${ex.message}")
            Log.e("checkDatabase SQLExcept", ex.message)
        } finally {
            db?.close()
        }
        return if (db == null) false else true
    }

    //Create new database dengan cara import file db via stream
    @Throws(IOException::class)
    private fun importDatabase(){
        //1. Buka file db
        val inputStream = context.assets.open(DATABASE_NAME)

        //2. Siapkan path untuk menyimpan database hasil import
        val outputFilePath = DATABASE_PATH

        //3. Write file
        val outputStream = FileOutputStream(outputFilePath)

        val buffer = 1024
        inputStream.use { input ->
            input.copyTo(outputStream, buffer)
        }

        //Close all file connection
        outputStream.flush()
        outputStream.close()
        inputStream.close()

        println("Import database success !!")
    }

    //Fungsi membuat database dari import db
    @Throws(IOException::class)
    fun createDatabaseFromImportedFile(){
        if(checkDatabase()){
            //Database sudah ada
            println("Database sudah ada !!! ")
        } else{
            //Database belum ada
            try{
                importDatabase()
            } catch (x: IOException){
                println("createDatabaseDromImportedFile : ${x.message}")
            }
        }
    }

    //Fungsi untuk delete database
    fun deleteDatabase(){
        val file = File(DATABASE_PATH + DATABASE_NAME)
        if (file.exists()){
            file.delete()

            println("Old database successfully deleted !!!")
        }
    }


}