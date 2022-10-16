package dev.five_star.mybooks.archived_booklist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import dev.five_star.mybooks.data.BookRepository
import dev.five_star.mybooks.ui_common.BookItem
import dev.five_star.mybooks.ui_common.toItem
import kotlinx.coroutines.flow.mapLatest

class ArchivedBooksViewModel(
    private var repository: BookRepository,
) : ViewModel() {

    val archivedBookList: LiveData<List<BookItem>> = repository.getAllArchivedBooks().mapLatest { it ->
        it.map { it.toItem() }
    }.asLiveData()

}

class ArchivedBooksViewModelFactory(
    private val repository: BookRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ArchivedBooksViewModel::class.java)) {
            return ArchivedBooksViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}