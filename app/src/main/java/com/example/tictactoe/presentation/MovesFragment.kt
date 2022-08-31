package com.example.tictactoe.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tictactoe.R
import com.example.tictactoe.databinding.FragmentMovesBinding
import kotlinx.android.synthetic.main.fragment_moves.*

class MovesFragment : Fragment() {
    private lateinit var binding: FragmentMovesBinding
//    private var layoutManager: RecyclerView.LayoutManager?=null
//    private var adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentMovesBinding.inflate(layoutInflater)

        // getting the recyclerview by its id
            //val recyclerview = view?.findViewById<RecyclerView>(R.id.recyclerview)

        // this creates a vertical layout Manager
//        if (recyclerview != null) {
//            recyclerview.layoutManager = LinearLayoutManager(view?.context)
//        }

//        // ArrayList of class ItemsViewModel
//        val data = ArrayList<ItemViewModel>()
//
//        // This loop will create 20 Views containing
//        // the image with the count of view
//        for (i in 1..20) {
//            data.add(ItemViewModel(R.drawable.ic_baseline_folder_24, "Item " + i))
//        }
//
//        // This will pass the ArrayList to our Adapter
//        val adapter = CustomAdapter(data)
//
//        // Setting the Adapter with the recyclerview
//        recyclerview?.adapter = adapter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_moves, container, false)
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?){
        super.onViewCreated(itemView, savedInstanceState)
        recyclerview.apply {
            layoutManager = LinearLayoutManager(activity)
            // ArrayList of class ItemsViewModel
            val data = ArrayList<ItemViewModel>()

            // This loop will create 20 Views containing
            // the image with the count of view
            for (i in 1..9) {
                data.add(ItemViewModel(R.drawable.ic_baseline_history, "Move " + i))
            }

            // This will pass the ArrayList to our Adapter
            val adapter = CustomAdapter(data)

            // Setting the Adapter with the recyclerview
            recyclerview?.adapter = adapter        }
    }
}

