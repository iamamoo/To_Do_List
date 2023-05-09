package com.app.todolist

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.app.todolist.databinding.ActivityMainBinding
import com.app.todolist.extra.NotificationReceiver
import com.app.todolist.extra.TodoViewModel
import com.app.todolist.models.TodoItem
import com.app.todolist.tasks.CreateListActivity
import com.app.todolist.tasks.ViewListActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var todoViewModel: TodoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        todoViewModel = ViewModelProvider(this)[TodoViewModel::class.java]

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LOCKED
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        lifecycleScope.launch(Dispatchers.Main) {
            val list : kotlin.collections.List<TodoItem> = todoViewModel.getAllTodoItems()
            if (list.isNotEmpty()){
                list.forEach {
                    setAlarm(it.date.toString(),it.title.toString(),it.description.toString())
                }
            }
            else {
                Log.d("To-Do:","List is empty")
            }
        }




        binding.help.setOnClickListener {
            startActivity(Intent(this@MainActivity,HelpActivity::class.java))

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

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun setAlarm(dateString: String, title : String, des : String) {
        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager

        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val date = dateFormat.parse(dateString)
        val calendar = Calendar.getInstance()
        calendar.time = date!!

        val intent = Intent(this, NotificationReceiver::class.java)
        intent.putExtra("title", title)
        intent.putExtra("description", des)
        this.sendBroadcast(intent)

        val pendingIntent = PendingIntent.getBroadcast(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            date.time,
            pendingIntent
        )

    }
}