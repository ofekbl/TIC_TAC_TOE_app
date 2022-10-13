package com.example.tictactoe.viewmodel

import android.util.Log
import com.example.tictactoe.*
import com.example.tictactoe.logic.Board
import com.example.tictactoe.presentation.BoardFragment

class Communicator(boardFragment: BoardFragment) {
    var boardFragment = boardFragment

    enum class Turn{
        X,
        O
    }

    var firstTurn = Turn.X
    var currentTurn = firstTurn
    var winner : String = "No one"
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
                    if (!isGameOver()) {
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
    }

     fun isGameOver(): Boolean {
        Log.i("Communicator", "is game over")

        if (Column.isFull() or Row.isFull() or Diagonal.isFull()) {
            if (currentPlayer.sign == "X")
                winner = "O"
            else
                winner = "X"
            boardFragment.showGameOverPopUp()
            return true
        }
        else if(cells.isFull()){
            if (currentPlayer.sign == "X")
                winner = "O"
            else
                winner = "X"
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

    fun gridToString(): String {
        return "${Board.cell1.value}${Board.cell2.value}${Board.cell3.value}${Board.cell4.value}${Board.cell5.value}${Board.cell6.value}${Board.cell7.value}${Board.cell8.value}${Board.cell9.value}"
    }
}