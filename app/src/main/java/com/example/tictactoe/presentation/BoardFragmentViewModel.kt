package com.example.tictactoe.presentation

import android.util.Log
import androidx.lifecycle.*
import com.example.tictactoe.*
import com.example.tictactoe.database.GameRepository
import com.example.tictactoe.database.GameState
import com.example.tictactoe.logic.Board
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class BoardFragmentViewModel(private val repository: GameRepository): ViewModel() {

    private val _gameState = MutableLiveData<GameState>()
    val gameState: LiveData<GameState>
        get() = _gameState

    init {
        viewModelScope.launch {
            _gameState.value = repository.getLatestGameState()
        }
    }
    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(gameState: GameState) = viewModelScope.launch {
        repository.insert(gameState)
    }

    enum class Turn{
        X,
        O
    }

    lateinit var playerO: Player
    var playerX = HumanPlayer()
    lateinit var gridState : String
    var turnLabel = "Turn X"
    var gameType = 0
    private var firstTurn = Turn.X
    var nextTurn : String = firstTurn.toString()
    var winner = "no one"
    private var currentPlayer = playerX as Player
    var gameOverState = false
    var currentTurn = firstTurn
    private val cells = Cells()

    data class Result(val valid: Boolean, val x: Int, val y: Int, val sign: String)

    fun isGameOver(): Boolean {
        Log.i("Communicator", "is game over")

        if (Column.isFull() or Row.isFull() or Diagonal.isFull()) {
            if (currentPlayer.sign == "X")
                winner = "O"
            else {
                winner = "X"
            }
            gameOverState = true
            endGame()
            val newGameState = gameState.value?.copy(nextTurn = "X", winner = "no one", isGameOver = false,  gridState = "---------")
            if (newGameState != null) {
                Log.i("tryToMakeA..", "newGameState is not null then insert")
                insert(newGameState)
            }
            return true
        }

        else if(cells.isFull()){
            if (currentPlayer.sign == "X")
                winner = "O"
            else
                winner = "X"
            winner = currentPlayer.sign
            gameOverState = true
            endGame()
            //start new game for the next time
            val newGameState = gameState.value?.copy(nextTurn = "X", winner = "no one", isGameOver = false,  gridState = "---------")
            if (newGameState != null) {
                Log.i("tryToMakeA..", "newGameState is not null then insert")
                insert(newGameState)
            }
            return true
        }
        return false
    }

    private fun endGame(){
        val newGameState = gameState.value?.copy(nextTurn = nextTurn, winner = winner, isGameOver = true,  gridState = gridState)
        if (newGameState != null) {
            Log.i("tryToMakeA..", "newGameState is not null then insert")
            insert(newGameState)
        }
    }

    private fun isTakenSpot(rowNum : Int, colNum: Int) : Boolean {
        Log.i("Communicator", "is taken spot")

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
        if (currentTurn == Turn.X){
            currentTurn = Turn.O
        }
        else {
            currentTurn = Turn.X
        }
        turnLabel = "Turn ${currentTurn}"
        return turnLabel
    }

    fun tryToMakeAMove(x: Int, y: Int): Result {
        var result: Result

        Log.i("board viewmodel", "try to make a move")

        result = Result(false, x, y, currentPlayer.sign)


        if (!isTakenSpot(x, y)) {
            result = Result(true, x, y, currentPlayer.sign)

            if (!isGameOver()) {
                    if(currentPlayer is HumanPlayer) {
                        Log.i("tryToMakeAMove", "if currentPlayer is human")
                        currentPlayer.play(x, y)
                        gridState = gridToString()
                        Log.i("isTakenSpot", "before insertion")

                        changeCurrentPlayer()
                        nextTurn = changeTurns()
                        val newGameState = gameState.value?.copy(nextTurn = nextTurn, winner = winner, isGameOver = gameOverState,  gridState = gridState)
                        if (newGameState != null) {
                            Log.i("tryToMakeA..", "newGameState is not null then insert")
                            insert(newGameState)
                        }
                    }

                    if (gameType == 2) { //AI
                        if (!isGameOver()) {
                            val spot = (playerO as ComputerPlayer).play()
                            val randomRow = spot[0]
                            val randomCol = spot[1]
                            currentPlayer.play(randomRow, randomCol)
                            //boardFragment.setCell(randomRow, randomCol, _currentPlayer.value?.sign)
                            //_shouldSetCell.value = true
                            changeCurrentPlayer()
                            //boardFragment.setTurnLabel("Turn ${_currentPlayer.value?.sign}")
                            nextTurn = changeTurns()
                            val newGameState = gameState.value?.copy(nextTurn = nextTurn, winner = winner, isGameOver = gameOverState,  gridState = gridState)
                            if (newGameState != null) {
                                Log.i("tryToMakeA..", "newGameState is not null then insert")
                                insert(newGameState)
                            }
                        }
                    }
                }
            }
            return result
        }

    fun gridToString(): String {
        return "${Board.cell1.value}${Board.cell2.value}${Board.cell3.value}${Board.cell4.value}${Board.cell5.value}${Board.cell6.value}${Board.cell7.value}${Board.cell8.value}${Board.cell9.value}"
    }

}

