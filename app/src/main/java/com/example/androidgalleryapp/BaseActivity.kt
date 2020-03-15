package com.example.androidgalleryapp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import com.example.androidgalleryapp.dialog.PhotoErrorDialogFragment
import com.example.androidgalleryapp.dialog.PhotoProgressDialogFragment

open class BaseActivity : AppCompatActivity() {

    private var progressDialog: PhotoProgressDialogFragment? = null

    fun startFragmentForResult(fragment: BaseFragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.commit()
    }

    fun checkPermission(permission: String, requestCode: Int) {
        val list = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
        val result = IntArray(PackageManager.PERMISSION_GRANTED)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED) {
                onRequestPermissionsResult(requestCode, list, result)
            } else {
                requestPermissions(list, requestCode)
            }
        } else {
            onRequestPermissionsResult(requestCode, list, result)
        }
    }

    open fun onFragmentResult(requestCode: Int, resultCode: Int, intent: Intent) {
    }

    fun showProgress() {
        progressDialog = PhotoProgressDialogFragment.getInstance()
        progressDialog!!.show(this)
    }

    fun dismissProgress() {
        if (progressDialog != null) {
            progressDialog!!.dismissAllowingStateLoss()
            progressDialog = null
        }
    }

    fun showError() {
        val errorDialog = PhotoErrorDialogFragment.getInstance()
        errorDialog.show(this)
    }
}