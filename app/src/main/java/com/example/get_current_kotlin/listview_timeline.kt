package com.example.get_current_kotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class listview_timeline : AppCompatActivity() {

    private lateinit var sqliteHelper:SQLiteHelper
//    private lateinit var ID: TextView
    private lateinit var latitube: TextView
    private lateinit var longitube: TextView
    private lateinit var datetext: TextView
    private lateinit var timetext: TextView
    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listview_timeline)
        sqliteHelper= SQLiteHelper(this)
        initView()
        var intent=intent
        var id : Int =intent.getIntExtra("id",0)
        var getlatitube=""
        var getlongitube=""
        var gettime=""
        var getdate=""
//        ID=findViewById<TextView>(R.id.inputid)
//        ID!!.text="ID : "+id.toString()
        val checkuser=sqliteHelper.checkID(id)
        if (checkuser==true){
            //Toast.makeText(this,"You can getID in SQLite",Toast.LENGTH_SHORT).show()
            getlatitube= sqliteHelper.getlatitube(id).toString()
            getlongitube=sqliteHelper.getlongitube(id).toString()
            gettime=sqliteHelper.getTime(id).toString()
            getdate=sqliteHelper.getDate(id).toString()
            latitube.text=getlatitube
            longitube.text=getlongitube
            datetext.text=getdate
            timetext.text=gettime
        }else{
            Toast.makeText(this,"You can't getID in SQLite",Toast.LENGTH_SHORT).show()
        }
        button.setOnClickListener {
            //ไม่รู้บัคหรืออะไรเอาออกได้แค่ทีละตัว
            getlatitube = getlatitube.replace("l","").replace("a","").replace("t","").replace("i","").replace("u","").replace("b","").replace("e","").replace(" ","").replace(":","").replace("d","")
            getlongitube = getlongitube.replace("l","").replace("o","").replace("n","").replace("g","").replace("i","").replace("t","").replace("u","").replace("d","").replace("e","").replace(" ","").replace(":","")
            //Toast.makeText(this,"latitube : $getlatitube and longitube : $getlongitube",Toast.LENGTH_SHORT).show()
            val intent= Intent(this,Get_Map_Time_line::class.java)
            intent.putExtra("getlatitube",getlatitube)
            intent.putExtra("getlongitube",getlongitube)
            startActivity(intent)
        }
    }

    private fun initView() {
        latitube=findViewById<TextView>(R.id.inputlatitube)
        longitube=findViewById<TextView>(R.id.inputlongitube)
        datetext=findViewById<TextView>(R.id.inputdate)
        timetext=findViewById<TextView>(R.id.inputtime)
        button=findViewById<Button>(R.id.btn_getmap)
    }
}