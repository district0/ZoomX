package com.zoomx.zoomx.ui.notification

import android.app.IntentService
import android.content.Intent

/**
 *
 * Created by Mohamed Ibrahim on 12/9/18.
 */
class ClearLogsIntentService(private val name: String = "ClearLogsIntentService") : IntentService(name) {

    override fun onHandleIntent(intent: Intent?) {
        ZoomxNotification(this).clear()
    }
}