package com.udacity

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat

private const val NOTIFICATION_ID = 0
private const val REQUEST_CODE=0

fun NotificationManager.sendNotification(messageBody: String, applicationContext: Context) {

    val contentIntent= Intent(applicationContext,DetailActivity::class.java)
    val contentPendingIntent=PendingIntent.getActivity(
            applicationContext,
            NOTIFICATION_ID,
            contentIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
    )

    val builder = NotificationCompat.Builder(applicationContext,
            applicationContext.getString(R.string.download_notification_channel_id))
            .setSmallIcon(R.drawable.ic_cloud_done)
            .setContentTitle(applicationContext.getString(R.string.download_complete))
            .setContentText(messageBody)
            .setContentIntent(contentPendingIntent)
            .setAutoCancel(true)

    notify(NOTIFICATION_ID, builder.build())

}