package com.arch.comm

import android.content.Context
import android.content.SharedPreferences

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPreferencesStorage @Inject constructor(context: Context) {
    private val pref: SharedPreferences = context.applicationContext.getSharedPreferences(
        BuildConfig.SHARED_NAME,
        Context.MODE_PRIVATE
    )
    private val editor: SharedPreferences.Editor = pref.edit()

    fun saveDataString(key: String, value: String) {
        editor.putString(key, value)
        editor.commit()
    }

    fun saveDataBoolean(key: String, value: Boolean) {
        editor.putBoolean(key, value)
        editor.commit()
    }

    fun saveDataLong(key: String, value: Long) {
        editor.putLong(key, value)
        editor.commit()
    }

    fun loadDataBoolean(key: String): Boolean {
        return pref.getBoolean(key, false)
    }

    fun loadDataString(key: String): String {
        return pref.getString(key, "") ?: ""
    }

    fun loadDataLong(key: String): Long {
        return pref.getLong(key, -1)
    }

    fun clearAll() {
        pref.edit().clear().apply()
    }
}