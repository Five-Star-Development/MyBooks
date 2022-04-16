package dev.five_star.mybooks.data

import kotlinx.coroutines.flow.Flow
import java.util.*

interface BookRepository {

    fun getAllBooks(): Flow<List<Book>>

    fun getBook(id: Int): Flow<Book>

    suspend fun getBookSync(id: Int): Book

    suspend fun addBook(title: String, pages: Int) : Boolean

    suspend fun archiveBook(bookId: Int) : Int

    suspend fun activateBook(bookId: Int)

    suspend fun addPageEntry(bookId: Int, date: Date, page: Int) : Boolean

}