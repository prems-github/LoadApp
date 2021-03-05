package com.udacity

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat

private const val NOTIFICATION_ID = 0
private const val REQUEST_CODE = 0

fun NotificationManager.sendNotification(url: String, status: String, app: Context) {

    var notificationContent:String
    val contentIntent = Intent(app, DetailActivity::class.java)
    contentIntent.putExtra("FILE_URL", url)
    contentIntent.putExtra("STATUS", status)
    notificationContent =
        if(status=="Success") app.getString(R.string.notification_success_text) else app.getString(R.string.notification_fail_text)

    val contentPendingIntent = PendingIntent.getActivity(
        app,
        REQUEST_CODE,
        contentIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )

    val builder = NotificationCompat.Builder(
        app,
        app.getString(R.string.download_notification_channel_id)
    )
        .setSmallIcon(R.drawable.ic_cloud_done)
        .setContentTitle(app.getString(R.string.download_complete))
        .setContentText(notificationContent)
        .addAction(
            R.drawable.ic_cloud_done,
            app.getString(R.string.check_status_button_text),
            contentPendingIntent)



    notify(NOTIFICATION_ID, builder.build())

}