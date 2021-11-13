package dev.five_star.mybooks.book_details

import android.view.KeyEvent
import android.view.View
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.five_star.mybooks.data.BookRepository
import dev.five_star.mybooks.database.toBookItem
import dev.five_star.mybooks.model.PagesEntry
import dev.five_star.mybooks.model.ui_model.BookItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailsViewModel(private val bookId: Int, private val repository: BookRepository) : ViewModel() {

    private var _bookDetails: MutableLiveData<BookItem> = MutableLiveData()
    val bookDetails: LiveData<BookItem> = _bookDetails

    private var _pagesList: MutableLiveData<List<PagesEntry>> = MutableLiveData()
    val pagesList: LiveData<List<PagesEntry>> = _pagesList

    private var _pageEntry: MutableLiveData<CharSequence> = MutableLiveData()
    val pageEntry = _pageEntry

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                setBookDetails()
                setPagesList()
            }
        }
    }

    private suspend fun setBookDetails() {
        val bookItem = repository.getBook(bookId).toBookItem()
        _bookDetails.postValue(bookItem)
    }

    private fun setPagesList() {
//        _pagesList.postValue(repository.getPagesForBook(book))
    }

    private fun pagesValid(enteredPage: String) : Boolean {
//        val lastPage = repository.getPagesForBook(book).last().page.toInt()
//        return (lastPage < enteredPage.toInt())
        return false
    }

    fun verifyEnter(view: View, keyEvent: KeyEvent, keyCode: Int): Boolean {
        if (view !is TextView) {
            return false
        }
        val enteredPage = view.text.toString()
        return if (keyEvent.action == KeyEvent.ACTION_DOWN &&
            keyCode == KeyEvent.KEYCODE_ENTER && pagesValid(enteredPage)
        ) {
            repository.addPageEntry(enteredPage)
            //TODO I think this should not happen directly on the view
            pageEntry.postValue(null)
//           TODO: how to replace this?
//            _pagesList.postValue(repository.getPagesForBook(book))
            true
        } else {
            false
        }
    }

}