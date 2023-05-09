package com.app.todolist.tasks

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LOCKED


        setSupportActionBar(binding.viewListToolbar)
        supportActionBar?.title = "Task List"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        coroutineScope.launch {
            val list = todoViewModel.getAllToDoPriority()
            if (list.isNotEmpty()){
                val adapter = TodoAdapter(list,this@ViewListActivity,application,coroutineScope)
                binding.todoRecycler.adapter = adapter

                binding.notaskFound.visibility = View.GONE
            }else {
                binding.notaskFound.visibility = View.VISIBLE
            }
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