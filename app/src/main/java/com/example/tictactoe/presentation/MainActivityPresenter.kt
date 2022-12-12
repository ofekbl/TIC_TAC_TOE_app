package com.example.tictactoe.presentation

import android.content.Context
import android.content.Intent

class MainActivityPresenter : MainContract.Presenter {

    private var view: MainContract.View? = null
    val model = MainActivityModel()

    override fun setMainView(view: MainContract.View) {
        this.view = view
    }

    override fun playerVSPlayerButtonClicked(context: Context): Intent {
        val intent = Intent(context, GameActivity::class.java)
        intent.putExtra("player type", 1)
        return intent
    }

    override fun playerVSAIButtonClicked(context: Context): Intent {
        val intent = Intent(context, GameActivity::class.java)
        intent.putExtra("player type", 2)
        return intent
    }

    override fun setPLayerVSAiButton(textColor: Int, buttonColor: Int, buttonText: String){
        view?.setPlayerVsAiButtonText(buttonText)
        view?.setPlayerVsAiButtonColor(buttonColor)
        view?.setPlayerVsAiButtonTextColor(textColor)
    }

    override fun setPLayerVSPlayerButton(textColor: Int, buttonColor: Int, buttonText: String){
        view?.setPlayerVsPlayerButtonColor(buttonColor)
        view?.setPlayerVsPlayerButtonText(buttonText)
        view?.setPlayerVsPlayerButtonTextColor(textColor)
    }

}