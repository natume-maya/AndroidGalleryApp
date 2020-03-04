package com.example.androidgalleryapp.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.androidgalleryapp.db.PhotoBaseTable.Photo.TABLE_NAME
import com.example.androidgalleryapp.db.PhotoBaseTable.Photo.COLUMN_ID
import com.example.androidgalleryapp.db.PhotoBaseTable.Photo.COLUMN_FILE_NAME
import com.example.androidgalleryapp.db.PhotoBaseTable.Photo.COLUMN_PATH
import com.example.androidgalleryapp.db.PhotoBaseTable.Photo.COLUMN_DESCRIPTION

class PhotoDBHelper(
        private val context: Context
) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object {
        private val TAG = PhotoDBHelper::class.java.simpleName
        const val DB_NAME = "TblPhoto.db"
        const val DB_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase) {
        createTable(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        dropTable(db)

        createTable(db)
    }

    fun insertValues(id: String, path: String, title: String, description: String) : Long {
        val db = writableDatabase
        val contentValue = ContentValues().apply {
            put(COLUMN_ID, id)
            put(COLUMN_PATH, path)
            put(COLUMN_FILE_NAME, title)
            put(COLUMN_DESCRIPTION, description)
        }
        val registerNumber = db.insert(TABLE_NAME, null, contentValue)
        db.close()
        return registerNumber
    }

    fun deleteValues(id: String): Int {
        val db = writableDatabase
        val contentValues = ContentValues().put(COLUMN_ID, id)
        val deleteNumber = db.delete(TABLE_NAME, "$COLUMN_ID = ?", arrayOf(id))
        db.close()
        return deleteNumber
    }

    private fun createTable(db: SQLiteDatabase) {
        val sql = "create table " + TABLE_NAME + " (" +
                COLUMN_ID + "integer primary key autoincrement, " +
                COLUMN_FILE_NAME + " text, " +
                COLUMN_PATH + " text, " +
                COLUMN_DESCRIPTION + " text)"
        db.execSQL(sql)
    }

    private fun dropTable(db: SQLiteDatabase) {
        val sql = "drop table if exists $TABLE_NAME"
        db.execSQL(sql)
    }
}