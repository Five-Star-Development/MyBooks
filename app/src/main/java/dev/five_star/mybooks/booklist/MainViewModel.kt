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

    private val _dialogEffect = SingleLiveEvent<DialogEffect>()
    val dialogEffect: LiveData<DialogEffect> = _dialogEffect

    init {
        getBookList()
    }

    private fun getBookList() {
        val bookItemList: List<BookItem> = repository.getAllBooks().map { it.toBookItem() }
        _bookList.postValue(bookItemList)
    }

    private fun openBookDetails(bookId: Int) {
        // TODO don't do the db call, use the id and call the db in the detailsFragment
        val selectedBook = repository.getBook(bookId - 1)
        _effects.postValue(Effect.ShowDetails(selectedBook))
    }

    private fun addBook(bookTitle: String,  bookPages: Int) {
        if(bookTitle.isNotEmpty() && bookPages > 0) {
            val entered = repository.addBook(bookTitle, bookPages)
            if (entered) {
                _dialogEffect.postValue(DialogEffect.CloseAddBook)
                getBookList()
            }
        } else {
            //TODO inform the user about the error
        }
    }

    fun dataInput(event: Event) = when(event) {
        Event.LoadBookList -> getBookList()
        Event.ShowAddBook -> {
            _effects.postValue(Effect.AddBook)
        }
        is Event.SelectItem -> {
            openBookDetails(event.bookId)
        }
        is Event.AddBook -> {
            val bookTitle = event.bookTitleInput.trim()
            val bookPages: Int = if (event.bookPagesInput.isEmpty()) 0 else event.bookPagesInput.toInt()
            addBook(bookTitle, bookPages)
        }
    }

    sealed class Event {
        object LoadBookList : Event()
        object ShowAddBook : Event()
        data class AddBook(val bookTitleInput: String, val bookPagesInput: String) : Event()
        data class SelectItem(val bookId: Int) : Event()
    }

    sealed class Effect {
        data class ShowDetails(val selectedBook: Book) : Effect()
        object AddBook : Effect()
    }

    sealed class DialogEffect {
        object CloseAddBook : DialogEffect()
    }
}