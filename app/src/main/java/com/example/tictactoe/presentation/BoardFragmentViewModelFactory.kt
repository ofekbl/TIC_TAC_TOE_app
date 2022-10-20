package com.example.tictactoe.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tictactoe.database.GameRepository


class BoardFragmentViewModelFactory(private val repository: GameRepository) : ViewModelProvider.Factory  {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(BoardFragmentViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BoardFragmentViewModel(repository) as T
        }
        throw IllegalArgumentException("Unkown ViewModel class")
    }
}