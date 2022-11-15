package com.example.tictactoe

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.work.*
import com.example.tictactoe.database.GameDatabase
import com.example.tictactoe.database.GameRepository


class GameStateWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {
     private val NOTIFICATION_ID = 1
     private val notificationManager = NotificationManager(context)
    private val dao = GameDatabase.getInstance(context, GameApplication().applicationScope).gameDatabaseDao
    private val repository = GameRepository(dao)
    private var counter = 0

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun doWork(): Result {
        if (!repository.getLatestGameState().isGameOver){
            counter++
            if (counter >= 3){
                val cleaningWork = OneTimeWorkRequestBuilder<CleaningGameWorker>().build()
                WorkManager.getInstance(applicationContext).enqueue(cleaningWork)
                return Result.failure()
            }
            val notification = notificationManager.createNotification(NotificationManager.Channel.TicTacToe)
            notificationManager.notify(notification, NOTIFICATION_ID)
            Log.i("doWork", "Hello Anna")
            return Result.success()
        }
         return Result.failure()
    }
}