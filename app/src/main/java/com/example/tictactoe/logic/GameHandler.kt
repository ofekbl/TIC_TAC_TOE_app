package com.example.tictactoe.logic

import android.util.Log
import com.example.tictactoe.logic.Board
import com.example.tictactoe.logic.Cell

class GameHandler {

    //val communicator = Communicator()

    fun addToGrid(rowNum : Int, columnNum: Int, playerSign: String) {
        Log.i("Game Handler", "add to grid")
        Board.gameGrid[rowNum - 1][columnNum - 1].value = playerSign  // 1, 2 -> cell2
        Log.i("addToGrid", "${Board.gameGrid}")
    }

    fun isTakenSpot(rowNum : Int, colNum: Int) : Boolean {
        Log.i("Communicator", "is taken spot")

        if(Board.gameGrid[rowNum - 1][colNum - 1].value != "-"){
            return true
        }
        return false
    }
}
