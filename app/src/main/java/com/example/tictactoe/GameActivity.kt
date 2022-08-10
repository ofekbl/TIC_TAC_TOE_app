package com.example.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.example.tictactoe.databinding.ActivityGameBinding
import com.example.tictactoe.databinding.FragmentBoardBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import kotlinx.android.synthetic.main.activity_game.*
import java.util.logging.XMLFormatter


class GameActivity : AppCompatActivity() {

    enum class Turn{
        X,
        O
    }

    private var firstTurn = Turn.X
    private var currentTurn = Turn.X

    private var boardList = mutableListOf<Button>()
    private lateinit var binding: ActivityGameBinding
    private lateinit var bindingBoard: FragmentBoardBinding

    private val movesFragment = MovesFragment()
    private val boardFragment = BoardFragment()


    lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        bindingBoard = FragmentBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initBoard()

        bottomNav = this.findViewById(R.id.bottom_navigation)
        bottomNav.setOnItemSelectedListener() {
            when(it.itemId){
                R.id.movesItemId -> replaceFragment(movesFragment)
                R.id.boardItemId -> replaceFragment(boardFragment)
            }
            true
        }
        //getting the player type that was chosen in the main activity
        var playerType = getIntent().getIntExtra("player type", 0)
        println("player type is: $playerType")

//        //checks which type of player should be O
//        var playerX = HumanPlayer()
//        var playerO: Player
//        if (playerType == 0){
//            playerO = HumanPlayer()
//        }
//        else{
//            playerO = ComputerPlayer()
//        }
    }

    private fun initBoard() {
        boardList.add(bindingBoard.cell1)
        boardList.add(bindingBoard.cell2)
        boardList.add(bindingBoard.cell3)
        boardList.add(bindingBoard.cell4)
        boardList.add(bindingBoard.cell5)
        boardList.add(bindingBoard.cell6)
        boardList.add(bindingBoard.cell7)
        boardList.add(bindingBoard.cell8)
        boardList.add(bindingBoard.cell9)

    }

    private fun replaceFragment(fragment : Fragment){
        println("replace fragment: $fragment")
        if (fragment != null){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.commit()
        }
    }

    fun boardTapped(view: android.view.View) {}
}