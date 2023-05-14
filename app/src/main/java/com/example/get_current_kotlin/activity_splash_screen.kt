package com.example.get_current_kotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class activity_splash_screen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        val logo : ImageView =findViewById<ImageView>(R.id.spalshScreen_logo)

        logo.alpha= 0f
        logo.animate().setDuration(3000).alpha(1f).withEndAction{
            val showLogo = Intent(this,MainActivity::class.java)
            startActivity(showLogo)
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
            finish()
        }
    }
}