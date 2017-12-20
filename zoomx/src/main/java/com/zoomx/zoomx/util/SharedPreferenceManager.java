package com.zoomx.zoomx.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Ibrahim AbdelGawad on 12/19/2017.
 */

public class SharedPreferenceManager {
    private Context context;
    private SharedPreferences.Editor editor;
    private SharedPreferences appSharedPrefs;

    public SharedPreferenceManager(Context context) {
        this.context = context;
        this.appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        this.editor = this.appSharedPrefs.edit();
    }

    public boolean getBoolean(String key) {
        return this.getBoolean(key, false);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return this.appSharedPrefs.getBoolean(key, defaultValue);
    }

    public long getLong(String key, long defaultValue) {
        return this.appSharedPrefs.getLong(key, defaultValue);
    }

    public long getLong(String key) {
        return this.appSharedPrefs.getLong(key, 0L);
    }

    public void saveBoolean(String key, boolean value) {
        this.editor.putBoolean(key, value).commit();
    }

    public String getString(String key) {
        return this.getString(key, (String) null);
    }

    public String getString(String key, String defaultValue) {
        return this.appSharedPrefs.getString(key, defaultValue);
    }

    public void saveString(String key, String value) {
        this.editor.putString(key, value).commit();
    }

    public int getInt(String key, int defaultVal) {
        return this.appSharedPrefs.getInt(key, defaultVal);
    }

    public void saveInt(String key, int value) {
        this.editor.putInt(key, value).commit();
    }

    public boolean contains(String key) {
        return this.appSharedPrefs.contains(key);
    }

    public void removeEntry(String key) {
        this.editor.remove(key).commit();
    }

    public void clearSharedPreferences() {
        this.appSharedPrefs.edit().clear().commit();
    }
}
