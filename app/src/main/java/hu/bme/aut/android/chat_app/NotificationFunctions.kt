package hu.bme.aut.android.chat_app

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.ContextCompat.getSystemServiceName


private const val NOTIFICATION_ID = 101
        const val CHANNEL_ID = "ForegroundServiceChannel"


   fun createNotification(text: String, context: Context): Notification {
        val notificationIntent = Intent(context, MainActivity::class.java)
        notificationIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK

        createNotificationChannel()

        val contentIntent = PendingIntent.getActivity(
            context,
            NOTIFICATION_ID,
            notificationIntent,
            PendingIntent.FLAG_CANCEL_CURRENT
        )

        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle("Service Location Demo")
            .setContentText(text)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setVibrate(longArrayOf(1000, 2000, 1000))
            .setContentIntent(contentIntent)
            .build()
    }

     fun updateNotification(text: String, context: Context) {
        val notification = createNotification(text, context)
       // val notificationManager = getSystemService(.NOTIFICATION_SERVICE ) as NotificationManager
      //  notificationManager.notify(NOTIFICATION_ID, notification)
    }

    fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "name"
            val descriptionText = "desc"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel =
                NotificationChannel(CHANNEL_ID, name, importance).apply {
                    description = descriptionText
                }
            // Register the channel with the system
           /* val notificationManager: NotificationManager =
                NOTIFICATION_SERVICE as NotificationManager
            notificationManager.createNotificationChannel(channel)*/
        }
    }
