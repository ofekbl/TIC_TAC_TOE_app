package com.example.tictactoe.presentation

import android.util.Log
import androidx.lifecycle.*
import com.example.tictactoe.*
import com.example.tictactoe.database.GameRepository
import com.example.tictactoe.database.GameState
import com.example.tictactoe.logic.Board
import kotlinx.coroutines.launch

class BoardFragmentViewModel(private val repository: GameRepository): ViewModel() {

    val gameState : LiveData<GameState?>? = repository.getLatestGameState?.asLiveData()


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

    val _result = MutableLiveData<Result>()
    var result: LiveData<Result>? = null
        get() = _result

    private val _turnLabel = MutableLiveData<String>()
    val turnLabel: LiveData<String>
        get() = _turnLabel

    private val _firstTurn = MutableLiveData<Turn>()
    val firstTurn: LiveData<Turn>
        get() = _firstTurn

    private val _currentTurn = MutableLiveData<Turn>()
    val currentTurn: LiveData<Turn>
        get() = _currentTurn

    private val _winner = MutableLiveData<String>()
    val winner: LiveData<String>
        get() = _winner

    private val _currentPlayer = MutableLiveData<Player>()
    val currentPlayer: LiveData<Player>
        get() = _currentPlayer

    private val _isGameOver = MutableLiveData<Boolean>()
    val isGameOver: LiveData<Boolean>
        get() = _isGameOver

    val _gameType = MutableLiveData<Int>()
    val gameType: LiveData<Int>
        get() = _gameType

    lateinit var playerO: Player
    var playerX = HumanPlayer()


    init {
        _firstTurn.value = Turn.X
        _currentTurn.value = _firstTurn.value
        _winner.value = "no one"
        _currentPlayer.value = playerX
        _isGameOver.value = false
        _gameType.value = 0
        _result.value = Result(false, 0, 0, "-")
        _turnLabel.value = "Turn X"
    }


    private val cells = Cells()

    fun isGameOver(): Boolean {
        Log.i("Communicator", "is game over")

        if (Column.isFull() or Row.isFull() or Diagonal.isFull()) {
            if (currentPlayer.value?.sign == "X")
                _winner.value = "O"
            else
                _winner.value = "X"
            _isGameOver.value = true
            return true
        }
        else if(cells.isFull()){
            if (currentPlayer.value?.sign == "X")
                _winner.value = "O"
            else
                _winner.value = "X"
            _winner.value = currentPlayer.value?.sign
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

    private fun changeCurrentPlayer(){
        if (_currentPlayer.value == playerO)
            _currentPlayer.value = playerX
        else
            _currentPlayer.value = playerO
    }

    fun changeTurns() {
        if (_currentTurn.value == Turn.X){
            _currentTurn.value = Turn.O
        }
        else {
            _currentTurn.value = Turn.X
        }
        _turnLabel.value = "Turn ${_currentTurn.value}"
    }

    data class Result(val valid: Boolean, val x: Int, val y: Int, val sign: String?)


    fun tryToMakeAMove(x: Int, y: Int):LiveData<Result>? {
        Log.i("board viewmodel", "try to make a move")

        _result.value = Result(false, x, y, _currentPlayer.value?.sign)


        if (!isTakenSpot(x, y)) {
            _result.value = Result(true, x, y, _currentPlayer.value?.sign)
            if (!isGameOver()) {
                if(_currentPlayer.value is HumanPlayer) {
                    Log.i("tryToMakeAMove", "if currentPlayer is human")
                    _currentPlayer.value?.play(x, y)
                    //boardFragment.setCell(x, y, _currentPlayer.value?.sign)
                    changeCurrentPlayer()
                    //boardFragment.setTurnLabel("Turn ${_currentPlayer.value?.sign}")
                    changeTurns()
                }
                if (gameType.value == 2) { //AI
                    if (!isGameOver()) {
                        val spot = (playerO as ComputerPlayer).play()
                        val randomRow = spot[0]
                        val randomCol = spot[1]
                        //boardFragment.setCell(randomRow, randomCol, _currentPlayer.value?.sign)
                        //_shouldSetCell.value = true
                        changeCurrentPlayer()
                        //boardFragment.setTurnLabel("Turn ${_currentPlayer.value?.sign}")
                        changeTurns()
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

