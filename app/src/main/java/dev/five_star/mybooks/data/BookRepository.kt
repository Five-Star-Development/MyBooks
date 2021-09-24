package dev.five_star.mybooks.data

import dev.five_star.mybooks.database.Book
import dev.five_star.mybooks.model.PagesEntry
import kotlinx.coroutines.flow.Flow

interface BookRepository {

    fun getAllBooks(): Flow<List<Book>>

    suspend fun getBook(id: Int): Book


    suspend fun addBook(book: Book) : Boolean

    fun getPagesForBook(book: Book): List<PagesEntry>

    fun getPagesForBook(bookId: Int): List<PagesEntry>

    fun addPageEntry(enteredPage: String)

}