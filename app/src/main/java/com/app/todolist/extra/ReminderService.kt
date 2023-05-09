package com.app.todolist.extra

import android.app.Service
import android.content.Intent
import android.os.IBinder
import java.util.*

class ReminderService : Service() {

    private val reminderCalendar: Calendar by lazy {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, 2023)
        calendar.set(Calendar.MONTH, Calendar.MAY)
        calendar.set(Calendar.DAY_OF_MONTH, 23)
        calendar.set(Calendar.HOUR_OF_DAY, 18)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar
    }

    private val notificationReceiver = NotificationReceiver()

    private val timer = Timer()

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        scheduleReminder()
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
    }

    private fun scheduleReminder() {
        timer.schedule(object : TimerTask() {
            override fun run() {
                val now = Calendar.getInstance()
                if (now.after(reminderCalendar)) {
                    // Trigger the notification
                    val intent = Intent(applicationContext, NotificationReceiver::class.java)
                    sendBroadcast(intent)

                    // Stop the service
                    stopSelf()
                }
            }
        }, 0, 60 * 60 * 1000) // Check every hour
    }
}
