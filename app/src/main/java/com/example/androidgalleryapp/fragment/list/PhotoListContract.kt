package com.example.androidgalleryapp.fragment.list

import android.content.Context
import com.example.app_data.PhotoDao

class PhotoListContract {

    interface Presenter {
        fun getImageList(context: Context): List<PhotoDao>
        fun getVideoList(context: Context): List<PhotoDao>
    }
}