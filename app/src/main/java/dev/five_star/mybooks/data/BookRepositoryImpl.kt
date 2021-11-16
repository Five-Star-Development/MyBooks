package dev.five_star.mybooks.data

import dev.five_star.mybooks.database.Book
import dev.five_star.mybooks.database.BookDao
import dev.five_star.mybooks.database.PageEntry
import dev.five_star.mybooks.database.PagesDao
import dev.five_star.mybooks.model.PagesEntry
import kotlinx.coroutines.flow.Flow

class BookRepositoryImpl(private val bookDao: BookDao, private val pagesDao: PagesDao) : BookRepository {

    private val pageList = mutableListOf(
        PagesEntry("13.03.2021", "13"),
        PagesEntry("14.03.2021", "35"),
        PagesEntry("15.03.2021", "67"),
        PagesEntry("16.03.2021", "89"),
        PagesEntry("17.03.2021", "111"),
    )


    override fun getAllBooks(): Flow<List<Book>> {
        return bookDao.getAllBooks()
    }

    override suspend fun getBook(id: Int): Book {
        return bookDao.getBook(id)
    }


    override suspend fun addBook(book: Book) : Boolean {
        val result = bookDao.insertBook(book)
        return result > 0
    }

//    //TODO store the book, or the id?
//    override fun getPagesForBook(book: Book): Flow<List<PageEntry>> {
//        return pagesDao.getPageForBook(book)
//    }

    override fun getPagesForBook(bookId: Int): Flow<List<PageEntry>> {
        return pagesDao.getPagesForBook(bookId)
    }

    override suspend fun getLastPageForBook(bookId: Int): Int {
        return pagesDao.getLastPageForBook(bookId)
    }

    override suspend fun addPageEntry(pageEntry: PageEntry) {
        pagesDao.insertPages(pageEntry)
    }


}