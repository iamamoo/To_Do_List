package com.app.todolist

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.app.todolist.databinding.ActivityMainBinding
import com.app.todolist.extra.NotificationReceiver
import com.app.todolist.extra.NotificationService
import com.app.todolist.tasks.CreateListActivity
import com.app.todolist.tasks.ViewListActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LOCKED
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN



        // send user to help screen
        binding.help.setOnClickListener {
            startActivity(Intent(this@MainActivity,HelpActivity::class.java))
        }

        // send user to about screen
        binding.about.setOnClickListener {
            startActivity(Intent(this@MainActivity,AboutActivity::class.java))
        }


        // send user to the create list activity
        binding.createList.setOnClickListener {
            startActivity(Intent(this@MainActivity,CreateListActivity::class.java))
        }

        // send user to the view list activity
        binding.viewList.setOnClickListener {
            startActivity(Intent(this@MainActivity,ViewListActivity::class.java))
        }

        binding.settings.setOnClickListener {
            startActivity(Intent(this@MainActivity,SettingActivity::class.java))

        }

    }
}