package com.example.get_current_kotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton


class MainActivity : AppCompatActivity() {
    private lateinit var location:ImageButton
    private lateinit var showlocation:ImageButton
    private lateinit var timeline:ImageButton
    private lateinit var bcovid19:ImageButton
    private lateinit var Imgview:ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        location=findViewById<ImageButton>(R.id.btn_location)
        showlocation=findViewById<ImageButton>(R.id.btn_showlocation)
        timeline=findViewById<ImageButton>(R.id.btn_timeline)
        bcovid19=findViewById<ImageButton>(R.id.btn_covid19)
        Imgview=findViewById<ImageButton>(R.id.btnimageView)
        //button next page
        location.setOnClickListener{
            val Intent= Intent(this,GetLocation::class.java)
            startActivity(Intent)
        }
        showlocation.setOnClickListener{
            val Intent= Intent(this,current_location::class.java)
            startActivity(Intent)
        }
        timeline.setOnClickListener{
            val Intent= Intent(this,save_read::class.java)
            startActivity(Intent)
        }
        bcovid19.setOnClickListener{
            val Intent= Intent(this,covid19::class.java)
            startActivity(Intent)
        }
//        Imgview.setOnClickListener{
//            val Intent= Intent(this,Make_a_Weather::class.java)
//            startActivity(Intent)
//        }
    }
}