package com.example.app_data

import android.graphics.Bitmap
import androidx.room.Dao

@Dao
class PhotoDao {
    var image: Bitmap? = null
    var id: String = ""
    var path: String = ""
    var title: String = ""
    var description: String = ""
}