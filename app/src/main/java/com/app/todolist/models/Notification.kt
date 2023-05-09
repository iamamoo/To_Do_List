package com.app.todolist.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "notifications")
data class Notification(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val message: String,
    val dateTime: LocalDateTime
)