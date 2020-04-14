package com.example.androidgalleryapp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import com.example.androidgalleryapp.db.PhotoDBHelper
import com.example.androidgalleryapp.fragment.PhotoDetailFragment
import com.example.androidgalleryapp.fragment.list.PhotoListFragment
import com.example.app_domain.model.Request

class MainActivity : BaseActivity() {

    companion object {
        const val REQUEST_PERMISSION = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val photoDBHelper = PhotoDBHelper(this)
        photoDBHelper.writableDatabase

        checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE, REQUEST_PERMISSION)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val fragment = PhotoListFragment()
                startFragmentForResult(fragment)
            } else {
                finish()
            }
        }
    }

    override fun onFragmentResult(requestCode: Int, resultCode: Int, intent: Intent) {
        if (requestCode == Request.REQUEST_PHOTO_LIST.ordinal) {
            val fragment = PhotoDetailFragment()
            fragment.arguments = intent.extras
            startFragmentForResult(fragment)
        } else if (requestCode == Request.REQUEST_PHOTO_DETAIL.ordinal) {
            val fragment = PhotoListFragment()
            startFragmentForResult(fragment)
        }
    }
}
