package com.example.tictactoe.presentation

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import androidx.work.OneTimeWorkRequest
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.tictactoe.*
import com.example.tictactoe.database.GameRepository
import com.example.tictactoe.database.GameState
import com.example.tictactoe.logic.Board
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.util.Date.from
import java.util.concurrent.TimeUnit

class BoardFragmentViewModel(private val repository: GameRepository): ViewModel() {

//    private val _shouldScheduleWork = MutableLiveData<Boolean>()
//    val shouldScheduleWork: LiveData<Boolean>
//        get() = _shouldScheduleWork
//
    private val _gameState = MutableLiveData<GameState>()
    val gameState: LiveData<GameState>
        get() = _gameState

    init {
       // _shouldScheduleWork.value = false
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

    lateinit var playerO: Player
    var playerX = HumanPlayer()

    private var currentPlayer = playerX as Player

    private val cells = Cells()


    fun isGameOver(): Boolean {
        Log.i("Communicator", "is game over")

        var newWinner : String
        var gameOverState : Boolean
        if (Column.isFull() or Row.isFull() or Diagonal.isFull()) {
            if (currentPlayer.sign == "X")
                newWinner = "O"
            else {
                newWinner = "X"
            }
            gameOverState = true
            //endGame()
            val newGameState = gameState.value?.copy( winner = newWinner, isGameOver = gameOverState)
            if (newGameState != null) {
                Log.i("tryToMakeA..", "newGameState is not null then insert")
                insert(newGameState)
                _gameState.value = newGameState!!
            }
            return true
        }

        else if(cells.isFull()){
            if (currentPlayer.sign == "X")
                newWinner = "O"
            else
                newWinner = "X"
            //newWinner = currentPlayer.sign
            gameOverState = true
            //endGame()
            //start new game for the next time
            val newGameState = gameState.value?.copy(winner = newWinner, isGameOver = gameOverState)
            if (newGameState != null) {
                Log.i("tryToMakeA..", "newGameState is not null then insert")
                insert(newGameState)
                _gameState.value = newGameState!!
            }
            return true
        }
        return false
    }

//    private fun endGame(){
//        val newGameState = gameState.value?.copy(nextTurn = nextTurn, gameType = gameType, winner = winner, isGameOver = true,  gridState = gameState.value!!.gridState)
//        if (newGameState != null) {
//            Log.i("tryToMakeA..", "newGameState is not null then insert")
//            insert(newGameState)
//            _gameState.value = newGameState!!
//        }
//    }

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
        val nextTurn: String
        if (gameState.value?.nextTurn == "X"){
            nextTurn = "O"
        }
        else {
            nextTurn = "O"
        }
        val newGameState = gameState.value?.copy(nextTurn = nextTurn)
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
        //var newNextTurn: String

        Log.i("board viewModel", "try to make a move")

        if (!isTakenSpot(x, y)) {
            if (!isGameOver()) {
                    if(currentPlayer is HumanPlayer) {
                        Log.i("tryToMakeAMove", "if currentPlayer is human")
                        currentPlayer.play(x, y)
//                        _gameState.value.gridState = gridToString()
                        Log.i("isTakenSpot", "before insertion")

                        changeCurrentPlayer()
                        //newNextTurn = changeTurns()
                        val newGameState = gameState.value?.copy(gridState = gameState.value!!.gridState)
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
                            //boardFragment.setCell(randomRow, randomCol, _currentPlayer.value?.sign)
                            //_shouldSetCell.value = true
                            changeCurrentPlayer()
                            //boardFragment.setTurnLabel("Turn ${_currentPlayer.value?.sign}")
                            //newNextTurn = changeTurns()

                            val newGameState = gameState.value?.copy(gridState = gameState.value!!.gridState)
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
        val cellNum = convertSpotToCellNum(x, y)
        if (gameState.value != null) {
            setCell(cellNum, getCurrTurn(gameState.value!!.nextTurn))
        }
        val newNextTurn = changeTurns()
        val newGameState = gameState.value?.copy(nextTurn = newNextTurn)
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
        if (newGameState != null){
            insert(newGameState)
            _gameState.value = newGameState!!
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

