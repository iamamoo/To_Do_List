package com.app.todolist.extra

import androidx.room.TypeConverter
import java.util.*

class DateConverter {
    @TypeConverter
    fun convertDateToLong(date: Date): Long = date.time

    @TypeConverter
    fun convertLongToDate(long: Long): Date = Date(long)
}
