package com.app.todolist.onboardingscreens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.todolist.MainActivity
import com.app.todolist.databinding.ActivityOnBoardTwoBinding
import com.app.todolist.extra.OnBoardMethods

class OnBoardTwo : AppCompatActivity() {
    private lateinit var binding : ActivityOnBoardTwoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardTwoBinding.inflate(layoutInflater)
        setContentView(binding.root)



        // click listener for "SKIP" text
        binding.textView.setOnClickListener {
            val onBoardMethods = OnBoardMethods()
            onBoardMethods.skip(this@OnBoardTwo)

            startActivity(Intent(this@OnBoardTwo, MainActivity::class.java))
            finishAffinity()
        }


        // click listener for "Next Button" send user to next OnBoard-Screen
        binding.nextButton.setOnClickListener {
            startActivity(Intent(this@OnBoardTwo, OnBoardThree::class.java))
        }

        // send user to back screen
        binding.backButton.setOnClickListener {
            finish()
        }


    }
}