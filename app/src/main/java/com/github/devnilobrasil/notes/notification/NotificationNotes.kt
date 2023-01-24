package com.github.devnilobrasil.notes.notification

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import com.github.devnilobrasil.notes.dialogs.ReminderDialog
import com.google.android.material.datepicker.MaterialDatePicker
import java.util.*


class NotificationNotes
{
    private fun getTime(reminderDialog: ReminderDialog): Long
    {
        val today = MaterialDatePicker.todayInUtcMilliseconds()
        val minute = reminderDialog.selectedMinute!!
        val hour = reminderDialog.selectedHour!!
        val calendar = Calendar.getInstance()

        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return (reminderDialog.selectedDate!! - today) + calendar.timeInMillis

    }


    fun createNotificationChannel(activity: Activity?){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name = "Notif Channel"
            val desc = "A description"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(channelID, name, importance)
            with(channel){
                description = desc
                setShowBadge(true)
                enableVibration(true)
                lockscreenVisibility = android.app.Notification.VISIBILITY_PUBLIC
                vibrationPattern = longArrayOf(300, 300, 300)
            }

            val notificationManager = activity?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun scheduleNotification(
        context: Context,
        titleNote: String,
        bodyNote: String,
        reminderDialog: ReminderDialog
    ){
        val intent = Intent(context, Notification::class.java)
        intent.putExtra(titleExtra, titleNote)
        intent.putExtra(messageExtra, bodyNote)
        ++notificationID

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            notificationID,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val time = getTime(reminderDialog)
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            time,
            pendingIntent
        )

    }

    fun cancelNotification(context: Context){
        val intent = Intent(context, Notification::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            notificationID,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pendingIntent)
    }
}