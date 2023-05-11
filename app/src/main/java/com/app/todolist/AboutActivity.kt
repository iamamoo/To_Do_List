package com.app.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.todolist.databinding.ActivityAboutBinding

class AboutActivity : AppCompatActivity() {
    private lateinit var binding : ActivityAboutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding  = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.aboutToolbar)
        supportActionBar!!.title= "About"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val about = "A To-Do List app is a powerful tool that helps users stay organized and productive by allowing them to create lists of tasks that need to be completed. " +
                "With a To-Do List app, users can easily add new tasks, " +
                "set due dates and reminders, prioritize tasks, and mark them as complete. " +
                "The app can also provide additional features such as sub-tasks, notes, tags, " +
                "and categories to help users organize their tasks in a way that works best for them.\n" +
                "\n" +
                "One of the key benefits of using a To-Do List app is that it can help users stay focused and on track with their goals. " +
                "By breaking down larger tasks into smaller, manageable steps, users can avoid feeling overwhelmed and can track their progress over time. " +
                "The app can also help users prioritize their tasks based on urgency or importance, ensuring that they are making the most efficient use of their time.\n" +
                "\n" +
                "Another benefit of using a To-Do List app is that it can help users reduce stress and anxiety by providing a sense of control and accomplishment. " +
                "By creating and completing tasks on a daily basis, users can feel a sense of satisfaction and progress towards their goals. " +
                "The app can also help users free up mental space by keeping track of tasks for them, allowing them to focus on more important things.\n" +
                "\n" +
                "Overall, a To-Do List app is a valuable tool for anyone who wants to stay organized and productive. With a range of features and customization options, " +
                "users can tailor the app to their individual needs and preferences, and achieve their goals with ease.\n"

        binding.textView13.text = about

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}