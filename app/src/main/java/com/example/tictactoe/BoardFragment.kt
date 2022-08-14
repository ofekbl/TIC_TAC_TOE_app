package com.example.tictactoe

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.tictactoe.databinding.FragmentBoardBinding
import kotlinx.android.synthetic.main.fragment_board.*
import java.util.logging.XMLFormatter


class BoardFragment : Fragment() {

    private val communicator = Communicator()
    private var visualTurn = communicator.currentTurn


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

        return inflater.inflate(R.layout.fragment_board, container, false)
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
}