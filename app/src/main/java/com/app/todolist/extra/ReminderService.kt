package com.app.todolist.extra

import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.room.Room
import com.app.todolist.room.NotificationDatabase
import kotlinx.coroutines.*
import java.time.LocalDateTime
import java.util.*

class NotificationService : Service() {
    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Default + job)

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        scope.launch {
            while (true) {
                val database = Room.databaseBuilder(
                    application.applicationContext,
                    NotificationDatabase::class.java, "notification_database"
                ).allowMainThreadQueries().build()

                val notifications = database.notificationDao().getNotificationsAfter(LocalDateTime.now())

                notifications.forEach {
                    NotificationReceiver.scheduleNotification(applicationContext, it)
                }
                delay(60 * 1000) // Check every minute
            }
        }
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
