package com.app.todolist.extra

import android.content.Context

class OnBoardMethods {
    fun skip(context: Context){
        val sharedPreferences = context.getSharedPreferences("newUser",Context.MODE_PRIVATE)
        with(sharedPreferences.edit()){
            putBoolean("isUserNew",true)
                .apply()
        }
    }

}