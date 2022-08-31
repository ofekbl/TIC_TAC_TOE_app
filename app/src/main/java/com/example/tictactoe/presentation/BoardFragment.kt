package com.example.tictactoe.presentation

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.tictactoe.*
import com.example.tictactoe.databinding.FragmentBoardBinding
import com.example.tictactoe.logic.Board
import com.example.tictactoe.logic.ConnectivityUtils
import com.example.tictactoe.logic.RetrofitHelper
import com.example.tictactoe.logic.SuggesterApi
import com.example.tictactoe.viewmodel.Communicator
import kotlinx.android.synthetic.main.card_view_design.*
import kotlinx.android.synthetic.main.fragment_board.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class BoardFragment : Fragment() {

    private val communicator = Communicator(this)
    private var progressBar : ProgressBar? = null
    private val handler = Handler()
    var gameType = 0
    lateinit var bindingBoard: FragmentBoardBinding
    var boardList = mutableListOf<Button>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingBoard = FragmentBoardBinding.inflate(layoutInflater)
        initVisualBoard()

        progressBar = bindingBoard.pBar

        // Inflate the layout for this fragment
        Log.i("Board frag", "on create view")

        val playerType = arguments?.getString("playerType")
        //checks which type of player should be O
        communicator.playerX.sign = "X"
        when(playerType){
            "1"-> { //human player
                gameType =  1
                communicator.playerO = HumanPlayer()
                (communicator.playerO as HumanPlayer).sign = "O"
            }
            "2" -> { //ai player
                gameType = 2
                communicator.playerO = ComputerPlayer()
                (communicator.playerO as ComputerPlayer).sign = "O"

            }
            else -> return null
        }
        return bindingBoard.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.cell1).setOnClickListener {
            clickEvent(it)
        }
        view.findViewById<Button>(R.id.cell2).setOnClickListener {
            clickEvent(it)
        }
        view.findViewById<Button>(R.id.cell3).setOnClickListener {
            clickEvent(it)
        }
        view.findViewById<Button>(R.id.cell4).setOnClickListener {
            clickEvent(it)
        }
        view.findViewById<Button>(R.id.cell5).setOnClickListener {
            clickEvent(it)
        }
        view.findViewById<Button>(R.id.cell6).setOnClickListener {
            clickEvent(it)
        }
        view.findViewById<Button>(R.id.cell7).setOnClickListener {
            clickEvent(it)
        }
        view.findViewById<Button>(R.id.cell8).setOnClickListener {
            clickEvent(it)
        }
        view.findViewById<Button>(R.id.cell9).setOnClickListener {
            clickEvent(it)
        }
        view.findViewById<Button>(R.id.suggest_move_button).setOnClickListener {
            suggestMove(it)
            progressBar?.visibility = View.VISIBLE
            var i = progressBar?.progress
            Thread(Runnable {
                if (i != null) {
                    while (i < 9){
                        activity?.runOnUiThread(Runnable { markButtonDisable(it as Button)
                        })
                        i += 1
                        handler.post(Runnable {
                            progressBar?.progress = i
                        })
                        try {
                            Thread.sleep(100)
                        }
                        catch (e: InterruptedException){
                            e.printStackTrace()
                        }
                    }
                }
                activity?.runOnUiThread(Runnable { setButtonToEnabled(it as Button)
                })
                progressBar?.visibility = View.INVISIBLE
            }).start()
        }
    }

    fun markButtonDisable(button: Button){
        button.isEnabled = false
        button.isClickable = false
    }

    fun setButtonToEnabled(button: Button){
        button.isEnabled = true
        button.isClickable = true
    }


    fun toast(msg: String)
    {
        Toast.makeText(activity,msg,Toast.LENGTH_SHORT).show()
    }


    @RequiresApi(Build.VERSION_CODES.M)
    fun suggestMove(click: View) {
        if (!communicator.isGameOver()) {
            val suggesterApi = RetrofitHelper.getInstance().create(SuggesterApi::class.java)
            if (!ConnectivityUtils.checkConnectivity(activity as Context)) {
                activity?.runOnUiThread(java.lang.Runnable {
                    Toast.makeText(
                        activity,
                        "Not available. There is no network.",
                        Toast.LENGTH_LONG
                    ).show()
                })
            }
            GlobalScope.launch {
                val result = suggesterApi.getBestMove(
                    communicator.gridToString(),
                    communicator.currentTurn.toString()
                )
                if (result != null) {
                    Log.d("ayush", result.body().toString())
                    activity?.runOnUiThread(java.lang.Runnable {
                        Toast.makeText(
                            activity,
                            "Next best move is: ${result.body()?.recommendation?.plus(1)}",
                            Toast.LENGTH_LONG
                        ).show()
                    })
                }
            }
        }
    }



    private fun initVisualBoard() {
        Log.i("Board frag", "init visual board")
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

    private fun convertCellToSpots(cell: Button) : Array<Int> {
        Log.i("Board frag", "convert to spots")

        var x = 0
        var y = 0
        val spot = arrayOf(0, 0)
        when(cell){
            cell1 -> {x = 1
                y = 1}
            cell2 -> {x = 1
                y = 2}
            cell3 -> {x = 1
                y = 3}
            cell4 -> {x = 2
                y = 1}
            cell5 -> {x = 2
                y = 2}
            cell6 -> {x = 2
                y = 3}
            cell7 -> {x = 3
                y = 1}
            cell8 -> {x = 3
                y = 2}
            cell9 -> {x = 3
                y = 3}
        }
       spot[0] = x
       spot[1] = y
        return spot
    }

    fun convertSpotToCell (x: Int, y: Int) : Button{
       lateinit var button: Button
       Log.i("convertSpotToCell", "entered with $x $y")
       when(Pair(x, y)){
           Pair(1, 1) -> {button = bindingBoard.cell1}
           Pair(1, 2) -> {button = bindingBoard.cell2}
           Pair(1, 3) -> {button = bindingBoard.cell3}
           Pair(2, 1) -> {button = bindingBoard.cell4}
           Pair(2, 2) -> {button = bindingBoard.cell5}
           Pair(2, 3) -> {button = bindingBoard.cell6}
           Pair(3, 1) -> {button = bindingBoard.cell7}
           Pair(3, 2) -> {button = bindingBoard.cell8}
           Pair(3, 3) -> {button = bindingBoard.cell9}
       }
        return button
    }

    fun setCell(x: Int, y: Int, playerSign: String){
        val button = convertSpotToCell(x, y)
        button.text = playerSign
        bindingBoard.turnLabel.text = "Turn $playerSign"
        Log.i("setCell", "turnLabel should be $playerSign")
    }

    fun setTurnLabel(sign: String){
        bindingBoard.turnLabel.text = "$sign"
    }

    private fun clickEvent(cell: View){
        val cellButton: Button = cell as Button
        val spot = convertCellToSpots(cellButton)
        val x = spot[0]
        val y = spot[1]
        communicator.tryToMakeAMove(x, y)
    }

    private fun resetGrid(){
        for (i in 1..9){
            Board.cell1.value = "-"
            Board.cell2.value = "-"
            Board.cell3.value = "-"
            Board.cell4.value = "-"
            Board.cell5.value = "-"
            Board.cell6.value = "-"
            Board.cell7.value = "-"
            Board.cell8.value = "-"
            Board.cell9.value = "-"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        resetGrid()
    }

    fun showGameOverPopUp(){
        toast("Game over! Winner is ${communicator.winner}")
    }
}