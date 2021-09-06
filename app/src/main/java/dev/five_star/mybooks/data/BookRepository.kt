package dev.five_star.mybooks.data

import androidx.annotation.WorkerThread
import dev.five_star.mybooks.database.Book
import dev.five_star.mybooks.database.BookDao
import dev.five_star.mybooks.model.PagesEntry
import kotlinx.coroutines.flow.Flow

class BookRepository(private val bookDao: BookDao) {


    private val pageList = mutableListOf(
        PagesEntry("13.03.2021", "13"),
        PagesEntry("14.03.2021", "35"),
        PagesEntry("15.03.2021", "67"),
        PagesEntry("16.03.2021", "89"),
        PagesEntry("17.03.2021", "111"),
    )

    fun getAllBooks(): Flow<List<Book>> {
        return bookDao.getAllBooks()
    }

    @WorkerThread
    suspend fun getBook(id: Int): Book {
        return bookDao.getBook(id)
    }

    @WorkerThread
    suspend fun addBook(book: Book) {
        return bookDao.insertBook(book)
    }

    // Haha this is part of the faknes ;-)
    fun getPagesForBook(book: Book): List<PagesEntry> {
        return pageList
    }

    fun addPageEntry(enteredPage: String) {
        pageList.add(PagesEntry("today", enteredPage))
    }

}