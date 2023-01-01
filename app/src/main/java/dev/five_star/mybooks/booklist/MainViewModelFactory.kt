package dev.five_star.mybooks.booklist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.five_star.mybooks.data.BookRepository
import dev.five_star.mybooks.utils.ArchiveEvent
import dev.five_star.mybooks.utils.EventBus

// TODO MainViewModel in AddBookDialog?
class MainViewModelFactory(
    private val repository: BookRepository,
    private val events: EventBus<ArchiveEvent>? = null
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(repository, events) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
