package com.udacity

import android.app.DownloadManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat


class MainActivity : AppCompatActivity() {

    private lateinit var loadingButton: LoadingButton
    private var downloadID: Long = 0

    private lateinit var notificationManager: NotificationManager
    private lateinit var pendingIntent: PendingIntent
    private lateinit var action: NotificationCompat.Action

    private lateinit var URL: String
    private lateinit var radioGroup: RadioGroup
    private lateinit var downloadManager: DownloadManager
    private var isChoiceSelected: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadingButton = findViewById(R.id.custom_button)
        radioGroup = findViewById(R.id.radio_group)

        loadingButton.setOnClickListener {
            if (isChoiceSelected) {
                isChoiceSelected = false
                download()
            } else {
                Toast.makeText(this, R.string.select_text, Toast.LENGTH_SHORT).show()
            }
        }
        downloadManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        registerReceiver(receiver, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))

        createChannel(getString(R.string.download_notification_channel_id),getString(R.string.download_notification_channel_name))
  }

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            var status = ""
            val id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
            val cursor = downloadManager.query(DownloadManager.Query().setFilterById(id!!))
            while (cursor.moveToNext()) {
                val downloadStatus = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
                status = if (downloadStatus == DownloadManager.STATUS_SUCCESSFUL) "Success" else "Fail"

                loadingButton.endDownload()
                radioGroup.clearCheck()
                notification(URL, status)
                //startActivity(Intent(DownloadManager.ACTION_VIEW_DOWNLOADS));
            }
        }
    }

    fun notification(url: String, status: String) {

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.sendNotification(URL, status, applicationContext)
    }

    fun createChannel(channelId: String, channelName: String) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                    channelId,
                    channelName,
                    NotificationManager.IMPORTANCE_HIGH
            )

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    fun onRadioButtonClicked(view: View) {
        isChoiceSelected = true
        URL = when (view.id) {
            R.id.glide -> "https://github.com/bumptech/glide"
            R.id.load_app -> "https://github.com/udacity/nd940-c3-advanced-android-programming-project-starter"
            else -> "https://github.com/square/retrofit"
        }
    }

    private fun download() {
        val request =
                DownloadManager.Request(Uri.parse(URL))
                        .setTitle(getString(R.string.app_name))
                        .setDescription(getString(R.string.app_description))
                        .setRequiresCharging(false)
                        .setAllowedOverMetered(true)
                        .setAllowedOverRoaming(true)
                        .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/repos/reopsitory.zip")
                        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)

        //val downloadManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        try {
            downloadID =
                    downloadManager.enqueue(request)// enqueue puts the download request in the queue.
            loadingButton.startDownload()

            Log.d("Download", "$downloadID")

        } catch (e: Exception) {
            Log.d("Exception", "$e")
        }
    }
}
