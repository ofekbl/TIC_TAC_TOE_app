package com.example.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.tictactoe.databinding.ActivityGameBinding
import com.example.tictactoe.databinding.FragmentBoardBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


class GameActivity : AppCompatActivity() {


    private lateinit var binding: ActivityGameBinding

    private val movesFragment = MovesFragment()
    private val boardFragment = BoardFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("onCreate")
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
        var playerType = getIntent().getIntExtra("player type", 0)
        println("player type is: $playerType")
    }
//        //checks which type of player should be O
//        var playerX = HumanPlayer()
//        var playerO: Player
//        if (playerType == 0){
//            playerO = HumanPlayer()
//        }
//        else{
//            playerO = ComputerPlayer()
//        }
//    }


        private fun replaceFragment(fragment: Fragment) {
            println("replace fragment: $fragment")
            if (fragment != null) {
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.fragment_container, fragment)
                transaction.commit()
            }
        }

    //onClick
    fun boardTapped(view: android.view.View) {
        if (view !is Button){
            return
        }
        boardFragment.addToVisualBoard(view)
    }
}
