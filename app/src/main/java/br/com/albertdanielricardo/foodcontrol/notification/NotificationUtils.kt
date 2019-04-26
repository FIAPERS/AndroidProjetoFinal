package br.com.albertdanielricardo.foodcontrol.notification

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import br.com.albertdanielricardo.foodcontrol.ListFoodActivity
import br.com.albertdanielricardo.foodcontrol.R

class NotificationUtils {
    companion object {

        @SuppressLint("NewApi")
        private fun createChannel(context: Context, channelId: String, channelName: String) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // Create the NotificationChannel, but only on API 26+ because
                // the NotificationChannel class is new and not in the support library
                val context = context.applicationContext
                val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

                val importance = NotificationManager.IMPORTANCE_HIGH
                val notificationChannel = NotificationChannel(channelId, channelName, importance)
                notificationChannel.enableVibration(true)
                notificationChannel.setShowBadge(true)
                notificationChannel.enableLights(true)
                notificationChannel.lightColor = Color.parseColor("#e8334a")
                notificationChannel.description = "CHANNEL_DESCRIPTION"
                notificationChannel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
                notificationManager.createNotificationChannel(notificationChannel)
            }

        }

        fun showNotification(context: Context, notificationId: Int,
                             channelId: String,
                             channelName: String,
                             title: String,
                             message: String) {

            var mNotification: Notification.Builder

            createChannel(context, channelId, channelName)

            val context = context
            var notificationManager: NotificationManager
            val notifyIntent = Intent(context, ListFoodActivity::class.java)

            notifyIntent.putExtra("title", title)
            notifyIntent.putExtra("message", message)
            notifyIntent.putExtra("notification", true)

            notifyIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

            val pendingIntent = PendingIntent.getActivity(context, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT)
            val res = context.resources
            val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {


                mNotification = Notification.Builder(context, channelId)
                    // Set the intent that will fire when the user taps the notification
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(BitmapFactory.decodeResource(res, R.mipmap.ic_launcher))
                    .setAutoCancel(true)
                    .setContentTitle(title)
                    .setStyle(
                        Notification.BigTextStyle()
                            .bigText(message))
                    .setContentText(message)
            } else {

                mNotification = Notification.Builder(context)
                    // Set the intent that will fire when the user taps the notification
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(BitmapFactory.decodeResource(res, R.mipmap.ic_launcher))
                    .setAutoCancel(true)
                    .setPriority(Notification.PRIORITY_MAX)
                    .setContentTitle(title)
                    .setStyle(
                        Notification.BigTextStyle()
                            .bigText(message))
                    .setSound(uri)
                    .setContentText(message)

            }

            val yesReceive = Intent(context, NotificationReceiver::class.java)
            yesReceive.action = "YES_ACTION" //AppConstant.YES_ACTION


            val pendingIntentYes = PendingIntent.getBroadcast(context, 12345, yesReceive, PendingIntent.FLAG_UPDATE_CURRENT)
            mNotification.addAction(R.mipmap.ic_launcher, context.getString(R.string.notification_button), pendingIntentYes)

            notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            // mNotificationId is a unique int for each notification that you must define
            notificationManager.notify(notificationId, mNotification.build())
        }
    }
}
