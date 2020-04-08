package com.example.androidgalleryapp.fragment.list

import android.content.Context
import com.example.app_data.PhotoDao

class PhotoListContract {

    interface Presenter {
        fun getList(context: Context): List<PhotoDao>
    }
}