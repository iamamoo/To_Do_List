package com.app.todolist.tasks

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.app.todolist.MainActivity
import com.app.todolist.databinding.ActivityTaskDetailBinding
import com.app.todolist.extra.TodoViewModel
import com.app.todolist.models.Notification
import com.app.todolist.models.TodoItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class TaskDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTaskDetailBinding
    private lateinit var todoViewModel: TodoViewModel
    private var sH: String? = ""
    private var sM: String? = ""
    private var selectedDate: String? = null


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        todoViewModel = ViewModelProvider(this)[TodoViewModel::class.java]
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LOCKED


        setSupportActionBar(binding.taskDetailToolbar)
        supportActionBar?.title = "Task Details"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val data = intent
        val title: String? = data.getStringExtra("title")
        binding.titleDetail.text = title

        val des: String? = data.getStringExtra("description")
        binding.desDetail.text = des

        val priority = data.getStringExtra("priority")
        val category = data.getStringExtra("category")
        val id = data.getLongExtra("id", 0)
        val isCompleted: Boolean = data.getBooleanExtra("isCompleted", false)
        val date = data.getStringExtra("date")
        val time = data.getStringExtra("time")


        sH = time!!.take(2)
        sM = time.takeLast(2)


        binding.categoryText.text = category
        binding.priorityText.text = priority
        binding.taskTimeText.text = date.toString()
        binding.taskHourText.text = time.toString()

        binding.deleteLayout.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Main) {
                val todoItem = TodoItem(
                    id,
                    title,
                    des,
                    selectedDate.toString(),
                    "$sH:$sM",
                    category,
                    priority,
                    isCompleted
                )
                todoViewModel.deleteTodoItem(todoItem)

                lifecycleScope.launch {
                    val method = CreateListActivity()
                    val formatHour = method.formatHour(sH!!)
                    val formatMinute = method.formatHour(sM!!)
                    val dateString = "$date $formatHour:$formatMinute"
                    val f1 = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")

                    val dateTime = LocalDateTime.parse(dateString, f1)
                    val notification =
                        Notification(id.toInt(), title.toString(), des.toString(), dateTime)
                    todoViewModel.deleteNotification(notification)
                }

                startActivity(Intent(this@TaskDetailActivity, MainActivity::class.java))
                finish()
                Toast.makeText(this@TaskDetailActivity, "Task Deleted", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}