package com.example.tictactoe.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface GamedatabaseDao {

    @Insert
    fun insert(gameState: GameState)

    @Update
    fun update(gameState: GameState)

    @Query("SELECT * from single_game_table WHERE gameId = :key")
    fun get(key: Long) : Flow<GameState?>

    @Query("DELETE FROM single_game_table")
    fun clear()

    @Query("SELECT * FROM single_game_table ORDER BY gameId DESC")
    fun getAllGames() : Flow<List<GameState>>

    @Query("SELECT * FROM single_game_table ORDER BY gameId DESC LIMIT 1")
    fun getLastGame(): Flow<GameState?>

}