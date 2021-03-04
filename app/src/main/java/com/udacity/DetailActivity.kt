package com.udacity

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        var fileText:TextView=findViewById(R.id.fileNameText)
        var statusText:TextView=findViewById(R.id.downloadStatusText)

        val intent=getIntent()

        fileText.text=intent.getStringExtra("FILE_URL")!!
        statusText.text=intent.getStringExtra("STATUS")!!

    }

}
