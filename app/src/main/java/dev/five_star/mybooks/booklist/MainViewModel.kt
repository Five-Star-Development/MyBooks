package dev.five_star.mybooks.booklist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import dev.five_star.mybooks.Model.Book
import dev.five_star.mybooks.Model.Dummy
import dev.five_star.mybooks.Model.ui_model.BookItem

class MainViewModel : ViewModel() {

    private val _bookList: MutableLiveData<List<BookItem>> = MutableLiveData<List<BookItem>>()
    val bookList: LiveData<List<BookItem>> = _bookList

    private val _navigateTo = MutableLiveData<NavDirections>()
    val navigateTo: LiveData<NavDirections> = _navigateTo

    private val _openBook = MutableLiveData<Book>()
    val openBook: LiveData<Book> = _openBook

    init {
        getBookList()
    }

    private fun getBookList() {
        val bookItemList: List<BookItem> = Dummy.bookList.map { it.toBookItem() }
        _bookList.postValue(bookItemList)
    }

    //TODO why does this not work?
    fun not_working_itemSelected(position: Int) {
        val selectedBook = Dummy.bookList[position]
        val action = MainFragmentDirections.actionMainFragmentToDetailsFragment(selectedBook)
        _navigateTo.postValue(action)
    }

    fun itemSelected(position: Int) {
        val selectedBook = Dummy.bookList[position]
        _openBook.postValue(selectedBook)
    }

    fun addButtonClick() {
        val action = MainFragmentDirections.actionMainFragmentToAddBookDialog()
        _navigateTo.postValue(action)

    }

}