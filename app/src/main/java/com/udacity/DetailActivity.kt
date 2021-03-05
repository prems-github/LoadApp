package com.udacity

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        //cancels previous notification
        val notificationManager=getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancelAll()

        var fileText:TextView=findViewById(R.id.fileNameText)
        var statusText:TextView=findViewById(R.id.downloadStatusText)
        var okButton:Button=findViewById(R.id.okButton)

        val intent=getIntent()

        //retriving data sent by notification
        fileText.text=intent.getStringExtra("FILE_URL")!!
        statusText.text=intent.getStringExtra("STATUS")!!

        okButton.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }

    }

}
