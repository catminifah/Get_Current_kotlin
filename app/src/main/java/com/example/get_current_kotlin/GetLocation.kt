package com.example.get_current_kotlin

import android.app.Activity
import android.content.pm.PackageManager
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.text.SimpleDateFormat
import java.util.*

class GetLocation : AppCompatActivity() {
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var latitube:TextView
    private lateinit var longitube:TextView
    private lateinit var datetext:TextView
    private lateinit var timetext:TextView
    private lateinit var button:Button
    private lateinit var buttonclear:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_location)

        fusedLocationProviderClient=LocationServices.getFusedLocationProviderClient(this)
        latitube = findViewById(R.id.latitude)
        longitube = findViewById(R.id.longitude)
        datetext = findViewById(R.id.datetext)
        timetext = findViewById(R.id.timetext)
        button = findViewById<Button>(R.id.btnlocation)
        buttonclear = findViewById<Button>(R.id.btnclearlocation)
        button.setOnClickListener {
            getLocation()
            getDate()
            getTime()
        }
        buttonclear.setOnClickListener {
            getClearLocation()
        }
    }

    private fun getClearLocation() {
        val textLatitude = "latitude : "
        val textLongitude = "longitude : "
        val textDate = "date : "
        val textTime = "time : "
        latitube.text=textLatitude
        longitube.text=textLongitude
        datetext.text=textDate
        timetext.text=textTime
    }

    private fun getLocation() {
        //check location permiss
        if (ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED &&
        ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_COARSE_LOCATION)
        != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
            arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),100)
            return
        }
        //get latitude and longitude
        val location = fusedLocationProviderClient.lastLocation
        location.addOnSuccessListener {
            if (it!=null){
                val textLatitude = "latitude : "+it.latitude.toString()
                val textLongitude = "longitude : "+it.longitude.toString()
                latitube.text=textLatitude
                longitube.text=textLongitude
            }
        }
    }
    private fun getDate(){
        val formatter = SimpleDateFormat("dd-MM-yyyy")
        val date = Date()
        val current = formatter.format(date)
        datetext.text="date : "+current.toString()
    }
    private fun getTime(){
        val time = Calendar.getInstance().time
        val formattertime = SimpleDateFormat("HH:mm")
        val currenttime = formattertime.format(time)
        timetext.text="time : "+currenttime.toString()
    }

}