package com.example.androidgalleryapp.fragment.list

import android.annotation.TargetApi
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.provider.MediaStore
import android.util.Size
import com.example.app_data.PhotoDao
import java.lang.Exception

class PhotoListPresenter : PhotoListContract.Presenter {

    @TargetApi(Build.VERSION_CODES.Q)
    override fun getList(context: Context): List<PhotoDao> {
        val list = arrayListOf<PhotoDao>()
        val contentResolver = context.contentResolver
        try {
            val cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    null, null, null, null)
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    val dao = PhotoDao().apply {
                        id = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media._ID))
                        path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
                        title = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME))
                        description = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DESCRIPTION))
                    }
                    var thumbnail: Bitmap
                    thumbnail = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        contentResolver.loadThumbnail(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, Size(96, 96), null)
                    } else {
                        val image = cursor.getLong(cursor.getColumnIndex("_ID"))
                        MediaStore.Images.Thumbnails.getThumbnail(contentResolver, image, MediaStore.Images.Thumbnails.MICRO_KIND, null)
                    }
                    dao.image = thumbnail
                    list.add(dao)
                } while (cursor.moveToFirst())
                cursor.close()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return list
    }
}