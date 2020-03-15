package com.example.androidgalleryapp.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.androidgalleryapp.BaseActivity
import com.example.androidgalleryapp.R

class PhotoErrorDialogFragment : DialogFragment() {

    companion object {
        private val TAG = PhotoErrorDialogFragment::class.java.simpleName

        fun getInstance(): PhotoErrorDialogFragment {
            return PhotoErrorDialogFragment()
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity!!).apply {
            setTitle(R.string.error_dialog_title)
            setMessage(R.string.error)
            setPositiveButton(R.string.close) { dialog, which -> dismiss() }
        }
        this.isCancelable = false
        return builder.create()
    }

    fun show(activity: BaseActivity) {
        super.show(activity.supportFragmentManager, "error")
    }
}