package com.example.tictactoe.presentation
class MainActivityPresenter : MainContract.Presenter {

    private var view: MainContract.View? = null
    private val model = MainActivityModel()

    override fun setMainView(view: MainContract.View) {
        this.view = view
    }

    override fun playerVSPlayerButtonClicked() {
       view?.openGameActivity("player type", 1)
    }

    override fun playerVSAIButtonClicked() {
        view?.openGameActivity("player type", 2)
    }

    override fun viewCreated() {
        view?.setPlayerVsAiButtonText(model.buttonText)
        view?.setPlayerVsAiButtonColor(model.buttonColor)
        view?.setPlayerVsAiButtonTextColor(model.buttonTextColor)
        view?.setPlayerVsPlayerButtonText(model.buttonText)
        view?.setPlayerVsPlayerButtonColor(model.buttonColor)
        view?.setPlayerVsPlayerButtonTextColor(model.buttonTextColor)
    }
}