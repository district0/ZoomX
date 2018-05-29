package com.zoomx.zoomx.util

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

/**
 * Created by Ibrahim AbdelGawad on 12/19/2017.
 */

class SharedPreferenceManager(private val context: Context) {
    private val editor: SharedPreferences.Editor
    private val appSharedPrefs: SharedPreferences

    init {
        this.appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
        this.editor = this.appSharedPrefs.edit()
    }

    fun getBoolean(key: String): Boolean {
        return this.getBoolean(key, false)
    }

    fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return this.appSharedPrefs.getBoolean(key, defaultValue)
    }

    fun getLong(key: String, defaultValue: Long): Long {
        return this.appSharedPrefs.getLong(key, defaultValue)
    }

    fun getLong(key: String): Long {
        return this.appSharedPrefs.getLong(key, 0L)
    }

    fun saveBoolean(key: String, value: Boolean) {
        this.editor.putBoolean(key, value).commit()
    }

    fun getString(key: String): String? {
        return this.getString(key, null as String?)
    }

    fun getString(key: String, defaultValue: String?): String? {
        return this.appSharedPrefs.getString(key, defaultValue)
    }

    fun saveString(key: String, value: String) {
        this.editor.putString(key, value).commit()
    }

    fun getInt(key: String, defaultVal: Int): Int {
        return this.appSharedPrefs.getInt(key, defaultVal)
    }

    fun saveInt(key: String, value: Int) {
        this.editor.putInt(key, value).commit()
    }

    operator fun contains(key: String): Boolean {
        return this.appSharedPrefs.contains(key)
    }

    fun removeEntry(key: String) {
        this.editor.remove(key).commit()
    }

    fun clearSharedPreferences() {
        this.appSharedPrefs.edit().clear().commit()
    }
}
