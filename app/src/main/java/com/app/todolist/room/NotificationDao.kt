package com.app.todolist.room

import androidx.room.*
import com.app.todolist.models.Notification
import com.app.todolist.models.TodoItem
import java.time.LocalDateTime

@Dao
interface NotificationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotification(notification: Notification)

    @Query("SELECT * FROM notifications WHERE dateTime >= :timeDate")
    fun getNotificationsAfter(timeDate: LocalDateTime): List<Notification>

    @Delete
    suspend fun deleteNotificationItem(notification : Notification)

    @Update
    suspend fun updateNotificationItem(notification : Notification)

}