package com.example.tictactoe

import android.util.Log
import android.widget.Button

class GameHandler {

    private val communicator = Communicator()
//    private val playerX = HumanPlayer()
//    private val playerO = ComputerPlayer()


//    fun runGame() {
//        turn = "X"
//        Communicate.printOutputToTerminal("Hello! Welcome to tic tac toe game!")
//
//        while (!isGameOver()) {
//            if (turn == "X") {
//                playerX.play()
//            } else
//                playerO.play()
//        }
//
//        if (winner != "") {
//            Communicate.printOutputToTerminal("Game is over! $winner is the winner")
//        } else {
//            Communicate.printOutputToTerminal("Game is over! It's a tie :$")
//        }
//    }



    fun addToGrid(rowNum : Int, columnNum: Int, currentTurn : Communicator.Turn) {
        Log.i("Game Handler", "add to grid")
//        if(!communicator.isTakenSpot(rowNum, columnNum)) {
        Board.gameGrid[rowNum - 1][columnNum - 1].value = currentTurn.toString()  // 1, 2 -> cell2
//        }
    }
//        for (i in 0 until 3) {
//            var j = 0
//            Communicate.printOutputToTerminal(Board.gameGrid[i][j].value + " " + Board.gameGrid[i][j + 1].value + " " + Board.gameGrid[i][j + 2].value)
//        }

//        if (turn == "X"){
//            turn = "O"
//        }
//
//        else{
//            turn = "X"
//        }





}
