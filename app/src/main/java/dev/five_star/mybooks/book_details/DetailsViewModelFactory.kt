package dev.five_star.mybooks.book_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.five_star.mybooks.data.BookRepository
import dev.five_star.mybooks.data.BookRepositoryImpl

class DetailsViewModelFactory(private val bookId: Int, private val repository: BookRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
            return DetailsViewModel(bookId = bookId, repository = repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}