package com.example.tictactoe.presentation

import android.content.SharedPreferences
import android.util.Log
import com.example.tictactoe.Consts

class MainActivityMVPFactory(private val sharedPreferences: SharedPreferences?) {

    var presenter : MainActivityPresenter? = null
    var model : MainActivityModel? = null

    fun createComponents(view: MainContract.View) {
        presenter = MainActivityPresenter()
        presenter!!.setMainView(view)
        presenter?.setMainModel(createModel())
    }

    fun createViewAndPresenter(view: MainContract.View) : MainContract.Presenter {
        presenter = MainActivityPresenter()
        presenter!!.setMainView(view)
        return presenter as MainActivityPresenter
    }

    private fun createModel() : MainContract.Model {
        val lastPlayedText = sharedPreferences?.getString(Consts.LAST_PLAYED_TIME, "default value")
        model = MainActivityModel(lastPlayed = lastPlayedText!!)
        presenter?.setMainModel(model)
        Log.i("CreateModel", "last played time from sharedPrefs is $lastPlayedText")
        return model as MainActivityModel
    }
}