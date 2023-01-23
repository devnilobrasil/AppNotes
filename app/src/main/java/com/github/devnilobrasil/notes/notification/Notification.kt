package com.github.devnilobrasil.notes.notification

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.navigation.NavDeepLinkBuilder
import com.github.devnilobrasil.notes.MainActivity
import com.github.devnilobrasil.notes.R


const val notificationID = 1
const val channelID = "channel1"
const val titleExtra = "titleExtra"
const val messageExtra = "messageExtra"


class Notification : BroadcastReceiver()
{
    override fun onReceive(context: Context, intent: Intent)
    {
        // Open HomeFragment after click notification
        val goToHomeFragment = NavDeepLinkBuilder(context)
            .setComponentName(MainActivity::class.java)
            .setGraph(R.navigation.nav_main)
            .setDestination(R.id.homeFragment)
            .createPendingIntent()

        val notification = NotificationCompat.Builder(context, channelID)
            .setSmallIcon(R.drawable.ic_alarm_notification)
            .setContentTitle(intent.getStringExtra(titleExtra))
            .setContentText(intent.getStringExtra(messageExtra))
            .setTicker(intent.getStringExtra(titleExtra))
            .setContentIntent(goToHomeFragment)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(notificationID, notification)
    }
}