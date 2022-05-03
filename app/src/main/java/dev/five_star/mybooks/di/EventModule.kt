package dev.five_star.mybooks.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.five_star.mybooks.utils.ArchiveEvent
import dev.five_star.mybooks.utils.EventBus
import dev.five_star.mybooks.utils.EventBusImpl
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class EventModule {

    @Singleton
    @Binds
    abstract fun bindEvent(event: EventBusImpl<ArchiveEvent>) : EventBus<ArchiveEvent>
}