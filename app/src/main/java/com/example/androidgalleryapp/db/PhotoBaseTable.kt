package com.example.androidgalleryapp.db

import android.provider.BaseColumns

class PhotoBaseTable {

    object Photo : BaseColumns {
        const val TABLE_NAME = "photo_table"
        const val COLUMN_ID = "id"
        const val COLUMN_FILE_NAME = "file_name"
        const val COLUMN_PATH = "path"
        const val COLUMN_DESCRIPTION = "description"
    }

}