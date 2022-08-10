package com.example.tictactoe

abstract class Player {
    val gameHandler = GameHandler()
    open fun play(){}
}

class ComputerPlayer : Player() {
    override fun play(){
        //computer's turn
        var randomRow = (1..3).random()
        var randomColumn = (1..3).random()
        Communicate.printOutputToTerminal("Player O turn. The spot entered is: $randomRow $randomColumn")

        if (gameHandler.isTakenSpot(randomRow, randomColumn)) { //check that the spot is not already taken
            do { //trying to choose a spot that is not taken
                var randomRow = (1..3).random()
                var randomColumn = (1..3).random()
            } while (gameHandler.isTakenSpot(randomRow, randomColumn))
        }

        else {
            gameHandler.addToGrid(randomRow, randomColumn)
        }
    }
}

class HumanPlayer : Player(){
    override fun play() {
        Communicate.printOutputToTerminal("Player X turn. Enter row column numbers to fix spot: ")
        var userInputX: String? = Communicate.getInputFromTerminal()//read user input
        var rowNumInt: Int
        var columnNumInt: Int
        if (userInputX != null) { //change input from string to int
            rowNumInt = userInputX[0].digitToInt() //1
            columnNumInt = userInputX[2].digitToInt() //2

            if (gameHandler.isTakenSpot(rowNumInt, columnNumInt)) { //check that the spot is not already taken
                do {
                    Communicate.printOutputToTerminal("The spot is taken. Please enter another spot: ")
                    userInputX = Communicate.getInputFromTerminal() //1 2
                    if (userInputX != null) { //change input from string to int
                        rowNumInt = userInputX[0].digitToInt() //2
                        columnNumInt = userInputX[2].digitToInt() //2
                    }
                } while (gameHandler.isTakenSpot(rowNumInt, columnNumInt))
                gameHandler.addToGrid(rowNumInt, columnNumInt)
            }
            else{
                gameHandler.addToGrid(rowNumInt, columnNumInt)
            }
        }

    }
}