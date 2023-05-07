package com.app.todolist.tasks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.todolist.databinding.ActivityAddTaskBinding

class TaskDetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivityAddTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)








    }
}