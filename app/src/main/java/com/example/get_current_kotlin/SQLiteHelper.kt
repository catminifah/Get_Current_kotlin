package com.example.get_current_kotlin
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Environment
import android.widget.Toast
import java.io.File
import java.io.FileWriter

class SQLiteHelper(context:Context):SQLiteOpenHelper(context,DATABASE_USER,null,DATABASE_VERSION) {
    companion object{
        private const val DATABASE_VERSION=1
        private const val DATABASE_USER="user.db"
        private const val TBL_USER="tbl_user"
        private const val id="id"
        private const val latitube="latitube"
        private const val longitube="longitube"
        private const val datetext="datetext"
        private const val timetext="timetext"

    }
    override fun onCreate(db: SQLiteDatabase?) {
        //val createTBLUSER=("CREATE TABLE "+ TBL_USER +"(" + Phone +" TEXT PRIMARY KEY,"+ ID +" TEXT,"+ Password +" TEXT,"+ Name +" TEXT," + BirthDate +" TEXT,"+ Email +" TEXT"+")")
        val createTBLUSER=("CREATE TABLE "+ TBL_USER +"(" + id +" INTEGER PRIMARY KEY AUTOINCREMENT,"+ latitube +" TEXT,"+ longitube +" TEXT,"+ datetext +" TEXT," + timetext +" TEXT"+")")
        db?.execSQL(createTBLUSER)
    }
    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TBL_USER")
        onCreate((db))
    }
    fun insertUser(user: UserModel): Long {
        val db=this.writableDatabase
        val contentValues=ContentValues()
        //ทำ auto แล้วไม่ต้องดึง id จาก user กด save ซ้ำๆอีก
        //contentValues.put(id,user.id)
        contentValues.put(latitube,user.latitube)
        contentValues.put(longitube,user.longitube)
        contentValues.put(datetext,user.datetext)
        contentValues.put(timetext,user.timetext)
        val success =db.insert(TBL_USER,null,contentValues)
        db.close()
        return success
    }
    @SuppressLint("Range")
    fun getAllUser():ArrayList<UserModel>{
        val userList : ArrayList<UserModel> = ArrayList()
        val selectQurey="SELECT * FROM $TBL_USER"
        val db=this.readableDatabase
        val  cursor:Cursor?
        try {
            cursor=db.rawQuery(selectQurey,null)
        }catch (e:java.lang.Exception){
            e.printStackTrace()
            db.execSQL(selectQurey)
            return ArrayList()
        }
        var id:Int
        var latitube:String
        var longitube:String
        var datetext:String
        var timetext:String
        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndex("id"))
                latitube = cursor.getString(cursor.getColumnIndex("latitube"))
                longitube = cursor.getString(cursor.getColumnIndex("longitube"))
                datetext = cursor.getString(cursor.getColumnIndex("datetext"))
                timetext = cursor.getString(cursor.getColumnIndex("timetext"))
                var us = UserModel(id = id, latitube = latitube, longitube = longitube,
                    datetext = datetext, timetext = timetext)
                userList.add(us)
            } while (cursor.moveToNext())
        }
        return userList
    }
    //check id
    fun checkID(id:Int):Boolean{
        val db=this.readableDatabase
        val selectQurey="select * from $TBL_USER where id = '$id'"
        val  cursor:Cursor?
        cursor=db.rawQuery(selectQurey,null)
        if (cursor.count<=0){
            cursor.close()
            return false
        }else{
            cursor.close()
            return true
        }
    }
    //get latitube
    @SuppressLint("Range")
    fun getlatitube(id:Int): String? {
        val db=this.readableDatabase
        val selectQurey="select * from $TBL_USER where id = '$id'"
        val  cursor:Cursor?
        cursor=db.rawQuery(selectQurey,null)
        var latitube:String=""
        if (cursor.count<=0){
            cursor.close()
            return ""
        }
        if (cursor.moveToFirst()) {
            do {
                latitube = cursor.getString(cursor.getColumnIndex("latitube"))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return latitube
    }
    @SuppressLint("Range")
    fun getlongitube(id:Int): String? {
        val db=this.readableDatabase
        val selectQurey="select * from $TBL_USER where id = '$id'"
        val  cursor:Cursor?
        cursor=db.rawQuery(selectQurey,null)
        var longitube:String=""
        if (cursor.count<=0){
            cursor.close()
            return ""
        }
        if (cursor.moveToFirst()) {
            do {
                longitube = cursor.getString(cursor.getColumnIndex("longitube"))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return longitube
    }
    @SuppressLint("Range")
    fun getDate(id:Int): String? {
        val db=this.readableDatabase
        val selectQurey="select * from $TBL_USER where id = '$id'"
        val  cursor:Cursor?
        cursor=db.rawQuery(selectQurey,null)
        var datetext:String=""
        if (cursor.count<=0){
            cursor.close()
            return ""
        }
        if (cursor.moveToFirst()) {
            do {
                datetext = cursor.getString(cursor.getColumnIndex("datetext"))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return datetext
    }
    @SuppressLint("Range")
    fun getTime(id:Int): String? {
        val db=this.readableDatabase
        val selectQurey="select * from $TBL_USER where id = '$id'"
        val  cursor:Cursor?
        cursor=db.rawQuery(selectQurey,null)
        var timetext:String=""
        if (cursor.count<=0){
            cursor.close()
            return ""
        }
        if (cursor.moveToFirst()) {
            do {
                timetext = cursor.getString(cursor.getColumnIndex("timetext"))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return timetext
    }

    @SuppressLint("Range")
    fun exporterCSV():FileWriter {
        var fileName = "sqlite.csv"
        var fileWriter = FileWriter(fileName)
        var id:Int
        var latitube:String
        var longitube:String
        var datetext:String
        var timetext:String
        val db=this.readableDatabase
        val selectQurey="select * from $TBL_USER"
        val cursor: Cursor?
        cursor=db.rawQuery(selectQurey,null)
        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndex("id"))
                latitube = cursor.getString(cursor.getColumnIndex("latitube"))
                longitube = cursor.getString(cursor.getColumnIndex("longitube"))
                datetext = cursor.getString(cursor.getColumnIndex("datetext"))
                timetext = cursor.getString(cursor.getColumnIndex("timetext"))
                fileWriter.append("$id")
                fileWriter.append(",")
                fileWriter.append("$latitube")
                fileWriter.append(",")
                fileWriter.append("$longitube")
                fileWriter.append(",")
                fileWriter.append("$datetext")
                fileWriter.append(",")
                fileWriter.append("$timetext")
                fileWriter.append("\n")
                //fileWriter.write("$id,$latitube,$longitube,$datetext,$timetext")
            } while (cursor.moveToNext())
            db.close()
            fileWriter.close()
        }
        return fileWriter
    }
//    https://androidexample365.com/import-and-export-csv-file-in-room-database-as-a-table-android-kotlin/
//    private fun exportCSV(){
//        val exportDir = File(Environment.getExternalStorageDirectory(), "/CSV")// your path where you want save your file
//        if (!exportDir.exists()) {
//            exportDir.mkdirs()
//        }
//        val file = File(exportDir, "$TBL_USER.csv")//$TABLE_NAME.csv is like user.csv or any name you want to save
//        try {
//            file.createNewFile()
//            val db=this.readableDatabase
//            val selectQurey="select * from $TBL_USER"
//            val csvWrite = CSVWriter(FileWriter(file))
//            val curCSV = db.rawQuery(selectQurey, null)// query for get all data of your database table
//            csvWrite.writeNext(curCSV.columnNames)
//            while (curCSV.moveToNext()) {
//                //Which column you want to export
//                val arrStr = arrayOfNulls<String>(curCSV.columnCount)
//                for (i in 0 until curCSV.columnCount - 1) {
//                    when (i) {
//                        20, 22 -> {
//                        }
//                        else -> arrStr[i] = curCSV.getString(i)
//                    }
//                }
//                csvWrite.writeNext(arrStr)
//            }
//            csvWrite.close()
//            curCSV.close()
//            showToast("Exported SuccessFully",this)
//        } catch (sqlEx: Exception) {
//            Timber.e(sqlEx)
//        }
//    }
}