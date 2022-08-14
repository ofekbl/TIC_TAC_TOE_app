package com.example.tictactoe

import android.util.Log
import android.widget.Button

class Communicator {
//      private val gameHandler = GameHandler()
//      val boardFragment = BoardFragment()

    enum class Turn{
        X,
        O
    }

    var firstTurn = Turn.X
    var currentTurn = Turn.X
    var winner = firstTurn
    private val cells = Cells()


    fun tryToMakeAMove(playerX: Player, playerO: Player, x: Int, y: Int) : Boolean {
        Log.i("Communicator", "try to make a move")

        if (!isTakenSpot(x, y)) {
            if (!isGameOver()){
                if (currentTurn.toString() == "X"){
                    playerX.play(x, y)
                }
                else{
                    playerO.play(x, y)
                }
                return true
            }
            return false
        }
        return false
    }


    private fun isGameOver(): Boolean {
        Log.i("Communicator", "is game over")

        if (Column.isFull() or Row.isFull() or Diagonal.isFull()) {
            winner = currentTurn
            return true
        }
        else if(cells.isFull()){
            winner = currentTurn
            return true
        }
        return false
    }

    fun isTakenSpot(rowNum : Int, colNum: Int) : Boolean {
        Log.i("Communicator", "is taken spot")

        if(Board.gameGrid[rowNum - 1][colNum - 1].value != "-"){
            return true
        }
        return false
    }

//    fun initBoard(boardList: List<Button>){
//        var cell1 = boardList[0].text
//        println("Cell1 is: $cell1")
//    }

}