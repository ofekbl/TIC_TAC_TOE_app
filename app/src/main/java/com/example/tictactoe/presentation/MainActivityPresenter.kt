package com.example.tictactoe.presentation

import com.example.tictactoe.Consts

class MainActivityPresenter : MainContract.Presenter {

    private var view: MainContract.View? = null
    private val model = MainActivityModel()

    fun setMainView(view: MainContract.View) {
        this.view = view
    }

    override fun playerVSPlayerButtonClicked() {
        view?.openGameActivity(Consts.HUMAN_PLAYER_TYPE)
    }

    override fun playerVSAIButtonClicked() {
        view?.openGameActivity(Consts.AI_PLAYER_TYPE)
    }

    override fun viewCreated() {
        view?.apply {
            setPlayerVsAiButtonText(model.buttonTextAI)
            setPlayerVsAiButtonColor(model.buttonColorAI)
            setPlayerVsAiButtonTextColor(model.buttonTextColorAI)
            setPlayerVsPlayerButtonText(model.buttonTextHuman)
            setPlayerVsPlayerButtonColor(model.buttonColorHuman)
            setPlayerVsPlayerButtonTextColor(model.buttonTextColorHuman)
            setLastPlayedText(model.lastPlayed)
        }
    }
}