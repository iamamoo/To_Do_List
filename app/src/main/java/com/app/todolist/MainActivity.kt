package com.app.todolist

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.app.todolist.databinding.ActivityMainBinding
import com.app.todolist.extra.NotificationService
import com.app.todolist.tasks.CreateListActivity
import com.app.todolist.tasks.ViewListActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LOCKED
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN


        lifecycleScope.launch(Dispatchers.Main) {
            if (isServiceRunning(this@MainActivity)) {
                Log.d("tag", "Service is already running")
            } else {
                val intent = Intent(
                    this@MainActivity,
                    NotificationService::class.java
                )
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    ContextCompat.startForegroundService(this@MainActivity, intent)
                } else {
                    Log.d("tag", "This Service cannot run on this device")
                }
            }
        }


        // send user to help screen
        binding.help.setOnClickListener {
            startActivity(Intent(this@MainActivity, HelpActivity::class.java))
        }

        // send user to about screen
        binding.about.setOnClickListener {
            startActivity(Intent(this@MainActivity, AboutActivity::class.java))
        }


        // send user to the create list activity
        binding.createList.setOnClickListener {
            startActivity(Intent(this@MainActivity, CreateListActivity::class.java))
        }

        // send user to the view list activity
        binding.viewList.setOnClickListener {
            startActivity(Intent(this@MainActivity, ViewListActivity::class.java))
        }

        binding.settings.setOnClickListener {
            startActivity(Intent(this@MainActivity, SettingActivity::class.java))

        }
    }

    private fun isServiceRunning(context: Context): Boolean {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val runningServices = activityManager.getRunningServices(Int.MAX_VALUE)
        for (serviceInfo in runningServices) {
            if (NotificationService::class.java.name == serviceInfo.service.className) {
                return true
            }
        }
        return false
    }


}