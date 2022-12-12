package com.example.tictactoe.presentation

class MainActivityMVPFactory {

    var presenter : MainActivityPresenter? = null
    var model : MainActivityModel? = null

    fun createViewAndPresenter(view: MainContract.View) : MainContract.Presenter {
        presenter = MainActivityPresenter()
        presenter!!.setMainView(view)
        return presenter as MainActivityPresenter
    }

    fun createModel(){
        model = MainActivityModel()
    }
}