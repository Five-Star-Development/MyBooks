package dev.five_star.mybooks.book_details

import androidx.lifecycle.*
import dev.five_star.mybooks.data.BookRepository
import dev.five_star.mybooks.ui_common.BookItem
import dev.five_star.mybooks.ui_common.toItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch
import java.util.*
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

class DetailsViewModel(private val bookId: Int, private val repository: BookRepository) :
    ViewModel() {

    val bookData: LiveData<BookItem> = repository.getBook(bookId).mapLatest { book ->
        book.toItem()
    }.asLiveData()

    private var _pageEntry: MutableLiveData<CharSequence> = MutableLiveData()
    val pageEntry: LiveData<CharSequence> = _pageEntry

    @OptIn(ExperimentalContracts::class)
    private fun pagesValid(enteredPage: String?): Boolean {
        contract {
            returns(true) implies (enteredPage != null)
        }
        //TODO: check this cases ->
        //if (bookData.value!!.currentPage < enteredPage.toInt())
        //if (enteredPage.toInt() < bookData.value!!.totalPages)
        //if (enteredPage.toInt() == bookData.value!!.totalPages)
        return if (bookData.value != null && !enteredPage.isNullOrBlank()) {
            (bookData.value!!.currentPage < enteredPage.toInt())
                    && (enteredPage.toInt() <= bookData.value!!.totalPages)
        } else {
            false
        }
    }

    fun verifyEnter(enteredPage: String?): Boolean {
        viewModelScope.launch(Dispatchers.IO) {
            if (pagesValid(enteredPage)) {
                val page = enteredPage.toInt()
                val result =
                    repository.addPageEntry(bookId = bookId, date = Date(), page = page)
                if (result) {
                    _pageEntry.postValue("")
                }
            }
            //TODO: else with output for the user why input is not valid
        }
        return true
    }

}