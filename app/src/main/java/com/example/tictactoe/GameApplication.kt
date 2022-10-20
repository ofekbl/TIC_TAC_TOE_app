package com.example.tictactoe

import android.app.Application
import com.example.tictactoe.database.GameDatabase
import com.example.tictactoe.database.GameRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class GameApplication : Application() {

    val applicationScope = CoroutineScope(SupervisorJob())


    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy {GameDatabase.getInstance(this, applicationScope)}
    val repository by lazy { GameRepository(database?.gameDatabaseDao) }
}