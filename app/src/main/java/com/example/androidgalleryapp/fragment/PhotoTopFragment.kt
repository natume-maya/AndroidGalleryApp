package com.example.androidgalleryapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.androidgalleryapp.BaseFragment
import com.example.androidgalleryapp.R
import com.example.androidgalleryapp.fragment.list.image.PhotoListImageFragment
import com.example.androidgalleryapp.fragment.list.video.PhotoListVideoFragment

class PhotoTopFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_top, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageButton = view.findViewById<Button>(R.id.image)
        imageButton.setOnClickListener {
            val fragment = PhotoListImageFragment()
            val transaction = this.fragmentManager!!.beginTransaction()
            transaction.replace(R.id.container, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        val videoButton = view.findViewById<Button>(R.id.video)
        videoButton.setOnClickListener {
            val fragment = PhotoListVideoFragment()
            val transaction = this.fragmentManager!!.beginTransaction()
            transaction.replace(R.id.container, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }
}