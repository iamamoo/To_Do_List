package com.app.todolist.extra

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.app.todolist.R
import com.app.todolist.tasks.TaskDetailActivity
import com.app.todolist.tasks.ViewListActivity


class NotificationReceiver : BroadcastReceiver() {

    @SuppressLint("UnspecifiedImmutableFlag")
    override fun onReceive(context: Context, intent: Intent) {

        val title = intent.getStringExtra("title") ?: "Event Reminder"
        val description = intent.getStringExtra("description") ?: "Your event is starting now!"


        // Create a new PendingIntent for the notification
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            Intent(context, ViewListActivity::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT
        )


        // Build the notification
        val builder = NotificationCompat.Builder(context, "channel_id")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(description)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        // Show the notification
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(1, builder.build())
    }
}
