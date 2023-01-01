package dev.five_star.mybooks

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.five_star.mybooks.data.toBook
import dev.five_star.mybooks.database.BookDao
import dev.five_star.mybooks.database.BookEntry
import dev.five_star.mybooks.database.BookRoomDatabase
import dev.five_star.mybooks.database.PagesDao
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DatabaseTest {

    companion object {
        const val TITLE = "Test Book Title"
        const val PAGES = 100

        val BOOK_LIST = listOf(
            BookEntry(title = "Title", pages = 1),
            BookEntry(title = "Title2", pages = 2),
            BookEntry(title = "Title3", pages = 3),
            BookEntry(title = "Title4", pages = 4),
            BookEntry(title = "Title5", pages = 5),
            BookEntry(title = "Title6", pages = 6),
        )
    }

    private lateinit var db: BookRoomDatabase
    private lateinit var bookDao: BookDao
    private lateinit var pagesDao: PagesDao

    @Before
    fun createDB() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, BookRoomDatabase::class.java).build()
        bookDao = db.bookDao()
        pagesDao = db.pagesDao()
    }

    @After
    fun closeDB() {
        db.close()
    }

    @Test
    fun insertBookTest() {
        val bookEntry = BookEntry(title = TITLE, pages = PAGES)
        runBlocking {
            val result = bookDao.insertBook(bookEntry)
            assert(result > 0L)
        }
    }

    @Test
    fun insertBooksTest() {
        BOOK_LIST.forEach {
            runBlocking {
                val result = bookDao.insertBook(it)
                assert(result > 0L)
            }
        }
    }

    @Test
    fun getBookTest() {
        runBlocking {
            insertBookTest()
            val result = bookDao.getAllBooks().first()
            assert(result.isNotEmpty())
            assert(result[0].title == TITLE)
            assert(result[0].pages == PAGES)
        }
    }

    @Test(expected = ClassCastException::class)
    fun getAllBookTest() = runBlocking {
        insertBookTest()
        val bookMap: BookRepositoryResponse = bookDao.getAllBooksWithPages().first()
        val bookList = bookMap.toBook()
        assert(bookList.isNotEmpty())
        assert(bookList[0].title == TITLE)
        assert(bookList[0].pages == PAGES)
        assert(bookList[0].bookmarks.isEmpty())
    }
}
