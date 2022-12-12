package com.example.tictactoe.presentation

interface MainContract {

    interface Model{
        var buttonTextAI: String
        var buttonColorAI : Int
        var buttonTextColorAI : Int
        var buttonTextHuman: String
        var buttonColorHuman : Int
        var buttonTextColorHuman : Int
        var lastPlayed: String
    }

    interface View {
        fun setPlayerVsPlayerButtonText(text: String)
        fun setPlayerVsAiButtonText(text: String)
        fun setPlayerVsPlayerButtonTextColor(color: Int)
        fun setPlayerVsAiButtonTextColor(color: Int)
        fun setPlayerVsPlayerButtonColor(color: Int)
        fun setPlayerVsAiButtonColor(color: Int)
        fun openGameActivity(value: Int)
        fun setLastPlayedText(date: String)
    }

    interface Presenter {
        fun viewCreated()
        fun playerVSPlayerButtonClicked()
        fun playerVSAIButtonClicked()
    }
}