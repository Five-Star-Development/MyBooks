package dev.five_star.mybooks.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Book::class], version = 1, exportSchema = false)
abstract class BookRoomDatabase : RoomDatabase() {

    abstract fun bookDao(): BookDao
}