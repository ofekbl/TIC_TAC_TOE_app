package com.example.tictactoe.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.tictactoe.R

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val suggesterApi = RetrofitHelper.getInstance().create(SuggesterApi::class.java)
//        GlobalScope.launch {
//            val result = suggesterApi.getBestMove()
//            if (result != null)
//                Log.d("ayush: ", result.body().toString())
//        }

        val humanPlayerButtonClick = findViewById<Button>(R.id.human_player)
        val aiPlayerButtonClick = findViewById<Button>(R.id.AI_player)
        humanPlayerButtonClick.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            intent.putExtra("player type", 1)
            startActivity(intent)
        }
        aiPlayerButtonClick.setOnClickListener{
            val intent = Intent(this, GameActivity::class.java)
            intent.putExtra("player type", 2)
            startActivity(intent)
        }
    }
}