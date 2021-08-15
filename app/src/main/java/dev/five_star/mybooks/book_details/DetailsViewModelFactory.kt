package dev.five_star.mybooks.book_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.five_star.mybooks.Model.Book

class DetailsViewModelFactory(val book: Book) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
            return DetailsViewModel(book = book) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}