package dev.five_star.mybooks.booklist

import dev.five_star.mybooks.data.Book
import dev.five_star.mybooks.data.BookRepository
import dev.five_star.mybooks.database.PageEntry
import java.util.Date
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MockedBookRepo : BookRepository {

    private var archiveBook: Book? = null

    private val bookList = mutableListOf(
        Book(1, "Book Number 1", 300),
        Book(2, "Book Number 2", 300, emptyList()),
        Book(3, "Book Number 3", 300, emptyList()),
        Book(4, "Book Number 4", 300, emptyList()),
        Book(5, "Book Number 5", 300, emptyList()),
        Book(6, "Book Number 6", 300, emptyList()),
        Book(7, "Book Number 7", 300, emptyList()),
    )

    private val pageList = mutableListOf(
        PageEntry(bookId = 1, date = Date(), page = 13),
        PageEntry(bookId = 1, date = Date(), page = 35),
        PageEntry(bookId = 1, date = Date(), page = 67),
        PageEntry(bookId = 1, date = Date(), page = 89),
        PageEntry(bookId = 1, date = Date(), page = 111),
    )

    override fun getAllBooks(): Flow<List<Book>> {
        return flow {
            emit(bookList)
        }
    }

    override fun getBook(id: Int): Flow<Book> {
        return flow {
            emit(bookList[id])
        }
    }

    override suspend fun getBookSync(id: Int): Book = bookList[id]

    override suspend fun addBook(title: String, pages: Int): Boolean {
        return bookList.add(Book(8, title, pages))
    }

    override suspend fun archiveBook(bookId: Int): Int {
        archiveBook = bookList[bookId - 1]
        val result = bookList.remove(archiveBook)
        return if (result) bookId else 0
    }

    override suspend fun activateBook(bookId: Int) {
        archiveBook?.let {
            bookList.add(it)
        }
    }

    override suspend fun addPageEntry(bookId: Int, date: Date, page: Int): Boolean {
        return pageList.add(PageEntry(bookId = bookId, date = date, page = page))
    }
}
