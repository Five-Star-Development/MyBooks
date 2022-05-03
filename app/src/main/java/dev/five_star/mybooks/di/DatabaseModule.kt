package dev.five_star.mybooks.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.five_star.mybooks.database.BookDao
import dev.five_star.mybooks.database.BookRoomDatabase
import dev.five_star.mybooks.database.PagesDao
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    fun provideBookDao(database: BookRoomDatabase) : BookDao {
        return database.bookDao()
    }

    @Provides
    fun providePagesDao(database: BookRoomDatabase) : PagesDao {
        return database.pagesDao()
    }

    @Provides
    @Singleton
    fun provideBookDatabase(@ApplicationContext context: Context) : BookRoomDatabase {
        return Room.databaseBuilder(
            context,
            BookRoomDatabase::class.java,
            "book_database"
        ).build()
    }
}