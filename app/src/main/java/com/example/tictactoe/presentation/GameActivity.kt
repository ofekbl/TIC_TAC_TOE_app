package com.example.tictactoe.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.example.tictactoe.R
import com.example.tictactoe.databinding.ActivityGameBinding


class GameActivity : AppCompatActivity() {


    private lateinit var binding: ActivityGameBinding

    private val movesFragment = MovesFragment()
    private val boardFragment = BoardFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("game activity", "onCreate")
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigation.setOnItemSelectedListener() {
            when (it.itemId) {
                R.id.movesItemId -> replaceFragment(movesFragment)
                R.id.boardItemId -> replaceFragment(boardFragment)
            }
            true
        }
        //getting the player type that was chosen in the main activity
        val playerType = intent.getIntExtra("player type",1)
        Log.i("Game Activity", "player type is: $playerType")

        val bundle: Bundle = Bundle()
        bundle.putString("playerType", "$playerType")
        boardFragment.arguments = bundle
    }

    private fun replaceFragment(fragment: Fragment) {
        Log.i("Game Activity", "replace fragment: $fragment")
        if (fragment != null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.commit()
        }
    }

    fun showSuggestedMove(click: View){
//        var message = "message"
//        var uid = "123456" //I wanted to use it as int, but mapOf didn't allow me
//        var token = "token"
//        val payload = mapOf("token" to token, "user_id" to uid, "message" to message)
//        get("https://some.api.com/method/some.method", params=payload)
//        val popup = Toast.makeText(this,"Message sent!", Toast.LENGTH_LONG)
//        popup.show()
    }
}
