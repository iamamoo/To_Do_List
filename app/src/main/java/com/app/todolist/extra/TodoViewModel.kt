package com.app.todolist.extra

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.room.Room
import com.app.todolist.models.TodoItem
import com.app.todolist.room.TodoDatabase

class TodoViewModel(application: Application) : AndroidViewModel(application) {
    private val database = Room.databaseBuilder(
        application.applicationContext,
        TodoDatabase::class.java, "todo_database"
    ).allowMainThreadQueries().build()

    suspend fun insertTodoItem(todoItem: TodoItem) {
        database.todoDao().insertTodoItem(todoItem)
    }

     fun updateTodoItem(todoItem: TodoItem) {
        database.todoDao().updateTodoItem(todoItem)
    }

    suspend fun deleteTodoItem(todoItem: TodoItem) {
        database.todoDao().deleteTodoItem(todoItem)
    }

    suspend fun getAllTodoItems(): List<TodoItem> {
        return database.todoDao().getAllTodoItems()
    }

    suspend fun getAllToDoPriority() : List<TodoItem>{
        return database.todoDao().getAccordingToPriority()
    }

}
