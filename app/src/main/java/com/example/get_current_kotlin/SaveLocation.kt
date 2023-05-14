package com.example.get_current_kotlin

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.text.SimpleDateFormat
import java.util.*

class SaveLocation : AppCompatActivity() {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var latitube: TextView
    private lateinit var longitube: TextView
    private lateinit var datetext: TextView
    private lateinit var timetext: TextView
    private lateinit var button: Button
    private lateinit var savebutton: Button
    private lateinit var sqliteHelper:SQLiteHelper
    private  var check:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_save_location)
        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this)

        latitube = findViewById(R.id.latitude)
        longitube = findViewById(R.id.longitude)
        datetext = findViewById(R.id.datetext)
        timetext = findViewById(R.id.timetext)
        button = findViewById<Button>(R.id.btnlocation)
        savebutton = findViewById<Button>(R.id.btnsavelocation)
        var boolean:Boolean=false
        button.setOnClickListener {
            getLocation()
            getDate()
            getTime()
            boolean=true
        }
        sqliteHelper= SQLiteHelper(this)
        savebutton.setOnClickListener {
            if (boolean==true){
                addUser()
            }else{
                Toast.makeText(this,"please click button GET LOCATION", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addUser() {
        val latitubett=latitube.text.toString()
        val longitubett=longitube.text.toString()
        val datetexttt=datetext.text.toString()
        val timetexttt=timetext.text.toString()
        if (latitubett.isEmpty()||longitubett.isEmpty()||datetexttt.isEmpty()||timetexttt.isEmpty()){
            Toast.makeText(this,"field", Toast.LENGTH_SHORT).show()
        }else{
            val user=UserModel(latitube = latitubett, longitube = longitubett, datetext = datetexttt, timetext = timetexttt)
            val status=sqliteHelper.insertUser(user)
            //check insert
            if (status>-1){
                check=true
                Toast.makeText(this,"User Added...", Toast.LENGTH_SHORT).show()
                val intent= Intent(this,save_read::class.java)
                startActivity(intent)
                //clearEditText()
            }else{
                Toast.makeText(this,"field User Add", Toast.LENGTH_SHORT).show()
                check=false
            }
        }
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