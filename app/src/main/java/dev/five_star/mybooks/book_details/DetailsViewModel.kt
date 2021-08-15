package dev.five_star.mybooks.book_details

import android.view.KeyEvent
import android.view.View
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.five_star.mybooks.Model.Book
import dev.five_star.mybooks.Model.Dummy
import dev.five_star.mybooks.Model.PagesEntry
import dev.five_star.mybooks.Model.toBookItem
import dev.five_star.mybooks.Model.ui_model.BookItem

class DetailsViewModel(private val book: Book) : ViewModel() {

    private var _bookDetails: MutableLiveData<BookItem> = MutableLiveData()
    val bookDetails: LiveData<BookItem> = _bookDetails

    private var _pagesList: MutableLiveData<List<PagesEntry>> = MutableLiveData()
    val pagesList: LiveData<List<PagesEntry>> = _pagesList

    private var _pageEntry: MutableLiveData<CharSequence> = MutableLiveData()
    val pageEntry = _pageEntry

    init {
        setBookDetails()
        setPagesList()
    }

    private fun setBookDetails() {
        _bookDetails.postValue(book.toBookItem())
    }

    private fun setPagesList() {
        _pagesList.postValue(Dummy.pageList)
    }

    private fun pagesValid(enteredPage: String) : Boolean {
        val lastPage = Dummy.pageList.last().page.toInt()
        return (lastPage < enteredPage.toInt())
    }

    fun verifyEnter(view: View, keyEvent: KeyEvent, keyCode: Int): Boolean {
        if (view !is TextView) {
            return false
        }
        val enteredPage = view.text.toString()
        return if (keyEvent.action == KeyEvent.ACTION_DOWN &&
            keyCode == KeyEvent.KEYCODE_ENTER && pagesValid(enteredPage)
        ) {
            Dummy.pageList.add(PagesEntry("today", enteredPage))
            //TODO I think this should not happen directly on the view
            pageEntry.postValue(null)
            _pagesList.postValue(Dummy.pageList)
            true
        } else {
            false
        }
    }

}