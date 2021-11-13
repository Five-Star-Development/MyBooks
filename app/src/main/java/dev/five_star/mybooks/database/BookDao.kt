package dev.five_star.mybooks.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {

    @Query("SELECT * FROM book_table")
    fun getAllBooks(): Flow<List<Book>>

    @Query("SELECT * FROM book_table WHERE id = :bookId")
    suspend fun getBook(bookId: Int) : Book

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBook(book: Book) : Long

    @Update
    suspend fun updateBook(book: Book) : Int

    @Delete
    suspend fun deleteBook(book: Book) : Int

}