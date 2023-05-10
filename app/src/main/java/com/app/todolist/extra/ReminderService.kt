package com.app.todolist.extra

import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.room.Room
import com.app.todolist.room.NotificationDatabase
import kotlinx.coroutines.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class NotificationService : Service() {
    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Default + job)
    private lateinit var dateTime : LocalDateTime

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        scope.launch {
            while (true) {
                val database = Room.databaseBuilder(
                    application.applicationContext,
                    NotificationDatabase::class.java, "notification_database"
                ).allowMainThreadQueries().build()

                // Get the current LocalDateTime object
                val localDateTime = LocalDateTime.now()
                val formatForLocalDateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
                val localDateTimeFormatted = localDateTime.format(formatForLocalDateTime)

                // Define the expected format
                val dateFormatterFromString = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
                dateTime = LocalDateTime.parse(localDateTimeFormatted, dateFormatterFromString)
                val notifications = database.notificationDao().getNotificationsAfter(dateTime)

                if (notifications.isEmpty()) {
                    Log.d("tag", "list is empty")
                } else {
                    notifications.forEach {
                        val dueDateTime = it.dateTime
                        val formatForDueDateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
                        val localDueDateTimeFormatted = localDateTime.format(formatForDueDateTime)

                        // Define the expected format
                        val dueDateFormatterFromString = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
                        val newDueDateTime = LocalDateTime.parse(localDueDateTimeFormatted, dueDateFormatterFromString)


                        if (newDueDateTime.isEqual(dueDateTime)){
                            Log.d("tag","$newDueDateTime and $dueDateTime")
                            NotificationReceiver.scheduleNotification(applicationContext, it)
                        }
                        else {
                            Log.d("tag","no todo for this moment.")
                        }
                    }
                }
                database.close()
                delay(60 * 1000) // Check every minute

            }

        }
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}

