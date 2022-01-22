package dev.five_star.mybooks.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {

    @Query("SELECT * FROM book_table")
    fun getAllBooks(): Flow<List<BookEntry>>

    @Query(
        "SELECT * " +
                "FROM book_table " +
                "LEFT JOIN pages_table ON book_table.id = pages_table.bookId"
    )
    fun getAllBooksWithPages(): Flow<BookRepositoryResponse>

    @Query(
        "SELECT * " +
                "FROM book_table " +
                "LEFT JOIN pages_table ON book_table.id = pages_table.bookId " +
                "WHERE book_table.id = :bookId "
    )
    fun getBookWithPages(bookId: Int): Flow<BookRepositoryResponse>


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBook(book: BookEntry): Long

    @Update
    suspend fun updateBook(book: BookEntry): Int

    @Delete
    suspend fun deleteBook(book: BookEntry): Int

}

public typealias BookRepositoryResponse = Map<BookEntry, List<PageEntry>>