package com.example.tictactoe.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.tictactoe.GameStateWorker
import com.example.tictactoe.R
import com.example.tictactoe.databinding.ActivityGameBinding
import java.util.concurrent.TimeUnit


class GameActivity : AppCompatActivity() {


    private lateinit var binding: ActivityGameBinding
    lateinit var workManager: WorkManager


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

    override fun onStart() {
        super.onStart()
        workManager = WorkManager.getInstance(this)
        workManager.cancelUniqueWork("GameStateWork")
    }
    override fun onStop() {
        super.onStop()
        shouldScheduleNotificationWorker()
    }
//should be in gameApplication
        private fun shouldScheduleNotificationWorker(){
        val work = PeriodicWorkRequestBuilder<GameStateWorker>(15, TimeUnit.MINUTES)
//            .setConstraints(constraints)
            .build()

            //REPLACE policy will cause a bug where if the user gets in and out in less than 15 minutes there will be no notification
        workManager.enqueueUniquePeriodicWork("GameStateWork", ExistingPeriodicWorkPolicy.REPLACE, work)
    }
}
