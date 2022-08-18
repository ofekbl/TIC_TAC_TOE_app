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

    private val communicator = Communicator(this)
    var gameType = 0
    lateinit var bindingBoard: FragmentBoardBinding
    var boardList = mutableListOf<Button>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingBoard = FragmentBoardBinding.inflate(layoutInflater)
        initVisualBoard()

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
 //   private fun tryToAddToVisualBoard(playerX: Player, playerO: Player, button: Button) : Boolean {
//        Log.i("Board frag", "try to add to visual board")

//        val spot = convertCellToSpots(button)
//        val x = spot[0]
//        val y = spot[1]
       // if (communicator.tryToMakeAMove(playerX, playerO, x, y)) {
 //           Log.i("Board frag tryTO...", "current turn is: ${communicator.currentTurn}")

            //return true
//            if (visualTurn == Communicator.Turn.X) {
//                button.text = Communicator.Turn.X.toString()
//                visualTurn = Communicator.Turn.O
//                bindingBoard.turnLabel.text = Communicator.Turn.O.toString()
//                return true
//            } else {
//                button.text = Communicator.Turn.O.toString() //adds to visual board
//                visualTurn = Communicator.Turn.X
//                bindingBoard.turnLabel.text = Communicator.Turn.X.toString()
//                return true
//            }
//        }
//        return false


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

//        if (playerSign == Communicator.Turn.X.toString()) {
//            button.text = playerSign
//            //visualTurn = Communicator.Turn.O
//            bindingBoard.turnLabel.text = Communicator.Turn.O.toString()
//        } else {
//            button.text = Communicator.Turn.O.toString() //adds to visual board
//            visualTurn = Communicator.Turn.X
//            bindingBoard.turnLabel.text = Communicator.Turn.X.toString()
//        }

    }

    fun setTurnLabel(sign: String){
        bindingBoard.turnLabel.text = "$sign"
    }

    private fun clickEvent(cell: View){
        val cellButton: Button = cell as Button
        val spot = convertCellToSpots(cellButton)
        communicator.tryToMakeAMove(spot[0], spot[1])
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
}