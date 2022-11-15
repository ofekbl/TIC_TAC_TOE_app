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
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.tictactoe.*
import com.example.tictactoe.database.GameDatabase
import com.example.tictactoe.database.GameState
import com.example.tictactoe.databinding.FragmentBoardBinding
import com.example.tictactoe.logic.Board

import com.example.tictactoe.logic.ConnectivityUtils
import com.example.tictactoe.logic.RetrofitHelper
import com.example.tictactoe.logic.SuggesterApi
import kotlinx.android.synthetic.main.fragment_board.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit


class BoardFragment : Fragment() {



    private var progressBar: ProgressBar? = null
    private val handler = Handler()
    lateinit var bindingBoard: FragmentBoardBinding
    var boardList = mutableListOf<Button>()

    private val boardFragmentViewModel: BoardFragmentViewModel by viewModels {
        BoardFragmentViewModelFactory(((activity?.application) as GameApplication).repository)
    }




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

        boardFragmentViewModel.playerX.sign = "X"
        Log.i("Board frag", "playerX sign has set")
        Log.i("Board frag", "playerX sign is now: ${boardFragmentViewModel.playerX.sign}")



        when (playerType) {

            "1" -> { //human player
                Log.i("Board frag", "human player")

                //boardFragmentViewModel.gameType = 1
                boardFragmentViewModel.playerO = HumanPlayer()
                (boardFragmentViewModel.playerO as HumanPlayer).sign = "O"

                Log.i("Board frag", "playerO sign has set")
                Log.i("Board frag", "playerX sign is now: ${boardFragmentViewModel.playerX.sign}")
            }


            "2" -> { //ai player
                //boardFragmentViewModel.gameType = 2
                boardFragmentViewModel.playerO = ComputerPlayer()
                (boardFragmentViewModel.playerO as ComputerPlayer).sign = "O"
                Log.i("Board frag", "playerO sign has set")
                Log.i("Board frag", "playerX sign is now: ${boardFragmentViewModel.playerX.sign}")

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
            showProgressBar(it)
        }
        boardFragmentViewModel.gameState.observe(viewLifecycleOwner, Observer(::setUIByState))
    }



    private fun clickEvent(cell: View) {
        val cellButton: Button = cell as Button
        val spot = convertCellToSpots(cellButton)
        val x = spot[0]
        val y = spot[1]

        boardFragmentViewModel.buttonClicked(x, y)
    }

    private fun showProgressBar(it: View){
        progressBar?.visibility = View.VISIBLE
        var i = progressBar?.progress
        Thread(Runnable {
            if (i != null) {
                while (i < 9) {
                    activity?.runOnUiThread(Runnable {
                        markButtonDisable(it as Button)
                    })
                    i += 1
                    handler.post(Runnable {
                        progressBar?.progress = i
                    })
                    try {
                        Thread.sleep(100)
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                }
            }
            activity?.runOnUiThread(Runnable {
                setButtonToEnabled(it as Button)
            })
            progressBar?.visibility = View.INVISIBLE
        }).start()
    }

    private fun setUIByState(gameState: GameState) {
        Log.i("setUIByState", "entered")
        if(!boardFragmentViewModel.isGameOver()) {
            //setTurnLabel(gameState.nextTurn)
            setGridUI(gameState.gridState, gameState)
        }
        else {
            markAllButtonsDisabled()
            toast("Game is over! Winner is ${gameState.winner}")
        }
    }

    private fun setGridUI(grid: String, gameState: GameState){
        for (i in 0..8){
            //if there was a sign in the cell
            Log.i("setGridUI", "grid in the $i spot: ${grid[i]}")
            if (grid[i].toString() != "-"){
                var cellNum: Int = i + 1
                var spots = convertCellToSpots(cellNum)
                var cell : Button = convertSpotToCell(spots[0], spots[1])
               cell.text = gameState.nextTurn
            }
        }
    }

    private fun showGameOverPopUp() {
        toast("Game over! Winner is ${boardFragmentViewModel.gameState.value!!.winner}")
    }

    private fun setTurnLabel(sign: String) {
        bindingBoard.turnLabel.text = "Turn $sign"
    }

    private fun markButtonDisable(button: Button) {
        button.isEnabled = false
        button.isClickable = false
    }

    private fun markAllButtonsDisabled(){
        bindingBoard.cell1.isEnabled = false
        bindingBoard.cell1.isClickable = false
        bindingBoard.cell2.isEnabled = false
        bindingBoard.cell2.isClickable = false
        bindingBoard.cell3.isEnabled = false
        bindingBoard.cell3.isClickable = false
        bindingBoard.cell4.isEnabled = false
        bindingBoard.cell4.isClickable = false
        bindingBoard.cell5.isEnabled = false
        bindingBoard.cell5.isClickable = false
        bindingBoard.cell6.isEnabled = false
        bindingBoard.cell6.isClickable = false
        bindingBoard.cell7.isEnabled = false
        bindingBoard.cell7.isClickable = false
        bindingBoard.cell8.isEnabled = false
        bindingBoard.cell8.isClickable = false
        bindingBoard.cell9.isEnabled = false
        bindingBoard.cell9.isClickable = false
        bindingBoard.suggestMoveButton.isEnabled = false
        bindingBoard.suggestMoveButton.isClickable = false
    }

    private fun markAllButtonsEnabled(){
        bindingBoard.cell1.isEnabled = true
        bindingBoard.cell1.isClickable = true
        bindingBoard.cell2.isEnabled = true
        bindingBoard.cell2.isClickable = true
        bindingBoard.cell3.isEnabled = true
        bindingBoard.cell3.isClickable = true
        bindingBoard.cell4.isEnabled = true
        bindingBoard.cell4.isClickable = true
        bindingBoard.cell5.isEnabled = true
        bindingBoard.cell5.isClickable = true
        bindingBoard.cell6.isEnabled = true
        bindingBoard.cell6.isClickable = true
        bindingBoard.cell7.isEnabled = true
        bindingBoard.cell7.isClickable = true
        bindingBoard.cell8.isEnabled = true
        bindingBoard.cell8.isClickable = true
        bindingBoard.cell9.isEnabled = true
        bindingBoard.cell9.isClickable = true
        bindingBoard.suggestMoveButton.isEnabled = true
        bindingBoard.suggestMoveButton.isClickable = true
    }

    private fun setButtonToEnabled(button: Button) {
        button.isEnabled = true
        button.isClickable = true
    }

    private fun toast(msg: String) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun suggestMove(click: View) {
        if (!boardFragmentViewModel.isGameOver()) {
            val suggesterApi = RetrofitHelper.getInstance().create(SuggesterApi::class.java)
            Log.i("suggestMove", "next line is checking connectivity")

            if (!ConnectivityUtils.checkConnectivity(activity as Context)) {
                Log.i("suggestMove", "no connectivity")
                activity?.runOnUiThread(java.lang.Runnable {
                    Toast.makeText(
                        activity,
                        "Not available. There is no network.",
                        Toast.LENGTH_LONG
                    ).show()
                })
            }
            Log.i("suggestMove", "skip no connectivity - so there is connectivity(?)")


            GlobalScope.launch {
                Log.i("suggestMove", "in the GlobalScope")
                val result = suggesterApi.getBestMove(
                    boardFragmentViewModel.gridToString(),
                    boardFragmentViewModel.gameState.value!!.nextTurn
                )
                Log.i("suugestMove", "after making a result")

                if (result.isSuccessful) { //TODO result.isScucess
                    Log.i("suggestMove", "inside the if - means, there is connectivity")
                    Log.d("ayush", result.body().toString())
                    val nextRecommendation = result.body()?.recommendation?.plus(1)

                    launch(Dispatchers.Main) {
                        Toast.makeText(
                            activity,
                            "Next best move is: $nextRecommendation",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } else {
                    Log.i("suggestMove", "inside the else")
                    launch(Dispatchers.Main) {
                        Toast.makeText(
                            activity,
                            "Error! Check your connectivity and try again!",
                            Toast.LENGTH_LONG
                        ).show()
                    }
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

    private fun convertCellToSpots(cellNum: Int): Array<Int> {
        Log.i("Board frag", "convert to spots")

        var x = 0
        var y = 0
        val spot = arrayOf(0, 0)
        when (cellNum) {
            1 -> {
                x = 1
                y = 1
            }
            2 -> {
                x = 1
                y = 2
            }
            3 -> {
                x = 1
                y = 3
            }
            4 -> {
                x = 2
                y = 1
            }
            5 -> {
                x = 2
                y = 2
            }
            6 -> {
                x = 2
                y = 3
            }
            7 -> {
                x = 3
                y = 1
            }
            8 -> {
                x = 3
                y = 2
            }
            9 -> {
                x = 3
                y = 3
            }
        }
        spot[0] = x
        spot[1] = y
        return spot
    }

    private fun convertCellToSpots(cell: Button): Array<Int> {
        Log.i("Board frag", "convert to spots")

        var x = 0
        var y = 0
        val spot = arrayOf(0, 0)
        when (cell) {
            cell1 -> {
                x = 1
                y = 1
            }
            cell2 -> {
                x = 1
                y = 2
            }
            cell3 -> {
                x = 1
                y = 3
            }
            cell4 -> {
                x = 2
                y = 1
            }
            cell5 -> {
                x = 2
                y = 2
            }
            cell6 -> {
                x = 2
                y = 3
            }
            cell7 -> {
                x = 3
                y = 1
            }
            cell8 -> {
                x = 3
                y = 2
            }
            cell9 -> {
                x = 3
                y = 3
            }
        }
        spot[0] = x
        spot[1] = y
        return spot
    }

    private fun convertSpotToCell(x: Int, y: Int): Button {
        lateinit var button: Button
        Log.i("convertSpotToCell", "entered with $x $y")
        when (Pair(x, y)) {
            Pair(1, 1) -> {
                button = bindingBoard.cell1
            }
            Pair(1, 2) -> {
                button = bindingBoard.cell2
            }
            Pair(1, 3) -> {
                button = bindingBoard.cell3
            }
            Pair(2, 1) -> {
                button = bindingBoard.cell4
            }
            Pair(2, 2) -> {
                button = bindingBoard.cell5
            }
            Pair(2, 3) -> {
                button = bindingBoard.cell6
            }
            Pair(3, 1) -> {
                button = bindingBoard.cell7
            }
            Pair(3, 2) -> {
                button = bindingBoard.cell8
            }
            Pair(3, 3) -> {
                button = bindingBoard.cell9
            }
        }
        return button
    }

    private fun convertSpotToCellNum(x: Int, y: Int): Int {
        var cellNum = 0
        Log.i("convertSpotToCell", "entered with $x $y")
        when (Pair(x, y)) {
            Pair(1, 1) -> {
                cellNum = 1
            }
            Pair(1, 2) -> {
                cellNum = 2
            }
            Pair(1, 3) -> {
                cellNum = 3
            }
            Pair(2, 1) -> {
                cellNum = 4
            }
            Pair(2, 2) -> {
                cellNum = 5
            }
            Pair(2, 3) -> {
                cellNum = 6
            }
            Pair(3, 1) -> {
                cellNum = 7
            }
            Pair(3, 2) -> {
                cellNum = 8
            }
            Pair(3, 3) -> {
                cellNum = 9
            }
        }
        return cellNum
    }

    private fun nextTurnLabel(sign: String?) : String{
        if (sign == "O")
            return "X"
        else
            return "O"
    }



//    private fun setCell(x: Int, y: Int, playerSign: String, gameState: GameState) {
//        val button = convertSpotToCell(x, y)
//        val cellNum = convertSpotToCellNum(x, y)
//
//        button.text = playerSign
////        boardFragmentViewModel.gameState.value?.copy(nextTurn = playerSign)
//        //setTurnLabel("Turn $playerSign")
//        Log.i("setCell", "turnLabel should be $playerSign")
//    }

    private fun resetGrid() {
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

    override fun onDestroyView() {
        super.onDestroyView()
        resetGrid()
        markAllButtonsEnabled()
    }


}