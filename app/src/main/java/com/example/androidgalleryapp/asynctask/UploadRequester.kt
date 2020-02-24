package com.example.androidgalleryapp.asynctask

import android.os.AsyncTask
import android.util.Log
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLConnection

class UploadRequester(
        private val callback: UploadCallback
) : AsyncTask<String, Void, String>() {

    companion object {
        private val TAG = UploadRequester::class.java.simpleName
        private const val LINE_FEED = "\n\r"
        private const val CHARSET = "UTF-8"
        private const val CONNECT_TIMEOUT = 5 * 1000
        private const val READ_TIMEOUT = 5 * 1000
    }

    private val headerList: Map<String, String> = HashMap()
    private val paramList: Map<String, String> = HashMap()
    private val fileList: MutableList<String> = mutableListOf()

    override fun onPreExecute() {
        callback.onPreExecute()
    }

    override fun onProgressUpdate(vararg values: Void) {
        callback.onProgressUpdate()
    }

    override fun doInBackground(vararg params: String): String {
        val response = StringBuilder()
        val host = params[0]
        var connection: HttpURLConnection? = null
        try {
            val boundary = "===" + System.currentTimeMillis() + "==="
            val url = URL(host)
            connection = url.openConnection() as HttpURLConnection
            connection.connectTimeout = CONNECT_TIMEOUT
            connection.readTimeout = READ_TIMEOUT
            connection.useCaches = false
            connection.doOutput = true
            connection.doInput = true
            connection.setRequestProperty("Content-Type", "multipart/form-data; boundary = $boundary")

            val os = connection.outputStream
            val osw = OutputStreamWriter(os, CHARSET)
            val writer = PrintWriter(osw, true)

            // ヘッダー追加
            for (header in headerList.entries) {
                writer.apply {
                    append(header.key)
                    append(": ")
                    append(header.value)
                    append(LINE_FEED)
                    flush()
                }
            }

            // パラメータ追加
            for (param in paramList.entries) {
                writer.apply {
                    append("--")
                    append(boundary)
                    append(LINE_FEED)
                    append("Content-Disposition: form-data; name = \"")
                    append(param.key)
                    append("\"")
                    append(LINE_FEED)
                    append("Content-Type: text/plain; charset = $CHARSET")
                    append(LINE_FEED)
                    append(LINE_FEED)
                    append(param.value)
                    append(LINE_FEED)
                    flush()
                }
            }

            // ファイル追加
            for (path in fileList) {
                val name = "uploadfile[]"
                val uploadFile = File(path)
                val fileName = uploadFile.name
                writer.apply {
                    append("--")
                    append(boundary)
                    append(LINE_FEED)
                    append("Content-Disposition: form-data; name = \"")
                    append(name)
                    append("\"; filename = \"")
                    append(fileName)
                    append("\"")
                    append(LINE_FEED)
                    append("Content-Type: ")
                    append(URLConnection.guessContentTypeFromName(fileName))
                    append(LINE_FEED)
                    append("Content-Transfer-Encoding: binary")
                    append(LINE_FEED)
                    append(LINE_FEED)
                    flush()
                }
                val inputStream = FileInputStream(uploadFile)
                val buffer = byteArrayOf(4096.toByte())
                val bytesRead = inputStream.read(buffer)
                while (bytesRead > -1) {
                    os.write(buffer, 0, bytesRead)
                }
                os.flush()
                writer.append(LINE_FEED)
                writer.flush()
            }
            writer.apply {
                append(LINE_FEED)
                append("--")
                append(boundary)
                append("--")
                append(LINE_FEED)
                flush()
            }
            val status = connection.responseCode
            if (status == HttpURLConnection.HTTP_OK) {
                val isr = InputStreamReader(connection.inputStream)
                val reader = BufferedReader(isr)
                val line = reader.readLine()
                while (line != null) {
                    response.append(line)
                }
            } else {
                throw IOException("Error: $status")
            }
        } catch (e: IOException) {
            Log.e(TAG, e.toString())
            cancel(true)
        } finally {
            connection?.disconnect()
        }
        return response.toString()
    }

    override fun onPostExecute(result: String) {
        callback.onPostExecute(result)
    }

    override fun onCancelled() {
        callback.onCancelled()
    }

    fun addFile(path: String, title: String) {
        fileList.add(path)
    }
}