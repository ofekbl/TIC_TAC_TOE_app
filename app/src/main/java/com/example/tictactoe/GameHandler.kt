package com.example.tictactoe

class GameHandler {
    var turn = "X"
    var winner: String = ""
    private val playerX = HumanPlayer()
    private val playerO = ComputerPlayer()
    private val cells = Cells()


    fun game() {
        turn = "X"
        Communicate.printOutputToTerminal("Hello! Welcome to tic tac toe game!")

        while (!isGameOver()) {
            if (turn == "X") {
                playerX.play()
            } else
                playerO.play()
        }

        if (winner != "") {
            Communicate.printOutputToTerminal("Game is over! $winner is the winner")
        } else {
            Communicate.printOutputToTerminal("Game is over! It's a tie :$")
        }
    }

    fun isGameOver(): Boolean {
        if (Column.isFull() or Row.isFull() or Diagonal.isFull()) {
            return true
        }
        else if(cells.isFull()){
            return true
        }
        return false
    }

    fun addToGrid(rowNum : Int, columnNum: Int) {
        if(!isTakenSpot(rowNum, columnNum)) {
            Board.gameGrid[rowNum - 1][columnNum - 1].value = turn  // 1, 2 -> cell2
        }

        for (i in 0 until 3) {
            var j = 0
            Communicate.printOutputToTerminal(Board.gameGrid[i][j].value + " " + Board.gameGrid[i][j + 1].value + " " + Board.gameGrid[i][j + 2].value)
        }

        if (turn == "X"){
            turn = "O"
        }

        else{
            turn = "X"
        }
    }

    fun isTakenSpot(rowNum : Int, colNum: Int) : Boolean {
        if(Board.gameGrid[rowNum - 1][colNum - 1].value != "-"){
            return true
        }
        return false
    }
}
