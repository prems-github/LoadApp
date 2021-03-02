package com.udacity

import android.app.DownloadManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat


class MainActivity : AppCompatActivity() {

    private var downloadID: Long = 0

    private lateinit var notificationManager: NotificationManager
    private lateinit var pendingIntent: PendingIntent
    private lateinit var action: NotificationCompat.Action

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       // setSupportActionBar(toolbar)

        /*registerReceiver(receiver, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))

        custom_button.setOnClickListener {
            download()
        }*/
    }

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
        }
    }

    fun onRadioButtonClicked(view: View) {

        when(view.id){
            R.id.glide-> Toast.makeText(this,"Glide is clicked",Toast.LENGTH_SHORT).show()
            R.id.load_app-> Toast.makeText(this,"LoadApp is clicked",Toast.LENGTH_SHORT).show()
            R.id.retrofit-> Toast.makeText(this,"Retrofit is clicked",Toast.LENGTH_SHORT).show()
        }
    }

    /* private fun download() {
         val request =
             DownloadManager.Request(Uri.parse(URL))
                 .setTitle(getString(R.string.app_name))
                 .setDescription(getString(R.string.app_description))
                 .setRequiresCharging(false)
                 .setAllowedOverMetered(true)
                 .setAllowedOverRoaming(true)

         val downloadManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
         downloadID =
             downloadManager.enqueue(request)// enqueue puts the download request in the queue.
     }

     companion object {
         private const val URL =
             "https://github.com/udacity/nd940-c3-advanced-android-programming-project-starter/archive/master.zip"
         private const val CHANNEL_ID = "channelId"
     }*/

}
