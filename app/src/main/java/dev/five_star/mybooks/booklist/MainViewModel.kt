package dev.five_star.mybooks.booklist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dev.five_star.mybooks.data.BookRepository
import dev.five_star.mybooks.ui_common.BookItem
import dev.five_star.mybooks.ui_common.toItem
import dev.five_star.mybooks.utils.SingleLiveEvent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch

class MainViewModel(private var repository: BookRepository, private val dispatcher: CoroutineDispatcher = Dispatchers.IO) : ViewModel() {

    val bookList: LiveData<List<BookItem>> = repository.getAllBooks().mapLatest { it ->
        it.map { it.toItem() }
    }.asLiveData()

    private val _effects = SingleLiveEvent<Effect>()
    val effect: LiveData<Effect> = _effects

    private val _dialogEffect = SingleLiveEvent<DialogEffect>()
    val dialogEffect: LiveData<DialogEffect> = _dialogEffect

//    private fun getBookList() {
//        val bookItemList: List<BookItem> = repository.getAllBooks().map { it.toBookItem() }
//        _bookList.postValue(bookItemList)
//    }

    private fun openBookDetails(bookId: Int) {
        val action = Action.ShowDetails(bookId)
        _effects.postValue(Effect.Navigate(action))
    }

    private fun addBook(bookTitle: String,  bookPages: Int) {
        if(bookTitle.isNotEmpty() && bookPages > 0) {
            // are there any differents?
            viewModelScope.launch {
                val entered = repository.addBook(title = bookTitle, pages = bookPages)
                if (entered) {
                    _dialogEffect.postValue(DialogEffect.CloseAddBook)
                } else {
                    _dialogEffect.postValue(DialogEffect.InputError)
                }
            }

        } else {
            _dialogEffect.postValue(DialogEffect.InputError)
        }
    }

    private fun archiveBook(bookId: Int) {
        viewModelScope.launch(dispatcher) {
            val archiveBookId = repository.archiveBook(bookId)
            if (archiveBookId > 0) {
                _effects.postValue(Effect.UndoMessage(archiveBookId))
            }
        }
    }

    private fun activateBook(bookId: Int) {
        viewModelScope.launch {
            repository.activateBook(bookId)
        }
    }

    fun dataInput(event: Event) = when(event) {
        Event.ShowAddBook -> {
            val action = Action.BookAdded
            _effects.postValue(Effect.Navigate(action))
        }
        is Event.SelectItem -> {
            openBookDetails(event.bookId)
        }
        is Event.AddBook -> {
            val bookTitle = event.bookTitleInput.trim()
            val bookPages: Int = if (event.bookPagesInput.isEmpty()) 0 else event.bookPagesInput.toInt()
            addBook(bookTitle, bookPages)
        }
        is Event.ArchiveBook -> {
            archiveBook(event.bookId)
        }
        is Event.ActivateBook -> {
            activateBook(event.bookId)
        }
    }

    sealed class Event {
        object ShowAddBook : Event()
        data class AddBook(val bookTitleInput: String, val bookPagesInput: String) : Event()
        data class ArchiveBook(val bookId: Int) : Event()
        data class ActivateBook(val bookId: Int) : Event()
        data class SelectItem(val bookId: Int) : Event()
    }

    sealed class Effect {
        data class UndoMessage(val bookId: Int) : Effect()
        data class Navigate(val action: Action) : Effect()
    }

    sealed class Action {
        data class ShowDetails(val bookId: Int) : Action()
        object BookAdded : Action()
    }

    sealed class DialogEffect {
        object CloseAddBook : DialogEffect()
        object InputError: DialogEffect()
    }
}

