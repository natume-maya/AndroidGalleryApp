package com.example.androidgalleryapp.fragment.list.video

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidgalleryapp.BaseFragment
import com.example.androidgalleryapp.R
import com.example.androidgalleryapp.adapter.PhotoAdapter
import com.example.androidgalleryapp.fragment.list.PhotoListPresenter
import com.example.androidgalleryapp.fragment.list.image.PhotoListImageFragment
import com.example.app_data.PhotoDao
import com.example.app_domain.model.Request

class PhotoListVideoFragment : BaseFragment(){

    companion object {
        private val TAG = PhotoListImageFragment::class.simpleName
    }

    private var recyclerView: RecyclerView? = null

    val requestCode: Int
        get() = Request.REQUEST_PHOTO_LIST_VIDEO.ordinal

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        recyclerView = view.findViewById(R.id.list_view)
        recyclerView!!.layoutManager = LinearLayoutManager(context)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = object : PhotoAdapter(PhotoListPresenter().getImageList(activity!!)) {
            override fun onRecyclerViewClicked(photoDao: PhotoDao?) {
                val intent = Intent().apply {
                    putExtra("ID", photoDao!!.id)
                    putExtra("PATH", photoDao.path)
                    putExtra("FILE_NAME", photoDao.title)
                    putExtra("DESCRIPTION", photoDao.description)
                }
                finishFragment(intent)
            }
        }
        recyclerView!!.adapter = adapter
    }
}