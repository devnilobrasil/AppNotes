package com.github.devnilobrasil.notes.notification

import android.app.Activity
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
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
        val scheduleTime = (reminderDialog.selectedDate!! - today) + calendar.timeInMillis
        Log.i("NILODIS", "$scheduleTime")
        return scheduleTime

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createNotificationChannel(activity: Activity?){
        val name = "Notif Channel"
        val desc = "A description"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelID, name, importance)
        channel.description = desc
        val notificationManager = activity?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

    }

    fun scheduleNotification(activity: Activity? ,context: Context, titleNote: String, bodyNote: String,
    reminderDialog: ReminderDialog
    ){
        val intent = Intent(context, Notification::class.java)
        intent.putExtra(titleExtra, titleNote)
        intent.putExtra(messageExtra, bodyNote)
        // Sabe tratar strings

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            notificationID,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = activity?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val time = getTime(reminderDialog)
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            time,
            pendingIntent
        )
    }
}