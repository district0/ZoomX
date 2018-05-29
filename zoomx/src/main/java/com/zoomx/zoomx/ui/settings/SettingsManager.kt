package com.zoomx.zoomx.ui.settings

import android.content.Context

import com.zoomx.zoomx.util.SharedPreferenceManager
import com.zoomx.zoomx.util.constants.PrefConstants

/**
 * Created by Ibrahim AbdelGawad on 12/19/2017.
 */

class SettingsManager private constructor() {
    private var sharedPreferenceManager: SharedPreferenceManager? = null

    val isNetworkTrackingEnabled: Boolean?
        get() = sharedPreferenceManager!!.getBoolean(PrefConstants.NETWORK_TRACKER_KEY, true)

    fun setNetworkTrackingStatus(trackingStatus: Boolean) {
        sharedPreferenceManager!!.saveBoolean(PrefConstants.NETWORK_TRACKER_KEY, trackingStatus)
    }

    companion object {

        private var settingsManager: SettingsManager? = null

        operator fun get(context: Context): SettingsManager {
            if (settingsManager == null) {
                settingsManager = SettingsManager()
            }
            settingsManager!!.sharedPreferenceManager = SharedPreferenceManager(context)
            return settingsManager
        }
    }
}

