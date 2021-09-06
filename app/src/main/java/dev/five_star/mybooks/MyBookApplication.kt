package dev.five_star.mybooks

import android.app.Application
import dev.five_star.mybooks.data.BookRepository
import dev.five_star.mybooks.database.BookRoomDatabase

class MyBookApplication : Application() {

    val database by lazy { BookRoomDatabase.getDatabase(this) }
    val bookRepository by lazy { BookRepository(database.bookDao()) }
}