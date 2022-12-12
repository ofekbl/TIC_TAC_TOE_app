package com.example.tictactoe.presentation

import android.content.Context
import android.content.Intent

interface MainContract {

    interface Model{
        var buttonText : String
        var buttonColor : Int
        var buttonTextColor : Int
    }

    interface View {
        fun setPlayerVsPlayerButtonText(text: String)
        fun setPlayerVsAiButtonText(text: String)
        fun setPlayerVsPlayerButtonTextColor(color: Int)
        fun setPlayerVsAiButtonTextColor(color: Int)
        fun setPlayerVsPlayerButtonColor(color: Int)
        fun setPlayerVsAiButtonColor(color: Int)

    }

    interface Presenter {
        fun setMainView(view: View)
        fun playerVSPlayerButtonClicked(context: Context): Intent
        fun playerVSAIButtonClicked(context: Context): Intent
        fun setPLayerVSAiButton(textColor: Int, buttonColor: Int, buttonText: String)
        fun setPLayerVSPlayerButton(textColor: Int, buttonColor: Int, buttonText: String)
    }
}