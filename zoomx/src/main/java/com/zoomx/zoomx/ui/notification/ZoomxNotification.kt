package com.zoomx.zoomx.ui.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import com.zoomx.zoomx.R
import com.zoomx.zoomx.config.ZoomX
import com.zoomx.zoomx.model.RequestEntity
import com.zoomx.zoomx.ui.requestlist.RequestActivity
import com.zoomx.zoomx.ui.settings.SettingActivity

/**
 *
 * Created by Mohamed Ibrahim on 12/8/18.
 */
class ZoomxNotification(private val context: Context) {

    private val notificationBuilder: NotificationCompat.Builder
    private val notificationManager: NotificationManagerCompat = NotificationManagerCompat.from(context)
    private val buffer = Buffer()
    private var counter: Int = 0

    private class Buffer(list: MutableList<String> = mutableListOf()) : MutableList<String> by list {
        operator fun plusAssign(string: String) {
            add(string)
            if (size > BUFFER_SIZE) {
                removeAt(0)
            }

        }
    }


    init {
        val intent = Intent(context, RequestActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
        notificationBuilder = with(NotificationCompat.Builder(context, ZOOMX_NOTIFICATON_CHANNEL)) {
            setContentTitle("Zoomx")
            setSubText("Expand to see logs`")
            setSmallIcon(R.drawable.ic_swap_vert_black_24dp)
            priority = NotificationCompat.PRIORITY_DEFAULT
            setContentIntent(pendingIntent)
            addAction(getClearAction())
            addAction(getSettingsAction())
            setAutoCancel(false)
        }
    }


    fun show(requestEntity: RequestEntity) {

        val inboxStyle = NotificationCompat.InboxStyle()
        with(notificationManager) {
            val body = """
                ${requestEntity.method}||${requestEntity.code}
                --> ${Uri.parse(requestEntity.url).path}
            """.trimIndent()

            buffer += body
            counter++
            buffer.reversed().forEachIndexed { index, s ->
                inboxStyle.addLine(buffer[index])
                notificationBuilder.setStyle(inboxStyle)
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                notificationBuilder.setSubText(counter.toString())
            } else {
                notificationBuilder.setNumber(counter)
            }
            notify(ZOOMX_NOTIFICATION_ID, notificationBuilder.build())
        }
    }


    private fun getClearAction(): NotificationCompat.Action {
        val clearTitle = context.getString(R.string.clear_logs)
        val deleteIntent = Intent(context, ClearLogsIntentService::class.java)
        val intent = PendingIntent.getService(context, 11, deleteIntent, PendingIntent.FLAG_ONE_SHOT)
        return NotificationCompat.Action(R.drawable.ic_delete_black_24dp, clearTitle, intent)
    }

    private fun getSettingsAction(): NotificationCompat.Action {
        val clearTitle = context.getString(R.string.setting_screen_title)
        val settingsAction = Intent(context, SettingActivity::class.java)
        val intent = PendingIntent.getActivity(context, 0, settingsAction, 0)
        return NotificationCompat.Action(R.drawable.ic_settings_black_24dp, clearTitle, intent)
    }

    fun clear() {
        counter = 0
        ZoomX.getRequestDao().clearRequestsData()
        buffer.clear()
        notificationManager.cancel(ZOOMX_NOTIFICATION_ID)

    }


    companion object {
        private const val ZOOMX_NOTIFICATON_CHANNEL: String = "Zoomx_notification"
        private const val ZOOMX_NOTIFICATION_ID = 1023
        private const val BUFFER_SIZE = 5

        @JvmStatic
        fun createNotificationChannel(context: Context) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val name = context.getString(R.string.channel_name)
                val descriptionText = context.getString(R.string.channel_description)
                val importance = NotificationManager.IMPORTANCE_DEFAULT
                val channel = NotificationChannel(ZOOMX_NOTIFICATON_CHANNEL, name, importance).apply {
                    description = descriptionText
                }
                val notificationManager: NotificationManager =
                        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(channel)
            }
        }
    }

}