package com.example.tictactoe.database
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

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

    @ColumnInfo(name = "grid_state")
    val gridState: String = initialGrid)

{
        companion object{
            var initialGrid = "---------"
        }
    }
