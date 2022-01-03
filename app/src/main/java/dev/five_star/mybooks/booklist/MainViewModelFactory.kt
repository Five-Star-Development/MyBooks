package dev.five_star.mybooks.booklist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.five_star.mybooks.data.BookRepository

class MainViewModelFactory(private val repository: BookRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}