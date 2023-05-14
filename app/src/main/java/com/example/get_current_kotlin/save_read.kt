package com.example.get_current_kotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import java.text.SimpleDateFormat
import java.util.*

class save_read : AppCompatActivity() {
    private lateinit var savetimeline: ImageButton
    private lateinit var readtimeline: ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_save_read)
        savetimeline=findViewById<ImageButton>(R.id.btn_save)
        readtimeline=findViewById<ImageButton>(R.id.btn_read)
        savetimeline.setOnClickListener{
            val Intent= Intent(this,SaveLocation::class.java)
            startActivity(Intent)
        }
        readtimeline.setOnClickListener{
            val Intent= Intent(this,ReadLocation::class.java)
            startActivity(Intent)
        }
    }
}