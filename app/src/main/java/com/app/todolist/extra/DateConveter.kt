package com.app.todolist.extra

import androidx.room.TypeConverter
import java.util.*

class DateConverter {
    @TypeConverter
    fun toDate(timestamp: Long): Date = Date(timestamp)

    @TypeConverter
    fun toTimestamp(date: Date): Long = date.time
}
