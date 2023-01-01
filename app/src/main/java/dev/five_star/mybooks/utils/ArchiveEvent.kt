package dev.five_star.mybooks.utils

sealed class ArchiveEvent {
    data class ArchiveBook(val bookId: Int) : ArchiveEvent()
    object CancelArchive : ArchiveEvent()
}
