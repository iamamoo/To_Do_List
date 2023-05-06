package com.app.todolist.tasks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.todolist.databinding.ActivityCreateListBinding

class CreateListActivity : AppCompatActivity() {
    private lateinit var binding : ActivityCreateListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "Create List"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)







    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}