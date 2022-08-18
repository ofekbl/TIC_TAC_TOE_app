package com.example.tictactoe

import android.util.Log

abstract class Player {
    abstract var sign: String
    var isDone = false
    val gameHandler = GameHandler()
    open var type = "player"
    open fun play(rowNum : Int, colNum : Int){}
}

class ComputerPlayer : Player() {
    var randomRow: Int = 0
    var randomColumn: Int = 0
    override var type: String = "ai"
    override lateinit var sign: String

    fun play() : Array<Int>{
      isDone = false
      val spot = arrayOf(0, 0)
    //computer's turn
    randomRow = (1..3).random()
    randomColumn = (1..3).random()
    Log.i("play for ai", "The spot entered is: $randomRow $randomColumn")

    if (gameHandler.isTakenSpot(randomRow, randomColumn)) { //check that the spot is not already taken
        do { //trying to choose a spot that is not taken
            randomRow = (1..3).random()
            randomColumn = (1..3).random()
        } while (gameHandler.isTakenSpot(randomRow, randomColumn))
    }

    else {
        gameHandler.addToGrid(randomRow, randomColumn, sign)
        spot[0] = randomRow
        spot[1] = randomColumn
        Log.i("PLay AI", "chosen AI spot is ${spot[0]}, ${spot[1]}")
    }
      isDone = true
      return spot
}
}

class HumanPlayer : Player(){
    override var type: String = "human"
    override lateinit var sign: String

    override fun play(rowNum : Int, colNum : Int) {
        isDone = false
        Log.i("human player play", "play with human player")
        gameHandler.addToGrid(rowNum, colNum, sign)
        isDone = true
    }
}

