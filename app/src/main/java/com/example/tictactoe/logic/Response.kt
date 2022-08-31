package com.example.tictactoe.logic

import java.util.function.IntToDoubleFunction

data class Response(
    val game: String,
    val player: String,
    val recommendation: Int,
    val strength: Int
) {
}