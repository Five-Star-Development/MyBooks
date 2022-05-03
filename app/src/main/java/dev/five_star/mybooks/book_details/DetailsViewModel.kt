package dev.five_star.mybooks.book_details

import androidx.lifecycle.*
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dev.five_star.mybooks.R
import dev.five_star.mybooks.data.BookRepository
import dev.five_star.mybooks.ui_common.BookItem
import dev.five_star.mybooks.ui_common.toItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch
import java.util.*
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

class DetailsViewModel @AssistedInject constructor(
    @Assisted private val bookId: Int,
    private val repository: BookRepository
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(bookId: Int): DetailsViewModel
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun provideFactory(assistedFactory: Factory, bookId: Int): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return assistedFactory.create(bookId) as T
                }
            }
    }

    val bookData: LiveData<BookItem> = repository.getBook(bookId).mapLatest { book ->
        book.toItem()
    }.asLiveData()

    private var _pageEntry: MutableLiveData<CharSequence> = MutableLiveData()
    val pageEntry: LiveData<CharSequence> = _pageEntry

    private var _errorMessage: MutableLiveData<Int> = MutableLiveData()
    val errorMessage: LiveData<Int> = _errorMessage

    @OptIn(ExperimentalContracts::class)
    private fun pagesValid(enteredPage: String?): Boolean {
        contract {
            returns(true) implies (enteredPage != null)
        }
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
            } else {
                _errorMessage.postValue(R.string.invalid_page_entery)
            }
        }
        return true
    }

}