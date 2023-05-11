package com.app.todolist.extra

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.room.Room
import com.app.todolist.R
import com.app.todolist.room.NotificationDatabase
import com.app.todolist.tasks.CreateListActivity
import kotlinx.coroutines.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class NotificationService : Service() {
    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Default + job)
    private lateinit var dateTime : LocalDateTime


    @RequiresApi(Build.VERSION_CODES.S)
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
        when (intent?.action) {
            "STOP_SERVICE" -> stopForegroundService()
        }

        val notification = createNotification()
        startForeground(NOTIFICATION_ID,notification)
        return START_STICKY
    }

    private fun stopForegroundService() {
        stopForeground(true)
        Log.d("tag","Service Stopped")
        stopSelf()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotification(): Notification {
        val largeIcon = BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher)
        val intent = Intent(this, CreateListActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val stopIntent = Intent(this, NotificationService::class.java)
        stopIntent.action = "STOP_SERVICE"

        val stopPendingIntent = PendingIntent.getService(this, 0, stopIntent, PendingIntent.FLAG_IMMUTABLE)

        val notificationBuilder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setContentTitle("Add Task")
            .setContentText("Enjoy your day!")
            .setSmallIcon(R.drawable.calendar)
            .setLargeIcon(largeIcon)
            .setContentIntent(pendingIntent)
            .addAction(R.drawable.timer, "Stop Reminder", stopPendingIntent)

        return notificationBuilder.build()
    }

    companion object {
        const val NOTIFICATION_CHANNEL_ID = "my_channel_id"
        const val NOTIFICATION_ID = 1
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "My Channel Name"
            val descriptionText = "My channel description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()
        scope.launch {
            createNotificationChannel()
        }

    }
}
