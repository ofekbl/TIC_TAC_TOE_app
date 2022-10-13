package com.example.tictactoe.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tictactoe.Cells
import com.example.tictactoe.HumanPlayer
import com.example.tictactoe.Player

class BoardFregmentViewModel : ViewModel() {
    private val _turn = MutableLiveData<String>()
    val turn: LiveData<String>
        get() = _turn

    enum class Turn{
        X,
        O
    }

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


    init {
        _firstTurn.value = Turn.X
        _currentTurn.value = _firstTurn.value
        _winner.value = "no one"
        _currentPlayer.value = HumanPlayer()
    }

    //lateinit var playerO: Player
    //var playerX = HumanPlayer()
//    private var currentPlayer : Player = playerX
    private val cells = Cells()


}