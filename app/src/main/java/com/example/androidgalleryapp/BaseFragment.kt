package com.example.androidgalleryapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.app_domain.domain.Request

open class BaseFragment : Fragment() {

    private var resultOk = AppCompatActivity.RESULT_OK

    private val requestCode: Int
        get() = Request.REQUEST_NONE.ordinal

    fun finishFragment(intent: Intent) {
        val base = activity
        if (base is BaseActivity) {
            base.onFragmentResult(requestCode, resultOk, intent)
        }
    }

    protected fun showProgress() {
        val base = activity
        if (base is BaseActivity) {
            base.showProgress()
        }
    }

    protected fun dismissProgress() {
        val base = activity
        if (base is BaseActivity) {
            base.dismissProgress()
        }
    }

    protected fun showError() {
        val base = activity
        if (base is BaseActivity) {
            base.showError()
        }
    }
}