package dev.five_star.mybooks.data

import kotlinx.coroutines.flow.Flow
import java.util.*

interface BookRepository {

    fun getAllBooks(): Flow<List<Book>>

    fun getBook(id: Int): Flow<Book>

    suspend fun addBook(title: String, pages: Int) : Boolean

    suspend fun addPageEntry(bookId: Int, date: Date, page: Int) : Boolean

}