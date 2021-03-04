package com.udacity

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat

private const val NOTIFICATION_ID = 0

fun NotificationManager.sendNotification(messageBody: String, applicationContext: Context) {

    val builder = NotificationCompat.Builder(applicationContext,
            applicationContext.getString(R.string.download_notification_channel_id))
            .setSmallIcon(R.drawable.ic_cloud_done)
            .setContentTitle(applicationContext.getString(R.string.download_complete))
            .setContentText(messageBody)

    notify(NOTIFICATION_ID, builder.build())

}