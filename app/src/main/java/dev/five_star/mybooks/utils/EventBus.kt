package dev.five_star.mybooks.utils

import kotlinx.coroutines.flow.SharedFlow

interface EventBus<T> {
    val events: SharedFlow<T>
    suspend fun invokeEvent(event: T)
}
