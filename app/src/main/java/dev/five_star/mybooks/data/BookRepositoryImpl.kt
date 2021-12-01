package dev.five_star.mybooks.data

import dev.five_star.mybooks.database.BookDao
import dev.five_star.mybooks.database.BookEntry
import dev.five_star.mybooks.database.PageEntry
import dev.five_star.mybooks.database.PagesDao
import kotlinx.coroutines.flow.Flow

class BookRepositoryImpl(private val bookDao: BookDao, private val pagesDao: PagesDao) : BookRepository {

    override fun getAllBooks(): Flow<List<Book>> {
        return bookDao.getAllBooks()
    }

    override suspend fun getBook(id: Int): BookEntry {
        return bookDao.getBook(id)
    }


    override suspend fun addBook(book: BookEntry) : Boolean {
        val result = bookDao.insertBook(book)
        return result > 0
    }

    override suspend fun updateCurrentPage(id: Int, currentPage: Int) {
//        val book = bookDao.getBook(id)
//        book.currentPage = currentPage
//        bookDao.updateBook(book)
    }

//    //TODO store the book, or the id?
//    override fun getPagesForBook(book: Book): Flow<List<PageEntry>> {
//        return pagesDao.getPageForBook(book)
//    }

    override fun getPagesForBook(bookId: Int): Flow<List<PageEntry>> {
        return pagesDao.getPagesForBook(bookId)
    }

    override suspend fun getLastPageForBook(bookId: Int): Int {
        return pagesDao.getLastPageForBook(bookId) ?: 0
    }

    override suspend fun addPageEntry(pageEntry: PageEntry) {
        pagesDao.insertPages(pageEntry)
        updateCurrentPage(pageEntry.bookId, pageEntry.pages)
    }


}