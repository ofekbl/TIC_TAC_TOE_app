package com.example.tictactoe.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tictactoe.database.GameRepository
import com.example.tictactoe.database.GameState
import com.example.tictactoe.logic.*
import kotlinx.coroutines.launch

class BoardFragmentViewModel(private val repository: GameRepository): ViewModel() {

    private val playerBuilder = PlayerBuilder()
    var gameType = -1

    private val _gameState = MutableLiveData<GameState>()
    val gameState: LiveData<GameState>
        get() = _gameState

    init {
        viewModelScope.launch {
            _gameState.value = repository.getLatestGameState()
            insert(_gameState.value!!.copy(gameType = gameType))
            _gameState.value = repository.getLatestGameState()
        }
    }
    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(gameState: GameState) = viewModelScope.launch {
        repository.insert(gameState)
    }

    lateinit var playerO: Player
    var playerX = playerBuilder.build("human")

    private var currentPlayer = playerX as Player

    private val cells = Cells()


    fun isGameOver(): Boolean {
        Log.i("view model", "is game over")

        val newWinner : String
        val gameOverState : Boolean
        if (Cells.Column.isFull() or Cells.Row.isFull() or Cells.Diagonal.isFull()) {
            Log.i("isGameOver", "checking if something is full")
            if (currentPlayer.sign == "X")
                newWinner = "O"
            else {
                newWinner = "X"
            }
            gameOverState = true
            val newGameState = gameState.value?.copy( winner = newWinner, isGameOver = gameOverState)
            Log.i("isGameOver", "grid state is ${gameState.value?.gridState}")
            if (newGameState != null) {
                Log.i("tryToMakeA..", "newGameState is not null then insert")
                insert(newGameState)
                _gameState.value = newGameState!!
            }
            return true
        }

        else if(cells.isFull()){
            Log.i("isGameOver", "cells.isFull returned true")
            newWinner = "no one"
            gameOverState = true
            val newGameState = gameState.value?.copy(winner = newWinner, isGameOver = gameOverState)
            Log.i("isGameOver2", "grid state is ${gameState.value?.gridState}")

            if (newGameState != null) {
                Log.i("tryToMakeA..", "newGameState is not null then insert")
                insert(newGameState)
                _gameState.value = newGameState!!
            }
            return true
        }
        return false
    }

    private fun isTakenSpot(rowNum : Int, colNum: Int) : Boolean {
        Log.i("board view model", "is taken spot")

        if(Board.gameGrid[rowNum - 1][colNum - 1].value != "-"){
            return true
        }
        return false
    }

    private fun changeCurrentPlayer(){
        if (currentPlayer == playerO)
            currentPlayer = playerX
        else
            currentPlayer = playerO
    }

    private fun changeTurns() :String {
        val nextTurn: String
        if (gameState.value?.nextTurn == "X"){
            nextTurn = "O"
        }
        else {
            nextTurn = "X"
        }
        val newGameState = gameState.value?.copy(nextTurn = nextTurn)
        Log.i("changeTurns", "grid state is ${gameState.value?.gridState}")

        if (newGameState != null){
            insert(newGameState)
            _gameState.value = newGameState!!
        }
        return "$nextTurn"
    }

    private fun getCurrTurn(nextTurn: String) : String{
        val currTurn: String
        if (nextTurn == "X"){
            currTurn = "O"
        }
        else {
            currTurn = "X"
        }
        return currTurn
    }


    private fun tryToMakeAMove(x: Int, y: Int) {
        Log.i("board viewModel", "try to make a move")

        if (!isTakenSpot(x, y)) {
            if (!isGameOver()) {
                if (currentPlayer is HumanPlayer) {
                    Log.i("tryToMakeAMove", "if currentPlayer is human")
                    currentPlayer.play(x, y)
                    Log.i("isTakenSpot", "before insertion")
                    val cellNum = convertSpotToCellNum(x, y)
                     setCell(cellNum, getCurrTurn(gameState.value!!.nextTurn))
                    val newNextTurn = changeTurns()

                    changeCurrentPlayer()
                    val newGameState =
                        gameState.value?.copy(gridState = gameState.value!!.gridState, gameType = gameType, nextTurn = newNextTurn)
                    Log.i("tryToMake - human player", "grid state is ${gameState.value?.gridState}")
                    if (newGameState != null) {
                        Log.i("tryToMakeA..", "newGameState is not null then insert")
                        insert(newGameState)
                        _gameState.value = newGameState!!
                    }
                }

                if (gameState.value!!.gameType == 2) { //AI
                    if (!isGameOver()) {
                        val spot = (playerO as ComputerPlayer).play()
                        val randomRow = spot[0]
                        val randomCol = spot[1]
                        currentPlayer.play(randomRow, randomCol)
                        val cellNum = convertSpotToCellNum(randomRow, randomCol)
                        setCell(cellNum, "O")
                        Log.i("tryTomake gameType2", "grid state is ${gameState.value!!.gridState}")
                        changeCurrentPlayer()
                        val newNextTurn = changeTurns()


                        val newGameState =
                            gameState.value?.copy(gridState = gameState.value!!.gridState, nextTurn = newNextTurn)
                        Log.i("tryToMake - gameType = 2", "grid state is ${gameState.value?.gridState}")

                        if (newGameState != null) {
                            Log.i("tryToMakeA..", "newGameState is not null then insert")
                            insert(newGameState)
                            _gameState.value = newGameState!!
                        }
                    }
                }
            }
        }
    }

    fun buttonClicked(x: Int, y: Int){
        tryToMakeAMove(x, y)
//        val cellNum = convertSpotToCellNum(x, y)
//        if (gameState.value != null) {
//            setCell(cellNum, getCurrTurn(gameState.value!!.nextTurn))
        //}
//        val newNextTurn = changeTurns()
        val isGameOver = isGameOver()

        val newGameState = gameState.value?.copy(winner = gameState.value!!.winner, isGameOver = isGameOver)
//        val newGameState = gameState.value?.copy(nextTurn = newNextTurn, winner = gameState.value!!.winner, isGameOver = isGameOver)
        Log.i("buttonClicked", "grid state is ${gameState.value?.gridState}")

        if (newGameState != null) {
            Log.i("tryToMakeA..", "newGameState is not null then insert")
            insert(newGameState)
            _gameState.value = newGameState!!
        }

    }

    private fun setCell(cellNum : Int, currTurn: String) {
        val gridAsCharArray = stringToArray(gameState.value?.gridState)
        gridAsCharArray[cellNum - 1] = currTurn[0]
        val newGameState = gameState.value?.copy(gridState = String(gridAsCharArray))

        Log.i("setCell", "grid state is ${gameState.value?.gridState}")

        if (newGameState != null){
            insert(newGameState)
            _gameState.value = newGameState!!
            Log.i("setCell", "new grid state is ${newGameState.gridState}")

        }
    }

    private fun stringToArray(string: String?) : CharArray{
        val arr = CharArray(9)
        if (string != null) {
            for (i in 0..8) {
                arr[i] = string[i]
            }
        }
        return arr
    }

    private fun convertSpotToCellNum(x: Int, y: Int): Int {
        var cellNum = 0
        Log.i("convertSpotToCell", "entered with $x $y")
        when (Pair(x, y)) {
            Pair(1, 1) -> {
                cellNum = 1
            }
            Pair(1, 2) -> {
                cellNum = 2
            }
            Pair(1, 3) -> {
                cellNum = 3
            }
            Pair(2, 1) -> {
                cellNum = 4
            }
            Pair(2, 2) -> {
                cellNum = 5
            }
            Pair(2, 3) -> {
                cellNum = 6
            }
            Pair(3, 1) -> {
                cellNum = 7
            }
            Pair(3, 2) -> {
                cellNum = 8
            }
            Pair(3, 3) -> {
                cellNum = 9
            }
        }
        return cellNum
    }

    fun gridToString(): String {
        return "${Board.cell1.value}${Board.cell2.value}${Board.cell3.value}${Board.cell4.value}${Board.cell5.value}${Board.cell6.value}${Board.cell7.value}${Board.cell8.value}${Board.cell9.value}"
    }

}

