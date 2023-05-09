package com.app.todolist

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import com.app.todolist.onboardingscreens.OnBoardOne

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private lateinit var handler : Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LOCKED
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        supportActionBar?.hide()

        // only new user go to onBoardScreen
        // old users direct go to MainScreen
        val sharedPreferences = this.getSharedPreferences("newUser",Context.MODE_PRIVATE)
        val check = sharedPreferences.getBoolean("isUserNew",false)

        handler = Handler()
        handler.postDelayed({
            if (check){
                startActivity(Intent(this@SplashActivity,MainActivity::class.java))
                finish()
            }else {
                startActivity(Intent(this@SplashActivity,OnBoardOne::class.java))
                finish()
            }
        },2000)
    }
}