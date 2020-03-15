package com.example.androidgalleryapp.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.example.androidgalleryapp.BaseActivity
import com.example.androidgalleryapp.R

class PhotoProgressDialogFragment : DialogFragment() {

    companion object {
        private val TAG = PhotoProgressDialogFragment::class.java.simpleName

        fun getInstance(): PhotoProgressDialogFragment {
            return PhotoProgressDialogFragment()
        }
    }

    private var listener: View.OnClickListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setCanceledOnTouchOutside(true)
        }
        setDialog(dialog)
        return dialog
    }

    private fun setDialog(dialog: Dialog) {
        dialog.setContentView(R.layout.dialog_progress)
        val button = dialog.findViewById<View>(R.id.dialog_progress_cancel)
        button.setOnClickListener {
            listener?.onClick(view)
            dismiss()
        }
    }

    fun show(activity: BaseActivity) {
        super.show(activity.supportFragmentManager, "progress")
    }

    fun setOnCancelListener(listener: View.OnClickListener) {
        this.listener = listener
    }
}