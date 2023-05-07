package com.app.todolist.tasks

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.app.todolist.R
import com.app.todolist.databinding.ActivityCreateListBinding
import com.app.todolist.extra.TodoViewModel
import com.app.todolist.models.TodoItem
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*

class CreateListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateListBinding
    private val job = Job()
    private var selectedCategory : String = ""
    private var selectedPriority : String = ""
    private var selectedDate : Date = Date()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + job)
    private lateinit var todoViewModel: TodoViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        todoViewModel = ViewModelProvider(this)[TodoViewModel::class.java]


        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "Create List"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)


        binding.fabButton.setOnClickListener {
            coroutineScope.launch {
                val bottomSheetDialog = BottomSheetDialog(this@CreateListActivity)
                val view = layoutInflater.inflate(R.layout.bottom_sheet, null)
                bottomSheetDialog.setContentView(view)
                bottomSheetDialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
                


                val selectDateTime = view.findViewById<ImageView>(R.id.timer)
                val category = view.findViewById<ImageView>(R.id.tag)
                val priority = view.findViewById<ImageView>(R.id.flag)
                val createTodo = view.findViewById<ImageView>(R.id.send)
                val title = view.findViewById<TextInputEditText>(R.id.title)
                val des = view.findViewById<TextInputEditText>(R.id.des)

                category.setOnClickListener {
                    lifecycleScope.launch(Dispatchers.Main) {
                        val builder = AlertDialog.Builder(this@CreateListActivity)
                        // Set the dialog title and message
                        builder.setTitle("Select Task Category")
                        // Add the options as a list of strings
                        val options = arrayOf(
                            "Grocery",
                            "Work",
                            "Sport",
                            "Study",
                            "Music",
                            "Health",
                            "Home",
                            "Other"
                        )
                        builder.setItems(options) { _, which ->
                            // Get the selected option
                            val selectedOption = options[which]

                            // Do something with the selected option, like displaying it in a TextView
                            selectedCategory = selectedOption
                            Log.d("Selected Category:", selectedCategory)
                        }

                        // Create and show the dialog box
                        val dialog = builder.create()
                        dialog.show()
                    }

                }

                priority.setOnClickListener {
                    lifecycleScope.launch(Dispatchers.Main) {
                        val builder = AlertDialog.Builder(this@CreateListActivity)
                        // Set the dialog title and message
                        builder.setTitle("Select Task Priority")
                        // Add the options as a list of strings
                        val options = arrayOf("High", "Medium", "Low")
                        builder.setItems(options) { _, which ->
                            // Get the selected option
                            val selectedOption = options[which]

                            // Do something with the selected option, like displaying it in a TextView
                            selectedPriority = selectedOption
                            Log.d("Selected Priority:", selectedPriority)
                        }

                        // Create and show the dialog box
                        val dialog = builder.create()
                        dialog.show()
                    }

                }




                selectDateTime.setOnClickListener {
                    val calendar = Calendar.getInstance()
                    // Create a DatePickerDialog
                    val datePickerDialog = DatePickerDialog(
                        this@CreateListActivity,
                        R.style.MyDatePickerDialogTheme,
                        { _, year, month, dayOfMonth ->
                            calendar.set(Calendar.YEAR, year)
                            calendar.set(Calendar.MONTH, month)
                            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                            selectedDate = calendar.time
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

                // store value in the database
                createTodo.setOnClickListener {
                    coroutineScope.launch {
                        if (title.text!!.isNotEmpty() && des.text!!.isNotEmpty() && selectedCategory != "" && selectedPriority != "") {
                            val todo = TodoItem(
                                0, title.text.toString(), des.text.toString(), selectedDate,
                                selectedCategory, selectedPriority, false
                            )

                            todoViewModel.insertTodoItem(todo)
                            Toast.makeText(
                                this@CreateListActivity,
                                "To-Do Created Successfully",
                                Toast.LENGTH_SHORT
                            ).show()
                            bottomSheetDialog.dismiss()

                        } else {
                            Toast.makeText(
                                this@CreateListActivity,
                                "Please fill all the details",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }

                bottomSheetDialog.show()
            }
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }


}