package com.example.tictactoe

object Communicate {
    fun getInputFromTerminal() : String?{
        return readLine()
    }
    fun printOutputToTerminal(output: String){
        println(output)
    }
}