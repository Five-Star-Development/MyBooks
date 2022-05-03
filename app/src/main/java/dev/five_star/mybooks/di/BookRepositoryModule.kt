package dev.five_star.mybooks.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.five_star.mybooks.data.BookRepository
import dev.five_star.mybooks.data.BookRepositoryImpl

@InstallIn(SingletonComponent::class)
@Module
abstract class BookRepositoryModule {

    @Binds
    abstract fun bindBookRepository(impl: BookRepositoryImpl) : BookRepository
}