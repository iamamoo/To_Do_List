package com.app.todolist.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.todolist.models.Notification
import java.time.LocalDateTime

@Dao
interface NotificationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotification(notification: Notification)

    @Query("SELECT * FROM notifications WHERE dateTime >= :timeDate")
    fun getNotificationsAfter(timeDate: LocalDateTime): List<Notification>
}