package com.example.androidgalleryapp.fragment

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.androidgalleryapp.BaseFragment
import com.example.androidgalleryapp.R
import com.example.androidgalleryapp.asynctask.UploadCallback
import com.example.androidgalleryapp.asynctask.UploadRequester

class PhotoDetailFragment : BaseFragment() {

    companion object {
        private val UPLOAD_SERVER = ""
    }

    private var id: String? = null
    private var path: String? = null
    private var title: String? = null
    private var description: String? = null

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
            val requester = UploadRequester(object : UploadCallback {
                override fun onPreExecute() {
                }

                override fun onProgressUpdate() {
                }

                override fun onPostExecute(string: String) {
                }

                override fun onCancelled() {
                }

            })
            requester.addFile(path!!, title!!)
            requester.execute(UPLOAD_SERVER)
        }
        val deleteButton = view.findViewById<Button>(R.id.delete)
        deleteButton.setOnClickListener {

        }
    }
}