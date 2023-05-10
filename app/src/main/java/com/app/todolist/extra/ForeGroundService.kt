package com.app.todolist.extra

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import com.app.todolist.R

class ForeGroundService(context: Context, intent: Intent?) : Service() {

    val notificationId = intent!!.getIntExtra("notificationId", 0)
    val notificationTitle = intent!!.getStringExtra("notificationTitle")
    val notificationMessage = intent!!.getStringExtra("notificationMessage")



    companion object {
        private const val NOTIFICATION_CHANNEL_ID = "NotificationServiceChannel"
        private const val NOTIFICATION_ID = 123
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notification = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotification()
        } else {
            Log.d("tag", "this feature is not available for this device")
        }
        startForeground(NOTIFICATION_ID, notification as Notification?)
        // Your background task code here
        return START_NOT_STICKY
    }


    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                "Foreground Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(serviceChannel)
        }else {
            Log.d("tag", "this feature is not available for this device")
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotification(): Notification {
        return Notification.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setContentTitle(notificationTitle)
            .setContentText(notificationMessage)
            .setSmallIcon(R.drawable.calendar)
            .build()

    }












    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}