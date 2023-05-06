package com.app.todolist.tasks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.todolist.databinding.ActivityViewListBinding

class ViewListActivity : AppCompatActivity() {
    private lateinit var binding : ActivityViewListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewListBinding.inflate(layoutInflater)
        setContentView(binding.root)




    }
}