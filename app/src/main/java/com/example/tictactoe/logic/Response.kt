package com.example.tictactoe.logic

data class Response<T>(
    val game: String,
    val player: String,
    val recommendation: Int,
    val strength: Int
) {
}