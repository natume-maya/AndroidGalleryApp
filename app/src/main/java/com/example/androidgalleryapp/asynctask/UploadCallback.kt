package com.example.androidgalleryapp.asynctask

interface UploadCallback {
    fun onPreExecute()
    fun onProgressUpdate()
    fun onPostExecute(string: String)
    fun onCancelled()
}