package com.example.tictactoe.database

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class GameRepository(private val gameDao: GamedatabaseDao?) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allGameState: Flow<List<GameState>>? = gameDao?.getAllGames()

    val getLatestGameState: Flow<GameState?>? = gameDao?.getLastGame()

    fun get(key: Long) : Flow<GameState?>? {
        return gameDao?.get(key)
    }

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(gameState: GameState) {
        gameDao?.insert(gameState)
    }

}