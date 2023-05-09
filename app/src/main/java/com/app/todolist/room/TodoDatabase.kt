package com.app.todolist.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.todolist.models.TodoItem

@Database(entities = [TodoItem::class], version = 1, exportSchema = false)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
}