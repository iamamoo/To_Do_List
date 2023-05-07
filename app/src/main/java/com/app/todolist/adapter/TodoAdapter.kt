package com.app.todolist.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.app.todolist.R
import com.app.todolist.models.Todo
import com.app.todolist.models.TodoItem
import com.app.todolist.tasks.AddTaskActivity

class TodoAdapter(private val list: List<TodoItem>,private val context: Context) : RecyclerView.Adapter<TodoAdapter.ViewHolder>(){

    

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val checkBox: CheckBox = itemView.findViewById(R.id.checkbox_id)
        val title: TextView = itemView.findViewById(R.id.todo_title)
        val description: TextView = itemView.findViewById(R.id.description)
        val edit: ImageView = itemView.findViewById(R.id.edit)
        val priority: TextView = itemView.findViewById(R.id.priorityText)
        val category: TextView = itemView.findViewById(R.id.categoryText)
        val time: TextView = itemView.findViewById(R.id.timeRemaining)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.todo_sample,parent,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = list[position]
        if (model.isCompleted){
            holder.checkBox.isChecked = true
        }
        holder.title.text = model.title
        holder.description.text = model.description
        holder.priority.text = model.priority
        holder.time.text = model.date!!.time.toString()
        holder.category.text = model.category






        holder.edit.setOnClickListener {
            val intent = Intent(context,AddTaskActivity::class.java)
            intent.putExtra("isCompleted",model.isCompleted)
            intent.putExtra("title",model.title)
            intent.putExtra("description",model.description)
            intent.putExtra("priority",model.priority)
            intent.putExtra("time",model.date)
            intent.putExtra("category",model.category)
            intent.putExtra("id",model.id)
            context.startActivity(intent)
        }
    }
}