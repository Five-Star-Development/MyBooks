package dev.five_star.mybooks.data

import android.util.Log
import dev.five_star.mybooks.database.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*

class BookRepositoryImpl(private val bookDao: BookDao, private val pagesDao: PagesDao) :
    BookRepository {

    override fun getAllBooks(): Flow<List<Book>> {

        val bookMap = bookDao.getAllBooksWithPages()
        return bookMap.map { bookEntryMap ->
            Log.d("BookRepository", bookEntryMap.toString())
            bookEntryMap.toBook()
        }
    }

    override fun getBook(id: Int): Flow<Book> {
        val book = bookDao.getBookWithPages(id).map { bookEntryMap ->
            bookEntryMap.toBook().first()
        }
        return book
    }

    override suspend fun getBookSync(id: Int): Book {
        val book = bookDao.getBookWithPagesSync(id).toBook()
        return book[0]
    }

    override suspend fun archiveBook(bookId: Int): Int {
        val book = bookDao.getBook(bookId).copy(archived = true)
        val result = bookDao.updateBook(book)
        return if (result > 0) bookId else 0
    }

    override suspend fun activateBook(bookId: Int) {
        val book = bookDao.getBook(bookId).copy(archived = false)
        bookDao.updateBook(book)
    }

    override suspend fun addBook(title: String, pages: Int): Boolean {
        val book = BookEntry(title = title, pages = pages)
        val result = bookDao.insertBook(book)
        return result > 0
    }

    override suspend fun addPageEntry(bookId: Int, date: Date, page: Int): Boolean {
        val pageEntry = PageEntry(bookId = bookId, date = date, page = page)
        val result = pagesDao.insertPages(pageEntry)
        return result > 0
    }

}

fun BookRepositoryResponse.toBook() : List<Book> {
    return map { item ->
        item.key.toBook(item.value.map { bookmark ->
            bookmark.toPageBookmark()
        })
    }
}