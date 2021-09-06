package dev.five_star.mybooks.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {

    @Query("SELECT * FROM book_table")
    fun getAllBooks(): Flow<List<Book>>

    @Query("SELECT * FROM book_table WHERE id = :bookId")
    fun getBook(bookId: Int) : Book

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(book: Book)

    @Query("DELETE FROM book_table WHERE id = :bookId")
    suspend fun deleteBook(bookId: Int)

}