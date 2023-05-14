package com.example.get_current_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView

class WebViewPage : AppCompatActivity() {
    private lateinit var myWeb: WebView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        myWeb=findViewById<WebView>( R.id.wb_webView)
        myWeb.apply {
            loadUrl("https://covid19.rajavithi.go.th/test/th_index.php")
            settings.javaScriptEnabled=true
        }

    }
}