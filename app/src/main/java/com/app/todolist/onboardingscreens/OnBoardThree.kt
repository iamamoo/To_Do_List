package com.app.todolist.onboardingscreens

import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.app.todolist.MainActivity
import com.app.todolist.databinding.ActivityOnBoardThreeBinding
import com.app.todolist.extra.OnBoardMethods

class OnBoardThree : AppCompatActivity() {
    private lateinit var binding : ActivityOnBoardThreeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardThreeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LOCKED

        supportActionBar?.hide()


        // click listener for "SKIP" text
        binding.textView.setOnClickListener {
            val onBoardMethods = OnBoardMethods()
            onBoardMethods.skip(this@OnBoardThree)

            startActivity(Intent(this@OnBoardThree, MainActivity::class.java))
            finish()
        }

        // click listener for "Next Button" send user to next OnBoard-Screen
        binding.nextButton.setOnClickListener {
            val onBoardMethods = OnBoardMethods()
            onBoardMethods.skip(this@OnBoardThree)

            startActivity(Intent(this@OnBoardThree, MainActivity::class.java))
            finishAffinity()
        }

        // send user to back screen
        binding.backButton.setOnClickListener {
            finish()
        }

    }
}