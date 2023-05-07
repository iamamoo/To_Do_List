package com.app.todolist.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Time
import java.util.Calendar
import java.util.Date


@Entity(tableName = "todo_items")
data class TodoItem(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String? = null,
    val description: String? = null,
    val date : Date? = null,
    val category : String? = null,
    val priority : String? = null,
    val isCompleted: Boolean = false
)

