package dev.five_star.mybooks.booklist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dev.five_star.mybooks.data.Book
import dev.five_star.mybooks.utils.ArchiveEvent
import dev.five_star.mybooks.utils.EventBus
import kotlinx.coroutines.launch

class ArchiveViewModel @AssistedInject constructor(
    private var eventBus: EventBus<ArchiveEvent>,
    @Assisted private var book: Book
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(book: Book) : ArchiveViewModel
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun provideFactory(assistedFactory: Factory, book: Book): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return assistedFactory.create(book) as T
                }
            }
    }

    fun accept() {
        viewModelScope.launch {
            eventBus.invokeEvent(ArchiveEvent.ArchiveBook(book.id))
        }
    }

    fun cancel() {
        viewModelScope.launch {
            Log.d("ArchiveViewModel", "cancel")
            eventBus.invokeEvent(ArchiveEvent.CancelArchive)
        }
    }
}