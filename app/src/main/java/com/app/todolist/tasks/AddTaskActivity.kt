package com.app.todolist.tasks

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.app.todolist.MainActivity
import com.app.todolist.R
import com.app.todolist.databinding.ActivityAddTaskBinding
import com.app.todolist.extra.TodoViewModel
import com.app.todolist.models.Notification
import com.app.todolist.models.TodoItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class AddTaskActivity : AppCompatActivity() {
    private lateinit var binding : ActivityAddTaskBinding

    private lateinit var selectedCategory : String
    private lateinit var selectedPriority : String
    private lateinit var selectedDate : String
    private lateinit var selectedTitle : String
    private var isCompleted : Boolean = false
    private lateinit var selectedDes : String
    private  var selectedID : Long = 0
    private var sH : String? = ""
    private var sM : String? = ""
    private lateinit var selectedTime : String
    private val job = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + job)
    private lateinit var todoViewModel: TodoViewModel

    @RequiresApi(Build.VERSION_CODES.O)
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
        selectedTitle = data.getStringExtra("title").toString()
        binding.title.setText(selectedTitle)

        selectedDes = data.getStringExtra("description").toString()
        binding.des.setText(selectedDes)

        selectedDate = data.getStringExtra("date").toString()
        selectedPriority = data.getStringExtra("priority").toString()
        selectedCategory = data.getStringExtra("category").toString()
        selectedID = data.getLongExtra("id",0)
        isCompleted = data.getBooleanExtra("isCompleted",false)
        selectedTime = data.getStringExtra("time").toString()


        sM = selectedTime.takeLast(2)
        Log.d("Last","min: $sM")

        binding.taskTimeText.text = selectedDate
        binding.categoryText.text = selectedCategory
        binding.priorityText.text = selectedPriority
        binding.taskHourText.text = selectedTime

        binding.taskHourLayout.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Main) {
                val currentTime = Calendar.getInstance()
                val hour = currentTime.get(Calendar.HOUR_OF_DAY)
                val minute = currentTime.get(Calendar.MINUTE)

                val timePickerDialog = TimePickerDialog(this@AddTaskActivity,
                    { _, selectedHour, selectedMinute ->
                        // Do something with the selected time
                        sH = selectedHour.toString()
                        sM = selectedMinute.toString()
                        selectedTime = "$sH:$sM"
                        Log.d("To-DO:","Time: $selectedTime")
                        binding.taskHourText.text = selectedTime

                    }, hour, minute, true)

                timePickerDialog.show()


            }
        }

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
                val todoItem = TodoItem(selectedID,selectedTitle,selectedDes,selectedDate,selectedTime,selectedCategory,selectedPriority,isCompleted)
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

               if (sM != null && !sM.equals("")){

                   val method = CreateListActivity()
                   val formatHour = method.formatHour(sH!!)
                   val formatMinute = method.formatHour(sM!!)

                   val dateString = "$selectedDate $formatHour:$formatMinute"
                   val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
                   val dateTime = LocalDateTime.parse(dateString, formatter)

                   val notification = Notification(selectedID.toInt(),selectedTitle,selectedDes, dateTime)
                   todoViewModel.insertNotification(notification)

                   val todoItem = TodoItem(selectedID,selectedTitle,selectedDes,selectedDate,"$formatHour:$sM",selectedCategory,selectedPriority,isCompleted)
                   todoViewModel.updateTodoItem(todoItem)

                   startActivity(Intent(this@AddTaskActivity, MainActivity::class.java))
                   finish()
                   Toast.makeText(this@AddTaskActivity,"Task Updated",Toast.LENGTH_SHORT).show()
               }
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