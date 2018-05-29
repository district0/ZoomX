package com.zoomx.zoomx.util

import android.app.ActivityManager
import android.content.Context
import android.content.Context.ACTIVITY_SERVICE
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.telephony.TelephonyManager
import android.util.DisplayMetrics


object PhoneUtils {

    val deviceModel: String
        get() {
            val manufacturer = Build.MANUFACTURER
            val model = Build.MODEL
            return if (model.startsWith(manufacturer)) {
                model
            } else {
                "$manufacturer $model"
            }
        }

    val androidRelease: String
        get() = Build.VERSION.RELEASE

    val androidApi: Int
        get() = Build.VERSION.SDK_INT

    fun getAppVersion(context: Context): Int {
        var appVersion = 0
        try {
            appVersion = context.packageManager.getPackageInfo(context.packageName, 0).versionCode
        } catch (ex: Exception) {

        }

        return appVersion
    }

    fun getPackageName(context: Context): String {
        return context.applicationContext.packageName
    }

    fun getAppVersionName(context: Context): String {
        var appVersionName = ""
        try {
            appVersionName = context.packageManager.getPackageInfo(context.packageName, 0).versionName
        } catch (ex: Exception) {

        }

        return appVersionName
    }

    fun getPhoneSerial(context: Context): String {
        val mTelephonyMgr = context
                .getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return mTelephonyMgr.deviceId
    }

    fun getDensity(context: Context): String {
        val displayMetrics = context.resources.displayMetrics
        when (displayMetrics.densityDpi) {
            DisplayMetrics.DENSITY_LOW -> return "ldpi"
            DisplayMetrics.DENSITY_MEDIUM -> return "mdpi"
            DisplayMetrics.DENSITY_HIGH -> return "hdpi"
            DisplayMetrics.DENSITY_XHIGH -> return "xhdpi"
            DisplayMetrics.DENSITY_XXHIGH -> return "xxhdpi"
            DisplayMetrics.DENSITY_XXXHIGH -> return "xxxhdpi"
            DisplayMetrics.DENSITY_TV -> return "tvdpi"
            else -> return displayMetrics.densityDpi.toString()
        }
    }

    fun getDeviceResolution(context: Context): String {
        val displayMetrics = context.resources.displayMetrics
        val width = displayMetrics.widthPixels
        val height = displayMetrics.heightPixels
        return width.toString() + " X " + height
    }

    fun clearAppData(context: Context) {
        if (Build.VERSION_CODES.KITKAT <= Build.VERSION.SDK_INT) {
            val manager = context.getSystemService(ACTIVITY_SERVICE) as ActivityManager
            manager.clearApplicationUserData()
        } else {
            try {
                // clearing app data
                val runtime = Runtime.getRuntime()
                runtime.exec("pm clear YOUR_APP_PACKAGE_GOES HERE")

            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    fun uninstall(context: Context) {
        val packageURI = Uri.parse("package:" + context.packageName)
        val uninstallIntent = Intent(Intent.ACTION_DELETE, packageURI)
        context.startActivity(uninstallIntent)
    }
}
