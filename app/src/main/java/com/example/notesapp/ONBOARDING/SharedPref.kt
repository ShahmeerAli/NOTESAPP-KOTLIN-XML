package com.example.notesapp.ONBOARDING

import android.content.Context
import android.content.SharedPreferences

class SharedPref (context: Context, savedname:String){

    private var sharedpre:SharedPreferences=context.getSharedPreferences(savedname,Context.MODE_PRIVATE)


    fun setState(key:Int){
        val ed:SharedPreferences.Editor=sharedpre.edit()
        ed.putInt("Key",key)
        ed.apply()
    }


    fun getState():Int{
        return sharedpre.getInt("Key",0)
    }




}