package dev.five_star.mybooks.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PagesDao {

    @Query("SELECT * FROM pages_table WHERE bookId = :bookId")
    fun getPagesForBook(bookId: Int): Flow<List<PageEntry>>

    @Query("SELECT pages FROM pages_table WHERE bookId = :bookId ORDER BY pages DESC LIMIT 1")
    suspend fun getLastPageForBook(bookId: Int): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPages(pageEntry: PageEntry) : Long


}