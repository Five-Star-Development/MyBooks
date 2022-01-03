package dev.five_star.mybooks.data

import android.util.Log
import dev.five_star.mybooks.database.BookDao
import dev.five_star.mybooks.database.BookEntry
import dev.five_star.mybooks.database.PageEntry
import dev.five_star.mybooks.database.PagesDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*

class BookRepositoryImpl(private val bookDao: BookDao, private val pagesDao: PagesDao) :
    BookRepository {

    override fun getAllBooks(): Flow<List<Book>> {

        val bookMap = bookDao.getAllBooksWithPages()
        return bookMap.map {
            Log.d("BookRepository", it.toString())
            it.map { item ->
                item.key.toBook(item.value.map { bookmark ->
                    bookmark.toPageBookmark()
                })
            }
        }
    }

    override fun getBook(id: Int): Flow<Book> {
        val book = bookDao.getBook(id).map {
            it.map { item ->
                item.key.toBook(item.value.map { bookmark ->
                    bookmark.toPageBookmark()
                })
            }.first()
        }
        return book
    }

    override suspend fun addBook(title: String, pages: Int): Boolean {
        val book = BookEntry(title = title, pages = pages)
        val result = bookDao.insertBook(book)
        return result > 0
    }

    override suspend fun addPageEntry(bookId: Int, date: Date, page: Int): Boolean {
        val pageEntry = PageEntry(bookId = bookId, date = Date(), page = page)
        val result = pagesDao.insertPages(pageEntry)
        return result > 0
    }
}