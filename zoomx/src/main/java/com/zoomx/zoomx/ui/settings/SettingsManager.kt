package com.zoomx.zoomx.ui.settings

import android.content.Context
import com.zoomx.zoomx.ui.ZoomxUIOption
import com.zoomx.zoomx.util.SharedPreferenceManager
import com.zoomx.zoomx.util.constants.PrefConstants

/**
 * Created by Ibrahim AbdelGawad on 12/19/2017.
 */

class SettingsManager(private val sharedPreferenceManager: SharedPreferenceManager) {


    val isNetworkTrackingEnabled: Boolean?
        get() = sharedPreferenceManager.getBoolean(PrefConstants.NETWORK_TRACKER_KEY, true)

    var zoomxUIOption: Int
        @ZoomxUIOption
        get() = sharedPreferenceManager.getInt(PrefConstants.ZOOMX_UI_OPTION_KEY, ZoomxUIOption.DRAW_OVER_APPS)
        set(@ZoomxUIOption value) {
            sharedPreferenceManager.saveInt(PrefConstants.ZOOMX_UI_OPTION_KEY, value)
        }

    fun setNetworkTrackingStatus(trackingStatus: Boolean) {
        sharedPreferenceManager.saveBoolean(PrefConstants.NETWORK_TRACKER_KEY, trackingStatus)
    }

    fun saveZoomxUIOption(@ZoomxUIOption option: Int) {
        sharedPreferenceManager.saveInt(PrefConstants.ZOOMX_UI_OPTION_KEY, option)
    }

    companion object {
        @JvmStatic
        operator fun get(context: Context): SettingsManager {
            return SettingsManager(SharedPreferenceManager(context))
        }
    }
}

