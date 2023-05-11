package com.app.todolist.extra

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.room.Room
import com.app.todolist.models.Notification
import com.app.todolist.models.TodoItem
import com.app.todolist.room.NotificationDatabase
import com.app.todolist.room.TodoDatabase

class TodoViewModel(application: Application) : AndroidViewModel(application) {

    private val database =
        Room.databaseBuilder(
            application.applicationContext,
            TodoDatabase::class.java, "todo_database"
        ).allowMainThreadQueries().build()



    private val nDatabase =
        Room.databaseBuilder(
            application.applicationContext,
            NotificationDatabase::class.java, "notification_database"
        ).allowMainThreadQueries().build()


    suspend fun insertNotification(notificationModel: Notification){
        nDatabase.notificationDao().insertNotification(notificationModel)
    }

    suspend fun updateNotification(notification : Notification){
        nDatabase.notificationDao().updateNotificationItem(notification)
    }

    suspend fun deleteNotification(notification: Notification){
        nDatabase.notificationDao().deleteNotificationItem(notification)
    }

    suspend fun insertTodoItem(todoItem: TodoItem) {
        database.todoDao().insertTodoItem(todoItem)
    }

     suspend fun updateTodoItem(todoItem: TodoItem) {
        database.todoDao().updateTodoItem(todoItem)
    }

    suspend fun deleteTodoItem(todoItem: TodoItem) {
        database.todoDao().deleteTodoItem(todoItem)
    }


    suspend fun getAllToDoPriority() : List<TodoItem>{
        return database.todoDao().getAccordingToPriority()
    }
}
