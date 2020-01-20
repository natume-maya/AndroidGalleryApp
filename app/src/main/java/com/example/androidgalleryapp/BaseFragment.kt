package com.example.androidgalleryapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.app_domain.domain.Request

class BaseFragment : Fragment() {

    private var resultOk = AppCompatActivity.RESULT_OK

    fun finishFragment(intent: Intent) {
        val base = activity
        if (base is BaseActivity) {
            base.onFragmentResult(getRequestCode(), getResult(), intent)
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

    protected fun getRequestCode(): Int {
        return Request.REQUEST_NONE.ordinal
    }

    protected fun setResult(result: Int) {
        resultOk = result
    }

    protected fun getResult(): Int {
        return resultOk
    }
}