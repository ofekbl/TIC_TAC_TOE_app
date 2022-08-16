package com.example.tictactoe

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.tictactoe.databinding.FragmentBoardBinding
import kotlinx.android.synthetic.main.fragment_board.*


class BoardFragment : Fragment() {

    val communicator = Communicator()
    private var visualTurn = communicator.currentTurn
    private val playerX: Player = HumanPlayer()
    lateinit var playerO: Player


    lateinit var bindingBoard: FragmentBoardBinding
     var boardList = mutableListOf<Button>()



    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i("Board frag", "on create")

        super.onCreate(savedInstanceState)
        bindingBoard = FragmentBoardBinding.inflate(layoutInflater)
        initVisualBoard()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        Log.i("Board frag", "on create view")

        val playerType = arguments?.getString("playerType")
        //checks which type of player should be O
        playerO = when(playerType){
            "1"-> {
                HumanPlayer()
            }
            "2" -> {
                ComputerPlayer()
            }
            else -> return null
        }

        return inflater.inflate(R.layout.fragment_board, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.cell1).setOnClickListener {
            doMove(it)
        }
        view.findViewById<Button>(R.id.cell2).setOnClickListener {
            doMove(it)
        }
        view.findViewById<Button>(R.id.cell3).setOnClickListener {
            doMove(it)
        }
        view.findViewById<Button>(R.id.cell4).setOnClickListener {
            doMove(it)
        }
        view.findViewById<Button>(R.id.cell5).setOnClickListener {
            doMove(it)
        }
        view.findViewById<Button>(R.id.cell6).setOnClickListener {
            doMove(it)
        }
        view.findViewById<Button>(R.id.cell7).setOnClickListener {
            doMove(it)
        }
        view.findViewById<Button>(R.id.cell8).setOnClickListener {
            doMove(it)
        }
        view.findViewById<Button>(R.id.cell9).setOnClickListener {
            doMove(it)
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

    //return true if spot was added, else return false
    fun tryToAddToVisualBoard(playerX: Player, playerO: Player, button: Button) : Boolean {
        Log.i("Board frag", "try to add to visual board")

//        communicator.initBoard(boardList)
        val spot = convertCellToSpots(button)
        val x = spot[0]
        val y = spot[1]
        if (communicator.tryToMakeAMove(playerX, playerO, x, y)) {
            Log.i("Board frag tryTO...", "current turn is: ${communicator.currentTurn}")
//            comment out because this checks if isTakenSpot, tryToMakeAMove is checking it,
//            if (button.text != "") {
//                return
//            }
            //button.text = communicator.currentTurn.toString()

            //return true
            if (visualTurn == Communicator.Turn.X) {
                button.text = Communicator.Turn.X.toString()
                visualTurn = Communicator.Turn.O
                return true
            } else {
                button.text = Communicator.Turn.O.toString() //adds to visual board
                visualTurn = Communicator.Turn.X
                //communicator.currentTurn = Communicator.Turn.O //changes currTurn
                return true
            }
        }
        return false
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

    //onClick
//    fun boardTapped(view: View) {
//        if (view !is Button){
//            return
//        }
//        doMove()
//        Log.i("board tapped", "player o is of type: ${playerO.type}")
//        Log.i("board tapped", "current turn is: ${communicator.currentTurn}")
//        if (playerO is ComputerPlayer){
//            if (communicator.currentTurn == Communicator.Turn.O)
//                return
//        }
//        tryToAddToVisualBoard(playerX, playerO, view)
//    }

    private fun doMove(cell: View){
        var cellButton: Button = cell as Button
        tryToAddToVisualBoard(playerX, playerO, cellButton)
    }
}