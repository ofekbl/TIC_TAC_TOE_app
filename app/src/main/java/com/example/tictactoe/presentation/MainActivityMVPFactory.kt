package com.example.tictactoe.presentation

import android.content.SharedPreferences
import android.util.Log
import com.example.tictactoe.Consts

class MainActivityMVPFactory(private val sharedPreferences: SharedPreferences?) {

    var presenter : MainActivityPresenter? = null
    var model : MainActivityModel? = null

    fun createViewAndPresenter(view: MainContract.View) : MainContract.Presenter {
        presenter = MainActivityPresenter()
        presenter!!.setMainView(view)
        return presenter as MainActivityPresenter
    }

    fun createModel(){
        val lastPlayedText = sharedPreferences?.getString(Consts.LAST_PLAYED_TIME, "12-13-22")
        model = MainActivityModel(lastPlayed = lastPlayedText!!)
        Log.i("CreateModel", "last played time from sharedPrefs is $lastPlayedText")

    }
}