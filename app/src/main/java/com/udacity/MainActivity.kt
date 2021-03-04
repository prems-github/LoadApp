package com.udacity

import android.app.DownloadManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat

private lateinit var loadingButton: LoadingButton

class MainActivity : AppCompatActivity() {

    private var downloadID: Long = 0

    private lateinit var notificationManager: NotificationManager
    private lateinit var pendingIntent: PendingIntent
    private lateinit var action: NotificationCompat.Action

    private lateinit var downloadManager: DownloadManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadingButton = findViewById(R.id.custom_button)

        loadingButton.setOnClickListener {
            Log.d("Main Activity", "Click in main activity")
            download()
        }
        // setSupportActionBar(toolbar)

        /*registerReceiver(receiver, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))

        custom_button.setOnClickListener {
            download()
        }*/
        downloadManager=getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        registerReceiver(receiver, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))

    }

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
            val cursor=downloadManager.query(DownloadManager.Query().setFilterById(id!!))
            while (cursor.moveToNext()){
                val downloadStatus=cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
                Log.d("Download status","column no is $downloadStatus")
                Log.d("Download status code", "${DownloadManager.STATUS_SUCCESSFUL})")
                if(downloadStatus==DownloadManager.STATUS_RUNNING){
                    Log.d("Download status","Download is running")
                    Log.d("Download status code", "${DownloadManager.STATUS_SUCCESSFUL})")

                }else if (downloadStatus==DownloadManager.STATUS_SUCCESSFUL){
                    Log.d("Download status","Download is completed")
                    startActivity(Intent(DownloadManager.ACTION_VIEW_DOWNLOADS));
                }
            }

        }
    }

    fun onRadioButtonClicked(view: View) {

        when (view.id) {
            R.id.glide -> Toast.makeText(this, "Glide is clicked", Toast.LENGTH_SHORT).show()
            R.id.load_app -> Toast.makeText(this, "LoadApp is clicked", Toast.LENGTH_SHORT).show()
            R.id.retrofit -> Toast.makeText(this, "Retrofit is clicked", Toast.LENGTH_SHORT).show()
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
                        .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,"/repos/reopsitory.zip")
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

    companion object {
        private const val URL =
                "https://github.com/udacity/nd940-c3-advanced-android-programming-project-starter/archive/master.zip"
        private const val CHANNEL_ID = "channelId"
    }

}
