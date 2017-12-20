package com.zoomx.zoomx.ui.settings;

import android.content.Context;

import com.zoomx.zoomx.util.SharedPreferenceManager;

import static com.zoomx.zoomx.ui.settings.SettingActivity.NETWORK_TRACKER_KEY;

/**
 * Created by Ibrahim AbdelGawad on 12/19/2017.
 */

public class SettingsManager {

    private static SettingsManager settingsManager;
    private SharedPreferenceManager sharedPreferenceManager;

    private SettingsManager() {

    }

    public static SettingsManager get(Context context) {
        if (settingsManager == null) {
            settingsManager = new SettingsManager();
        }
        settingsManager.sharedPreferenceManager = new SharedPreferenceManager(context);
        return settingsManager;
    }

    public Boolean isNetworkTrackingEnabled() {
        return sharedPreferenceManager.getBoolean(NETWORK_TRACKER_KEY, false);
    }

    public void setNetworkTrackingStatus(boolean trackingStatus) {
        sharedPreferenceManager.saveBoolean(NETWORK_TRACKER_KEY, trackingStatus);
    }
}
