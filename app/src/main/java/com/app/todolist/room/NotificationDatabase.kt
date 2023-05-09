package com.app.todolist.room

import androidx.room.*
import com.app.todolist.extra.LocalDateTimeConverter
import com.app.todolist.models.Notification
import java.time.LocalDateTime


@Database(entities = [Notification::class], version = 1)
@TypeConverters(LocalDateTimeConverter::class)
abstract class NotificationDatabase : RoomDatabase() {
    abstract fun notificationDao(): NotificationDao
}