package com.example.get_current_kotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class covid19 : AppCompatActivity() {
    private lateinit var webcovid19: Button
    private lateinit var Tracking: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_covid19)
        webcovid19=findViewById<Button>(R.id.btnwebview)
        Tracking=findViewById<Button>(R.id.btnTracking)
        webcovid19.setOnClickListener{
            val Intent= Intent(this,WebViewPage::class.java)
            startActivity(Intent)
        }
        Tracking.setOnClickListener {
            val Intent= Intent(this,Trackingpage::class.java)
            startActivity(Intent)
        }
    }
}