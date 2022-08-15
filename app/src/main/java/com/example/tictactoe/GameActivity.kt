package com.example.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.tictactoe.databinding.ActivityGameBinding
import kotlinx.android.synthetic.main.activity_main.*


class GameActivity : AppCompatActivity() {


    private lateinit var binding: ActivityGameBinding
    private lateinit var playerO: Player
    private val playerX: Player = HumanPlayer()


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
        val playerType = intent.getIntExtra("player type",0)
        Log.i("Game Activity", "player type is: $playerType")

        //checks which type of player should be O
        playerO = when(playerType){
            1-> {
                HumanPlayer()
            }
            2-> {
                ComputerPlayer()
            }
            else-> return
        }
    }




        private fun replaceFragment(fragment: Fragment) {
            Log.i("Game Activity", "replace fragment: $fragment")
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
        Log.i("board tapped", "player o is of type: ${playerO.type}")
        Log.i("board tapped", "current turn is: ${boardFragment.communicator.currentTurn}")
        if (playerO is ComputerPlayer){
            if (boardFragment.communicator.currentTurn == Communicator.Turn.O)
                return
        }
        boardFragment.tryToAddToVisualBoard(playerX, playerO, view)
    }
}
