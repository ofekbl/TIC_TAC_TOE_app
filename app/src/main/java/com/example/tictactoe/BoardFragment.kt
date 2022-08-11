package com.example.tictactoe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.tictactoe.databinding.FragmentBoardBinding


class BoardFragment : Fragment() {

    enum class Turn{
        X,
        O
    }

     val communicator = Communicator()

     var firstTurn = Turn.X
     var currentTurn = Turn.X

     lateinit var bindingBoard: FragmentBoardBinding
     var boardList = mutableListOf<Button>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingBoard = FragmentBoardBinding.inflate(layoutInflater)
        initVisualBoard()



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_board, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initVisualBoard() {
        println("init visual board")
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


    fun addToVisualBoard(button: Button) {
        println("add to visual board")
        communicator.initBoard(boardList)
        //if (communicator.tryToMakeAMove) {
            if (button.text != "") {
                return
            }
            if (currentTurn == Turn.O) {
                button.text = Turn.O.toString()
                currentTurn = Turn.X
            } else if (currentTurn == Turn.X) {
                button.text = Turn.X.toString()
                currentTurn = Turn.O
            }
        }
    //}
}