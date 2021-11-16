package dev.five_star.mybooks.data

import dev.five_star.mybooks.database.Book
import dev.five_star.mybooks.database.PageEntry
import kotlinx.coroutines.flow.Flow

interface BookRepository {

    fun getAllBooks(): Flow<List<Book>>

    suspend fun getBook(id: Int): Book


    suspend fun addBook(book: Book) : Boolean

//    fun getPagesForBook(book: Book): Flow<List<PageEntry>>

    fun getPagesForBook(bookId: Int): Flow<List<PageEntry>>

    suspend fun getLastPageForBook(bookId: Int): Int

    suspend fun addPageEntry(pageEntry: PageEntry)

}