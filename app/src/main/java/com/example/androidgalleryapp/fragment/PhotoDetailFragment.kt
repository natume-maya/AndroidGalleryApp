package com.example.androidgalleryapp.fragment

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.androidgalleryapp.BaseFragment
import com.example.androidgalleryapp.R
import com.example.androidgalleryapp.asynctask.UploadCallback
import com.example.androidgalleryapp.asynctask.UploadRequester
import com.example.androidgalleryapp.db.PhotoDBHelper
import com.example.app_data.ResultDao
import com.example.app_domain.model.AppChannelID
import com.example.app_domain.model.Request
import com.google.android.material.snackbar.Snackbar

class PhotoDetailFragment : BaseFragment() {

    companion object {
        private const val UPLOAD_SERVER = "http://160.16.88.242/photo_server/api/api_upload.php"
    }

    private var id: String? = null
    private var path: String? = null
    private var title: String? = null
    private var description: String? = null
    private val photoDBHelper: PhotoDBHelper = PhotoDBHelper(activity)

    val requestCode: Int
        get() = Request.REQUEST_PHOTO_DETAIL.ordinal

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        val view = inflater.inflate(R.layout.fragment_detail, container, false)
        val bundle = arguments
        if (bundle != null) {
            id = bundle.getString("ID", "")
            path = bundle.getString("PATH", "")
            title = bundle.getString("FILE_NAME", "")
            val fileNameText: TextView = view.findViewById(R.id.file_name) as TextView
            fileNameText.text = title
            val imageView: ImageView = view.findViewById(R.id.detail) as ImageView
            val bitmap = BitmapFactory.decodeFile(path)
            imageView.setImageBitmap(bitmap)
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val button: Button = view.findViewById(R.id.upload)
        button.setOnClickListener {
            showProgress()
            val requester = UploadRequester(object : UploadCallback {
                override fun onPreExecute() {
                }

                override fun onProgressUpdate() {
                }

                override fun onPostExecute(string: String) {
                    dismissProgress()

                    val resultDao = ResultDao(string)
                    if (resultDao.isResult) {
                        photoDBHelper.insertValues(id, path, title, description)

                        val intent = Intent()
                        showNotification(getString(R.string.upload_complete), "")
                        finishFragment(intent)
                    } else {
                        showError()
                    }
                }

                override fun onCancelled() {
                    dismissProgress()
                    showError()
                }

            })
            requester.addFile(path!!, title!!)
            requester.execute(UPLOAD_SERVER)
        }
        val deleteButton = view.findViewById<Button>(R.id.delete)
        deleteButton.setOnClickListener {
            val deleteNumber = photoDBHelper.deleteValues(id)
            if (deleteNumber <= 0) {
                showSnackbar(getString(R.string.delete_not_file))
            } else {
                showSnackbar(getString(R.string.delete_success))
            }
        }
    }

    private fun showSnackbar(message: String) {
        val view = view ?: return
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun showNotification(title: String, message: String) {
        val builder = NotificationCompat.Builder(activity!!, AppChannelID.UPLOAD_COMPLETE.channel).apply {
            setContentTitle(title)
            setContentText(message)
            setTicker(title)
        }.build()

        with(NotificationManagerCompat.from(activity!!)) {
            notify(0, builder)
        }
    }
}