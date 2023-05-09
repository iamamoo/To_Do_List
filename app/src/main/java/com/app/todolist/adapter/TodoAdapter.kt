package com.app.todolist.adapter

import android.app.Application
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.app.todolist.R
import com.app.todolist.extra.TodoViewModel
import com.app.todolist.models.TodoItem
import com.app.todolist.tasks.AddTaskActivity
import com.app.todolist.tasks.TaskDetailActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TodoAdapter(private val list: List<TodoItem>,private val context: Context,application: Application,
private val coroutineScope: CoroutineScope) : RecyclerView.Adapter<TodoAdapter.ViewHolder>(){

    private val todoViewModel =  TodoViewModel(application)
    private val prefShared = context.getSharedPreferences("allowEdit",Context.MODE_PRIVATE)
    private val allowEdit = prefShared.getBoolean("isAllowEdit",false)


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val checkBox: CheckBox = itemView.findViewById(R.id.checkbox_id)
        val title: TextView = itemView.findViewById(R.id.todo_title)
        val description: TextView = itemView.findViewById(R.id.description)
        val edit: ImageView = itemView.findViewById(R.id.edit)
        val priority: TextView = itemView.findViewById(R.id.priorityText)
        val category: TextView = itemView.findViewById(R.id.categoryText)
        val time: TextView = itemView.findViewById(R.id.timeRemaining)
        val card : CardView = itemView.findViewById(R.id.todoCard)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.addtodo_sample,parent,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        coroutineScope.launch(Dispatchers.Main) {
            val model = list[position]
            if (model.isCompleted){
                holder.checkBox.isChecked = true
                holder.title.paintFlags = holder.title.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                holder.description.paintFlags = holder.description.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

            }

            when(model.priority){
                "High" -> {
                    holder.priority.setTextColor(Color.RED)
                }
                "Medium" -> {
                    holder.priority.setTextColor(Color.YELLOW)
                }
                "Low" -> {
                    holder.priority.setTextColor(Color.GREEN)
                }
            }

            holder.title.text = model.title
            holder.description.text = model.description
            holder.priority.text = model.priority
            holder.time.text = model.date!!.toString()
            holder.category.text = model.category

            holder.checkBox.setOnClickListener {
                if (model.isCompleted){
                    holder.checkBox.isChecked = false

                    val isCompleted = false
                    val todoItem = TodoItem(model.id,model.title,model.description,model.date,model.time,model.category,
                        model.priority,isCompleted)

                    coroutineScope.launch {
                        todoViewModel.updateTodoItem(todoItem)
                    }

                    holder.title.paintFlags = holder.title.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                    holder.description.paintFlags = holder.description.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()

                } else {
                    Toast.makeText(context,"Task Completed",Toast.LENGTH_SHORT).show()
                    holder.checkBox.isChecked = true
                    val isCompleted = true
                    val todoItem = TodoItem(model.id,model.title,model.description,model.date,model.time,model.category,
                        model.priority,isCompleted)



                    coroutineScope.launch {
                        todoViewModel.updateTodoItem(todoItem)
                    }

                    holder.title.paintFlags = holder.title.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    holder.description.paintFlags = holder.description.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                }
            }

            holder.edit.setOnClickListener {
                if (allowEdit){
                    val intent = Intent(context,AddTaskActivity::class.java)
                    intent.putExtra("isCompleted",model.isCompleted)
                    intent.putExtra("title",model.title)
                    intent.putExtra("description",model.description)
                    intent.putExtra("priority",model.priority)
                    intent.putExtra("date",model.date)
                    intent.putExtra("category",model.category)
                    intent.putExtra("id",model.id)
                    intent.putExtra("time",model.time)
                    context.startActivity(intent)
                } else {
                    Toast.makeText(context,"You're not allowed to edit tasks",Toast.LENGTH_SHORT).show()
                }

            }

            holder.card.setOnClickListener {
                val intent = Intent(context,TaskDetailActivity::class.java)
                intent.putExtra("isCompleted",model.isCompleted)
                intent.putExtra("title",model.title)
                intent.putExtra("description",model.description)
                intent.putExtra("priority",model.priority)
                intent.putExtra("date",model.date)
                intent.putExtra("category",model.category)
                intent.putExtra("id",model.id)
                intent.putExtra("time",model.time)
                context.startActivity(intent)
            }
        }
    }
}