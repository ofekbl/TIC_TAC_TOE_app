package com.example.tictactoe.presentation

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.tictactoe.R

class MainActivity : AppCompatActivity(), MainContract.View {

    private var mvpFactory = MainActivityMVPFactory()

    private var presenter: MainContract.Presenter? = null
    private lateinit var humanPlayerButton: Button
    private lateinit var aiPlayerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createPresenterAndModel()
        setContentView(R.layout.activity_main)

        humanPlayerButton = findViewById(R.id.human_player)
        aiPlayerButton = findViewById(R.id.AI_player)

        humanPlayerButton.setOnClickListener {
            val intent = presenter?.playerVSPlayerButtonClicked( this)
            startActivity(intent)
        }

        aiPlayerButton.setOnClickListener{
            val intent = presenter?.playerVSAIButtonClicked(this)
            startActivity(intent)
        }
    }

    private fun createPresenterAndModel(){
        presenter = mvpFactory.createViewAndPresenter(this)
        mvpFactory.createModel()
    }

    override fun setPlayerVsPlayerButtonText(text: String) {
        humanPlayerButton.text = "test"
    }

    override fun setPlayerVsAiButtonText(text: String) {
        humanPlayerButton.text = "test1"
    }

    override fun setPlayerVsPlayerButtonTextColor(color: Int){
        humanPlayerButton.setTextColor(color)
    }
    override fun setPlayerVsAiButtonTextColor(color: Int){
        aiPlayerButton.setTextColor(color)
    }
    override fun setPlayerVsPlayerButtonColor(color: Int){
        humanPlayerButton.setBackgroundColor(color)
    }
    override fun setPlayerVsAiButtonColor(color: Int){
        aiPlayerButton.setBackgroundColor(color)

    }
}