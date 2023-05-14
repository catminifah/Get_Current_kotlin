package com.example.get_current_kotlin

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.io.FileWriter

class ReadLocation : AppCompatActivity() {
    private lateinit var sqliteHelper:SQLiteHelper
    private lateinit var recyclerView: RecyclerView
    private var adapter : UserAdapler?=null
    private var id : Int? = null
    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read_location)

        button = findViewById<Button>(R.id.btngetCSV)

        sqliteHelper= SQLiteHelper(this)
        recyclerView=findViewById(R.id.listlocation)

        initRecyclerView()
        getUsers()

        button.setOnClickListener {
            exportCSV()
        }
    }
    private fun getUsers(){
        val userlist = sqliteHelper.getAllUser()
        Log.e("pppp","${userlist.size}")
        //Ok Now we need to display data in RecyclerView
        adapter?.addItems(userlist)
    }

    @SuppressLint("Range")
    private fun exportCSV() {
        val exportDir = File(
            Environment.getExternalStorageDirectory(),
            "/CSV"
        )// your path where you want save your file
        if (!exportDir.exists()) {
            exportDir.mkdirs()
        }
        val file = File(
            exportDir,
            "tbl_user.csv"
        )//$TABLE_NAME.csv is like user.csv or any name you want to save
        try {
            file.createNewFile()
            val db = sqliteHelper.readableDatabase
            val selectQurey = "select * from tbl_user"
            var iduser: Int
            var latitube: String
            var longitube: String
            var datetext: String
            var timetext: String
            //val csvWrite = CSVWriter(FileWriter(file))
            val csvWrite = FileWriter(file)
            val curCSV = db.rawQuery(selectQurey, null)// query for get all data of your database table
            if (curCSV.moveToFirst()) {
                do {
                    iduser = curCSV.getInt(curCSV.getColumnIndex("id"))
                    latitube = curCSV.getString(curCSV.getColumnIndex("latitube"))
                    longitube = curCSV.getString(curCSV.getColumnIndex("longitube"))
                    datetext = curCSV.getString(curCSV.getColumnIndex("datetext"))
                    timetext = curCSV.getString(curCSV.getColumnIndex("timetext"))
                    csvWrite.append("$iduser")
                    csvWrite.append(",")
                    csvWrite.append("$latitube")
                    csvWrite.append(",")
                    csvWrite.append("$longitube")
                    csvWrite.append(",")
                    csvWrite.append("$datetext")
                    csvWrite.append(",")
                    csvWrite.append("$timetext")
                    csvWrite.append("\n")
                    //csvWrite.write("$id,$latitube,$longitube,$datetext,$timetext")
                } while (curCSV.moveToNext())
                db.close()
                curCSV.close()
                csvWrite.close()
            }
        }catch (sqlEx: Exception){
            Toast.makeText(this,"File CSV failed writer", Toast.LENGTH_SHORT).show()
        }
    }
    private fun initRecyclerView(){
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = UserAdapler()
        recyclerView.adapter = adapter
        adapter!!.setItemClickListener(object :UserAdapler.onItemClickListener{
            override fun onItemClick(position: Int) {
                id=position+1
                //Toast.makeText(this@ReadLocation,"You Clicked on item on. $id", Toast.LENGTH_SHORT).show()
                val intent= Intent(this@ReadLocation,listview_timeline::class.java)
                intent.putExtra("id",id)
                startActivity(intent)
            }
        })
    }



}