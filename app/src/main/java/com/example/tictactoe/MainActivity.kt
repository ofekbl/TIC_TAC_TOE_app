package com.example.tictactoe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.navigation.findNavController

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val humanPlayerButtonClick = findViewById<Button>(R.id.human_player)
        val aiPlayerButtonClick = findViewById<Button>(R.id.AI_player)
        humanPlayerButtonClick.setOnClickListener {
            val intent: Intent = Intent(this, GameScreenActivity::class.java)
            startActivity(intent)
        }
        aiPlayerButtonClick.setOnClickListener{
            val intent: Intent = Intent(this, GameScreenActivity::class.java)
            startActivity(intent)
        }
    }
}