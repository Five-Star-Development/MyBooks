package dev.five_star.mybooks.booklist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import dev.five_star.mybooks.data.BookRepository
import dev.five_star.mybooks.model.Book
import dev.five_star.mybooks.model.toBookItem
import dev.five_star.mybooks.model.ui_model.BookItem
import dev.five_star.mybooks.utils.SingleLiveEvent

class MainViewModel(private var repository: BookRepository) : ViewModel() {

    private val _bookList: MutableLiveData<List<BookItem>> = MutableLiveData<List<BookItem>>()
    val bookList: LiveData<List<BookItem>> = _bookList

    private val _navigateTo = MutableLiveData<NavDirections>()
    val navigateTo: LiveData<NavDirections> = _navigateTo

    private val _openBook = MutableLiveData<Book>()
    val openBook: LiveData<Book> = _openBook

    private val _effects = SingleLiveEvent<Effect>()
    val effect: LiveData<Effect> = _effects

    init {
        getBookList()
    }

    private fun getBookList() {
        val bookItemList: List<BookItem> = repository.getAllBooks().map { it.toBookItem() }
        _bookList.postValue(bookItemList)
    }

    fun itemSelected(bookId: Int) {
        // TODO don't do the db call, use the id and call the db in the detailsFragment
        val selectedBook = repository.getBook(bookId)
        _effects.postValue(Effect.ShowDetails(selectedBook))
    }

    fun addButtonClick() {
        _effects.postValue(Effect.AddBook)
    }

    sealed class Effect {
        data class ShowDetails(val selectedBook: Book) : Effect()
        object AddBook : Effect()
    }

}