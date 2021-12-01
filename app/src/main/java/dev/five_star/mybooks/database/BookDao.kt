package dev.five_star.mybooks.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {

    @Query("SELECT * FROM book_table")
    fun getAllBooks(): Flow<List<BookEntry>>

    @Query("SELECT * FROM book_table WHERE id = :bookId")
    suspend fun getBook(bookId: Int) : BookEntry

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBook(book: BookEntry) : Long

    @Update
    suspend fun updateBook(book: BookEntry) : Int

    @Delete
    suspend fun deleteBook(book: BookEntry) : Int

}