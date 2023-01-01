package dev.five_star.mybooks

import android.app.Activity
import android.app.Application
import androidx.fragment.app.Fragment
import androidx.room.Room
import dagger.hilt.android.HiltAndroidApp
import dev.five_star.mybooks.data.BookRepository
import dev.five_star.mybooks.data.BookRepositoryImpl
import dev.five_star.mybooks.database.BookRoomDatabase

@HiltAndroidApp
class MyBookApplication : Application() {

    private val database by lazy {
        Room.databaseBuilder(
            this,
            BookRoomDatabase::class.java,
            "book_database"
        ).build()
    }

    val bookRepository: BookRepository by lazy {
        BookRepositoryImpl(database.bookDao(), database.pagesDao())
    }
}

fun Fragment.requireMyBookApplication() = requireActivity().application as MyBookApplication

fun Activity.requireMyBookApplication() = application as MyBookApplication
