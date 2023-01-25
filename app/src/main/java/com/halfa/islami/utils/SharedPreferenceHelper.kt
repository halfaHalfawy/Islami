package com.halfa.islami.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import java.util.Objects

object SharedPreferenceHelper {
    private lateinit var sharedPreferences: SharedPreferences
    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    }

    private val gson = Gson()

    fun putString(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun deleteString(key: String) {
        sharedPreferences.edit().remove(key).apply()

    }

    public fun putObject(key: String, objecto: Any) {

        val serial = gson.toJson(objecto)

        putString(key, serial)
    }

    fun <T : Any> getObject(key: String, classType: Class<T>): Any? {
        val des = getString(key)
        if (des != null)
            return gson.fromJson(des, classType)
        return null
    }

    fun getString(key: String): String? {
        return sharedPreferences.getString(key, null)
    }

}
