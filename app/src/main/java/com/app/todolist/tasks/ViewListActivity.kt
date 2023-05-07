package com.app.todolist.tasks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.app.todolist.adapter.TodoAdapter
import com.app.todolist.databinding.ActivityViewListBinding
import com.app.todolist.extra.TodoViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ViewListActivity : AppCompatActivity() {
    private lateinit var binding : ActivityViewListBinding
    private val job = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + job)
    private lateinit var todoViewModel: TodoViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        todoViewModel = ViewModelProvider(this)[TodoViewModel::class.java]

        setSupportActionBar(binding.viewListToolbar)
        supportActionBar?.title = "To-Do List"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        coroutineScope.launch {
            val list = todoViewModel.getAllToDoPriority()
            val adapter = TodoAdapter(list,this@ViewListActivity)
            binding.todoRecycler.adapter = adapter
        }




    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}