package com.example.tictactoe.presentation

import android.graphics.Color


data class MainActivityModel(
    override var buttonTextAI: String = "Play vs AI Player",
    override var buttonColorAI : Int = Color.BLACK,
    override var buttonTextColorAI : Int = Color.MAGENTA,
    override var buttonTextHuman: String = "Play vs Human Player",
    override var buttonColorHuman : Int = Color.BLACK,
    override var buttonTextColorHuman : Int = Color.MAGENTA,
    override var lastPlayed : String = ""
) : MainContract.Model