package com.app.todolist.tasks

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.app.todolist.MainActivity
import com.app.todolist.R
import com.app.todolist.databinding.ActivityAddTaskBinding
import com.app.todolist.extra.TodoViewModel
import com.app.todolist.models.TodoItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class AddTaskActivity : AppCompatActivity() {
    private lateinit var binding : ActivityAddTaskBinding
    private var selectedCategory : String = ""
    private var selectedPriority : String = ""
    private var selectedDate : String = ""
    private var selectedTitle : String = ""
    private var selectedDes : String = ""
    private val job = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + job)
    private lateinit var todoViewModel: TodoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        todoViewModel = ViewModelProvider(this)[TodoViewModel::class.java]
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LOCKED


        setSupportActionBar(binding.addTaskToolbar)
        supportActionBar?.title = "Edit"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val data = intent
        val title : String? = data.getStringExtra("title")
        binding.title.setText(title)

        val des : String? = data.getStringExtra("description")
        binding.des.setText(des)

        val date = data.getStringExtra("date")
        val priority = data.getStringExtra("priority")
        val category = data.getStringExtra("category")
        val id = data.getLongExtra("id",0)
        val isCompleted : Boolean = data.getBooleanExtra("isCompleted",false)

        binding.taskTimeText.text = date
        binding.categoryText.text = category
        binding.priorityText.text = priority


        binding.setDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            // Create a DatePickerDialog
            val datePickerDialog = DatePickerDialog(
                this@AddTaskActivity,
                R.style.MyDatePickerDialogTheme,
                { _, year, month, dayOfMonth ->
                    calendar.set(Calendar.YEAR, year)
                    calendar.set(Calendar.MONTH, month)
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                    selectedDate = calendar.time.toString()
                    val inputDate = calendar.time.toString()
                    val inputFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US)
                    val outputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.US)

                    val unFormatted = inputFormat.parse(inputDate)
                    selectedDate = outputFormat.format(unFormatted!!)

                    binding.taskTimeText.text = selectedDate
                    Log.d("To-Do:", "Selected date and time: ${calendar.time}")
                },

                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            // Show the DatePickerDialog
            datePickerDialog.datePicker.minDate
            datePickerDialog.show()
        }

        binding.setCategory.setOnClickListener {
            coroutineScope.launch(Dispatchers.Main) {
                val builder = AlertDialog.Builder(this@AddTaskActivity)
                builder.setTitle("Select Task Category")
                val options = arrayOf("Grocery", "Work", "Sport", "Study","Music","Health", "Home","Other")
                builder.setItems(options) { _, which ->
                    val selectedOption = options[which]
                    // Do something with the selected option, like displaying it in a TextView
                    selectedCategory  = selectedOption
                    binding.categoryText.text = selectedCategory
                    Log.d("Selected Category:",selectedCategory)
                }
                // Create and show the dialog box
                val dialog = builder.create()
                dialog.show()
            }

        }

        binding.setPriority.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Main) {
                val builder = AlertDialog.Builder(this@AddTaskActivity)
                builder.setTitle("Select Task Priority")
                val options = arrayOf("High", "Medium", "Low")
                builder.setItems(options) { _, which ->
                    val selectedOption = options[which]

                    // Do something with the selected option, like displaying it in a TextView
                    selectedPriority  = selectedOption
                    binding.priorityText.text = selectedPriority
                    Log.d("Selected Priority:",selectedPriority)
                }

                // Create and show the dialog box
                val dialog = builder.create()
                dialog.show()
            }
        }


        binding.deleteLayout.setOnClickListener {
            coroutineScope.launch {
                val todoItem = TodoItem(id,title,des,selectedDate,category,priority,isCompleted)
                todoViewModel.deleteTodoItem(todoItem)

                startActivity(Intent(this@AddTaskActivity,MainActivity::class.java))
                finish()
                Toast.makeText(this@AddTaskActivity,"Task Deleted",Toast.LENGTH_SHORT).show()
            }
        }

        binding.saveButton.setOnClickListener {
           coroutineScope.launch {

               selectedTitle = binding.title.text.toString()
               selectedDes = binding.des.text.toString()

               if (selectedCategory != "" && selectedPriority != "" && selectedDate != "" && selectedTitle != "" && selectedDes != ""){
                   val todoItem = TodoItem(id, selectedTitle,selectedDes,selectedDate,selectedCategory,selectedPriority,isCompleted)
                   todoViewModel.updateTodoItem(todoItem)
                   startActivity(Intent(this@AddTaskActivity, MainActivity::class.java))
                   finish()
                   Toast.makeText(this@AddTaskActivity,"Task Updated",Toast.LENGTH_SHORT).show()
               } else {
                   Toast.makeText(this@AddTaskActivity,"Please update all the fields",Toast.LENGTH_SHORT).show()
               }
           }
        }
    }

    /*

     lifecycleScope.launch(Dispatchers.Main) {
                if (selectedCategory!= "" || selectedPriority!= "" || selectedDate != ""){
                    val todoItem = TodoItem(id,title,des,selectedDate,selectedCategory,selectedPriority,isCompleted)
                    todoViewModel.updateTodoItem(todoItem)
                } else {
                  Toast.makeText(this@AddTaskActivity,"Task Updated",Toast.LENGTH_SHORT).show()
                }
            }

     */

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}