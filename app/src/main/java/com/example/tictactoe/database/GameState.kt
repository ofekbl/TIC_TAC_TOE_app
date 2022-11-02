package com.example.tictactoe.database
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector

@Entity(tableName = "single_game_table")
data class GameState(

    @PrimaryKey(autoGenerate = true)
    var gameId: Long = 0L,

    @ColumnInfo(name = "game_type")
    val gameType: Int = -1,

    @ColumnInfo(name = "next_turn")
    val nextTurn: String = "-",

    @ColumnInfo(name = "is_game_over")
    val isGameOver: Boolean = false,

    @ColumnInfo(name = "winner")
    val winner: String = "no one",

    @ColumnInfo(name = "grid_state")
    val gridState: String = initialGrid)

     {
        companion object{
            var initialGrid = "---------"
        }

    fun copy(
        gameType: Int = this.gameType,
        nextTurn: String = this.nextTurn,
        isGameOver: Boolean = this.isGameOver,
        winner: String = this.winner,
        gridState: String = this.gridState
    ) = GameState(gameId, gameType, nextTurn, isGameOver, winner, gridState)
}
