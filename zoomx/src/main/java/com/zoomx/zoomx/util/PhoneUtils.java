package com.zoomx.zoomx.util;

import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;


public class PhoneUtils {

    public static int getAppVersion(Context context) {
        int appVersion = 0;
        try {
            appVersion = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (Exception ex) {

        }
        return appVersion;
    }

    public static String getPackageName(Context context) {
        return context.getApplicationContext().getPackageName();
    }

    public static String getAppVersionName(Context context) {
        String appVersionName = "";
        try {
            appVersionName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (Exception ex) {

        }
        return appVersionName;
    }

    public static String getPhoneSerial(Context context) {
        TelephonyManager mTelephonyMgr = ((TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE));
        String imei = mTelephonyMgr.getDeviceId();
        return imei;
    }

    public static String getDeviceModel() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return model;
        } else {
            return manufacturer + " " + model;
        }
    }

    public static String getDensity(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        switch (displayMetrics.densityDpi) {
            case DisplayMetrics.DENSITY_LOW:
                return "ldpi";
            case DisplayMetrics.DENSITY_MEDIUM:
                return "mdpi";
            case DisplayMetrics.DENSITY_HIGH:
                return "hdpi";
            case DisplayMetrics.DENSITY_XHIGH:
                return "xhdpi";
            case DisplayMetrics.DENSITY_XXHIGH:
                return "xxhdpi";
            case DisplayMetrics.DENSITY_XXXHIGH:
                return "xxxhdpi";
            case DisplayMetrics.DENSITY_TV:
                return "tvdpi";
            default:
                return String.valueOf(displayMetrics.densityDpi);
        }
    }

    public static String getDeviceResolution(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        return width + " X " + height;
    }

    public static String getAndroidRelease() {
        return Build.VERSION.RELEASE;
    }

    public static int getAndroidApi() {
        return Build.VERSION.SDK_INT;
    }
}
