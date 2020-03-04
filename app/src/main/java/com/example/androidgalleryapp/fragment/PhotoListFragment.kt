package com.example.androidgalleryapp.fragment

import android.annotation.TargetApi
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.util.Size
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidgalleryapp.BaseFragment
import com.example.androidgalleryapp.R
import com.example.app_data.PhotoDao
import com.example.app_domain.domain.Request
import java.lang.Exception

class PhotoListFragment : BaseFragment() {

    companion object {
        private val TAG = PhotoListFragment::class.simpleName
    }

    private var recyclerView: RecyclerView? = null

    val requestCode: Int
        get() = Request.REQUEST_PHOTO_LIST.ordinal

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        recyclerView = view.findViewById(R.id.list_view)
        recyclerView!!.layoutManager = LinearLayoutManager(context)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val photoDao = getList()


    }

    @TargetApi(Build.VERSION_CODES.Q)
    private fun getList(): List<PhotoDao> {
        val list = arrayListOf<PhotoDao>()
        val contentResolver = activity!!.contentResolver
        try {
            val cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    null, null, null, null)
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    val dao = PhotoDao()
                    dao.id = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media._ID))
                    dao.path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
                    dao.title = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.TITLE))
                    dao.description = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DESCRIPTION))
                    var thumbnail: Bitmap
                    thumbnail = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        contentResolver.loadThumbnail(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, Size(96, 96), null)
                    } else {
                        val image = cursor.getLong(cursor.getColumnIndex("_id"))
                        MediaStore.Images.Thumbnails.getThumbnail(contentResolver, image, MediaStore.Images.Thumbnails.MICRO_KIND, null)
                    }
                    dao.image = thumbnail
                    list.add(dao)
                } while (cursor.moveToFirst())
                cursor.close()
            }
        } catch (e: Exception) {
            Log.d(TAG, "例外 = $e")
        }
        return list
    }
}