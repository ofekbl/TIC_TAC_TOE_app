package com.example.tictactoe

open class Cells {
    val gameHandler = GameHandler()
    open fun isFull() : Boolean {
        for (i in 0 until 3){
            for (j in 0 until 3) {
                if (Board.gameGrid[i][j].value == "-") {
                    return false
                }
            }
        }
        return true
    }
}

object Row : Cells(){
    override fun isFull() : Boolean{
        for (i in 0 until 3){
            if (((Board.gameGrid[i][0].value == "X") and (Board.gameGrid[i][1].value == "X")) and (Board.gameGrid[i][2].value == "X")) {
                gameHandler.winner = "X"
                return true
            }
            else if (((Board.gameGrid[i][0].value == "O") and (Board.gameGrid[i][1].value == "O") and (Board.gameGrid[i][2].value == "O"))) {
                gameHandler.winner = "O"
                return true
            }
        }
        return false
    }
}

object Column : Cells(){
    override fun isFull(): Boolean {
        for (i in 0 until 3){
            if (((Board.gameGrid[0][i].value == "X") and (Board.gameGrid[1][i].value == "X") and (Board.gameGrid[2][i].value == "X"))) {
                gameHandler.winner = "X"
                return true
            }
            else if (((Board.gameGrid[0][i].value == "O") and (Board.gameGrid[1][i].value == "O") and (Board.gameGrid[2][i].value == "O"))) {
                gameHandler.winner = "O"
                return true
            }
        }
        return false
    }
}

object Diagonal : Cells(){
    override fun isFull(): Boolean {
        //right to left diagonal
        if (((Board.gameGrid[0][0].value == "X") and (Board.gameGrid[1][1].value == "X") and (Board.gameGrid[2][2].value == "X"))) {
            gameHandler.winner = "X"
            return true
        }
        else if (((Board.gameGrid[0][0].value == "O") and (Board.gameGrid[1][1].value == "O") and (Board.gameGrid[2][2].value == "O"))) {
            gameHandler.winner = "O"
            return true
        }

        //left to right diagonal
        else if (((Board.gameGrid[0][2].value == "X") and (Board.gameGrid[1][1].value == "X") and (Board.gameGrid[2][0].value == "X"))) {
            gameHandler.winner = "X"
            return true
        }
        else if (((Board.gameGrid[0][2].value == "O") and (Board.gameGrid[1][1].value == "O") and (Board.gameGrid[2][0].value == "O"))) {
            gameHandler.winner = "O"
            return true
        }
        return false
    }
}
