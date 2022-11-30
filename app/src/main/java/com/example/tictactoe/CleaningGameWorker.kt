package com.example.tictactoe

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.tictactoe.database.GameDatabase
import com.example.tictactoe.database.GameRepository

class CleaningGameWorker(context: Context, params: WorkerParameters): CoroutineWorker(context, params) {
    private val dao = GameDatabase.getInstance(context, GameApplication().applicationScope).gameDatabaseDao
    private val repository = GameRepository(dao)

    override suspend fun doWork(): Result {
        //reset game
        Log.i("clean work", "reset game")
        //the worker should not clear, it should over the game -> the other worker will doWork() in loop
        //but won't do anything
        repository.clear()
        return Result.success()
    }

}