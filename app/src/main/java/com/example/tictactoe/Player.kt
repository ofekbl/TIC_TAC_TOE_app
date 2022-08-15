package com.example.tictactoe

import android.util.Log

abstract class Player {
    val gameHandler = GameHandler()
    val communicator = Communicator()
    open var type = "player"
    open fun play(rowNum : Int, colNum : Int){}

    open fun changeTurns() {
        if (communicator.currentTurn == Communicator.Turn.X){
            Log.i(
                "changeTurns",
                "current turn is: ${communicator.currentTurn} after this line the change occurs"
            )
            communicator.currentTurn = Communicator.Turn.O
            Log.i("changeTurns", "current turn after the change is: ${communicator.currentTurn}")
        }
        else
            communicator.currentTurn = Communicator.Turn.X
    }
}

class ComputerPlayer : Player() {
    override var type: String = "ai"
//        get() = super.type
//        set(value) {}

     override fun play(rowNum : Int, colNum : Int){
        //computer's turn
        var randomRow = (1..3).random()
        var randomColumn = (1..3).random()
        Communicate.printOutputToTerminal("Player O turn. The spot entered is: $randomRow $randomColumn")

        if (communicator.isTakenSpot(randomRow, randomColumn)) { //check that the spot is not already taken
            do { //trying to choose a spot that is not taken
                var randomRow = (1..3).random()
                var randomColumn = (1..3).random()
            } while (communicator.isTakenSpot(randomRow, randomColumn))
        }

        else {
            gameHandler.addToGrid(randomRow, randomColumn, communicator.currentTurn)
            changeTurns()

//            Log.i("ai play", "add to gris of ai player")
//            Log.i("ai play", "change turns")

        }
    }
}

class HumanPlayer : Player(){
    override var type: String = "human"
//        get() = super.type
//        set(value) {}
    //override fun play() {
    override fun play(rowNum : Int, colNum : Int) {
        gameHandler.addToGrid(rowNum, colNum, communicator.currentTurn)
        changeTurns()
}
}

        //Communicate.printOutputToTerminal("Player X turn. Enter row column numbers to fix spot: ")
        //var userInputX: String? = Communicate.getInputFromTerminal()//read user input
//        var rowNumInt: Int
//        var columnNumInt: Int
//        if (userInputX != null) { //change input from string to int
//            rowNumInt = userInputX[0].digitToInt() //1
//            columnNumInt = userInputX[2].digitToInt() //2

//            if (gameHandler.isTakenSpot(rowNumInt, columnNumInt)) { //check that the spot is not already taken
//                do {
//                    Communicate.printOutputToTerminal("The spot is taken. Please enter another spot: ")
                    //userInputX = Communicate.getInputFromTerminal() //1 2
//                    if (userInputX != null) { //change input from string to int
//                        rowNumInt = userInputX[0].digitToInt() //2
//                        columnNumInt = userInputX[2].digitToInt() //2
//                    }
//                } while (gameHandler.isTakenSpot(rowNumInt, columnNumInt))
//                gameHandler.addToGrid(rowNumInt, columnNumInt)
//            }
//            else{
//                gameHandler.addToGrid(rowNumInt, columnNumInt)
//            }
//        }
//
//    }

