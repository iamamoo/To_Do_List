package com.app.todolist.tasks

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.app.todolist.databinding.ActivityTaskDetailBinding
import com.app.todolist.extra.TodoViewModel
import com.app.todolist.models.TodoItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class TaskDetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivityTaskDetailBinding
    private lateinit var todoViewModel: TodoViewModel
    private var selectedDate : Date = Date()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        todoViewModel = ViewModelProvider(this)[TodoViewModel::class.java]

        setSupportActionBar(binding.taskDetailToolbar)
        supportActionBar?.title = "Task Details"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val data = intent
        val title : String? = data.getStringExtra("title")
        binding.titleDetail.text = title

        val des : String? = data.getStringExtra("description")
        binding.desDetail.text = des

        val date = data.getStringExtra("date")
        val priority = data.getStringExtra("priority")
        val category = data.getStringExtra("category")
        val id = data.getLongExtra("id",0)
        val isCompleted : Boolean = data.getBooleanExtra("isCompleted",false)

        binding.taskTimeText.text = date
        binding.categoryText.text = category
        binding.priorityText.text = priority

        binding.deleteLayout.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Main) {
                val todoItem = TodoItem(id,title,des,selectedDate,category,priority,isCompleted)
                todoViewModel.deleteTodoItem(todoItem)
                Toast.makeText(this@TaskDetailActivity,"Task Deleted", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}