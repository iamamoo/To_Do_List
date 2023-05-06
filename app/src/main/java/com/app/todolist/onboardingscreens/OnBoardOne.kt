package com.app.todolist.onboardingscreens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.app.todolist.MainActivity
import com.app.todolist.databinding.ActivityOnBoardOneBinding
import com.app.todolist.extra.OnBoardMethods

class OnBoardOne : AppCompatActivity() {
    private lateinit var binding : ActivityOnBoardOneBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardOneBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        supportActionBar?.hide()


        // click listener for "SKIP" text
        binding.textView.setOnClickListener {
            val onBoardMethods = OnBoardMethods()
            onBoardMethods.skip(this@OnBoardOne)

            startActivity(Intent(this@OnBoardOne,MainActivity::class.java))
            finishAffinity()
        }


        // click listener for "Next Button" send user to next OnBoard-Screen
        binding.nextButton.setOnClickListener {
            startActivity(Intent(this@OnBoardOne,OnBoardTwo::class.java))
        }

    }
}