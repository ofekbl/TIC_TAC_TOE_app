package com.example.tictactoe.logic

class PlayerBuilder  {
    fun build(playerType: String?) : Player{
        if(playerType.equals("human")){
            return HumanPlayer()
        }
        return ComputerPlayer()
    }
}