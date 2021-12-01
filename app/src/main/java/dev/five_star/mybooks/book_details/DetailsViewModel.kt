package dev.five_star.mybooks.book_details

import android.view.KeyEvent
import android.view.View
import android.widget.TextView
import androidx.lifecycle.*
import dev.five_star.mybooks.data.BookRepository
import dev.five_star.mybooks.database.PageEntry
import dev.five_star.mybooks.database.toBookItem
import dev.five_star.mybooks.ui_common.BookItem
import dev.five_star.mybooks.ui_common.ui_model.BookItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class DetailsViewModel(private val bookId: Int, private val repository: BookRepository) : ViewModel() {

    private var _bookDetails: MutableLiveData<BookItem> = MutableLiveData()
    val bookDetails: LiveData<BookItem> = _bookDetails

    val pagesList: LiveData<List<PageEntry>> = repository.getPagesForBook(bookId).asLiveData()

    private var _pageEntry: MutableLiveData<CharSequence> = MutableLiveData()
    val pageEntry = _pageEntry

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                setBookDetails()
            }
        }
    }

    private suspend fun setBookDetails() {
        val bookItem = repository.getBook(bookId).toBookItem()
        _bookDetails.postValue(bookItem)
    }


    private suspend fun pagesValid(enteredPage: String) : Boolean {
        val lastPage = repository.getLastPageForBook(bookId)
        return (lastPage < enteredPage.toInt())
    }

    fun verifyEnter(view: View, keyEvent: KeyEvent, keyCode: Int): Boolean {
        if (view !is TextView) {
            return false
        }

        if(keyEvent.action != KeyEvent.ACTION_DOWN || keyCode != KeyEvent.KEYCODE_ENTER) {
            return false
        }

        val enteredPage = view.text.toString()
        viewModelScope.launch {
            if (pagesValid(enteredPage)
            ) {
                val page = enteredPage.toInt()
                val pagesEntry = PageEntry(bookId = bookId, date = Date(), pages = page)
                viewModelScope.launch {
                    repository.addPageEntry(pagesEntry)
                    setBookDetails()
                }
                //TODO I think this should not happen directly on the view
                pageEntry.postValue(null)
//           TODO: how to replace this?
//            _pagesList.postValue(repository.getPagesForBook(book))
            }
        }
        return true
    }

}