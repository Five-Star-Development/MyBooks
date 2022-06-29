package dev.five_star.mybooks.utils

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

class EventBusImpl<T> @Inject constructor() : EventBus<T> {

    private val _events = MutableSharedFlow<T>()

    override val events: SharedFlow<T>
        get() = _events.asSharedFlow()

    override suspend fun invokeEvent(event: T) {
        _events.emit(event)
    }
}