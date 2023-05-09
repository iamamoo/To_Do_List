package com.app.todolist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.app.todolist.adapter.ExpandableListAdapter
import com.app.todolist.databinding.ActivityHelpBinding
import com.app.todolist.models.ExpandableItem

class HelpActivity : AppCompatActivity() {
    private lateinit var  binding : ActivityHelpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHelpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.helpToolbar)
        supportActionBar?.title = "Help"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val list = ArrayList<ExpandableItem>()
        list.add(ExpandableItem("How do I create a new task in the app?  ",
            "To create a new task, simply click on the Create Task button and fill out the necessary details such" +
                    " as task name, description, due date, and priority level.",false))

        list.add(ExpandableItem("How do I set a reminder for a task?",
            "To set a reminder for a task, click on the Create Task Button and then click on the Floating Icon button. Select the date and time you want " +
                    "to be reminded of, and the app will send you a notification at the specified time.",false))

        list.add(ExpandableItem("How do I edit the existing task?",
            "You can edit the existing lists with the following steps: \n" +
                "1: Open to task list \n" +
                "2: Tap on the edit icon you want to edit. \n" +
                "3: Edit Task Screen Opens and you can easily edit your task.",false))


        list.add(ExpandableItem("How do I prioritize my tasks in the app?  ","You can prioritize your tasks by setting a priority level for each task, such as high, medium, or low. ",false))

        list.add(ExpandableItem("The phone cannot receive notifications and reminders?",
            "If you still cannot receive notifications and reminders, please go to the phone system settings and authorize to receive notifications from the app. \n" +
                    "\n" +
                    "( Phone Settings > Apps and Reminders > To-do List > Notifications > Show notifications ) \n" +
                    " 1. Click the \"Settings\" of the mobile phone system. \n" +
                    " 2. Click Apps and Reminders. \n" +
                    " 3. Click on this application. \n" +
                    " 4. Click Notifications. \n" +
                    " 5. Turn on Show notifications.",false))

        list.add(ExpandableItem("Other issues",
            "If you have any other questions or suggestions, you can click Feedback and send us an email via todolist@example.com, thank you!",false))


        val adapter = ExpandableListAdapter(list)
        binding.expandableListView.adapter = adapter

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.help_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.feeback -> {
                try {
                    val recipient = "support@todoapp.com"
                    val subject = "Feedback Subject"
                    val message = "Hi,\n\nI would like to share my feedback on To-Do List App.\n\n"

                    val intent = Intent(Intent.ACTION_SEND)
                    intent.type = "message/rfc822"
                    intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(recipient))
                    intent.putExtra(Intent.EXTRA_SUBJECT, subject)
                    intent.putExtra(Intent.EXTRA_TEXT, message)
                    startActivity(intent)
                } catch (e : Exception){
                    e.printStackTrace()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }



}