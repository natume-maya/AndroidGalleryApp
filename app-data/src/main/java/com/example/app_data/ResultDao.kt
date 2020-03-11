package com.example.app_data

import android.util.Log
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class ResultDao(
        text: String
) {

    companion object {
        private val TAG = ResultDao::class.java.simpleName
    }

    init {
        load(text)
    }

    var isResult = false
    private val messageList: MutableList<String> = ArrayList()

    fun addMessage(message: String) {
        messageList.add(message)
    }

    fun load(text: String) {
        try {
            val jsonObject = JSONObject(text)
            isResult = jsonObject.getBoolean("result")
            val type = jsonObject["message"]
            if (type is JSONArray) {
                val list = jsonObject.getJSONArray("message")
                for (cnt in 0 until list.length()) {
                    addMessage(list.getString(cnt))
                }
            } else {
                addMessage(jsonObject.getString("message"))
            }
        } catch (e: JSONException) {
            Log.d(TAG, "エラー: $e")
        }
    }

    init {
        load(text)
    }
}