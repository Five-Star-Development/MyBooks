package dev.five_star.mybooks.book_details

import android.view.KeyEvent
import android.view.View
import android.widget.TextView
import androidx.lifecycle.*
import dev.five_star.mybooks.data.BookRepository
import dev.five_star.mybooks.ui_common.BookItem
import dev.five_star.mybooks.ui_common.toItem
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch
import java.util.*

class DetailsViewModel(private val bookId: Int, private val repository: BookRepository) :
    ViewModel() {

    val bookData: LiveData<BookItem> = repository.getBook(bookId).mapLatest { book ->
        book.toItem()
    }.asLiveData()

    private var _pageEntry: MutableLiveData<CharSequence> = MutableLiveData()
    val pageEntry: LiveData<CharSequence> = _pageEntry

    private fun pagesValid(enteredPage: String): Boolean {
        return if (bookData.value != null) {
            (bookData.value!!.currentPage < enteredPage.toInt())
                    && (enteredPage.toInt() <= bookData.value!!.totalPages)
        } else {
            false
        }
    }

    fun verifyEnter(view: View, keyEvent: KeyEvent, keyCode: Int): Boolean {
        if (view !is TextView) {
            return false
        }

        if (keyEvent.action != KeyEvent.ACTION_DOWN || keyCode != KeyEvent.KEYCODE_ENTER) {
            return false
        }

        val enteredPage = view.text.toString()
        viewModelScope.launch {
            if (pagesValid(enteredPage)) {
                val page = enteredPage.toInt()
                viewModelScope.launch {
                    val result =
                        repository.addPageEntry(bookId = bookId, date = Date(), page = page)
                    if (result) {
                        _pageEntry.postValue("")
                    }
                }
            }
            //TODO: else with output for the user why input is not valid
        }
        return true
    }

}