package dev.five_star.mybooks.data

import dev.five_star.mybooks.database.PageEntry
import kotlinx.coroutines.flow.Flow

interface BookRepository {

    fun getAllBooks(): Flow<List<Book>>

    suspend fun getBook(id: Int): Book

    suspend fun addBook(book: Book) : Boolean

    //TODO refector this one too
    suspend fun addPageEntry(pageEntry: PageEntry)

}