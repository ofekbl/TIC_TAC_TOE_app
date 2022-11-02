package com.example.tictactoe.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface GamedatabaseDao {

    @Insert(onConflict = REPLACE)
    suspend fun insert(gameState: GameState?)

    @Update
    suspend fun update(gameState: GameState)

    @Query("SELECT * from single_game_table WHERE gameId = :key")
    suspend fun get(key: Long) : GameState

    @Query("DELETE FROM single_game_table")
    suspend fun clear()

    @Query("SELECT * FROM single_game_table ORDER BY gameId DESC")
    suspend fun getAllGames() : List<GameState>

    @Query("SELECT * FROM single_game_table ORDER BY gameId DESC LIMIT 1")
    suspend fun getLastGame(): GameState?

}