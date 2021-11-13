package dev.five_star.mybooks.booklist

import dev.five_star.mybooks.data.BookRepository
import dev.five_star.mybooks.database.Book
import dev.five_star.mybooks.model.PagesEntry
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MockedBookRepo : BookRepository {

    private val bookList = mutableListOf(
        Book(1,"Book Number 1", 300, 3),
        Book(2, "Book Number 2", 300, 30),
        Book(3,"Book Number 3", 300, 233),
        Book(4,"Book Number 4", 300, 113),
        Book(5,"Book Number 5", 300, 299),
        Book(6,"Book Number 6", 300, 300),
        Book(7,"Book Number 7", 300, 100),
    )

    private val pageList = mutableListOf(
        PagesEntry("13.03.2021", "13"),
        PagesEntry("14.03.2021", "35"),
        PagesEntry("15.03.2021", "67"),
        PagesEntry("16.03.2021", "89"),
        PagesEntry("17.03.2021", "111"),
    )

    override fun getAllBooks(): Flow<List<Book>> {
        return flow {
            emit(bookList)
        }
    }

    override suspend fun getBook(id: Int): Book {
        return Book(1, "title", 100, 10)
    }

    override suspend fun addBook(book: Book): Boolean {
        return bookList.add(book)
    }

    override fun getPagesForBook(book: Book): List<PagesEntry> {
        return pageList
    }

    override fun getPagesForBook(bookId: Int): List<PagesEntry> {
        return pageList
    }

    override fun addPageEntry(enteredPage: String) {
        //
    }
}