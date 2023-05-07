package com.app.todolist.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.app.todolist.extra.DateConverter
import com.app.todolist.models.TodoItem

@Database(entities = [TodoItem::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
}