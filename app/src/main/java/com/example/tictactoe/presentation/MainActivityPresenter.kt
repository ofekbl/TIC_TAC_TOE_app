package com.example.tictactoe.presentation

import android.content.SharedPreferences
import com.example.tictactoe.Consts

class MainActivityPresenter : MainContract.Presenter {

    private var view: MainContract.View? = null
    private var model: MainContract.Model? = null

    fun setMainView(view: MainContract.View) {
        this.view = view
    }

    fun setMainModel(model: MainContract.Model?){
        if (model != null)
            this.model = model
    }

    override fun playerVSPlayerButtonClicked() {
        view?.openGameActivity(Consts.HUMAN_PLAYER_TYPE)
    }

    override fun playerVSAIButtonClicked() {
        view?.openGameActivity(Consts.AI_PLAYER_TYPE)
    }

    override fun viewCreated() {
        view?.apply {
            model?.buttonTextAI?.let { setPlayerVsAiButtonText(it) }
            model?.buttonColorAI?.let { setPlayerVsAiButtonColor(it) }
            model?.buttonTextColorAI?.let { setPlayerVsAiButtonTextColor(it) }
            model?.buttonTextHuman?.let { setPlayerVsPlayerButtonText(it) }
            model?.buttonColorHuman?.let { setPlayerVsPlayerButtonColor(it) }
            model?.buttonTextColorHuman?.let { setPlayerVsPlayerButtonTextColor(it) }
            model?.lastPlayed?.let { setLastPlayedText(it) }
        }
    }
}