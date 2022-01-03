package dev.five_star.mybooks.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.five_star.mybooks.utils.Converters

@Database(
    entities = [BookEntry::class, PageEntry::class],
    version = 1,
    exportSchema = true,
//    autoMigrations = [
//        AutoMigration(from = 1, to = 2),
//        AutoMigration(from = 2, to = 3)
//    ]
)

@TypeConverters(Converters::class)
abstract class BookRoomDatabase : RoomDatabase() {

    abstract fun bookDao(): BookDao

    abstract fun pagesDao(): PagesDao

}