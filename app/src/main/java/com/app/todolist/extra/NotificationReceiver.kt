package com.app.todolist.extra

import android.annotation.SuppressLint
import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.app.todolist.R
import java.time.ZoneId

 class NotificationReceiver : BroadcastReceiver() {

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(context: Context, intent: Intent) {
        val notificationId = intent.getIntExtra("notificationId", 0)
        val notificationTitle = intent.getStringExtra("notificationTitle")
        val notificationMessage = intent.getStringExtra("notificationMessage")

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "default",
                "Default Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        } else {
            Log.d("tag","Your device is not applicable for this feature.")
        }

        val largeIcon = BitmapFactory.decodeResource(context.resources, R.drawable.large_icon)

        val notification = NotificationCompat.Builder(context, "default")
            .setSmallIcon(R.drawable.calendar)
            .setContentTitle(notificationTitle)
            .setContentText(notificationMessage)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setLargeIcon(largeIcon)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(notificationId, notification)
    }

    companion object {
        @RequiresApi(Build.VERSION_CODES.O)
        fun scheduleNotification(context: Context, notification: com.app.todolist.models.Notification) {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

            val intent = Intent(context, NotificationReceiver::class.java).apply {
                putExtra("notificationId", notification.id)
                putExtra("notificationTitle", notification.title)
                putExtra("notificationMessage", notification.message)
            }
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

            val pendingIntent = PendingIntent.getBroadcast(
                context,
                notification.id,
                intent,
                PendingIntent.FLAG_IMMUTABLE
            )

            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                notification.dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
                pendingIntent
            )
        }
    }
}