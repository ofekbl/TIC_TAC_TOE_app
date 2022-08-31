package com.example.tictactoe.logic

object Communicate {
    fun getInputFromTerminal() : String?{
        return readLine()
    }
    fun printOutputToTerminal(output: String){
        println(output)
    }
}