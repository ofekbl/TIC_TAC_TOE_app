package com.example.tictactoe

import android.util.Log
import android.widget.Button

class Communicator(boardFragment: BoardFragment) {
    var boardFragment = boardFragment

    enum class Turn{
        X,
        O
    }

    var firstTurn = Turn.X
    var currentTurn = firstTurn
    lateinit var winner : String
    lateinit var playerO: Player
    var playerX = HumanPlayer()
    private var currentPlayer : Player = playerX
    private val cells = Cells()

     fun changeTurns() {
        if (currentTurn == Turn.X){
            currentTurn = Turn.O
        }
        else {
            currentTurn = Turn.X
        }
    }

    private fun changeCurrentPlayer(){
        if (currentPlayer == playerO)
            currentPlayer = playerX
        else
            currentPlayer = playerO
    }

    fun tryToMakeAMove(x: Int, y: Int) {
        Log.i("Communicator", "try to make a move")

        if (!isTakenSpot(x, y)) {
            if (!isGameOver()) {
                if(currentPlayer is HumanPlayer) {
                    Log.i("tryToMakeAMove", "if currentPlayer is human")
                    currentPlayer.play(x, y)
                    boardFragment.setCell(x, y, currentPlayer.sign)
                    changeCurrentPlayer()
                    boardFragment.setTurnLabel("Turn ${currentPlayer.sign}")
                    changeTurns()
                }
                if (boardFragment.gameType == 2) { //AI
                    val spot = (playerO as ComputerPlayer).play()
                    val randomRow = spot[0]
                    val randomCol = spot[1]
                    boardFragment.setCell(randomRow, randomCol, currentPlayer.sign)
                    changeCurrentPlayer()
                    boardFragment.setTurnLabel("Turn ${currentPlayer.sign}")
                    changeTurns()
                }
            }
        }
    }

    private fun isGameOver(): Boolean {
        Log.i("Communicator", "is game over")

        if (Column.isFull() or Row.isFull() or Diagonal.isFull()) {
            winner = currentPlayer.sign
            return true
        }
        else if(cells.isFull()){
            winner = currentPlayer.sign
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
}