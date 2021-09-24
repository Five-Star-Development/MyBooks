package dev.five_star.mybooks.booklist

import androidx.lifecycle.*
import androidx.navigation.NavDirections
import dev.five_star.mybooks.data.BookRepository
import dev.five_star.mybooks.database.toBookItem
import dev.five_star.mybooks.model.Book
import dev.five_star.mybooks.model.ui_model.BookItem
import dev.five_star.mybooks.utils.SingleLiveEvent
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch

class MainViewModel(private var repository: BookRepository) : ViewModel() {

    private val _bookList: MutableLiveData<List<BookItem>> = MutableLiveData<List<BookItem>>()
    val bookList: LiveData<List<BookItem>> = repository.getAllBooks().mapLatest { it ->
        it.map { it.toBookItem() }
    }.asLiveData()

    val testBookList = repository.getAllBooks().asLiveData()


    private val _navigateTo = MutableLiveData<NavDirections>()
    val navigateTo: LiveData<NavDirections> = _navigateTo

    private val _openBook = MutableLiveData<Book>()
    val openBook: LiveData<Book> = _openBook

    private val _effects = SingleLiveEvent<Effect>()
    val effect: LiveData<Effect> = _effects

    private val _dialogEffect = SingleLiveEvent<DialogEffect>()
    val dialogEffect: LiveData<DialogEffect> = _dialogEffect

    init {
//        getBookList()

    }

//    private fun getBookList() {
//        val bookItemList: List<BookItem> = repository.getAllBooks().map { it.toBookItem() }
//        _bookList.postValue(bookItemList)
//    }

    private fun openBookDetails(bookId: Int) {
        _effects.postValue(Effect.ShowDetails(bookId))
    }

    private fun addBook(bookTitle: String,  bookPages: Int) {
        if(bookTitle.isNotEmpty() && bookPages > 0) {
            // are there any differents?
            viewModelScope.launch {
                val newBook = dev.five_star.mybooks.database.Book(
                    title = bookTitle,
                    pages = bookPages,
                )

                repository.addBook(newBook)
                _dialogEffect.postValue(DialogEffect.CloseAddBook)

                val entered = repository.addBook(newBook)
                if (entered) {
                    _dialogEffect.postValue(DialogEffect.CloseAddBook)
                }
            }

        } else {
            //TODO inform the user about the error
        }
    }

    fun dataInput(event: Event) = when(event) {
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
        object ShowAddBook : Event()
        data class AddBook(val bookTitleInput: String, val bookPagesInput: String) : Event()
        data class SelectItem(val bookId: Int) : Event()
    }

    sealed class Effect {
        data class ShowDetails(val bookId: Int) : Effect()
        object AddBook : Effect()
    }

    sealed class DialogEffect {
        object CloseAddBook : DialogEffect()
    }
}

