package com.app.todolist.tasks

import android.app.AlertDialog
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.app.todolist.R
import com.app.todolist.databinding.ActivityAddTaskBinding
import com.app.todolist.extra.TodoViewModel
import com.app.todolist.models.TodoItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*

class AddTaskActivity : AppCompatActivity() {
    private lateinit var binding : ActivityAddTaskBinding
    private var selectedCategory : String = ""
    private var selectedPriority : String = ""
    private var selectedDate : Date = Date()
    private val job = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + job)
    private lateinit var todoViewModel: TodoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        todoViewModel = ViewModelProvider(this)[TodoViewModel::class.java]

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

                    selectedDate = calendar.time
                    binding.taskTimeText.text = selectedDate.time.toString()
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
                Toast.makeText(this@AddTaskActivity,"Task Deleted",Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        binding.saveButton.setOnClickListener {
            coroutineScope.launch {
                if (selectedCategory!= "" && selectedPriority!= ""){
                    val todoItem = TodoItem(id,title,des,selectedDate,selectedCategory,selectedPriority,isCompleted)
                    todoViewModel.updateTodoItem(todoItem)
                } else {
                  Toast.makeText(this@AddTaskActivity,"Task Updated",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}