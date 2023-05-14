package com.example.get_current_kotlin

import android.security.identity.AccessControlProfileId
import kotlin.random.Random
import java.util.*

data class UserModel(
    val id:Int = getAutoID(),
    val latitube: String? = null,
    val longitube: String? = null,
    val datetext: String? = null,
    val timetext: String? = null
){
    companion object{
        var id=0
        fun getAutoID():Int{
            id++
//            val random = Random()
//            return random.nextInt()
            return id
        }
    }
}