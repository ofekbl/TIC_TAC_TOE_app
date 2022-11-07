package com.example.tictactoe.database

import android.util.Log
import androidx.annotation.WorkerThread
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

class GameRepository(private val gameDao: GamedatabaseDao) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
//    val allGameState: List<GameState> = gameDao.getAllGames()

    suspend fun getLatestGameState() : GameState {
        var latestGameState = gameDao.getLastGame()
        if (latestGameState == null){
            Log.i("getLatestGameState", "latest game state was null so entered to if")
            val gameState = GameState(0 , -1, "-", false, "no one", GameState.initialGrid)
            latestGameState = gameState
        }
        return latestGameState
    }

    suspend fun get(key: Long) : GameState {
        return gameDao.get(key)
    }

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.

    suspend fun insert(gameState: GameState) {
        gameDao.insert(gameState)
    }
}