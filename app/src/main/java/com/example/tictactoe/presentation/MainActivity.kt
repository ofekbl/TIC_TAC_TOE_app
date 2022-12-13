package com.example.tictactoe.presentation

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.tictactoe.Consts
import com.example.tictactoe.R
import java.time.LocalDate
import java.util.*

class MainActivity : AppCompatActivity(), MainContract.View {
    private var mvpFactory: MainActivityMVPFactory? = null
    private var sharedPref: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null

    private var presenter: MainContract.Presenter? = null
    private var humanPlayerButton: Button? = null
    private var aiPlayerButton: Button? = null
    private var lastPlayedTextView: TextView? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPref = this.getSharedPreferences(Consts.MAIN_ACTIVITY_SHARED_PREF_TABLE, Context.MODE_PRIVATE)
        editor = sharedPref?.edit()
        mvpFactory = MainActivityMVPFactory(sharedPref)
        createPresenterAndModel()

        humanPlayerButton = findViewById(R.id.human_player)
        aiPlayerButton = findViewById(R.id.AI_player)
        lastPlayedTextView = findViewById(R.id.Last_Seen)

        humanPlayerButton?.setOnClickListener {
            presenter?.playerVSPlayerButtonClicked()
            editor?.putString(Consts.LAST_PLAYED_TIME, LocalDate.now().toString())
            editor?.apply()
            Log.i("Human button clicked", LocalDate.now().toString())
        }

        aiPlayerButton?.setOnClickListener {
            presenter?.playerVSAIButtonClicked()
            editor?.putString(Consts.LAST_PLAYED_TIME, LocalDate.now().toString())
            editor?.apply()

            Log.i("AI button clicked", LocalDate.now().toString())
        }
        presenter?.viewCreated()
    }

    private fun createPresenterAndModel() {
        mvpFactory?.createComponents(this)
        this.presenter = mvpFactory?.presenter
    }

    override fun setPlayerVsPlayerButtonText(text: String) {
        humanPlayerButton?.text = text
    }

    override fun setPlayerVsAiButtonText(text: String) {
        aiPlayerButton?.text = text
    }

    override fun setPlayerVsPlayerButtonTextColor(color: Int) {
        humanPlayerButton?.setTextColor(color)
    }

    override fun setPlayerVsAiButtonTextColor(color: Int) {
        aiPlayerButton?.setTextColor(color)
    }

    override fun setPlayerVsPlayerButtonColor(color: Int) {
        humanPlayerButton?.setBackgroundColor(color)
    }

    override fun setPlayerVsAiButtonColor(color: Int) {
        aiPlayerButton?.setBackgroundColor(color)
    }

    override fun setLastPlayedText(date: String){
        lastPlayedTextView?.text = date
    }

    override fun openGameActivity(gameType: Int) {
        val intent = Intent(this, GameActivity::class.java)
        intent.putExtra(Consts.PLAYER_TYPE, gameType)
        startActivity(intent)
    }
}