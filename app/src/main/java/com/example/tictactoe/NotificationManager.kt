package com.example.tictactoe

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationManagerCompat

class NotificationManager(val context: Context) {

    enum class Channel(val id: String, val nameRes: Int, val descriptionRes: Int) {
        TicTacToe("TIC TAC TOE", R.string.channel_name, R.string.channel_description)
    }

    init {
        createNotificationChannels()
    }

    private fun createNotificationChannels() {
        for (channel in Channel.values()) {
            createNotificationChannel(channel)
        }
    }

    private fun createNotificationChannel(channel: Channel) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel
            val name = context.getString(channel.nameRes)
            val descriptionText = context.getString(channel.descriptionRes)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val mChannel = NotificationChannel(channel.id, name, importance)
            mChannel.description = descriptionText


            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            val notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createNotification(channel: Channel) : Notification{
        val notification = Notification.Builder(context,channel.id)
            .setContentTitle("Hello Anna")
            .setContentText("Hello Ofek")
            .setSmallIcon(R.drawable.ic_baseline_folder_24)
            .build()

        return notification
    }

    fun notify(notification: Notification, notificationId: Int){
        with(NotificationManagerCompat.from(context)){
            notify(notificationId, notification)
        }
    }

}