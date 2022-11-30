package com.example.tictactoe.logic

import android.util.Log

open class Cells {
    open fun isFull(): Boolean {
        for (i in 0 until 3) {
            for (j in 0 until 3) {
                if (Board.gameGrid[i][j].value == "-") {
                    return false
                }
            }
        }
        return true
    }

    open fun invokeX(i: Int, j: Int): Boolean {
        if (Board.gameGrid[i][j].value == "X") {
            return true
        }
        return false
    }


    open fun invokeO(i: Int, j: Int): Boolean {
        if (Board.gameGrid[i][j].value == "O") {
            return true
        }
        return false
    }


    object Row : Cells() {
        override fun isFull(): Boolean {
            var isFull = false
            for (i in 0 until 3) {
                Log.i("isFull row", "entered loop")
                isFull = (invokeX(i, 0) and invokeX(i, 1) and invokeX(i, 2)) or
                        (invokeO(i, 0) and invokeO(i, 1) and invokeO(i,2))
                if (isFull)
                    return isFull
            }
            return isFull
        }
    }

    object Column : Cells() {
        override fun isFull(): Boolean {
            var isFull = false
            for (j in 0 until 3) {
                Log.i("isFull row", "entered loop")
                isFull = (invokeX(0, j) and invokeX(1, j) and invokeX(2, j)) or
                        (invokeO(0, j) and invokeO(1, j) and invokeO(2, j))
                if (isFull)
                    return isFull
            }
            return isFull
        }
    }

object Diagonal : Cells(){
    override fun isFull(): Boolean {
        //right to left diagonal
        if (((Board.gameGrid[0][0].value == "X") and (Board.gameGrid[1][1].value == "X") and (Board.gameGrid[2][2].value == "X"))) {
            return true
        }
        else if (((Board.gameGrid[0][0].value == "O") and (Board.gameGrid[1][1].value == "O") and (Board.gameGrid[2][2].value == "O"))) {
            return true
        }

            //left to right diagonal
            else if (((Board.gameGrid[0][2].value == "X") and (Board.gameGrid[1][1].value == "X") and (Board.gameGrid[2][0].value == "X"))) {
                return true
            } else if (((Board.gameGrid[0][2].value == "O") and (Board.gameGrid[1][1].value == "O") and (Board.gameGrid[2][0].value == "O"))) {
                return true
            }
            return false
        }
    }
}
