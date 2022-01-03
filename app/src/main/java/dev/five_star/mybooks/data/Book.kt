package dev.five_star.mybooks.data

import android.os.Parcelable
import dev.five_star.mybooks.database.BookEntry
import kotlinx.parcelize.Parcelize

@Parcelize
data class Book(
    val id: Int,
    val title: String,
    val pages: Int,
    val bookmarks: List<PageBookmark> = mutableListOf()
) : Parcelable

fun BookEntry.toBook(bookmarks: List<PageBookmark>): Book {
    return Book(id, title, pages, bookmarks)
}

